package com.example.ambika.dailybloodsugarcalc;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter implements DialogInterface.OnClickListener {

    private Context context;

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

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bloodsugarvalues, null);
        }

        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        tvTime.setText(String.valueOf(UpdateGraph.series_bloodsugar.getX(position)));

        TextView tvBS = (TextView) convertView.findViewById(R.id.tvBS);
        tvBS.setText(String.valueOf(UpdateGraph.series_bloodsugar.getY(position)));
        return convertView;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int x){

    }
}
