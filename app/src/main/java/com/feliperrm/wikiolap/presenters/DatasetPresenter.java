package com.feliperrm.wikiolap.presenters;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.interfaces.DatasetViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;
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

    private static final int PREVIEW_QUANTITY = 10;
    DatasetViewCallbacks callbacks;

    public DatasetPresenter(DatasetViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }


    private Call<JsonArray> loadDatasetFormattedCall;
    Response<JsonArray> responseCache;
    String lastSuccessfulRequest;

    public void loadDatasetFormatted(final ChartMetadata chartMetadata) {
        callbacks.onLoadingStarted();
        if (loadDatasetFormattedCall != null) {
            loadDatasetFormattedCall.cancel();
        }
        if (responseCache != null && lastSuccessfulRequest.equals(chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getyColumnId())) {
            callbacks.onDataLoaded(formatChartData(chartMetadata, responseCache.body()));
        } else {
            loadDatasetFormattedCall = Network.getApiCalls().getDataAggregated(chartMetadata.getTableId(), chartMetadata.getGroupByString(), chartMetadata.getAggregationAsEnum(), chartMetadata.getyColumnId());
            loadDatasetFormattedCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        responseCache = response;
                        lastSuccessfulRequest = chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getyColumnId();
                        callbacks.onDataLoaded(formatChartData(chartMetadata, response.body()));
                    } else {
                        onFailure(call, new Exception("Null Body or Server Error"));
                    }
                    loadDatasetFormattedCall = null;
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    responseCache = null;
                    lastSuccessfulRequest = null;
                    if (!call.isCanceled()) {
                        if (BuildConfig.DEBUG) {
                            if (t != null) {
                                t.printStackTrace();
                            }
                        }
                        callbacks.onError("Error while loading dataset, tap to try again!");
                    }
                    loadDatasetFormattedCall = null;
                }
            });
        }
    }

    private Call<JsonArray> loadDatasetRawCall;
    Response<JsonArray> rawResponseCache;
    String rawLastSuccessfulRequest;

    public void loadDatasetRaw(final DatasetMetadata datasetMetadata) {
        callbacks.onLoadingStarted();
        if (loadDatasetRawCall != null) {
            loadDatasetRawCall.cancel();
        }
        if (rawResponseCache != null && rawLastSuccessfulRequest.equals(datasetMetadata.getTableId() + PREVIEW_QUANTITY)) {
            callbacks.onRawDataLoaded(getRawValuesAsArray(datasetMetadata, rawResponseCache.body()));
        } else {
            loadDatasetRawCall = Network.getApiCalls().getData(datasetMetadata.getTableId(), PREVIEW_QUANTITY);
            loadDatasetRawCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        rawResponseCache = response;
                        rawLastSuccessfulRequest = datasetMetadata.getTableId() + PREVIEW_QUANTITY;
                        callbacks.onRawDataLoaded(getRawValuesAsArray(datasetMetadata, response.body()));
                    } else {
                        onFailure(call, new Exception("Null Body or Server Error"));
                    }
                    loadDatasetRawCall = null;
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    rawResponseCache = null;
                    rawLastSuccessfulRequest = null;
                    if (!call.isCanceled()) {
                        if (BuildConfig.DEBUG) {
                            if (t != null) {
                                t.printStackTrace();
                            }
                        }
                        callbacks.onError("Error while loading dataset, tap to try again!");
                    }
                    loadDatasetRawCall = null;
                }
            });
        }
    }

    private static ArrayList<XYHolder> formatChartData(ChartMetadata chartMetadata, JsonArray jsonArray) {
        ArrayList<XYHolder> returnArray = new ArrayList<>();
        int size = jsonArray.size();
        int i = 0;
        String yColunmName = chartMetadata.getAggregationFunction().toString() + "(" + chartMetadata.getyColumnId() + ")";
        while (i < size) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            Double x = Double.valueOf(i);//object.get(chartMetadata.getxColumnId()).getAsDouble();
            Double y = object.get(yColunmName).getAsDouble();
            String label = "";
            StringBuilder builder = new StringBuilder(label);
            for (String groupByX : chartMetadata.getxColumnIds()) {
                builder.append(object.get(groupByX).getAsString());
                builder.append("/");
            }
            label = builder.toString();
            label = label.substring(0, label.length() - 1);
            XYHolder holder = new XYHolder(x, y, label);
            returnArray.add(holder);
            i++;
        }
        return returnArray;
    }

    private static ArrayList<ArrayList<String>> getRawValuesAsArray(DatasetMetadata datasetMetadata, JsonArray jsonArray) {
        int size = jsonArray.size();
        ArrayList<ArrayList<String>> returnValues = new ArrayList<>(size);
        returnValues.add(datasetMetadata.getAliasColumns());
        int i = 0;
        int rowSize = datasetMetadata.getOriginalColumns().size();
        while (i < size) {
            ArrayList<String> row = new ArrayList<>(rowSize);
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            for (String column : datasetMetadata.getOriginalColumns()) {
                String strToAdd;
                try {
                    strToAdd = object.get(column).toString();
                } catch (Exception e) {
                    strToAdd = "ERROR";
                }
                row.add(strToAdd);
            }
            returnValues.add(row);
            i++;
        }
        return returnValues;
    }

}
