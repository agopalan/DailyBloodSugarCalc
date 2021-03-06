package com.example.ambika.dailybloodsugarcalc;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.achartengine.model.XYSeries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateGraph {
    //constants
    public static final int FOOD_HR = 2;
    public static final double ONE_HUNDRED = 100.0;
    public static final int ONE_HOUR = 3600000;
    public static final int TWO_HOUR = 7200000;
    public static final int GLYCATION_LIMIT = 150;
    public static final int INCREMENT = 60;
    public static int counter = 0;
    public static Calendar now = Calendar.getInstance();
    public static ArrayList<Long> x_values = new ArrayList<Long>(){};

    //blood sugar graph
    public static float slope = 0;
    public static XYSeries series_bloodsugar = new XYSeries("Blood Sugar");
    public static float currBloodSugar = MainActivity.INITIAL_BLOOD_SUGAR;
    public static float newBloodSugar = 0;
    public static int timer_counter = 0;
    public static ArrayList<CountDownTimer> listoftimers = new ArrayList<CountDownTimer>();

    //glycation graph
    public static XYSeries series_glycation = new XYSeries("Glycation");
    public static int glycation = 0;


    public static void initializeGraph(){
        //set value of new blood sugar to be initial value
        UpdateGraph.newBloodSugar = UpdateGraph.currBloodSugar;

        //set up blood sugar graph
        UpdateGraph.series_bloodsugar.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                        (float)(UpdateGraph.now.get(Calendar.MINUTE)/ONE_HUNDRED)),
                MainActivity.INITIAL_BLOOD_SUGAR);

        //keep track of milliseconds
        UpdateGraph.x_values.add(System.currentTimeMillis());

        //set up glycation graph
        UpdateGraph.series_glycation.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                (float)UpdateGraph.now.get(Calendar.MINUTE)/INCREMENT),
                MainActivity.INITIAL_GLYCATION);
    }

    public static void updateGraph(){
        //get current slope, use to calculate new y from previous x and y (from previous event)
        //save new y
        UpdateGraph.newBloodSugar=((UpdateGraph.slope/ONE_HOUR) * (System.currentTimeMillis()-
                (float)UpdateGraph.x_values.get(UpdateGraph.counter)))+
            UpdateGraph.currBloodSugar;

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
                        (float)(UpdateGraph.now.get(Calendar.MINUTE)/ONE_HUNDRED)),
                UpdateGraph.newBloodSugar);

        //add glycation value to series
        UpdateGraph.series_glycation.add((float)((UpdateGraph.now.get(Calendar.HOUR_OF_DAY))+
                        (float)(UpdateGraph.now.get(Calendar.MINUTE)/ONE_HUNDRED)),
                UpdateGraph.glycation);

        //update current blood sugar value
        UpdateGraph.currBloodSugar = UpdateGraph.newBloodSugar;

    }

    public static void updateGraphWithIndex(final int index, String typeOfInput){
        //update previous x (current time) and y (use previous slope) - done in updateGraph
        // 2 conditions - exercise input, food input
        // exercise input - subtract index from current slope, add timer+index to data structure
        if(typeOfInput == "exercise"){
            //update slope
            UpdateGraph.slope = UpdateGraph.slope - index;
            //call updategraph to update blood sugar value
            updateGraph();
            //create new timer for exercise input
            CountDownTimer timer = new CountDownTimer(ONE_HOUR, ONE_HOUR) {
                int temp_index = index;
                @Override
                public void onTick(long millisUntilFinished) {

                }
                //exercise timer - add modifier (associated with timer in data structure) to slope, delete entry
                @Override
                public void onFinish() {
                    //update slope when timer finishes
                    UpdateGraph.slope = UpdateGraph.slope + temp_index;
                    //update value of blood sugar
                    updateGraph();
                    //decrement data structure containing number of counters
                    UpdateGraph.timer_counter--;
                }
            }.start();
            //increment data structure containing number of counters
            UpdateGraph.timer_counter++;
            //add timer to data structure
            UpdateGraph.listoftimers.add(timer);
        }
        //food input - add index/2 to current slope, add timer + index to data structure
        else if(typeOfInput == "food"){
            //update slope
            UpdateGraph.slope = UpdateGraph.slope + (index/FOOD_HR);
            //update blood sugar value
            updateGraph();
            //create new time for food input
            CountDownTimer timer = new CountDownTimer(TWO_HOUR, TWO_HOUR) {
                int temp_index = index;
                @Override
                public void onTick(long millisUntilFinished) {
                }
                //food timer - subtract modifier (associated with timer in data structure) to slope, delete entry
                @Override
                public void onFinish() {
                    //update slope
                    UpdateGraph.slope = UpdateGraph.slope - temp_index;
                    //update blood sugar value
                    updateGraph();
                    //decrement data structure containing number of counters
                    UpdateGraph.timer_counter--;
                }
            }.start();
            //increment data structure containing number of counters
            UpdateGraph.timer_counter++;
            //add timer to data structure
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
