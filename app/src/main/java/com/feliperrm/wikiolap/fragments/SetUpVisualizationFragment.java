package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpVisualizationFragment extends Fragment implements DatasetViewCallbacks{

    /**
     * Contants
     */
    public static final String DATASET_KEY = "datasetkey";

    /**
     * Views
     */
    BarChart barChart;
    ProgressBar progressBar;
    TextView errorTextView;

    /**
     * Attributes
     */
    private DatasetMetadata datasetMetadata;
    private ChartMetadata chartMetadata;
    private DatasetPresenter presenter;

    public static SetUpVisualizationFragment newInstance(DatasetMetadata datasetMetadata) {
        Bundle args = new Bundle();
        args.putSerializable(DATASET_KEY, datasetMetadata);
        SetUpVisualizationFragment fragment = new SetUpVisualizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SetUpVisualizationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DatasetPresenter(this);
        chartMetadata = new ChartMetadata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_up_visualization, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recoverBundle();
        findViews(view);
        setUpViews();
        presenter.loadDataset(chartMetadata);
    }

    private void recoverBundle(){
        datasetMetadata = (DatasetMetadata) getArguments().getSerializable(DATASET_KEY);
        chartMetadata.setTableId(datasetMetadata.getTableId());
        chartMetadata.setTitle(datasetMetadata.getTitle());
        chartMetadata.setId(String.valueOf(new Date().getTime()));
    }

    private void findViews(View v){
        barChart = (BarChart) v.findViewById(R.id.chart);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
    }

    private void setUpViews(){
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadDataset(chartMetadata);
            }
        });
    }

    @Override
    public void onLoadingStarted() {
        progressBar.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    @Override
    public void onDataLoaded(ArrayList<XYHolder> dataset) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for(XYHolder xyHolder : dataset){
            BarEntry barEntry = new BarEntry((float)(xyHolder.getX()), (float)(xyHolder.getY()), xyHolder.getLabel() );
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
        progressBar.setVisibility(View.GONE);
    }

}
