package com.feliperrm.wikiolap.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by felip on 10/03/2017.
 */

public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
