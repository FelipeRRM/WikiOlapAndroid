package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.Chart;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends BaseFrgment implements DatasetViewCallbacks {


    /**
     * Contants
     */
    public static final String CHART_KEY = "chartkey";

    /**
     * Views
     */
    BarChart barChart;

    /**
     * Attributes
     */
    private Chart chart;
    private DatasetPresenter presenter;

    public static ChartFragment newInstance(Chart chart) {

        Bundle args = new Bundle();
        args.putParcelable(CHART_KEY, chart);
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DatasetPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recoverBundle();
        presenter.loadDataset(chart.getTableId());
        findViews(view);
        setUpViews();
    }

    private void recoverBundle(){
        chart = getArguments().getParcelable(CHART_KEY);
    }

    private void findViews(View v){
        barChart = (BarChart) v.findViewById(R.id.chart);
    }

    private void setUpViews(){

    }

    @Override
    public void onLoadingStarted() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onDataLoaded(ArrayList<Object> dataset) {

    }
}
