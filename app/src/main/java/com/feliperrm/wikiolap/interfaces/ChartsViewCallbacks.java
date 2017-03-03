package com.feliperrm.wikiolap.interfaces;

import com.feliperrm.wikiolap.models.Chart;

import java.util.ArrayList;

/**
 * Created by felip on 02/03/2017.
 */

public interface ChartsViewCallbacks extends SimpleAsyncCallback{
    public void onDataLoaded(ArrayList<Chart> charts);
}
