package com.feliperrm.wikiolap.network;

import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by felip on 23/02/2017.
 */

public interface ApiCalls {

    @GET("searchmetadata/{search_string}")
    Call<ArrayList<DatasetMetadata>> searchMetadata(@Path("search_string") String searchString);

    @GET("getdata/{table_id}")
    Call<JsonArray> getData(@Path("table_id") String tableId);

    @GET("getdata/{table_id}/{select_columns}")
    Call<ArrayList<Object>> getData(@Path("table_id") String tableId,
                                    @Path("select_columns") String columns);

    @GET("getdata/{table_id}/{select_columns}/{limit}")
    Call<ArrayList<Object>> getData(@Path("table_id") String tableId,
                                    @Path("select_columns") String columns,
                                    @Path("limit") Integer limit);

    @GET("getdata/{table_id}/{group_by}/{agg_func}/{agg_columns}/{limit}")
    Call<ArrayList<Object>> getDataAggregated(@Path("table_id") String tableId,
                                              @Path("group_by") String groupBy,
                                              @Path("agg_func") AggregationFunctions aggregationFunction,
                                              @Path("agg_columns") String columns,
                                              @Path("limit") Integer limit);
}
