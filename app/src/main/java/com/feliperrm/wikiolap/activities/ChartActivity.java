package com.feliperrm.wikiolap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.fragments.ChartFragment;
import com.feliperrm.wikiolap.models.Chart;

public class ChartActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Chart chart = (Chart) getIntent().getParcelableExtra(ChartFragment.CHART_KEY);
        actionBar.setTitle(chart.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ChartFragment.newInstance(chart)).commitAllowingStateLoss();

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

    public static Intent getIntent(Context context, Chart chart) {
        Intent intent = new Intent(context, ChartActivity.class);
        intent.putExtra(ChartFragment.CHART_KEY, chart);
        return intent;
    }

}
