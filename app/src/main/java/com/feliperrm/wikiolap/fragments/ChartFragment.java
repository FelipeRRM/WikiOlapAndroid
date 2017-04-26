package com.feliperrm.wikiolap.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.activities.SetUpVisualizationActivity;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.interfaces.UserViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.User;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.feliperrm.wikiolap.presenters.UserPresenter;
import com.feliperrm.wikiolap.utils.ChartUtil;
import com.feliperrm.wikiolap.utils.MyApp;
import com.google.gson.Gson;
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

    private void recoverBundle() {
        chartMetadata = getArguments().getParcelable(CHART_KEY);
    }

    private void findViews(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
        chartHolder = (FrameLayout) v.findViewById(R.id.chartHolder);
        profilePicture = (CircularImageView) v.findViewById(R.id.profile_image);
        creatorName = (TextView) v.findViewById(R.id.name);
        creatorLoader = (ProgressBar) v.findViewById(R.id.creator_loader);
        description = (TextView) v.findViewById(R.id.description);
        btnDataset = (Button) v.findViewById(R.id.btn_dataset);
    }

    private void setUpViews() {
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
                if (MyApp.app.getLoggedUser() == null) {
                    new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.login_required)).setMessage(getString(R.string.login_required_message))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                } else {
                    Gson gson = new Gson();
                    DatasetMetadata dataset1 = gson.fromJson(chartMetadata.getDataset1(), DatasetMetadata.class);
                    DatasetMetadata dataset2 = gson.fromJson(chartMetadata.getDataset2(), DatasetMetadata.class);
                    startActivity(SetUpVisualizationActivity.getIntent(getContext(), dataset1, dataset2));
                }
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
        try {
            if (isAdded()) {
                progressBar.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(message);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDataLoaded(ArrayList<ArrayList<XYHolder>> dataset) {
        try {
            if (isAdded()) {
                chartHolder.removeAllViews();
                chartHolder.addView(ChartUtil.buildChart(getContext(), dataset, chartMetadata));
                chartHolder.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRawDataLoaded(ArrayList<ArrayList<String>> values) {
        // Not used
    }

    @Override
    public void onDataLoaded(User user, String requestEmail) {
        try {
            creatorLoader.setVisibility(View.GONE);
            if (user != null) {
                profilePicture.setVisibility(View.VISIBLE);
                if (isAdded()) {
                    Glide.with(getContext()).load(user.getPicture()).placeholder(R.drawable.profile_placeholder).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(profilePicture);
                    creatorName.setText(user.getName());
                    creatorName.setOnClickListener(null);
                }
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUserLoadingStarted() {
        creatorLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUserError(String message) {
        try {
            if (isAdded()) {
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
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }
}
