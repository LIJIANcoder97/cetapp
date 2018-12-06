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
 * Created by Administrator on 2018/6/11 0011.
 */

public class mplanAdapter extends ArrayAdapter<planbein> {
    private int resourceid;

    public mplanAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<planbein> objects) {
        super(context,  textViewResourceId, objects);
        resourceid=textViewResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        planbein planb = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        TextView plan = (TextView)view.findViewById(R.id.Layout_plan);
        TextView local = (TextView)view.findViewById(R.id.Layout_loca);
        plan.setText(planb.getPlan());
        local.setText(planb.getLocality());
        return view;
    }
}
