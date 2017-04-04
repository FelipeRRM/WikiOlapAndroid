package com.feliperrm.wikiolap.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DatasetMetadataAdapter;
import com.feliperrm.wikiolap.fragments.ChartFragment;
import com.feliperrm.wikiolap.fragments.DatasetMenuFragment;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;

public class AddDatasetActivity extends BaseActivity implements DatasetMetadataAdapter.DatasetInterface{

    public static final String SELECTED_DATASET_KEY = "selecteddataset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dataset);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.chose_dataset_to_join));
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new DatasetMenuFragment()).commitAllowingStateLoss();

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

    @Override
    public void onDatasetClicked(DatasetMetadata datasetMetadata) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_DATASET_KEY, datasetMetadata);
        setResult(RESULT_OK, intent);
        finish();
    }
}
