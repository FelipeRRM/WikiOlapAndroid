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

public class DynamicSpinnerAdapter extends RecyclerView.Adapter<DynamicSpinnerViewHolder> implements DynamicSpinnerViewHolder.ItemChangedInterface {

    MetadataProvider metadataProvider;
    ChartUpdateInterface chartUpdateInterface;
    ArrayList<DynamicSpinnerItem> items;

    public DynamicSpinnerAdapter(MetadataProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
        this.chartUpdateInterface = metadataProvider.getChartMetadata().getUpdateInterface();
        this.items = new ArrayList<>();
        int size = metadataProvider.getChartMetadata().getyColumnIds().size();
        int i;
        for (i = 0; i < size; i++) {
            items.add(new DynamicSpinnerItem(metadataProvider.getDataset1Metadata().getDbColumns().size() - 1));
        }
    }

    @Override
    public DynamicSpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_spinner_item, parent, false);
        return new DynamicSpinnerViewHolder(v, chartUpdateInterface, metadataProvider, this);
    }

    @Override
    public void onBindViewHolder(DynamicSpinnerViewHolder holder, int position) {
        holder.bind(items.get(position), position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return metadataProvider.getChartMetadata().getyColumnIds().size();
    }

    public void onItemAdded() {
        items.add(new DynamicSpinnerItem(metadataProvider.getDataset1Metadata().getDbColumns().size() - 1));
        notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public static class DynamicSpinnerItem {
        int position;

        public DynamicSpinnerItem(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


}
