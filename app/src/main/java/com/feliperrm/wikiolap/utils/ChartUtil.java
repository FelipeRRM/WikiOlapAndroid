package com.feliperrm.wikiolap.utils;

import android.content.Context;
import android.view.View;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by felip on 22/03/2017.
 */

public class ChartUtil {

    public static final int BAR_CHART = 0;
    public static final int LINE_CHART = 1;

    public static Chart buildChart(Context context, ArrayList<XYHolder> dataset, ChartMetadata chartMetadata) {
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
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.invalidate();
        barChart.setVisibility(View.VISIBLE);
        return barChart;
    }

    public static ArrayList<String> getChartTypes(Context context){
        ArrayList<String> types = new ArrayList<>();
        types.add(context.getString(R.string.bar_chart));
        types.add(context.getString(R.string.line_chart));
        return types;
    }

}
