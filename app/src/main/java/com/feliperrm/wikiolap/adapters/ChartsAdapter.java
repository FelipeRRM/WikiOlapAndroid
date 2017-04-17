package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ChartMetadata;
import com.feliperrm.wikiolap.view_holders.ChartViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 23/02/2017.
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    ArrayList<ChartMetadata> chartMetadatas;
    ChartInterface chartInterface;
    boolean isHorizontal;

    public ChartsAdapter(ArrayList<ChartMetadata> chartMetadatas, boolean horizontal, ChartInterface chartInterface) {
        this.chartMetadatas = chartMetadatas;
        this.chartInterface = chartInterface;
        this.isHorizontal = horizontal;
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(!isHorizontal) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item_vertical, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item_horizontal, parent, false);
        }
        ChartViewHolder viewHolder = new ChartViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        holder.bind(chartMetadatas.get(position), chartInterface);
    }

    @Override
    public int getItemCount() {
        if (chartMetadatas != null) {
            return chartMetadatas.size();
        } else {
            return 0;
        }
    }

    public interface ChartInterface{
        public void onChartClicked(ChartMetadata chartMetadata);
    }

}
