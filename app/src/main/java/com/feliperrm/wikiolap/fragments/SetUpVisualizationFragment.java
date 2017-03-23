package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.feliperrm.wikiolap.utils.ChartUtil;
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
public class SetUpVisualizationFragment extends Fragment implements DatasetViewCallbacks, ChartUpdateInterface{

    /**
     * Contants
     */
    public static final String DATASET_KEY = "datasetkey";

    /**
     * Views
     */
    FrameLayout chartHolder;
    ProgressBar progressBar;
    TextView errorTextView;
    Spinner chartTypeSpinner;

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
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
        chartHolder = (FrameLayout) v.findViewById(R.id.chartHolder);
        chartTypeSpinner = (Spinner) v.findViewById(R.id.chartTypeSpinner);
    }

    private void setUpViews(){
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadDataset(chartMetadata);
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ChartUtil.getChartTypes(getContext()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(arrayAdapter);
        chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                chartMetadata.setChartType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onLoadingStarted() {
        progressBar.setVisibility(View.VISIBLE);
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
        chartHolder.removeAllViews();
        chartHolder.addView(ChartUtil.buildChart(getContext(), dataset, chartMetadata));
        chartHolder.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onChartUpdated() {
        presenter.loadDataset(chartMetadata);
    }
}
