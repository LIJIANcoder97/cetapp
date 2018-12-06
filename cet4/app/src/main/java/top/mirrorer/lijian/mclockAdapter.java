package top.mirrorer.lijian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class mclockAdapter extends ArrayAdapter<TimeclockBine> {
    private int resourceid;

    public mclockAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<TimeclockBine> objects) {
        super(context,  textViewResourceId, objects);
        resourceid=textViewResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TimeclockBine bein = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        TextView name = (TextView)view.findViewById(R.id.clockmode_name);
        TextView time = (TextView)view.findViewById(R.id.clockmode_time);
        time.setText(bein.getYear()+"."+(bein.getMonth()+1)+"."+bein.getDay()+"."+bein.getHour());
        name.setText(bein.getName());
        return view;
    }
}