package com.feliperrm.wikiolap.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.feliperrm.wikiolap.models.User;
import com.google.gson.Gson;

/**
 * Created by felip on 10/03/2017.
 */

public class MyApp extends Application {

    public static MyApp app;

    private User loggedUser;

    private static final String LOGGED_USER_KEY = "loggeduserkey";

    public User getLoggedUser() {
        if(loggedUser == null){
            loggedUser = new Gson().fromJson(PersistanceUtil.loadSharedPreference(app, LOGGED_USER_KEY, null), User.class);
        }
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
        String userStr = new Gson().toJson(loggedUser);
        PersistanceUtil.saveSharedPreferences(app, LOGGED_USER_KEY, userStr);
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
