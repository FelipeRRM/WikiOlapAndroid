package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.Chart;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.view_holders.ChartViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 23/02/2017.
 */

public class ChartsAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    ArrayList<Chart> charts;
    ChartInterface chartInterface;

    public ChartsAdapter(ArrayList<Chart> charts, ChartInterface chartInterface) {
        this.charts = charts;
        this.chartInterface = chartInterface;
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item, parent, false);
        ChartViewHolder viewHolder = new ChartViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        holder.bind(charts.get(position), chartInterface);
    }

    @Override
    public int getItemCount() {
        if (charts != null) {
            return charts.size();
        } else {
            return 0;
        }
    }

    public interface ChartInterface{
        public void onChartClicked(Chart chart);
    }

}
