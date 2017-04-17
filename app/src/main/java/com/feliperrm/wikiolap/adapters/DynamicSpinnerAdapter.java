package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.view_holders.DynamicSpinnerViewHolder;

import java.util.ArrayList;

/**
 * Created by Felipe on 4/17/2017.
 */

public class DynamicSpinnerAdapter extends RecyclerView.Adapter<DynamicSpinnerViewHolder> {

    MetadataProvider metadataProvider;
    ArrayList<String> possibleValues;
    ChartUpdateInterface chartUpdateInterface;

    @Override
    public DynamicSpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_spinner_item, parent, false);
        return new DynamicSpinnerViewHolder(v, chartUpdateInterface, possibleValues);
    }

    @Override
    public void onBindViewHolder(DynamicSpinnerViewHolder holder, int position) {
        holder.bind(metadataProvider.getChartMetadata().getyColumnIds().get(position), possibleValues, position);
    }

    @Override
    public int getItemCount() {
        return metadataProvider.getChartMetadata().getyColumnIds().size();
    }
}
