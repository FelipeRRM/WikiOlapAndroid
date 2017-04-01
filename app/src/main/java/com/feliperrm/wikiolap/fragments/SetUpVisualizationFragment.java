package com.feliperrm.wikiolap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.presenters.DatasetPresenter;
import com.feliperrm.wikiolap.utils.ChartUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpVisualizationFragment extends Fragment implements DatasetViewCallbacks, ChartUpdateInterface {

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
    private TabLayout tabLayout;
    private ViewPager viewPager;


    /**
     * Attributes
     */
    private DatasetMetadata datasetMetadata;
    private ChartMetadata chartMetadata;
    private DatasetPresenter presenter;

    public DatasetMetadata getDatasetMetadata() {
        return datasetMetadata;
    }

    public ChartMetadata getChartMetadata() {
        return chartMetadata;
    }

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

    private void recoverBundle() {
        datasetMetadata = (DatasetMetadata) getArguments().getSerializable(DATASET_KEY);
        chartMetadata.setTableId(datasetMetadata.getTableId());
        chartMetadata.setTitle(datasetMetadata.getTitle());
        chartMetadata.setId(String.valueOf(new Date().getTime()));
    }

    private void findViews(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        errorTextView = (TextView) v.findViewById(R.id.errorTextView);
        chartHolder = (FrameLayout) v.findViewById(R.id.chartHolder);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
    }

    private void setUpViews() {
        errorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loadDatasetFormatted(chartMetadata);
            }
        });
        viewPager.setAdapter(new SetUpVisualizationAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpInitialValues() {
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add(datasetMetadata.getOriginalColumns().get(0));
        chartMetadata.setxColumnIds(xValues);
        chartMetadata.setYColumnId(datasetMetadata.getOriginalColumns().get(datasetMetadata.getOriginalColumns().size() - 1));
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

    private class SetUpVisualizationAdapter extends FragmentPagerAdapter {

        SetUpAxisFragment frag1;
        SetUpAppearanceFragment frag2;

        public SetUpVisualizationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (frag1 == null) {
                    frag1 = new SetUpAxisFragment();
                }
                return frag1;
            }
            if (position == 1) {
                if (frag2 == null) {
                    frag2 = new SetUpAppearanceFragment();
                }
                return frag2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getContext().getString(R.string.axis_settings);
            } else if (position == 1) {
                return getContext().getString(R.string.appearance_settings);
            } else {
                return "";
            }
        }

    }

}
