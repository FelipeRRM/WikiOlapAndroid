package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.view_holders.DatasetPreviewViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 27/03/2017.
 */

public class DatasetPreviewAdapter extends RecyclerView.Adapter<DatasetPreviewViewHolder> {

    ArrayList<ArrayList<String>> data;
    int size;
    int numColumns;

    public DatasetPreviewAdapter(ArrayList<ArrayList<String>> data, DatasetMetadata datasetMetadata) {
        this.data = data;
        this.numColumns = datasetMetadata.getDbColumns().size();
        this.size = numColumns * data.size();
    }

    @Override
    public DatasetPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dataset_preview_item, parent, false);
        return new DatasetPreviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DatasetPreviewViewHolder holder, int position) {
        int row = position / numColumns;
        int column = position - (row * numColumns);
        holder.bind(data.get(row).get(column), row);
    }

    @Override
    public int getItemCount() {
        return size;
    }
}
