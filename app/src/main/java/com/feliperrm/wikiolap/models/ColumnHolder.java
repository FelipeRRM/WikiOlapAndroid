package com.feliperrm.wikiolap.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by felip on 23/03/2017.
 */

public class ColumnHolder implements Serializable {
    private String id;
    private boolean isSelected;
    private int order;

    public ColumnHolder(String id, boolean isSelected, int order) {
        this.id = id;
        this.isSelected = isSelected;
        this.order = order;
    }

    public static ArrayList<ColumnHolder> getColumnHoldersFromStrings(ArrayList<String> strings){
        int i;
        ArrayList<ColumnHolder> columnHolders = new ArrayList<>();
        for(i = 0; i<strings.size(); i++){
            columnHolders.add(new ColumnHolder(strings.get(i), i == 0, i));
        }
        return columnHolders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
