package com.feliperrm.wikiolap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.fragments.DatasetPreviewFragment;
import com.feliperrm.wikiolap.fragments.SetUpVisualizationFragment;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;

public class SetUpVisualizationActivity extends BaseActivity implements MetadataProvider {

    /**
     * Views
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView back;

    /**
     * Attributes
     */
    private DatasetMetadata datasetMetadata;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_visualization);
        datasetMetadata = (DatasetMetadata) getIntent().getSerializableExtra(SetUpVisualizationFragment.DATASET_KEY);
        findViews();
        setUpViews();
    }

    private void findViews() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.container);
        back = (ImageView) findViewById(R.id.back);
    }

    private void setUpViews() {
        adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                finish();
            }
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public static Intent getIntent(Context context, DatasetMetadata datasetMetadata) {
        Intent intent = new Intent(context, SetUpVisualizationActivity.class);
        intent.putExtra(SetUpVisualizationFragment.DATASET_KEY, datasetMetadata);
        return intent;
    }

    @Override
    public DatasetMetadata getDatasetMetada() {
        return adapter.frag2.getDatasetMetadata();
    }

    @Override
    public ChartMetadata getChartMetadata() {
        return adapter.frag2.getChartMetadata();
    }

    class Adapter extends FragmentPagerAdapter {

        DatasetPreviewFragment frag1;
        SetUpVisualizationFragment frag2;

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    if (frag1 == null) {
                        frag1 = DatasetPreviewFragment.newInstance(datasetMetadata);
                    }
                    return frag1;
                }
                case 1: {
                    if (frag2 == null) {
                        frag2 = SetUpVisualizationFragment.newInstance(datasetMetadata);
                    }
                    return frag2;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return getString(R.string.dataset);
                }
                case 1: {
                    return getString(R.string.visualization);
                }
                default: {
                    return "";
                }
            }
        }
    }


}
