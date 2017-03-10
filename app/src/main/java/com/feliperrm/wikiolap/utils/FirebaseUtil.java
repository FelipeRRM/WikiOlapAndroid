package com.feliperrm.wikiolap.utils;

import com.feliperrm.wikiolap.models.ChartMetadata;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by felip on 10/03/2017.
 */

public class FirebaseUtil {

    public void createChart(ChartMetadata chartMetadata){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("charts");
        Map<String, ChartMetadata> users = new HashMap<String, ChartMetadata>();
        users.put(chartMetadata.getId(), chartMetadata);
        myRef.setValue(users);
    }

}
