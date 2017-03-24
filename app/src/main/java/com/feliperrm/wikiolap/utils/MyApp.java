package com.feliperrm.wikiolap.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.feliperrm.wikiolap.models.User;

/**
 * Created by felip on 10/03/2017.
 */

public class MyApp extends Application {

    public static MyApp app;

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
