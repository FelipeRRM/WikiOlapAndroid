package com.feliperrm.wikiolap.interfaces;

import com.feliperrm.wikiolap.models.DatasetMetadata;

import java.util.ArrayList;

/**
 * Created by felip on 02/03/2017.
 */

public interface DatasetMetadataViewCallbacks extends SimpleAsyncCallback{
    public void onDataLoaded(ArrayList<DatasetMetadata> dataSets);
}
