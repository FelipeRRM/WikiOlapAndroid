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
import com.feliperrm.wikiolap.adapters.DatasetPreviewAdapter;
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
import com.feliperrm.wikiolap.utils.FixedGridLayoutManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatasetPreviewFragment extends Fragment implements DatasetViewCallbacks{

    /**
     * Contants
     */
    public static final String DATASET_KEY = "datasetkey";

    /**
     * Views
     */
    private ProgressBar progressBar;
    private TextView errorTextView;
    private TextView title;
    private RecyclerView recyclerView;
    private TextView description;
    private TextView source;
    private TextView email;
    private TextView date;

    /**
     * Attributes
     */
    private DatasetMetadata datasetMetadata;
    private DatasetPresenter presenter;

    public static DatasetPreviewFragment newInstance(DatasetMetadata datasetMetadata) {
        Bundle args = new Bundle();
        args.putSerializable(DATASET_KEY, datasetMetadata);
        DatasetPreviewFragment fragment = new DatasetPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DatasetPreviewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DatasetPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dataset_preview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recoverBundle();
        findViews(view);
        setUpViews();
        presenter.loadDatasetRaw(datasetMetadata);
    }

    private void recoverBundle(){
        datasetMetadata = (DatasetMetadata) getArguments().getSerializable(DATASET_KEY);
    }

    private void findViews(View v){
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        title = (TextView) v.findViewById(R.id.title);
        description = (TextView) v.findViewById(R.id.description);
        source = (TextView) v.findViewById(R.id.source);
        email = (TextView) v.findViewById(R.id.email);
        date = (TextView) v.findViewById(R.id.date);
    }

    private void setUpViews(){
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadDatasetRaw(datasetMetadata);
            }
        });
        title.setText(datasetMetadata.getTitle());
        FixedGridLayoutManager fixedGridLayoutManager = new FixedGridLayoutManager();
        fixedGridLayoutManager.setTotalColumnCount(datasetMetadata.getOriginalColumns().size());
        recyclerView.setLayoutManager(fixedGridLayoutManager);
        description.setText(datasetMetadata.getDescription());
        source.setText(datasetMetadata.getSource());
        email.setText(datasetMetadata.getEmail());
        Date createdDate = new Date(datasetMetadata.getCreated_at().get$date());
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        date.setText(df.format(createdDate));
    }

    @Override
    public void onLoadingStarted() {
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    @Override
    public void onDataLoaded(ArrayList<XYHolder> dataset) {
        // NOT USED
    }

    @Override
    public void onRawDataLoaded(ArrayList<ArrayList<String>> values) {
        recyclerView.setAdapter(new DatasetPreviewAdapter(values, datasetMetadata));
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}
