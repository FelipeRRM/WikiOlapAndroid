package com.feliperrm.wikiolap.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.fragments.ChartsMenuFragment;
import com.feliperrm.wikiolap.fragments.DatasetMenuFragment;
import com.feliperrm.wikiolap.fragments.ProfileMenuFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.lang.ref.WeakReference;

public class BottomBarActivity extends AppCompatActivity {

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
        findViews();
        setUpViews();
    }

    private void findViews(){
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        contentContainer = (FrameLayout) findViewById(R.id.contentContainer);
    }

    private void setUpViews(){
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_charts) {
                    if(chartsMenuFragment==null || chartsMenuFragment.get() == null) {
                        chartsMenuFragment = new WeakReference<ChartsMenuFragment>(new ChartsMenuFragment());
                    }
                    changeContentView(chartsMenuFragment.get());
                }
                else if (tabId == R.id.tab_dataset){
                    if(datasetMenuFragment==null || datasetMenuFragment.get() == null) {
                        datasetMenuFragment = new WeakReference<DatasetMenuFragment>(new DatasetMenuFragment());
                    }
                    changeContentView(datasetMenuFragment.get());
                }
                else {
                    if(profileMenuFragment==null || profileMenuFragment.get() == null) {
                        profileMenuFragment = new WeakReference<ProfileMenuFragment>(new ProfileMenuFragment());
                    }
                    changeContentView(profileMenuFragment.get());
                }
            }
        });
    }

    private void changeContentView(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, fragment).commitAllowingStateLoss();
    }

}
