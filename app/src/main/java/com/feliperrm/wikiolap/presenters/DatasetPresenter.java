package com.feliperrm.wikiolap.presenters;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.network.Network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by felip on 10/03/2017.
 */

public class DatasetPresenter {

    DatasetViewCallbacks callbacks;

    public DatasetPresenter(DatasetViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void loadDataset(String tableId) {
        callbacks.onLoadingStarted();
        Network.getApiCalls().getData(tableId).enqueue(new Callback<ArrayList<Object>>() {
            @Override
            public void onResponse(Call<ArrayList<Object>> call, Response<ArrayList<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callbacks.onDataLoaded(response.body());
                } else {
                    onFailure(call, new Exception("Null Body or Server Error"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Object>> call, Throwable t) {
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
