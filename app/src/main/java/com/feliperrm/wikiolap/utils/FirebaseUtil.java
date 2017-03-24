package com.feliperrm.wikiolap.utils;

import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by felip on 10/03/2017.
 */

public class FirebaseUtil {

    public static void createChart(ChartMetadata chartMetadata){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("charts");
        Map<String, ChartMetadata> charts = new HashMap<String, ChartMetadata>();
        charts.put(chartMetadata.getId(), chartMetadata);
        myRef.setValue(charts);
    }

    public static void createUser(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        Map<String, User> users = new HashMap<String, User>();
        users.put(encodeForFirebaseKey(user.getEmail()), user);
        myRef.setValue(users);
    }

    public static String encodeForFirebaseKey(String s) {
        return s
                .replace("_", "__")
                .replace(".", "_P")
                .replace("$", "_D")
                .replace("#", "_H")
                .replace("[", "_O")
                .replace("]", "_C")
                .replace("/", "_S")
                ;
    }

    public static String decodeFromFirebaseKey(String s) {
        int i = 0;
        int ni;
        String res = "";
        while ((ni = s.indexOf("_", i)) != -1) {
            res += s.substring(i, ni);
            if (ni + 1 < s.length()) {
                char nc = s.charAt(ni + 1);
                if (nc == '_') {
                    res += '_';
                } else if (nc == 'P') {
                    res += '.';
                } else if (nc == 'D') {
                    res += '$';
                } else if (nc == 'H') {
                    res += '#';
                } else if (nc == 'O') {
                    res += '[';
                } else if (nc == 'C') {
                    res += ']';
                } else if (nc == 'S') {
                    res += '/';
                } else {
                    // this case is due to bad encoding
                }
                i = ni + 2;
            } else {
                // this case is due to bad encoding
                break;
            }
        }
        res += s.substring(i);
        return res;
    }

}
