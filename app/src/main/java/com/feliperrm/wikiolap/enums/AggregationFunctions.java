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

    public static AggregationFunctions getFunctionFromString(String function) {
        if (function.equalsIgnoreCase(FunctionAverage.apiString)) {
            return FunctionAverage;
        } else if (function.equalsIgnoreCase(FunctionSum.apiString)) {
            return FunctionSum;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return apiString;
    }
}
