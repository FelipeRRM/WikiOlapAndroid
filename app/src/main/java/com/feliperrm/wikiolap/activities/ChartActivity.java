package com.feliperrm.wikiolap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.ChartsAdapter;
import com.feliperrm.wikiolap.fragments.ChartFragment;
import com.feliperrm.wikiolap.models.ChartMetadata;

public class ChartActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ChartMetadata chartMetadata = (ChartMetadata) getIntent().getParcelableExtra(ChartFragment.CHART_KEY);
        actionBar.setTitle(chartMetadata.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ChartFragment.newInstance(chartMetadata)).commitAllowingStateLoss();

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

    public static Intent getIntent(Context context, ChartMetadata chartMetadata) {
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra(ChartFragment.CHART_KEY, chartMetadata);
        return intent;
    }

}
