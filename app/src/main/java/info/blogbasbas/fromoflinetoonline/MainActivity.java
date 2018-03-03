package info.blogbasbas.fromoflinetoonline;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import info.blogbasbas.fromoflinetoonline.adapter.NameAdapter;
import info.blogbasbas.fromoflinetoonline.db.DatabaseHelper;
import info.blogbasbas.fromoflinetoonline.network.ApiClient;
import info.blogbasbas.fromoflinetoonline.network.ApiInterface;
import info.blogbasbas.fromoflinetoonline.pojo.Nama;
import info.blogbasbas.fromoflinetoonline.pojo.ResponseInsert;
import info.blogbasbas.fromoflinetoonline.pojo.getdata.DataItem;
import info.blogbasbas.fromoflinetoonline.pojo.getdata.ResponseGetData;
import info.blogbasbas.fromoflinetoonline.reciever.NetworkStateChecker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.buttonSave)
    Button buttonSave;
    @BindView(R.id.listViewNames)
    ListView listViewNames;

    private DatabaseHelper db;
    private List<Nama> names;
    private List<DataItem> dataFromServer;

    //magic number
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "info.blogbasbas.oflinetoonlineretrofit";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
    private NameAdapter nameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        db = new DatabaseHelper(this);
        names = new ArrayList<>();
        dataFromServer = new ArrayList<>();


        loadNames();

        broadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                loadNames();
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
    }

    private void loadNames() {
        names.clear();
        Cursor cursor= db.getNames();
        if (cursor.moveToFirst()){
            do {
                Nama name = new Nama(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            }while (cursor.moveToNext());
        }
        nameAdapter = new NameAdapter(this,R.layout.names,names);
        getdataFromServer();
        listViewNames.setAdapter(nameAdapter);
    }

    private void getdataFromServer() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Saving Name . . .");
        progressDialog.show();

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseGetData> call = service.getdata();
        call.enqueue(new Callback<ResponseGetData>() {
            @Override
            public void onResponse(Call<ResponseGetData> call, Response<ResponseGetData> response) {
                Log.d("", "data respon: "+new Gson().toJson(response).toString());
                //get data

                names.clear();
                progressDialog.dismiss();
                ResponseGetData getdataName = response.body();
                if (!getdataName.isError()){
                   List<Nama> dataNama = getdataName.getData();
                   for (int i = 0 ; i < dataNama.size();i++){
                       Nama newNamaFromServer = dataNama.get(i);
                       String namaFromServer = newNamaFromServer.getName();

                       //insert data
                       Nama namaModel = new Nama(namaFromServer,NAME_SYNCED_WITH_SERVER);
                       names.add(namaModel);


                   }
                    refreshList();
                }

            }

            @Override
            public void onFailure(Call<ResponseGetData> call, Throwable t) {
                Toast.makeText(MainActivity.this, " error get data"+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void refreshList(){
        nameAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.buttonSave)
    public void onViewClicked() {
        final String nama = editTextName.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Saving Name . . .");
        progressDialog.show();
        try {
            ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseInsert> call = service.insertName(nama);
            call.enqueue(new Callback<ResponseInsert>() {
                @Override
                public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {


                    boolean error = response.body().isError();
                    if (error == false) {
                        saveNameToLocalStorage(nama, NAME_SYNCED_WITH_SERVER);
                       //nuglang activity

                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "success insert data to server", Toast.LENGTH_SHORT).show();
                        Log.d("", "onResponse: "+error);
                    } else {
                        progressDialog.dismiss();
                        saveNameToLocalStorage(nama, NAME_NOT_SYNCED_WITH_SERVER);
                        Toast.makeText(MainActivity.this, "failed insert data to server", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseInsert> call, Throwable t) {
                    saveNameToLocalStorage(nama, NAME_NOT_SYNCED_WITH_SERVER);
                    progressDialog.dismiss();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, "Erro "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("", "onViewClicked: "+e.toString());
        }
    }
    private void saveNameToLocalStorage(String name, int status) {
        editTextName.setText("");
        db.addName(name,status);
        Nama n = new Nama(name,status);
        names.add(n);
        refreshList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menumain){
            refreshList();
            //ngulang activity
            finish();
           startActivity(getIntent());


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
