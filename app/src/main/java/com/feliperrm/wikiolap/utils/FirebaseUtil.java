package com.feliperrm.wikiolap.utils;

import com.feliperrm.wikiolap.models.Chart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by felip on 10/03/2017.
 */

public class FirebaseUtil {

    public void createChart(Chart chart){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("charts");
        Map<String, Chart> users = new HashMap<String, Chart>();
        users.put(chart.getId(), chart);
        myRef.setValue(users);
    }

}
