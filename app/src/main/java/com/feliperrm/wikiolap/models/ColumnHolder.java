package com.feliperrm.wikiolap.models;

import java.io.Serializable;

/**
 * Created by felip on 23/03/2017.
 */

public class ColumnHolder implements Serializable {
    private String column1Id;
    private String column1DisplayName;
    private String column2Id;
    private String column2DisplayName;
    private int spinnerSelection;
    private boolean isSelected;
    private int order;

    public ColumnHolder(String column1Id, String column1DisplayName, String column2Id, String column2DisplayName, int spinnerSelection, boolean isSelected, int order) {
        this.column1Id = column1Id;
        this.column1DisplayName = column1DisplayName;
        this.column2Id = column2Id;
        this.column2DisplayName = column2DisplayName;
        this.spinnerSelection = spinnerSelection;
        this.isSelected = isSelected;
        this.order = order;
    }

    public String getColumn1Id() {
        return column1Id;
    }

    public void setcolumn1Id(String column1Id) {
        this.column1Id = column1Id;
    }

    public String getColumn2Id() {
        return column2Id;
    }

    public void setColumn2Id(String column2Id) {
        this.column2Id = column2Id;
    }

    public String getColumn1DisplayName() {
        return column1DisplayName;
    }

    public void setColumn1DisplayName(String column1DisplayName) {
        this.column1DisplayName = column1DisplayName;
    }

    public String getColumn2DisplayName() {
        return column2DisplayName;
    }

    public void setColumn2DisplayName(String column2DisplayName) {
        this.column2DisplayName = column2DisplayName;
    }

    public int getSpinnerSelection() {
        return spinnerSelection;
    }

    public void setSpinnerSelection(int spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
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
