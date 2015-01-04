package com.example.ambika.dailybloodsugarcalc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

//if device is rebooted - will take care of setting alarm
public class DeviceBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //resets values by calling AlarmReceiver class
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            //alarm manager to set alarm if device is rebooted
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            //create a calendar for midnight
            Calendar todayMidnight = Calendar.getInstance();
            todayMidnight.add(Calendar.DATE, 1);
            todayMidnight.set(Calendar.HOUR_OF_DAY, 0);
            todayMidnight.set(Calendar.MINUTE, 0);
            todayMidnight.set(Calendar.SECOND, 0);

            //set repeating alarm for midnight every night
            manager.setRepeating(AlarmManager.RTC_WAKEUP, todayMidnight.getTimeInMillis(),
                    TimeUnit.DAYS.toMillis(1), pendingIntent);

        }
    }
}
