package com.feliperrm.wikiolap.models;

import java.io.Serializable;

/**
 * Created by Felipe on 3/30/2017.
 */

public class TimeStamp implements Serializable {
    private long $date;

    public long get$date() {
        return $date;
    }

    public void set$date(long $date) {
        this.$date = $date;
    }
}
