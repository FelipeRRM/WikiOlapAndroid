package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.XValuesAdapter;
import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.feliperrm.wikiolap.utils.ChartUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpVisualizationFragment extends Fragment implements DatasetViewCallbacks, ChartUpdateInterface, XValuesAdapter.XValuesInterface{

    /**
     * Contants
     */
    public static final String DATASET_KEY = "datasetkey";

    /**
     * Views
     */
    private FrameLayout chartHolder;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private Spinner chartTypeSpinner;
    private Spinner yAxisValueSpinner;
    private RecyclerView xRecyclerView;
    private RadioButton radioSum;
    private RadioButton radioAverage;

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
        setUpInitialValues();
        chartMetadata.setUpdateInterface(this);
        presenter.loadDatasetFormatted(chartMetadata);
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
        yAxisValueSpinner = (Spinner) v.findViewById(R.id.yValueSpinner);
        xRecyclerView = (RecyclerView) v.findViewById(R.id.xRecyclerView);
        radioSum = (RadioButton) v.findViewById(R.id.radio_sum);
        radioAverage = (RadioButton) v.findViewById(R.id.radio_average);
    }

    private void setUpViews(){
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadDatasetFormatted(chartMetadata);
            }
        });
        ArrayAdapter<String> chartTypesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ChartUtil.getChartTypes(getContext()));
        chartTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(chartTypesAdapter);
        chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                chartMetadata.setChartType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> yValuesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, datasetMetadata.getOriginalColumns());
        yValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yAxisValueSpinner.setAdapter(yValuesAdapter);
        yAxisValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                chartMetadata.setYColumnId(datasetMetadata.getOriginalColumns().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        yAxisValueSpinner.setSelection(yValuesAdapter.getCount()-1);

        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        xRecyclerView.setAdapter(new XValuesAdapter(ColumnHolder.getColumnHoldersFromStrings(datasetMetadata.getOriginalColumns()), this));

        radioSum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    chartMetadata.setAggregationFunctionAsEnum(AggregationFunctions.FunctionSum);
                }
            }
        });

        radioAverage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked) {
                    chartMetadata.setAggregationFunctionAsEnum(AggregationFunctions.FunctionAverage);
                }
            }
        });
    }

    private void setUpInitialValues(){
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add(datasetMetadata.getOriginalColumns().get(0));
        chartMetadata.setxColumnIds(xValues);
        chartMetadata.setYColumnId(datasetMetadata.getOriginalColumns().get(datasetMetadata.getOriginalColumns().size()-1));
        chartMetadata.setAggregationFunctionAsEnum(AggregationFunctions.FunctionSum);
    }

    @Override
    public void onLoadingStarted() {
        chartHolder.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        chartHolder.removeAllViews();
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
    public void onRawDataLoaded(ArrayList<ArrayList<String>> values) {

    }

    @Override
    public void onChartUpdated() {
        presenter.loadDatasetFormatted(chartMetadata);
    }

    @Override
    public void onXValuesChanged(ArrayList<String> xValues) {
        chartMetadata.setxColumnIds(xValues);
    }
}
