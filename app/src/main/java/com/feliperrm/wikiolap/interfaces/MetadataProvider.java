package com.feliperrm.wikiolap.interfaces;

import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.models.DatasetMetadata;

/**
 * Created by Felipe on 4/1/2017.
 */

public interface MetadataProvider {
    public DatasetMetadata getDataset1Metadata();

    public DatasetMetadata getDataset2Metadata();

    public ChartMetadata getChartMetadata();
}
