package com.feliperrm.wikiolap.presenters;

import android.util.Log;

import com.feliperrm.wikiolap.interfaces.ChartsViewCallbacks;
import com.feliperrm.wikiolap.models.ChartMetadata;
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

public class ChartsPresenter {

    ChartsViewCallbacks callbacks;

    public ChartsPresenter(ChartsViewCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void loadCharts(String search){
        callbacks.onLoadingStarted();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("charts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.removeEventListener(this);
                GenericTypeIndicator<HashMap<String, ChartMetadata>> t = new GenericTypeIndicator<HashMap<String, ChartMetadata>>() {};
                HashMap<String,ChartMetadata> charts = dataSnapshot.getValue(t);
                callbacks.onDataLoaded(new ArrayList<ChartMetadata>(charts.values()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if(error!=null) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
                callbacks.onError("");
            }
        });

    }

}
