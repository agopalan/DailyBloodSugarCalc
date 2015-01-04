package com.example.ambika.dailybloodsugarcalc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

//resets all values when alarm goes off
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //clear data points on graph

        //reset all values at midnight
//        UpdateGraph.slope = 0;
//        UpdateGraph.prev_x = 0;
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }

}
