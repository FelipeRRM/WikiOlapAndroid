package com.feliperrm.wikiolap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.fragments.ChartFragment;
import com.feliperrm.wikiolap.fragments.SetUpVisualizationFragment;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;

public class SetUpVisualizationActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        DatasetMetadata datasetMetadata = (DatasetMetadata) getIntent().getSerializableExtra(SetUpVisualizationFragment.DATASET_KEY);
        actionBar.setTitle(datasetMetadata.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, SetUpVisualizationFragment.newInstance(datasetMetadata)).commitAllowingStateLoss();

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

}
