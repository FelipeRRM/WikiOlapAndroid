package com.feliperrm.wikiolap.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DatasetMetadataAdapter;
import com.feliperrm.wikiolap.fragments.ChartsMenuFragment;
import com.feliperrm.wikiolap.fragments.DatasetMenuFragment;
import com.feliperrm.wikiolap.fragments.ProfileMenuFragment;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.utils.MyApp;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BottomBarActivity extends BaseActivity implements DatasetMetadataAdapter.DatasetInterface {

    /**
     * Attributes
     */
    ChartsMenuFragment chartsMenuFragment;
    DatasetMenuFragment datasetMenuFragment;
    ProfileMenuFragment profileMenuFragment;

    /**
     * Views
     */
    BottomBar bottomBar;
    FrameLayout contentContainer;
    Button closeButtom;
    LinearLayout onboardingView;


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
        closeButtom = (Button) findViewById(R.id.closeBtn);
        onboardingView = (LinearLayout) findViewById(R.id.onboardingView);
    }

    private void setUpViews() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_charts) {
                    if (chartsMenuFragment == null) {
                        chartsMenuFragment = (new ChartsMenuFragment());
                    }
                    changeContentView(chartsMenuFragment);
                } else if (tabId == R.id.tab_dataset) {
                    if (datasetMenuFragment == null) {
                        datasetMenuFragment = (new DatasetMenuFragment());
                    }
                    changeContentView(datasetMenuFragment);
                } else {
                    if (profileMenuFragment == null) {
                        profileMenuFragment = (new ProfileMenuFragment());
                    }
                    changeContentView(profileMenuFragment);
                }
            }
        });
        closeButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onboardingView.setVisibility(View.GONE);
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
        if (MyApp.app.getLoggedUser() == null) {
            new AlertDialog.Builder(BottomBarActivity.this).setTitle(getString(R.string.login_required)).setMessage(getString(R.string.login_required_message))
                    .setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            bottomBar.selectTabAtPosition(2, true);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        } else {
            startActivity(SetUpVisualizationActivity.getIntent(BottomBarActivity.this, datasetMetadata, null));
        }
    }
}
