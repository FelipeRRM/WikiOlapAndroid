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

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.DatasetMetadata;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatasetPreviewPagerFragment extends Fragment {

    /**
     * Constantes
     */
    private static final String DATASET1_KEY = "dataset1key";
    private static final String DATASET2_KEY = "dataset2key";
    private static final String SINGLE_FRAGMENT_TAG = "singlefragtag";

    /**
     * Views
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;

    /**
     * Attributes
     */
    private DatasetMetadata dataset1;
    private DatasetMetadata dataset2;
    private Adapter adapter;

    public static DatasetPreviewPagerFragment newInstance(DatasetMetadata dataset1, @Nullable DatasetMetadata dataset2) {
        Bundle args = new Bundle();
        args.putSerializable(DATASET1_KEY, dataset1);
        args.putSerializable(DATASET2_KEY, dataset2);
        DatasetPreviewPagerFragment fragment = new DatasetPreviewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DatasetPreviewPagerFragment() {

    }

    public DatasetMetadata getDataset1() {
        return dataset1;
    }

    public void setDataset1(DatasetMetadata dataset1) {
        this.dataset1 = dataset1;
        if(adapter!=null){
            if(adapter.frag1!=null){
                adapter.frag1.setDatasetMetadata(this.dataset1);
            }
        }
        setUpViews();
    }

    public DatasetMetadata getDataset2() {
        return dataset2;
    }

    public void setDataset2(DatasetMetadata dataset2) {
        this.dataset2 = dataset2;
        if(adapter!=null){
            if(adapter.frag2!=null){
                adapter.frag2.setDatasetMetadata(this.dataset2);
            }
        }
        setUpViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dataset_preview_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recoverBundle();
        findViews(view);
        setUpViews();
    }

    private void recoverBundle() {
        dataset1 = (DatasetMetadata) getArguments().getSerializable(DATASET1_KEY);
        dataset2 = (DatasetMetadata) getArguments().getSerializable(DATASET2_KEY);
    }

    private void findViews(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
    }

    private void setUpViews() {
        if (dataset2 != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            Fragment frag = fragmentManager.findFragmentByTag(SINGLE_FRAGMENT_TAG);
            if (frag != null) {
                fragmentManager.beginTransaction().remove(frag).commitAllowingStateLoss();
            }
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            adapter = new Adapter(getChildFragmentManager());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            getChildFragmentManager().beginTransaction().replace(R.id.root, DatasetPreviewFragment.newInstance(dataset1, false)).commitAllowingStateLoss();
        }

    }

    private class Adapter extends FragmentPagerAdapter {

        DatasetPreviewFragment frag1;
        DatasetPreviewFragment frag2;

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (frag1 == null) {
                    frag1 = DatasetPreviewFragment.newInstance(dataset1, true);
                }
                return frag1;
            } else {
                if (frag2 == null) {
                    frag2 = DatasetPreviewFragment.newInstance(dataset2, true);
                }
                return frag2;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getString(R.string.dataset_number, 1);
            } else {
                return getString(R.string.dataset_number, 2);
            }
        }
    }

}
