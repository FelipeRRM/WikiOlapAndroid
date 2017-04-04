package com.feliperrm.wikiolap.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.FrameLayout;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DatasetMetadataAdapter;
import com.feliperrm.wikiolap.fragments.ChartsMenuFragment;
import com.feliperrm.wikiolap.fragments.DatasetMenuFragment;
import com.feliperrm.wikiolap.fragments.ProfileMenuFragment;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BottomBarActivity extends BaseActivity implements DatasetMetadataAdapter.DatasetInterface {

    /**
     * Attributes
     */
    WeakReference<ChartsMenuFragment> chartsMenuFragment;
    WeakReference<DatasetMenuFragment> datasetMenuFragment;
    WeakReference<ProfileMenuFragment> profileMenuFragment;

    /**
     * Views
     */
    BottomBar bottomBar;
    FrameLayout contentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        if (BuildConfig.DEBUG) {
            printKeyHash();
        }
        findViews();
        setUpViews();
    }

    private void findViews() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        contentContainer = (FrameLayout) findViewById(R.id.contentContainer);
    }

    private void setUpViews() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_charts) {
                    if (chartsMenuFragment == null || chartsMenuFragment.get() == null) {
                        chartsMenuFragment = new WeakReference<ChartsMenuFragment>(new ChartsMenuFragment());
                    }
                    changeContentView(chartsMenuFragment.get());
                } else if (tabId == R.id.tab_dataset) {
                    if (datasetMenuFragment == null || datasetMenuFragment.get() == null) {
                        datasetMenuFragment = new WeakReference<DatasetMenuFragment>(new DatasetMenuFragment());
                    }
                    changeContentView(datasetMenuFragment.get());
                } else {
                    if (profileMenuFragment == null || profileMenuFragment.get() == null) {
                        profileMenuFragment = new WeakReference<ProfileMenuFragment>(new ProfileMenuFragment());
                    }
                    changeContentView(profileMenuFragment.get());
                }
            }
        });
    }

    private void changeContentView(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commitAllowingStateLoss();
    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.feliperrm.wikiolap",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onDatasetClicked(DatasetMetadata datasetMetadata) {
        startActivity(SetUpVisualizationActivity.getIntent(BottomBarActivity.this, datasetMetadata, null));
    }
}
