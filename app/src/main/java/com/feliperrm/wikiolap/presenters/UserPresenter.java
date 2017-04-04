package com.feliperrm.wikiolap.presenters;

import android.util.Log;

import com.feliperrm.wikiolap.BuildConfig;
import com.feliperrm.wikiolap.interfaces.UserViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.User;
import com.feliperrm.wikiolap.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by felip on 02/03/2017.
 */

public class UserPresenter {

    UserViewCallbacks callbacks;

    public UserPresenter(UserViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void loadUser(final String email) {
        callbacks.onUserLoadingStarted();
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        myRef.removeEventListener(this);
                        GenericTypeIndicator<HashMap<String, User>> t = new GenericTypeIndicator<HashMap<String, User>>() {
                        };
                        HashMap<String, User> users = dataSnapshot.getValue(t);
                        callbacks.onDataLoaded(users.get(FirebaseUtil.encodeForFirebaseKey(email)), email);
                    }
                    catch (NullPointerException e){
                        if(BuildConfig.DEBUG){
                            e.printStackTrace();
                        }
                        callbacks.onDataLoaded(null, email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    if (error != null) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                    callbacks.onUserError("");
                }
            });
        }
        catch (NullPointerException e){
            callbacks.onDataLoaded(null, email);
        }
    }

}
