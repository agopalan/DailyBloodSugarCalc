package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class DisplayGraphsActivity extends Activity {
    final int MAX_X_BS = 24;
    final int MIN_X_BS = 0;
    final int MAX_Y_BS = 300;
    final int MIN_Y_BS = 0;
    final int MAX_X_G = 24;
    final int MIN_X_G = 0;
    final int MAX_Y_G = 1440;
    final int MIN_Y_G = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        //set up blood sugar graph
        GraphView bloodsugargraph = (GraphView) findViewById(R.id.bloodsugargraph);
        LineGraphSeries<DataPoint> series_bloodsugar = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        bloodsugargraph.addSeries(series_bloodsugar);
        //set min and max x-axis and y-axis values
        bloodsugargraph.getViewport().setMaxX(MAX_X_BS);
        bloodsugargraph.getViewport().setMinX(MIN_X_BS);
        bloodsugargraph.getViewport().setMinY(MIN_Y_BS);
        bloodsugargraph.getViewport().setMaxY(MAX_Y_BS);
        bloodsugargraph.getViewport().setYAxisBoundsManual(true);
        bloodsugargraph.getViewport().setXAxisBoundsManual(true);
        //set axis labels
        bloodsugargraph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");
        bloodsugargraph.getGridLabelRenderer().setVerticalAxisTitle("Blood Sugar");


        //set up glycation graph
        GraphView glycationgraph = (GraphView) findViewById(R.id.glycationgraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        glycationgraph.addSeries(series);
        //set min and max x-axis and y-axis values
        glycationgraph.getViewport().setMaxX(MAX_X_G);
        glycationgraph.getViewport().setMinX(MIN_X_G);
        glycationgraph.getViewport().setMinY(MIN_Y_G);
        glycationgraph.getViewport().setMaxY(MAX_Y_G);
        glycationgraph.getViewport().setYAxisBoundsManual(true);
        glycationgraph.getViewport().setXAxisBoundsManual(true);
        //set axis labels
        glycationgraph.getGridLabelRenderer().setHorizontalAxisTitle("Hour");
        glycationgraph.getGridLabelRenderer().setVerticalAxisTitle("Glycation");
    }
}
