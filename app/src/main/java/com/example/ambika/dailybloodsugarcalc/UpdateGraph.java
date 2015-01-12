package com.example.ambika.dailybloodsugarcalc;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.achartengine.model.XYSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateGraph {
    //constants
    public static final int FOOD_HR = 2;
    public static final int ONE_HOUR = 3600000;
    public static final int TWO_HOUR = 7200000;
    public static final int ONE_MIN = 60000;
    public static final int TEN_SEC_HOUR = 1/360;
    public static final int GLYCATION_LIMIT = 150;
    public static final int INCREMENT = 60;
    public static int counter = 0;
    public static Calendar now = Calendar.getInstance();
    public static ArrayList<Long> x_values = new ArrayList<Long>(){};

    //blood sugar graph
    public static float slope = 0;
    public static XYSeries series_bloodsugar = new XYSeries("Blood Sugar");
    //public static long currTime = UpdateGraph.now.get(Calendar.HOUR_OF_DAY);
    public static float currBloodSugar = MainActivity.INITIAL_BLOOD_SUGAR;
    public static float newBloodSugar = 0;
    public static int timer_counter = 0;
    public static ArrayList<CountDownTimer> listoftimers = new ArrayList<CountDownTimer>();

    //glycation graph
    public static XYSeries series_glycation = new XYSeries("Glycation");
    public static int glycation = 0;


    public static void initializeGraph(){
        //set up blood sugar graph
        UpdateGraph.series_bloodsugar.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                        (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT),
                MainActivity.INITIAL_BLOOD_SUGAR);

        Log.d("firstbsxactual", "" + (float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT));
        Log.d("firstbsx", "" + UpdateGraph.series_bloodsugar.getX(0));
        Log.d("firstbsyactual", "" + MainActivity.INITIAL_BLOOD_SUGAR);
        Log.d("firstbsy", "" + UpdateGraph.series_bloodsugar.getY(0));

        UpdateGraph.x_values.add(System.currentTimeMillis());

        //set up glycation graph
        UpdateGraph.series_glycation.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT),
                MainActivity.INITIAL_GLYCATION);
    }

    public static void updateGraph(){
        //when timer interrupt for every min returns
        //get current m, use to calculate new y from previous x and y (from previous event)
        //save new y

        UpdateGraph.newBloodSugar=((UpdateGraph.slope/ONE_HOUR) * (System.currentTimeMillis()-
                (float)UpdateGraph.x_values.get(UpdateGraph.counter)))+
            UpdateGraph.currBloodSugar;

        Log.d("bs", "" + ((UpdateGraph.slope/ONE_HOUR) * (System.currentTimeMillis()-
                (float)UpdateGraph.x_values.get(UpdateGraph.counter)))+
                UpdateGraph.currBloodSugar);
        Log.d("computation", "" + ((System.currentTimeMillis()-
                (float)UpdateGraph.x_values.get(UpdateGraph.counter))));


        UpdateGraph.x_values.add(System.currentTimeMillis());

        UpdateGraph.counter++;

        //compare to 150
        //if greater or equal to - add one to new y value and add to series
        //otherwise add another value to series to plot glycation
        if(UpdateGraph.newBloodSugar >= GLYCATION_LIMIT){
            UpdateGraph.glycation = UpdateGraph.glycation++;
        }

        //add (current time in hours, current y value) to series
        UpdateGraph.series_bloodsugar.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                        (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT),
                UpdateGraph.newBloodSugar);

        Log.d("bsxactual", "" + (float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT));
        Log.d("bsx", "" + UpdateGraph.series_bloodsugar.getX(UpdateGraph.counter));
        Log.d("yactual", "" + UpdateGraph.newBloodSugar);
        Log.d("bsy", "" + UpdateGraph.series_bloodsugar.getY(UpdateGraph.counter));
        Log.d("slope", "" + UpdateGraph.slope);


        //add glycation value to series
        UpdateGraph.series_glycation.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                        (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT),
                UpdateGraph.glycation);

        //update current blood sugar value
        UpdateGraph.currBloodSugar = UpdateGraph.newBloodSugar;

    }

    public static void updateGraphWithIndex(final int index, String typeOfInput){
        //call updateGraph
        //updateGraph();
        //update previous x (current time) and y (use previous slope) - done in updateGraph
        // 4 conditions - exercise input, food input, exercise timer, food timer
        // exercise input - subtract index from current slope, add timer+index to data structure
        if(typeOfInput == "exercise"){
            UpdateGraph.slope = UpdateGraph.slope - index;
            updateGraph();
            CountDownTimer timer = new CountDownTimer(ONE_HOUR, ONE_HOUR) {
                int temp_index = index;
                @Override
                public void onTick(long millisUntilFinished) {

                }
                //exercise timer - add modifier (associated with timer in data structure) to slope, delete entry
                @Override
                public void onFinish() {
                    UpdateGraph.slope = UpdateGraph.slope + temp_index;
                    updateGraph();
                    UpdateGraph.timer_counter--;
                }
            }.start();
            UpdateGraph.timer_counter++;
            UpdateGraph.listoftimers.add(timer);
        }
        //food input - add index/2 to current slope, add timer + index to data structure
        else if(typeOfInput == "food"){
            UpdateGraph.slope = UpdateGraph.slope + (index/FOOD_HR);
            updateGraph();
            CountDownTimer timer = new CountDownTimer(TWO_HOUR, TWO_HOUR) {
                int temp_index = index;
                @Override
                public void onTick(long millisUntilFinished) {
                }
                //food timer - subtract modifier (associated with timer in data structure) to slope, delete entry
                @Override
                public void onFinish() {
                    UpdateGraph.slope = UpdateGraph.slope - temp_index;
                    updateGraph();
                    UpdateGraph.timer_counter--;
                }
            }.start();
            UpdateGraph.timer_counter++;
            UpdateGraph.listoftimers.add(timer);
        }
        //check for no timers
        //if empty, check to see if current y is greater than 80, if so, add -60 to slope, if not
        //add 60 to slope
        if(UpdateGraph.timer_counter==0){
            if(UpdateGraph.newBloodSugar > MainActivity.INITIAL_BLOOD_SUGAR){
                UpdateGraph.slope = UpdateGraph.slope - INCREMENT;
            } else{
                UpdateGraph.slope = UpdateGraph.slope + INCREMENT;
            }
        }

    }
}
