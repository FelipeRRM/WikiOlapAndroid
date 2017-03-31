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
import android.widget.RadioButton;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.XValuesAdapter;
import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.utils.ChartUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpAxisFragment extends Fragment implements XValuesAdapter.XValuesInterface {

    /**
     * Views
     */
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


    public SetUpAxisFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_up_axis, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setUpViews();
    }

    private void findViews(View v){
        chartTypeSpinner = (Spinner) v.findViewById(R.id.chartTypeSpinner);
        yAxisValueSpinner = (Spinner) v.findViewById(R.id.yValueSpinner);
        xRecyclerView = (RecyclerView) v.findViewById(R.id.xRecyclerView);
        radioSum = (RadioButton) v.findViewById(R.id.radio_sum);
        radioAverage = (RadioButton) v.findViewById(R.id.radio_average);
    }

    private void setUpViews(){
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

    @Override
    public void onXValuesChanged(ArrayList<String> xValues) {
        chartMetadata.setxColumnIds(xValues);
    }
}
