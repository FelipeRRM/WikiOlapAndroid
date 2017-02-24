package com.feliperrm.wikiolap.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by felip on 23/02/2017.
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .addHeader("Accept", "application/json")
                //.addHeader("Content-Type", "multipart/form-data");
                ;
        request = builder.build();
        Response response = chain.proceed(request);
        Log.d("Request Headers", request.headers().toString());
        return response;
    }

}
