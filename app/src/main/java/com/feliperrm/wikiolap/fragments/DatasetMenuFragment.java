package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.activities.SetUpVisualizationActivity;
import com.feliperrm.wikiolap.adapters.DatasetMetadataAdapter;
import com.feliperrm.wikiolap.interfaces.DatasetMetadataViewCallbacks;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.presenters.DatasetMetadataPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatasetMenuFragment extends BaseFrgment implements DatasetMetadataViewCallbacks, DatasetMetadataAdapter.DatasetInterface {

    /**
     * Views
     */
    EditText searchEditText;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    /**
     * Attriutes
     */
    DatasetMetadataPresenter presenter;

    public DatasetMenuFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DatasetMetadataPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dataset_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setUpViews();
    }

    private void findViews(View v) {
        searchEditText = (EditText) v.findViewById(R.id.searchEditText);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
    }

    private void setUpViews() {
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.loadDatasets(searchEditText.getText().toString());
                }
                return false;
            }
        });
    }


    @Override
    public void onLoadingStarted() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onDataLoaded(ArrayList<DatasetMetadata> dataSets) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DatasetMetadataAdapter(dataSets, this));
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDatasetClicked(DatasetMetadata datasetMetadata) {
        startActivity(SetUpVisualizationActivity.getIntent(getContext(), datasetMetadata));
    }
}
