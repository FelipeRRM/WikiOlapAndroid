package com.feliperrm.wikiolap.utils;

import android.content.Context;
import android.view.View;

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

import java.util.ArrayList;

/**
 * Created by felip on 22/03/2017.
 */

public class ChartUtil {

    public static final int BAR_CHART = 0;
    public static final int LINE_CHART = 1;

    public static Chart buildChart(Context context, ArrayList<XYHolder> dataset, ChartMetadata chartMetadata) {

        if (chartMetadata.getChartType() == BAR_CHART) {
            BarChart barChart = new BarChart(context);
            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            for (XYHolder xyHolder : dataset) {
                BarEntry barEntry = new BarEntry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
                entries.add(barEntry);
                labels.add(xyHolder.getLabel());
            }

            BarDataSet barDataSet = new BarDataSet(entries, chartMetadata.getYColumnId());

            BarData barData = new BarData(barDataSet);

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

            barChart.invalidate();
            barChart.setVisibility(View.VISIBLE);
            return barChart;
        } else {
            LineChart lineChart = new LineChart(context);
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();
            for (XYHolder xyHolder : dataset) {
                Entry entry = new Entry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
                entries.add(entry);
                labels.add(xyHolder.getLabel());
            }

            LineDataSet lineDataSet = new LineDataSet(entries, chartMetadata.getYColumnId());

            LineData lineData = new LineData(lineDataSet);

            lineChart.setData(lineData);
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setDrawGridLines(chartMetadata.isDrawXLines());
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

            YAxis yAxis1 = lineChart.getAxisLeft();
            YAxis yAxis2 = lineChart.getAxisRight();
            yAxis1.setDrawGridLines(chartMetadata.isDrawYLines());
            yAxis2.setDrawGridLines(chartMetadata.isDrawYLines());

            lineChart.invalidate();
            lineChart.setVisibility(View.VISIBLE);
            return lineChart;
        }
    }

    public static ArrayList<String> getChartTypes(Context context) {
        ArrayList<String> types = new ArrayList<>();
        types.add(context.getString(R.string.bar_chart));
        types.add(context.getString(R.string.line_chart));
        return types;
    }

}
