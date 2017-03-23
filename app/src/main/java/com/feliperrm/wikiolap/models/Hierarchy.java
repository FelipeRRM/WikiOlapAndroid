package com.feliperrm.wikiolap.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by felip on 23/03/2017.
 */

public class Hierarchy implements Serializable {

    String hierarchy;
    ArrayList<Level> levels;

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

}
