package com.feliperrm.wikiolap.models;

import java.io.Serializable;

/**
 * Created by felip on 23/03/2017.
 */

public class Level implements Serializable {

    String column;
    int level;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
