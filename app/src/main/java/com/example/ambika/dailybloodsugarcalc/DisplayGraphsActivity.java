package com.example.ambika.dailybloodsugarcalc;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.TouchHandler;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.Zoom;
import org.achartengine.tools.ZoomListener;

import java.util.Arrays;
import java.util.Calendar;


public class DisplayGraphsActivity extends Activity {
    final int MINY_BS = 0;
    final int MINX_BS = 0;
    final int MINY_G = 0;
    final int MINX_G = 0;
    final int SIZE = 18;
    final int LINE_WIDTH = 2;
    final int STROKE_WIDTH = 3;
    private XYMultipleSeriesDataset dataset_bloodsugar;
    private XYSeriesRenderer renderer_bloodsugar;
    private XYMultipleSeriesRenderer mRenderer_bloodsugar;
    private XYMultipleSeriesDataset dataset_glycation;
    private XYSeriesRenderer renderer_glycation;
    private XYMultipleSeriesRenderer mRenderer_glycation;
    private GraphicalView chartViewBS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        //blood sugar dataset
        dataset_bloodsugar = new XYMultipleSeriesDataset();
        dataset_bloodsugar.addSeries(UpdateGraph.series_bloodsugar);

        //glycation dataset
        dataset_glycation = new XYMultipleSeriesDataset();
        dataset_glycation.addSeries(UpdateGraph.series_glycation);

        setRenderer();

        setMultipleSeriesRenderer();

        //set up blood sugar graph
        chartViewBS = ChartFactory.getLineChartView(this, dataset_bloodsugar, mRenderer_bloodsugar);
        LinearLayout layout_BS = (LinearLayout) findViewById(R.id.bloodsugargraph);
        layout_BS.addView(chartViewBS);

        //set up glycation graph
        GraphicalView chartViewG = ChartFactory.getLineChartView(this, dataset_glycation, mRenderer_glycation);
        LinearLayout layout_G = (LinearLayout) findViewById(R.id.glycationgraph);
        layout_G.addView(chartViewG);
    }

    public void setRenderer(){

        //set up blood sugar
        renderer_bloodsugar = new XYSeriesRenderer();
        renderer_bloodsugar.setLineWidth(LINE_WIDTH);
        renderer_bloodsugar.setColor(Color.WHITE);
        renderer_bloodsugar.setDisplayBoundingPoints(true);
        renderer_bloodsugar.setPointStyle(PointStyle.CIRCLE);
        renderer_bloodsugar.setPointStrokeWidth(STROKE_WIDTH);
        renderer_bloodsugar.setDisplayChartValues(true);
        renderer_bloodsugar.setChartValuesTextSize(SIZE);

        //set up glycation
        renderer_glycation = new XYSeriesRenderer();
        renderer_glycation.setLineWidth(LINE_WIDTH);
        renderer_glycation.setColor(Color.WHITE);
        renderer_glycation.setDisplayBoundingPoints(true);
        renderer_glycation.setPointStyle(PointStyle.CIRCLE);
        renderer_glycation.setPointStrokeWidth(STROKE_WIDTH);
        renderer_glycation.setDisplayChartValues(true);
        renderer_glycation.setChartValuesTextSize(SIZE);

    }

    public void setMultipleSeriesRenderer(){
        //set up blood sugar
        mRenderer_bloodsugar = new XYMultipleSeriesRenderer();
        mRenderer_bloodsugar.addSeriesRenderer(renderer_bloodsugar);
        mRenderer_bloodsugar.setAxisTitleTextSize(SIZE);
        mRenderer_bloodsugar.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer_bloodsugar.setXAxisMax(UpdateGraph.series_bloodsugar.getMaxX());
        mRenderer_bloodsugar.setXAxisMin(MINX_BS);
        mRenderer_bloodsugar.setYAxisMax(UpdateGraph.series_bloodsugar.getMaxY());
        mRenderer_bloodsugar.setYAxisMin(MINY_BS);
        mRenderer_bloodsugar.setXTitle("Time (Hours)");
        mRenderer_bloodsugar.setYTitle("Blood Sugar", 0);
        mRenderer_bloodsugar.setShowGrid(true);
        mRenderer_bloodsugar.setLabelsTextSize(SIZE);
        mRenderer_bloodsugar.setLegendTextSize(SIZE);
        mRenderer_bloodsugar.setAxesColor(Color.WHITE);
        mRenderer_bloodsugar.setApplyBackgroundColor(true);
        mRenderer_bloodsugar.setBackgroundColor(Color.BLACK);

        //set up glycation
        mRenderer_glycation = new XYMultipleSeriesRenderer();
        mRenderer_glycation.addSeriesRenderer(renderer_glycation);
        mRenderer_glycation.setAxisTitleTextSize(SIZE);
        mRenderer_glycation.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer_glycation.setXAxisMax(UpdateGraph.series_glycation.getMaxX());
        mRenderer_glycation.setXAxisMin(MINX_G);
        mRenderer_glycation.setYAxisMax(UpdateGraph.series_glycation.getMaxY());
        mRenderer_glycation.setYAxisMin(MINY_G);
        mRenderer_glycation.setXTitle("Time (Hours)");
        mRenderer_glycation.setYTitle("Glycation", 0);
        mRenderer_glycation.setShowGrid(true);
        mRenderer_glycation.setLabelsTextSize(SIZE);
        mRenderer_glycation.setLegendTextSize(SIZE);
        mRenderer_glycation.setAxesColor(Color.WHITE);
        mRenderer_glycation.setApplyBackgroundColor(true);
        mRenderer_glycation.setBackgroundColor(Color.BLACK);
    }

    }
