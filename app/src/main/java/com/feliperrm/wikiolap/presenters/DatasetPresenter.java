package com.feliperrm.wikiolap.presenters;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.XYHolder;
import com.feliperrm.wikiolap.network.Network;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

    public void loadDataset(final ChartMetadata chartMetadata) {
        callbacks.onLoadingStarted();
        Network.getApiCalls().getData(chartMetadata.getTableId()).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callbacks.onDataLoaded(formatChartData(chartMetadata, response.body()));
                } else {
                    onFailure(call, new Exception("Null Body or Server Error"));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                if (BuildConfig.DEBUG) {
                    if (t != null) {
                        t.printStackTrace();
                    }
                }
                callbacks.onError("Error while loading dataset, tap to try again!");
            }
        });
    }

    private static ArrayList<XYHolder> formatChartData(ChartMetadata chartMetadata, JsonArray jsonArray){
        ArrayList<XYHolder> returnArray = new ArrayList<>();
        int size = jsonArray.size();
        int i = 0;
        while (i < size) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            Double x = object.get(chartMetadata.getxColumnId()).getAsDouble();
            Double y = object.get(chartMetadata.getyColumnId()).getAsDouble();
            XYHolder holder = new XYHolder(x,y);
            returnArray.add(holder);
            i++;
        }
        return returnArray;
    }

}
