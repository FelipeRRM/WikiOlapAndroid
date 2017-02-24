package com.feliperrm.wikiolap.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felip on 23/02/2017.
 */

public class Network {

    private static final String API_URL = "http://wikiolap.feliperrm.com/api/";

    private static Network network;
    private ApiCalls apiCalls;

    private Network() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        HeaderInterceptor headerInterceptor = new HeaderInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addNetworkInterceptor(headerInterceptor)
                .addInterceptor(interceptor);

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiCalls = retrofit.create(ApiCalls.class);

    }

    public static ApiCalls getApiCalls() {
        return getNetwork().apiCalls;
    }

    private static Network getNetwork() {
        if (network == null)
            network = new Network();
        return network;
    }

}
