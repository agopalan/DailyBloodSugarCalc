package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class DisplayGraphsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        GraphView bloodsugargraph = (GraphView) findViewById(R.id.bloodsugargraph);
        bloodsugargraph.addSeries(UpdateGraph.series_bloodsugar);
        //set axis labels
        bloodsugargraph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");
        bloodsugargraph.getGridLabelRenderer().setVerticalAxisTitle("Blood Sugar");


        //set up glycation graph
        GraphView glycationgraph = (GraphView) findViewById(R.id.glycationgraph);
        glycationgraph.addSeries(UpdateGraph.series_glycation);
        //set axis labels
        glycationgraph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");
        glycationgraph.getGridLabelRenderer().setVerticalAxisTitle("Glycation");
    }
}
