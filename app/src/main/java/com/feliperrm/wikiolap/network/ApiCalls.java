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

    @GET("getdata/{table_id}/{limit}")
    Call<JsonArray> getData(@Path("table_id") String tableId,
                                    @Path("limit") Integer limit);

    @GET("getdata/{table_id}/{select_columns}")
    Call<JsonArray> getData(@Path("table_id") String tableId,
                                    @Path("select_columns") String columns);

    @GET("getdata/{table_id}/{select_columns}/{limit}")
    Call<JsonArray> getData(@Path("table_id") String tableId,
                                    @Path("select_columns") String columns,
                                    @Path("limit") Integer limit);

    @GET("getdata/{table_id}/{group_by}/{agg_func}/{agg_columns}")
    Call<JsonArray> getDataAggregated(@Path("table_id") String tableId,
                                      @Path("group_by") String groupBy,
                                      @Path("agg_func") AggregationFunctions aggregationFunction,
                                      @Path("agg_columns") String columns);

    @GET("getdata/{table_id}/{group_by}/{agg_func}/{agg_columns}/{limit}")
    Call<JsonArray> getDataAggregated(@Path("table_id") String tableId,
                                              @Path("group_by") String groupBy,
                                              @Path("agg_func") AggregationFunctions aggregationFunction,
                                              @Path("agg_columns") String columns,
                                              @Path("limit") Integer limit);

    @GET("joindata/{table1_id}/{table2_id}/{table1_join}/{table2_join}/{group_by}/{agg_func}/{agg_columns}")
    Call<JsonArray> getJoinedDataAggregated(@Path("table1_id") String table1Id,
                                            @Path("table2_id") String table2Id,
                                            @Path("table1_join") String table1Join,
                                            @Path("table2_join") String table2Join,
                                      @Path("group_by") String groupBy,
                                      @Path("agg_func") AggregationFunctions aggregationFunction,
                                      @Path("agg_columns") String columns);

    @GET("joindata/{table1_id}/{table1_id}/{table1_join}/{table2_join}/{group_by}/{agg_func}/{agg_columns}/{limit}")
    Call<JsonArray> getJoinedDataAggregated(@Path("table1_id") String table1Id,
                                            @Path("table2_id") String table2Id,
                                            @Path("table1_join") String table1Join,
                                            @Path("table2_join") String table2Join,
                                            @Path("group_by") String groupBy,
                                            @Path("agg_func") AggregationFunctions aggregationFunction,
                                            @Path("agg_columns") String columns,
                                            @Path("limit") Integer limit);
}
