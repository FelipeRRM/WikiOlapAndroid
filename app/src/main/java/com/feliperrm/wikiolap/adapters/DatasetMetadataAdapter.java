package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.view_holders.DatasetMetadataViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 23/02/2017.
 */

public class DatasetMetadataAdapter extends RecyclerView.Adapter<DatasetMetadataViewHolder> {

    ArrayList<DatasetMetadata> datasets;

    public DatasetMetadataAdapter(ArrayList<DatasetMetadata> datasets) {
        this.datasets = datasets;
    }

    @Override
    public DatasetMetadataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dataset_metadata_item, parent, false);
        DatasetMetadataViewHolder viewHolder = new DatasetMetadataViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DatasetMetadataViewHolder holder, int position) {
        holder.bind(datasets.get(position));
    }

    @Override
    public int getItemCount() {
        if (datasets != null) {
            return datasets.size();
        } else {
            return 0;
        }
    }
}
