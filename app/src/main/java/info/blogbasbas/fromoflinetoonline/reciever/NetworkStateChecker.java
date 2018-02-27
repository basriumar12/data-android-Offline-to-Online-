package info.blogbasbas.fromoflinetoonline.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import info.blogbasbas.fromoflinetoonline.MainActivity;
import info.blogbasbas.fromoflinetoonline.db.DatabaseHelper;
import info.blogbasbas.fromoflinetoonline.network.ApiClient;
import info.blogbasbas.fromoflinetoonline.network.ApiInterface;
import info.blogbasbas.fromoflinetoonline.pojo.ResponseInsert;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 27/02/2018.
 */

public class NetworkStateChecker extends BroadcastReceiver {
    private Context context;
    private DatabaseHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        db = new DatabaseHelper(context);

        //cek jaringan
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acNetworkInfo = cm.getActiveNetworkInfo();
        //cek jaringan
        if (acNetworkInfo !=null){
            if (acNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI || acNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){

                Cursor cursor = db.getUnsyncedNames();
                if (cursor.moveToFirst()){
                    do {
                        saveName(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
                        );
                    }
                    while (cursor.moveToNext());
                }
            }
        }
    }

    private void saveName(final int id, final String nama) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseInsert> call = service.insertName(nama);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                boolean error = response.body().isError();
                if (error == false) {
                    db.updateNameStatus(id, MainActivity.NAME_SYNCED_WITH_SERVER);

                    //sending the broadcast to refresh the list
                    context.sendBroadcast(new Intent(MainActivity.DATA_SAVED_BROADCAST));

                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }
}