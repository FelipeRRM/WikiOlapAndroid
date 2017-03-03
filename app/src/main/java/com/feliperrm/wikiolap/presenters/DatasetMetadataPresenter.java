package com.feliperrm.wikiolap.presenters;

import android.view.View;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.interfaces.DatasetMetadataViewCallbacks;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.network.Network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felip on 02/03/2017.
 */

public class DatasetMetadataPresenter {

    DatasetMetadataViewCallbacks callbacks;

    public DatasetMetadataPresenter(DatasetMetadataViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void loadDatasets(String search){
        callbacks.onLoadingStarted();
        Network.getApiCalls().searchMetadata(search).enqueue(new Callback<ArrayList<DatasetMetadata>>() {
            @Override
            public void onResponse(Call<ArrayList<DatasetMetadata>> call, Response<ArrayList<DatasetMetadata>> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    callbacks.onDataLoaded(response.body());
                }
                else{
                    onFailure(call, new Exception("Null Body or Server Error"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DatasetMetadata>> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    if (t != null) {
                        t.printStackTrace();
                    }
                }
                callbacks.onError("");
            }
        });
    }

}
