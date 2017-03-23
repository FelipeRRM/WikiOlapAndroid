package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.view_holders.XValueViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 23/03/2017.
 */

public class XValuesAdapter extends RecyclerView.Adapter<XValueViewHolder> implements XValueViewHolder.CheckChangeInterface {

    ArrayList<ColumnHolder> columns;
    XValuesInterface xValuesInterface;

    public XValuesAdapter(ArrayList<ColumnHolder> columns, XValuesInterface xValuesInterface) {
        this.columns = columns;
        this.xValuesInterface = xValuesInterface;
    }

    @Override
    public XValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.x_value_item, parent, false);
        return new XValueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(XValueViewHolder holder, int position) {
        holder.bind(columns.get(position), this);
    }

    @Override
    public int getItemCount() {
        if (columns != null) {
            return columns.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onCheckChanged() {
        ArrayList<String> xValues = new ArrayList<>();
        for(ColumnHolder columnHolder : columns){
            if(columnHolder.isSelected()){
                xValues.add(columnHolder.getId());
            }
        }
        xValuesInterface.onXValuesChanged(xValues);
    }


    public interface XValuesInterface {
        public void onXValuesChanged(ArrayList<String> xValues);
    }

}
