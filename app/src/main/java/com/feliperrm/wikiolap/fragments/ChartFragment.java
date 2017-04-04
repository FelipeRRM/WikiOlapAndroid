package com.feliperrm.wikiolap.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.activities.SetUpVisualizationActivity;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.interfaces.UserViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.User;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.feliperrm.wikiolap.presenters.UserPresenter;
import com.feliperrm.wikiolap.utils.ChartUtil;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends BaseFrgment implements DatasetViewCallbacks, UserViewCallbacks {


    /**
     * Contants
     */
    public static final String CHART_KEY = "chartkey";

    /**
     * Views
     */
    FrameLayout chartHolder;
    ProgressBar progressBar;
    TextView errorTextView;
    CircularImageView profilePicture;
    TextView creatorName;
    ProgressBar creatorLoader;
    TextView description;
    Button btnDataset;


    /**
     * Attributes
     */
    private ChartMetadata chartMetadata;
    private DatasetPresenter datasetPresenter;
    private UserPresenter userPresenter;

    public static ChartFragment newInstance(ChartMetadata chartMetadata) {

        Bundle args = new Bundle();
        args.putParcelable(CHART_KEY, chartMetadata);
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ChartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datasetPresenter = new DatasetPresenter(this);
        userPresenter = new UserPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recoverBundle();
        findViews(view);
        setUpViews();
        datasetPresenter.loadDatasetFormatted(chartMetadata);
        userPresenter.loadUser(chartMetadata.getCreator_id());
    }

    private void recoverBundle(){
        chartMetadata = getArguments().getParcelable(CHART_KEY);
    }

    private void findViews(View v){
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
        chartHolder = (FrameLayout) v.findViewById(R.id.chartHolder);
        profilePicture = (CircularImageView) v.findViewById(R.id.profile_image);
        creatorName = (TextView) v.findViewById(R.id.name);
        creatorLoader = (ProgressBar) v.findViewById(R.id.creator_loader);
        description = (TextView) v.findViewById(R.id.description);
        btnDataset = (Button) v.findViewById(R.id.btn_dataset);
    }

    private void setUpViews(){
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datasetPresenter.loadDatasetFormatted(chartMetadata);
            }
        });
        description.setText(chartMetadata.getDescription());
        btnDataset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onLoadingStarted() {
        chartHolder.setVisibility(View.INVISIBLE);
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
    public void onRawDataLoaded(ArrayList<ArrayList<String>> values) {
        // Not used
    }

    @Override
    public void onDataLoaded(User user, String requestEmail) {
        profilePicture.setVisibility(View.VISIBLE);
        Glide.with(getContext()).load(user.getPicture()).placeholder(R.drawable.profile_placeholder).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(profilePicture);
        creatorName.setText(user.getName());
        creatorLoader.setVisibility(View.GONE);
        creatorName.setOnClickListener(null);
    }

    @Override
    public void onUserLoadingStarted() {
        creatorLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUserError(String message) {
        creatorLoader.setVisibility(View.GONE);
        profilePicture.setVisibility(View.GONE);
        creatorName.setText(message);
        creatorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPresenter.loadUser(chartMetadata.getCreator_id());
            }
        });
    }
}
