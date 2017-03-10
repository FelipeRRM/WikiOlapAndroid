package com.feliperrm.wikiolap.enums;

/**
 * Created by felip on 10/03/2017.
 */

public enum AggregationFunctions {
    FunctionAverage("avg"),
    FunctionSum("sum");

    String apiString;

    AggregationFunctions(String apiString) {
        this.apiString = apiString;
    }


    @Override
    public String toString() {
        return apiString;
    }
}
