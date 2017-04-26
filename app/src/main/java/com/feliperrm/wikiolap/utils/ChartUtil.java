package com.feliperrm.wikiolap.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feliperrm.wikiolap.BuildConfig;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felip on 22/03/2017.
 */

public class ChartUtil {

    public static final int BAR_CHART = 0;
    public static final int LINE_CHART = 1;

    public static View buildChart(Context context, ArrayList<ArrayList<XYHolder>> dataset, ChartMetadata chartMetadata) {
        try {
            View viewToReturn = LayoutInflater.from(context).inflate(R.layout.chart_with_titles, null);
            TextView xTitle = (TextView) viewToReturn.findViewById(R.id.xTitle);
            TextView yTitle = (TextView) viewToReturn.findViewById(R.id.yTitle);
            xTitle.setText(chartMetadata.getxTitle());
            yTitle.setText(chartMetadata.getyTitle());
            FrameLayout container = (FrameLayout) viewToReturn.findViewById(R.id.container);
            try {
                View chartView;
                if (chartMetadata.getChartType() == BAR_CHART) {
                    ArrayList<String> labels = new ArrayList<>();
                    BarChart chart = new BarChart(context);
                    List<IBarDataSet> sets = new ArrayList<>();
                    int size = dataset.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<XYHolder> XYHolders = dataset.get(i);
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        for (XYHolder xyHolder : XYHolders) {
                            BarEntry barEntry = new BarEntry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
                            entries.add(barEntry);
                            labels.add(xyHolder.getLabel());
                        }
                        String lbl;
                        if (chartMetadata.getyAlias() != null && chartMetadata.getyAlias().get(i) != null) {
                            lbl = chartMetadata.getyAlias().get(i);
                        } else {
                            lbl = chartMetadata.getyColumnIds().get(i);
                        }
                        BarDataSet set = new BarDataSet(entries, lbl);
                        set.setColor(chartMetadata.getyColors().get(i));
                        sets.add(set);
                    }
                    BarData barData = new BarData(sets);
                    chart.setData(barData);
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setDrawGridLines(chartMetadata.isDrawXLines());
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawLabels(true);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

                    YAxis yAxis1 = chart.getAxisLeft();
                    YAxis yAxis2 = chart.getAxisRight();
                    yAxis1.setDrawGridLines(chartMetadata.isDrawYLines());
                    yAxis2.setDrawGridLines(chartMetadata.isDrawYLines());

                    chart.getDescription().setText("");
                    chart.invalidate();
                    chart.setVisibility(View.VISIBLE);
                    chartView = chart;
                } else {
                    ArrayList<String> labels = new ArrayList<>();
                    LineChart chart = new LineChart(context);
                    List<ILineDataSet> sets = new ArrayList<>();
                    int size = dataset.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<XYHolder> XYHolders = dataset.get(i);
                        ArrayList<Entry> entries = new ArrayList<>();
                        for (XYHolder xyHolder : XYHolders) {
                            Entry entry = new Entry((float) (xyHolder.getX()), (float) (xyHolder.getY()), xyHolder.getLabel());
                            entries.add(entry);
                            labels.add(xyHolder.getLabel());
                        }
                        String lbl;
                        if (chartMetadata.getyAlias() != null && chartMetadata.getyAlias().get(i) != null) {
                            lbl = chartMetadata.getyAlias().get(i);
                        } else {
                            lbl = chartMetadata.getyColumnIds().get(i);
                        }
                        LineDataSet set = new LineDataSet(entries, lbl);
                        set.setCircleColor(chartMetadata.getyColors().get(i));
                        set.setColor(chartMetadata.getyColors().get(i));
                        sets.add(set);
                    }
                    LineData data = new LineData(sets);
                    chart.setData(data);
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setDrawGridLines(chartMetadata.isDrawXLines());
                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawLabels(true);
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

                    YAxis yAxis1 = chart.getAxisLeft();
                    YAxis yAxis2 = chart.getAxisRight();
                    yAxis1.setDrawGridLines(chartMetadata.isDrawYLines());
                    yAxis2.setDrawGridLines(chartMetadata.isDrawYLines());

                    chart.getDescription().setText("");
                    chart.invalidate();
                    chart.setVisibility(View.VISIBLE);
                    chartView = chart;
                }
                container.addView(chartView);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
            return viewToReturn;
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
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
