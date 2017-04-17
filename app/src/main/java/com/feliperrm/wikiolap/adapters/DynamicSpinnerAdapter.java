package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.view_holders.DynamicSpinnerViewHolder;

/**
 * Created by Felipe on 4/17/2017.
 */

public class DynamicSpinnerAdapter extends RecyclerView.Adapter<DynamicSpinnerViewHolder> implements DynamicSpinnerViewHolder.ItemChangedInterface {

    MetadataProvider metadataProvider;
    ChartUpdateInterface chartUpdateInterface;

    public DynamicSpinnerAdapter(MetadataProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
        this.chartUpdateInterface = metadataProvider.getChartMetadata().getUpdateInterface();
    }

    @Override
    public DynamicSpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_spinner_item, parent, false);
        return new DynamicSpinnerViewHolder(v, chartUpdateInterface, metadataProvider, this);
    }

    @Override
    public void onBindViewHolder(DynamicSpinnerViewHolder holder, int position) {
        holder.bind(metadataProvider.getChartMetadata().getyColumnIds().get(position), position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return metadataProvider.getChartMetadata().getyColumnIds().size();
    }

    @Override
    public void onItemRemoved(int position) {
        notifyDataSetChanged();
    }

}
