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




    public void loadDatasetFormatted(final ChartMetadata chartMetadata) {
        if(chartMetadata.getTable2Id()!= null && !chartMetadata.getTable2Id().isEmpty()){
            loadJoinData(chartMetadata);
        }
        else{
            loadRegularData(chartMetadata);
        }
    }

    private Call<JsonArray> loadJoinDatasetFormattedCall;
    private Response<JsonArray> joinResponseCache;
    private String joinLastSuccessfulRequest;
    private void loadJoinData(final ChartMetadata chartMetadata){
        callbacks.onLoadingStarted();
        if (loadJoinDatasetFormattedCall != null) {
            loadJoinDatasetFormattedCall.cancel();
        }
        if (joinResponseCache != null && joinLastSuccessfulRequest.equals(chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getAggregateString())) {
            callbacks.onDataLoaded(formatChartData(chartMetadata, joinResponseCache.body()));
        } else {
            loadJoinDatasetFormattedCall = Network.getApiCalls().getJoinedDataAggregated(chartMetadata.getTableId(), chartMetadata.getTable2Id(), chartMetadata.getJoin1String(), chartMetadata.getJoin2String(), chartMetadata.getGroupByString(), chartMetadata.getAggregationAsEnum(), chartMetadata.getAggregateString());
            loadJoinDatasetFormattedCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        joinResponseCache = response;
                        joinLastSuccessfulRequest = chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getAggregateString();
                        callbacks.onDataLoaded(formatChartData(chartMetadata, response.body()));
                    } else {
                        onFailure(call, new Exception("Null Body or Server Error"));
                    }
                    loadJoinDatasetFormattedCall = null;
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    joinResponseCache = null;
                    joinLastSuccessfulRequest = null;
                    if (!call.isCanceled()) {
                        if (BuildConfig.DEBUG) {
                            if (t != null) {
                                t.printStackTrace();
                            }
                        }
                        callbacks.onError("Error while loading dataset, tap to try again!");
                    }
                    loadJoinDatasetFormattedCall = null;
                }
            });
        }
    }

    private Call<JsonArray> loadDatasetRegularFormattedCall;
    private Response<JsonArray> regularResponseCache;
    private String regularLastSuccessfulRequest;
    private void loadRegularData(final ChartMetadata chartMetadata){
        callbacks.onLoadingStarted();
        if (loadDatasetRegularFormattedCall != null) {
            loadDatasetRegularFormattedCall.cancel();
        }
        if (regularResponseCache != null && regularLastSuccessfulRequest.equals(chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getAggregateString())) {
            callbacks.onDataLoaded(formatChartData(chartMetadata, regularResponseCache.body()));
        } else {
            loadDatasetRegularFormattedCall = Network.getApiCalls().getDataAggregated(chartMetadata.getTableId(), chartMetadata.getGroupByString(), chartMetadata.getAggregationAsEnum(), chartMetadata.getAggregateString());
            loadDatasetRegularFormattedCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        regularResponseCache = response;
                        regularLastSuccessfulRequest = chartMetadata.getTableId() + chartMetadata.getGroupByString() + chartMetadata.getAggregationFunction() + chartMetadata.getAggregateString();
                        callbacks.onDataLoaded(formatChartData(chartMetadata, response.body()));
                    } else {
                        onFailure(call, new Exception("Null Body or Server Error"));
                    }
                    loadDatasetRegularFormattedCall = null;
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    regularResponseCache = null;
                    regularLastSuccessfulRequest = null;
                    if (!call.isCanceled()) {
                        if (BuildConfig.DEBUG) {
                            if (t != null) {
                                t.printStackTrace();
                            }
                        }
                        callbacks.onError("Error while loading dataset, tap to try again!");
                    }
                    loadDatasetRegularFormattedCall = null;
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

    private static ArrayList<ArrayList<XYHolder>> formatChartData(ChartMetadata chartMetadata, JsonArray jsonArray) {
        ArrayList<ArrayList<XYHolder>> returnArray = new ArrayList<>();
        int size = jsonArray.size();
        int i;
        for (String yValue : chartMetadata.getyColumnIds()) {
            i = 0;
            ArrayList<XYHolder> entryValues = new ArrayList<>();
            String yColunmName = chartMetadata.getAggregationFunction().toString() + "(" + yValue + ")";
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
                entryValues.add(holder);
                i++;
            }
            returnArray.add(entryValues);
        }
        return returnArray;
    }

    private static ArrayList<ArrayList<String>> getRawValuesAsArray(DatasetMetadata datasetMetadata, JsonArray jsonArray) {
        int size = jsonArray.size();
        ArrayList<ArrayList<String>> returnValues = new ArrayList<>(size);
        returnValues.add(datasetMetadata.getAliasColumns());
        int i = 0;
        int rowSize = datasetMetadata.getDbColumns().size();
        while (i < size) {
            ArrayList<String> row = new ArrayList<>(rowSize);
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            for (String column : datasetMetadata.getDbColumns()) {
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
