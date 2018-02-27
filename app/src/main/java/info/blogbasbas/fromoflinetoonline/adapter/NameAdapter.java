package info.blogbasbas.fromoflinetoonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.blogbasbas.fromoflinetoonline.R;
import info.blogbasbas.fromoflinetoonline.pojo.Nama;

/**
 * Created by User on 26/02/2018.
 */

public class NameAdapter extends ArrayAdapter<Nama> {
    private List<Nama> names;
    private Context context;

    public NameAdapter(Context context, int resource, List<Nama>names) {
        super(context, resource, names);
        this.context= context;
        this.names=names;

    }

    @Override
    public View getView (int position, View convertview, ViewGroup parent){
        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.names, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        Nama name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getName());

        if (name.getStatus()==0) {
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        }
            else
            imageViewStatus.setBackgroundResource(R.drawable.success);

            return listViewItem;
        }
    }

