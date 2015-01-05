package com.example.ambika.dailybloodsugarcalc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//resets all values when alarm goes off
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //clear data points on graph, reset values at midnight
        UpdateGraph.slope = 0;

        //resets all data in series
        DataPoint[] data = new DataPoint[] {new DataPoint (
                TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()),
                MainActivity.INITIAL_BLOOD_SUGAR)};
        UpdateGraph.series_bloodsugar.resetData(data);

        //clear out timer, modifier data structure
        UpdateGraph.timer_counter = 0;
        for(int i = 0; i < UpdateGraph.listoftimers.size(); i++){
            UpdateGraph.listoftimers.get(i).cancel();
        }
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }

}
