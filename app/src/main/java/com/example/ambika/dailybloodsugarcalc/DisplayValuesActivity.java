package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class DisplayValuesActivity extends Activity {
    Button graphButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.values_layout);

        ListView listView = (ListView) findViewById(R.id.listView);

        ListViewAdapter adapter = new ListViewAdapter(this);

        listView.setAdapter(adapter);

        graphButton = (Button) findViewById(R.id.graphButton);

        //"submit" button is clicked for viewing values
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DisplayGraphsActivity.class);
                startActivity(i);
            }
        });
    }
}
