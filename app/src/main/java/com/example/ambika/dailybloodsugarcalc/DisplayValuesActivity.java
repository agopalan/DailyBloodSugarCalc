package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayValuesActivity extends Activity {
    Button graphButton;
    final static int LOW_BLOOD_SUGAR = 25;
    final static int HIGH_BLOOD_SUGAR = 150;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.values_layout);
        //check blood sugar limit to display message
        checkBloodSugarLimit();
        //set list view format
        ListView listView = (ListView) findViewById(R.id.listView);
        ListViewAdapter adapter = new ListViewAdapter(this);

        //set adapter
        listView.setAdapter(adapter);

        //set up view graphs button
        graphButton = (Button) findViewById(R.id.graphButton);

        //"view graphs" button is clicked for viewing graphs
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayGraphsActivity.class);
                startActivity(i);
            }
        });
    }

    public void checkBloodSugarLimit(){

        //check for low/high blood sugar
        if(UpdateGraph.newBloodSugar <= LOW_BLOOD_SUGAR){
            Toast.makeText(DisplayValuesActivity.this, "DANGER! BLOOD SUGAR TOO LOW", Toast.LENGTH_LONG).show();
        }
        if(UpdateGraph.newBloodSugar >= HIGH_BLOOD_SUGAR ){
            Toast.makeText(DisplayValuesActivity.this, "DANGER! BLOOD SUGAR TOO HIGH", Toast.LENGTH_LONG).show();
        }
    }
}
