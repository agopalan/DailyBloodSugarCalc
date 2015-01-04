package com.example.ambika.dailybloodsugarcalc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    final int NUM_CHAR = 2;
    final int INITIAL_BLOOD_SUGAR = 80;
    public static int currBloodSugar;
    private PendingIntent pendingIntent;

    ArrayAdapter<String> exerciseAdapter;
    ArrayAdapter<String> foodAdapter;
    Button exerciseButton;
    Button foodButton;
    Button graphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent resets values every midnight
        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        //start alarm to go off every midnight and reset values
        start();

        //-------METHOD TO ACCESS STRAIGHT FROM GOOGLE DOCS ---------------
        String databaseKeys[] = {"1XBl8AvLRoycm034Rh-lMoe_pGHnY14DCtZToBnz4v-w",
                "16Zz2hk6fD0LvbdeODpr_02s5hVclpvbZtkOt4-T_FEM"};
        BuildDB.main(databaseKeys);

        setViews();

        //exercise search box
        final AutoCompleteTextView autoTV1= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        autoTV1.setThreshold(NUM_CHAR);
        autoTV1.setAdapter(exerciseAdapter);

        //food search box
        final AutoCompleteTextView autoTV2= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        autoTV2.setThreshold(NUM_CHAR);
        autoTV2.setAdapter(foodAdapter);

        //"submit" button is clicked for exercise input
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateGraph.updateSlope(BuildDB.exerciseEntries.get(autoTV1.getText().toString()), "exercise");
                autoTV1.setText("");

            }
        });

        //"submit" button is clicked for food input
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateGraph.updateSlope(BuildDB.foodEntries.get(autoTV2.getText().toString()), "food");
                autoTV2.setText("");
            }
        });

        //"submit" button is clicked for food input
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayGraphsActivity.class);
                startActivity(i);
            }
        });
    }

    //set up initial views and values
    public void setViews(){
        //exercise search box
        exerciseAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, BuildDB.listofexercises);
        //food search box
        foodAdapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, BuildDB.listoffoods);
        //exercise submit button
        exerciseButton = (Button) findViewById(R.id.exerciseButton);
        //food submit button
        foodButton = (Button) findViewById(R.id.foodButton);
        //view graph button
        graphButton = (Button) findViewById(R.id.graphButton);
        //initial value of blood sugar
        currBloodSugar = INITIAL_BLOOD_SUGAR;
    }

    //start alarm to go off at midnight
    public void start() {
        //alarm manager to set alarm
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
