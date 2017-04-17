package com.feliperrm.wikiolap.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 22/03/2017.
 */

public class ChartUtil {

    public static final int BAR_CHART = 0;
    public static final int LINE_CHART = 1;

    public static View buildChart(Context context, ArrayList<ArrayList<XYHolder>> dataset, ChartMetadata chartMetadata) {

        View viewToReturn = LayoutInflater.from(context).inflate(R.layout.chart_with_titles, null);
        TextView xTitle = (TextView) viewToReturn.findViewById(R.id.xTitle);
        TextView yTitle = (TextView) viewToReturn.findViewById(R.id.yTitle);
        xTitle.setText(chartMetadata.getxTitle());
        yTitle.setText(chartMetadata.getyTitle());
        FrameLayout container = (FrameLayout) viewToReturn.findViewById(R.id.container);
        View chartView = null;

        if (chartMetadata.getChartType() == BAR_CHART) {
            ArrayList<String> labels = new ArrayList<>();
            BarChart barChart = new BarChart(context);
            List<IBarDataSet> sets = new ArrayList<>();
            int size = dataset.size();
            for (int i = 0 ; i<size ; i++){
                ArrayList<XYHolder> XYHolders = dataset.get(i);
                ArrayList<BarEntry> entries = new ArrayList<>();
                for (XYHolder xyHolder : XYHolders) {
                    BarEntry barEntry = new BarEntry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
                    entries.add(barEntry);
                    labels.add(xyHolder.getLabel());
                }
                String lbl;
                if(chartMetadata.getyAlias() != null && chartMetadata.getyAlias().get(i) != null){
                    lbl = chartMetadata.getyAlias().get(i);
                }
                else{
                    lbl = chartMetadata.getyColumnIds().get(i);
                }
                sets.add(new BarDataSet(entries, lbl));
            }
            BarData barData = new BarData(sets);

            barChart.setData(barData);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setDrawGridLines(chartMetadata.isDrawXLines());
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

            YAxis yAxis1 = barChart.getAxisLeft();
            YAxis yAxis2 = barChart.getAxisRight();
            yAxis1.setDrawGridLines(chartMetadata.isDrawYLines());
            yAxis2.setDrawGridLines(chartMetadata.isDrawYLines());

            barChart.getDescription().setText("");
            barChart.invalidate();
            barChart.setVisibility(View.VISIBLE);
            chartView = barChart;
        } else {
//            LineChart lineChart = new LineChart(context);
//            ArrayList<Entry> entries = new ArrayList<>();
//            ArrayList<String> labels = new ArrayList<>();
//            for (XYHolder xyHolder : dataset) {
//                Entry entry = new Entry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
//                entries.add(entry);
//                labels.add(xyHolder.getLabel());
//            }
//
//            LineDataSet lineDataSet = new LineDataSet(entries, (chartMetadata.getyColumnAlias() == null || chartMetadata.getyColumnAlias().isEmpty()) ? chartMetadata.getyColumnId() : chartMetadata.getyColumnAlias());
//
//            LineData lineData = new LineData(lineDataSet);
//
//            lineChart.setData(lineData);
//            XAxis xAxis = lineChart.getXAxis();
//            xAxis.setDrawGridLines(chartMetadata.isDrawXLines());
//            xAxis.setGranularity(1f);
//            xAxis.setGranularityEnabled(true);
//            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//            xAxis.setDrawLabels(true);
//            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//
//            YAxis yAxis1 = lineChart.getAxisLeft();
//            YAxis yAxis2 = lineChart.getAxisRight();
//            yAxis1.setDrawGridLines(chartMetadata.isDrawYLines());
//            yAxis2.setDrawGridLines(chartMetadata.isDrawYLines());
//
//            lineChart.getDescription().setText("");
//            lineChart.invalidate();
//            lineChart.setVisibility(View.VISIBLE);
//            chartView = lineChart;
        }

        container.addView(chartView);
        return viewToReturn;
    }

    public static ArrayList<String> getChartTypes(Context context) {
        ArrayList<String> types = new ArrayList<>();
        types.add(context.getString(R.string.bar_chart));
        types.add(context.getString(R.string.line_chart));
        return types;
    }

    public static Bitmap getChartSnapshot(View chartHolder) {
        FrameLayout container = (FrameLayout) chartHolder.findViewById(R.id.container);
        Chart chart = (Chart) container.getChildAt(0);
        return chart.getChartBitmap();
    }

}
