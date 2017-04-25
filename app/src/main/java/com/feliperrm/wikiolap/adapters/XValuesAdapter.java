package com.feliperrm.wikiolap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.models.DatasetMetadata;
import com.feliperrm.wikiolap.view_holders.XValueViewHolder;

import java.util.ArrayList;

/**
 * Created by felip on 23/03/2017.
 */

public class XValuesAdapter extends RecyclerView.Adapter<XValueViewHolder> implements XValueViewHolder.CheckChangeInterface {

    ArrayList<ColumnHolder> columns;
    DatasetMetadata dataset1;
    DatasetMetadata dataset2;
    XValuesInterface xValuesInterface;

    public XValuesAdapter(DatasetMetadata dataset1, DatasetMetadata dataset2, XValuesInterface xValuesInterface) {
        this.dataset1 = dataset1;
        this.dataset2 = dataset2;
        this.xValuesInterface = xValuesInterface;
        this.columns = new ArrayList<>();
        int size = dataset1.getDbColumns().size();
        for(int i = 0; i<size;i++){
            String db1 = dataset1.getDbColumns().get(i);
            String alias1 = dataset1.getAliasColumns().get(i);
            String db2 = null;
            String alias2 = null;
            if(dataset2 != null){
                if(dataset2.getDbColumns().size() > 0){
                    db2 = dataset2.getDbColumns().get(0);
                    alias2 = dataset2.getAliasColumns().get(0);
                }
            }
            ColumnHolder columnHolder = new ColumnHolder(db1, alias1, db2, alias2, 0, i == 0, i);
            columns.add(columnHolder);
        }
    }

    @Override
    public XValueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.x_value_item, parent, false);
        return new XValueViewHolder(v, dataset1, dataset2);
    }

    @Override
    public void onBindViewHolder(XValueViewHolder holder, int position) {
        holder.bind(columns.get(position), position, this);
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
        updateValues();
    }

    @Override
    public void onSpinnerItemChanged() {
        updateValues();
    }

    private void updateValues() {
        ArrayList<String> x1Values = new ArrayList<>();
        ArrayList<String> x2values = null;
        if (dataset2 != null) {
            x2values = new ArrayList<>();
        }
        for (ColumnHolder columnHolder : columns) {
            if (columnHolder.isSelected()) {
                x1Values.add(columnHolder.getColumn1Id());
                if(x2values != null && columnHolder.getColumn2Id() != null){
                    x2values.add(columnHolder.getColumn2Id());
                }
            }
        }
        xValuesInterface.onXValuesChanged(x1Values, x2values);
    }


    public interface XValuesInterface {
        public void onXValuesChanged(ArrayList<String> x1Values, ArrayList<String> x2Values);
    }

}
