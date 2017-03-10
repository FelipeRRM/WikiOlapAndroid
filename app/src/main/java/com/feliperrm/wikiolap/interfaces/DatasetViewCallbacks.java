package com.feliperrm.wikiolap.interfaces;

import com.feliperrm.wikiolap.models.Chart;

import java.util.ArrayList;

/**
 * Created by felip on 02/03/2017.
 */

public interface DatasetViewCallbacks extends SimpleAsyncCallback{
    public void onDataLoaded(ArrayList<Object> dataset);
}
