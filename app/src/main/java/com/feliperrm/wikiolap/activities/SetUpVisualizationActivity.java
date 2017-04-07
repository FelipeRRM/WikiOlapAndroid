package com.feliperrm.wikiolap.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.fragments.DatasetPreviewPagerFragment;
import com.feliperrm.wikiolap.fragments.SetUpVisualizationFragment;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.utils.AnimaitonUtil;
import com.feliperrm.wikiolap.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class SetUpVisualizationActivity extends BaseActivity implements MetadataProvider {

    /**
     * Constants
     */
    private static final String DATASET1_KEY = "dataset1key";
    private static final String DATASET2_KEY = "dataset2key";
    private static final int ADD_DATASET_REQUEST_CODE = 66;

    /**
     * Views
     */
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView back;
    private FloatingActionButton addDataset;
    private FloatingActionButton saveVisualization;
    private ProgressDialog progressDialog;

    /**
     * Attributes
     */
    private DatasetMetadata dataset1;
    private DatasetMetadata dataset2;
    private Adapter adapter;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_visualization);
        storageRef = FirebaseStorage.getInstance().getReference();
        dataset1 = (DatasetMetadata) getIntent().getSerializableExtra(DATASET1_KEY);
        dataset2 = (DatasetMetadata) getIntent().getSerializableExtra(DATASET2_KEY);
        findViews();
        setUpViews();
    }

    private void findViews() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.container);
        back = (ImageView) findViewById(R.id.back);
        addDataset = (FloatingActionButton) findViewById(R.id.add_dataset);
        saveVisualization = (FloatingActionButton) findViewById(R.id.save_visualization);
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

        addDataset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDataSet = new Intent(SetUpVisualizationActivity.this, AddDatasetActivity.class);
                startActivityForResult(addDataSet, ADD_DATASET_REQUEST_CODE);
            }
        });

        saveVisualization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SetUpVisualizationActivity.this)
                        .setTitle(R.string.create_new_vis_dialog_title)
                        .setMessage(getString(R.string.create_new_vis_dialog_body))
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                createVisualization();
                            }
                        }).show();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    addDataset.show();
                    saveVisualization.hide();
                }
                else{
                    addDataset.hide();
                    AnimaitonUtil.showFabWithAnimation(saveVisualization, 50);
                }
            }
        });
    }

    public void createVisualization(){
        progressDialog = new ProgressDialog(SetUpVisualizationActivity.this);
        progressDialog.setTitle(getString(R.string.uploading));
        progressDialog.setMessage(getString(R.string.uploading_visualization));
        progressDialog.show();
        StorageReference fileRef = storageRef.child(getChartMetadata().getId() + ".jpg");
        Bitmap bitmap = adapter.frag2.getChartSnapshot();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = fileRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if(BuildConfig.DEBUG) {
                    Log.d("Upload Failed", "Visualization Thumb");
                    progressDialog.dismiss();
                    new AlertDialog.Builder(SetUpVisualizationActivity.this).setTitle(getString(R.string.error))
                            .setMessage(getString(R.string.uploading_error))
                            .show();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getChartMetadata().setDataset1(dataset1);
                getChartMetadata().setDataset2(dataset2);
                progressDialog.dismiss();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                getChartMetadata().setThumbnail(downloadUrl.toString());
                FirebaseUtil.createChart(getChartMetadata());
                finish();
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

    public static Intent getIntent(Context context, DatasetMetadata dataset1, @Nullable DatasetMetadata dataset2) {
        Intent intent = new Intent(context, SetUpVisualizationActivity.class);
        intent.putExtra(DATASET1_KEY, (Serializable) dataset1);
        intent.putExtra(DATASET2_KEY, (Serializable) dataset2);
        return intent;
    }

    @Override
    public DatasetMetadata getDatasetMetada() {
        return adapter.frag2.getDataset1();
    }

    @Override
    public ChartMetadata getChartMetadata() {
        return adapter.frag2.getChartMetadata();
    }

    class Adapter extends FragmentPagerAdapter {

        DatasetPreviewPagerFragment frag1;
        SetUpVisualizationFragment frag2;

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    if (frag1 == null) {
                        frag1 = DatasetPreviewPagerFragment.newInstance(dataset1, dataset2);
                    }
                    return frag1;
                }
                case 1: {
                    if (frag2 == null) {
                        frag2 = SetUpVisualizationFragment.newInstance(dataset1, dataset2);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == ADD_DATASET_REQUEST_CODE){
                if(data != null){
                    dataset2 = (DatasetMetadata) data.getSerializableExtra(AddDatasetActivity.SELECTED_DATASET_KEY);
                    if(adapter!=null){
                        if(adapter.frag1 != null){
                            adapter.frag1.setDataset2(dataset2);
                        }
                        if(adapter.frag2 != null){
                            adapter.frag2.setDataset2(dataset2);
                        }
                    }
                }
            }
        }
    }
}
