package com.feliperrm.wikiolap.network;

import com.feliperrm.wikiolap.models.DatasetMetadata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by felip on 23/02/2017.
 */

public interface ApiCalls {

    @GET("searchmetadata/{searchString}")
    Call<ArrayList<DatasetMetadata>> searchMetadata(@Path("searchString") String searchString);

}
