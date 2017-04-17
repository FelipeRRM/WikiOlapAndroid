package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;

import java.util.ArrayList;

/**
 * Created by Felipe on 4/17/2017.
 */

public class DynamicSpinnerViewHolder extends RecyclerView.ViewHolder {

    Spinner spinner;
    ChartUpdateInterface chartUpdateInterface;

    public DynamicSpinnerViewHolder(View itemView, ChartUpdateInterface chartUpdateInterface, ArrayList<String> possibleValues) {
        super(itemView);
        spinner = (Spinner) itemView.findViewById(R.id.yValueSpinner);

        ArrayAdapter<String> yValuesAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, possibleValues);
        yValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(yValuesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                metadataProvider.getChartMetadata().getyColumnIds().set(0, metadataProvider.getDataset1Metadata().getDbColumns().get(position));
                metadataProvider.getChartMetadata().getyAlias().set(0, metadataProvider.getDataset1Metadata().getAliasColumns().get(position));
                ChartUpdateInterface chartUpdateInterface = metadataProvider.getChartMetadata().getUpdateInterface();
                if (chartUpdateInterface != null) {
                    chartUpdateInterface.onChartUpdated();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void bind(String s, ArrayList<String> possibleValues, int position) {
        yAxisValueSpinner.setSelection(yValuesAdapter.getCount() - 1);
    }
}
