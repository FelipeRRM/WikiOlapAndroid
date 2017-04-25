package com.feliperrm.wikiolap.fragments;


import android.content.Context;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DynamicSpinnerAdapter;
import com.feliperrm.wikiolap.adapters.XValuesAdapter;
import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.utils.ChartUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpAxisFragment extends Fragment implements XValuesAdapter.XValuesInterface {

    /**
     * Views
     */
    private Spinner chartTypeSpinner;
    private RecyclerView xRecyclerView;
    private RadioButton radioSum;
    private RadioButton radioAverage;
    private Button addYAxis;
    private RecyclerView ySpinnerRecycler;

    /**
     * Attributes
     */
    private MetadataProvider metadataProvider;
    private DynamicSpinnerAdapter adapter;
    private Random random;


    public SetUpAxisFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        metadataProvider = (MetadataProvider) context;
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

    private void findViews(View v) {
        chartTypeSpinner = (Spinner) v.findViewById(R.id.chartTypeSpinner);
        xRecyclerView = (RecyclerView) v.findViewById(R.id.xRecyclerView);
        radioSum = (RadioButton) v.findViewById(R.id.radio_sum);
        radioAverage = (RadioButton) v.findViewById(R.id.radio_average);
        addYAxis = (Button) v.findViewById(R.id.button_add_y);
        ySpinnerRecycler = (RecyclerView) v.findViewById(R.id.spinnerRecycler);
    }

    public void metadataUpdated() {
        setUpViews();
    }

    private void setUpViews() {
        setUpSharedViews();
        setUpNonJoinViews();
    }

    private void setUpSharedViews() {
        radioSum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    metadataProvider.getChartMetadata().setAggregationFunctionAsEnum(AggregationFunctions.FunctionSum);
                }
            }
        });

        radioAverage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    metadataProvider.getChartMetadata().setAggregationFunctionAsEnum(AggregationFunctions.FunctionAverage);
                }
            }
        });

        addYAxis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (metadataProvider.getDataset2Metadata() == null) {
                    metadataProvider.getChartMetadata().getyColumnIds().add(metadataProvider.getDataset1Metadata().getDbColumns().get(0));
                    metadataProvider.getChartMetadata().getyAlias().add(metadataProvider.getDataset1Metadata().getAliasColumns().get(0));
                    metadataProvider.getChartMetadata().getyColors().add(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                    adapter.notifyDataSetChanged();
                    metadataProvider.getChartMetadata().getUpdateInterface().onChartUpdated();
                } else {
                    String table1Id = metadataProvider.getDataset1Metadata().getTableId();
                    metadataProvider.getChartMetadata().getyColumnIds().add(table1Id + "_" + metadataProvider.getDataset1Metadata().getDbColumns().get(0));
                    metadataProvider.getChartMetadata().getyAlias().add(metadataProvider.getDataset1Metadata().getAliasColumns().get(0));
                    metadataProvider.getChartMetadata().getyColors().add(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                    adapter.notifyDataSetChanged();
                    metadataProvider.getChartMetadata().getUpdateInterface().onChartUpdated();
                }
            }
        });

        adapter = new DynamicSpinnerAdapter(metadataProvider);
        ySpinnerRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ySpinnerRecycler.setAdapter(adapter);
    }


    private void setUpNonJoinViews() {
        ArrayAdapter<String> chartTypesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ChartUtil.getChartTypes(getContext()));
        chartTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chartTypeSpinner.setAdapter(chartTypesAdapter);
        chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                metadataProvider.getChartMetadata().setChartType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        xRecyclerView.setAdapter(new XValuesAdapter(metadataProvider.getDataset1Metadata(), metadataProvider.getDataset2Metadata(), this));
    }

    @Override
    public void onXValuesChanged(ArrayList<String> x1Values, ArrayList<String> x2Values) {
        metadataProvider.getChartMetadata().setxColumnIds(x1Values);
        if (x2Values != null) {
            metadataProvider.getChartMetadata().setJoin1(x1Values);
            metadataProvider.getChartMetadata().setJoin2(x2Values);
        } else {
            metadataProvider.getChartMetadata().setJoin1(null);
            metadataProvider.getChartMetadata().setJoin2(null);
        }
    }
}
