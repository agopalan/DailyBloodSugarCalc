package com.example.ambika.dailybloodsugarcalc;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ListViewAdapter extends BaseAdapter implements DialogInterface.OnClickListener {

    public static final int ONE_DAY = 12;
    private Context context;
    //constructor
    public ListViewAdapter(Context context) {
        this.context = context;
    }
    // not using these functions
    public int getCount() {
        return UpdateGraph.series_bloodsugar.getItemCount();
    }
    // not using these functions
    public Object getItem(int position) {
        return null;
    }
    // not using these functions
    public long getItemId(int position) {
        return position;
    }

    //set view format
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bloodsugarvalues, null);
        }

        //set time view
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvTime.setText(String.format("%02d:%02d", (int)((UpdateGraph.series_bloodsugar.getX(position))%ONE_DAY),
                TimeUnit.MILLISECONDS.toMinutes(UpdateGraph.x_values.get(position)) - TimeUnit.HOURS.toMinutes
                        (TimeUnit.MILLISECONDS.toHours(UpdateGraph.x_values.get(position)))));

        //set blood sugar view
        TextView tvBS = (TextView) convertView.findViewById(R.id.tvBS);
        tvBS.setText(String.valueOf(new DecimalFormat("#.##").format(UpdateGraph.series_bloodsugar.getY(position))));

        //set glycation view
        TextView tvG = (TextView) convertView.findViewById(R.id.tvG);
        tvG.setText(String.valueOf(UpdateGraph.series_glycation.getY(position)));
        return convertView;
    }

    //not using function
    @Override
    public void onClick(DialogInterface dialogInterface, int x){
    }
}
