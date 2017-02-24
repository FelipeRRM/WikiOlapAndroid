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

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DatasetMetadataAdapter;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.network.Network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatasetMenuFragment extends Fragment {

    EditText searchEditText;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    public DatasetMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                    searchMetadataFromApi(searchEditText.getText().toString());
                }
                return false;
            }
        });
    }

    private void searchMetadataFromApi(String textToSearch) {
        progressBar.setVisibility(View.VISIBLE);
        Network.getApiCalls().searchMetadata(textToSearch).enqueue(new Callback<ArrayList<DatasetMetadata>>() {
            @Override
            public void onResponse(Call<ArrayList<DatasetMetadata>> call, Response<ArrayList<DatasetMetadata>> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new DatasetMetadataAdapter(response.body()));
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    onFailure(call, new Exception("Null Body or Server Error"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DatasetMetadata>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (BuildConfig.DEBUG) {
                    if (t != null) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}
