package com.feliperrm.wikiolap.fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.feliperrm.wikiolap.utils.FirebaseUtil;
import com.feliperrm.wikiolap.utils.MyApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpVisualizationFragment extends Fragment implements DatasetViewCallbacks, ChartUpdateInterface {

    /**
     * Contants
     */
    private static final String DATASET1_KEY = "dataset1key";
    private static final String DATASET2_KEY = "dataset2key";


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
    private DatasetMetadata dataset1;
    private DatasetMetadata dataset2;
    private ChartMetadata chartMetadata;
    private DatasetPresenter presenter;
    private SetUpVisualizationAdapter adapter;
    private Random random;

    public DatasetMetadata getDataset1() {
        return dataset1;
    }

    public ChartMetadata getChartMetadata() {
        return chartMetadata;
    }

    public static SetUpVisualizationFragment newInstance(DatasetMetadata dataset1, @Nullable DatasetMetadata dataset2) {
        Bundle args = new Bundle();
        args.putSerializable(DATASET1_KEY, dataset1);
        args.putSerializable(DATASET2_KEY, dataset2);
        SetUpVisualizationFragment fragment = new SetUpVisualizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setDataset1(DatasetMetadata dataset1) {
        this.dataset1 = dataset1;
        updateFragments();
    }

    public DatasetMetadata getDataset2() {
        return dataset2;
    }

    public void setDataset2(DatasetMetadata dataset2) {
        this.dataset2 = dataset2;
        updateFragments();
    }

    public void updateFragments() {
        if (adapter != null) {
            if (adapter.frag1 != null) {
                adapter.frag1.metadataUpdated();
            }
            if (adapter.frag2 != null) {

            }
        }
        setUpInitialValues();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random(System.currentTimeMillis());
        presenter = new DatasetPresenter(this);
        chartMetadata = new ChartMetadata();
        chartMetadata.setCreator_id(MyApp.app.getLoggedUser().getEmail());
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
        dataset1 = (DatasetMetadata) getArguments().getSerializable(DATASET1_KEY);
        dataset2 = (DatasetMetadata) getArguments().getSerializable(DATASET2_KEY);
        chartMetadata.setTableId(dataset1.getTableId());
        chartMetadata.setId(FirebaseUtil.encodeForFirebaseKey(MyApp.app.getLoggedUser().getEmail()) + String.valueOf(new Date().getTime()));
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
        adapter = new SetUpVisualizationAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpInitialValues() {
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add(dataset1.getDbColumns().get(0));
        chartMetadata.setxColumnIds(xValues);
        ArrayList<String> yColumnsIDs = new ArrayList<>();
        yColumnsIDs.add(dataset1.getDbColumns().get(dataset1.getDbColumns().size() - 1));
        chartMetadata.setyColumnIds(yColumnsIDs);
        ArrayList<String> yColumnsAlias = new ArrayList<>();
        yColumnsAlias.add(dataset1.getAliasColumns().get(dataset1.getAliasColumns().size() - 1));
        chartMetadata.setyAlias(yColumnsAlias);
        ArrayList<Integer> yColors = new ArrayList<>();
        yColors.add(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        chartMetadata.setyColors(yColors);
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
    public void onDataLoaded(ArrayList<ArrayList<XYHolder>> dataset) {
        chartHolder.removeAllViews();
        chartHolder.addView(ChartUtil.buildChart(getContext(), dataset, chartMetadata));
        chartHolder.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public Bitmap getChartSnapshot() {
        return ChartUtil.getChartSnapshot(chartHolder.getChildAt(0));
    }

    ;

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
