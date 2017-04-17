package com.feliperrm.wikiolap.view_holders;

import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Felipe on 4/17/2017.
 */

public class DynamicSpinnerViewHolder extends RecyclerView.ViewHolder {

    Spinner spinner;
    AppCompatButton btnDelete;
    AppCompatButton btnColor;
    ChartUpdateInterface chartUpdateInterface;
    MetadataProvider metadataProvider;
    ItemChangedInterface itemChangedInterface;
    int position;

    public DynamicSpinnerViewHolder(final View itemView, final ChartUpdateInterface updateInterface, final MetadataProvider metaProvider, final ItemChangedInterface changedInterface) {
        super(itemView);
        this.metadataProvider = metaProvider;
        this.chartUpdateInterface = updateInterface;
        this.itemChangedInterface = changedInterface;
        spinner = (Spinner) itemView.findViewById(R.id.yValueSpinner);
        ArrayAdapter<String> yValuesAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, metaProvider.getDataset1Metadata().getDbColumns());
        yValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(yValuesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                metadataProvider.getChartMetadata().getyColumnIds().set(position, metadataProvider.getDataset1Metadata().getDbColumns().get(pos));
                metadataProvider.getChartMetadata().getyAlias().set(position, metadataProvider.getDataset1Metadata().getAliasColumns().get(pos));
                ChartUpdateInterface chartUpdateInterface = metadataProvider.getChartMetadata().getUpdateInterface();
                if (chartUpdateInterface != null) {
                    chartUpdateInterface.onChartUpdated();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDelete = (AppCompatButton) itemView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metaProvider.getChartMetadata().getyColumnIds().remove(position);
                metaProvider.getChartMetadata().getyAlias().remove(position);
                metaProvider.getChartMetadata().getyColors().remove(position);
                itemChangedInterface.onItemRemoved(position);
                chartUpdateInterface.onChartUpdated();
            }
        });
        btnColor = (AppCompatButton) itemView.findViewById(R.id.btnColor);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AmbilWarnaDialog(itemView.getContext(), metaProvider.getChartMetadata().getyColors().get(position), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        metaProvider.getChartMetadata().getyColors().set(position, color);
                        btnColor.setSupportBackgroundTintList(ColorStateList.valueOf(metadataProvider.getChartMetadata().getyColors().get(position)));
                        updateInterface.onChartUpdated();
                    }
                }).show();
            }
        });
    }

    public void bind(String s, int position, int itemCount) {
        int length = metadataProvider.getDataset1Metadata().getDbColumns().size();
        int i;
        for (i = 0; i < length; i++) {
            if (s.equals(metadataProvider.getDataset1Metadata().getDbColumns().get(i))) {
                break;
            }
        }
        spinner.setSelection(i);
        this.position = position;
        if (itemCount > 1) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        btnColor.setSupportBackgroundTintList(ColorStateList.valueOf(metadataProvider.getChartMetadata().getyColors().get(position)));
    }

    public interface ItemChangedInterface {
        public void onItemRemoved(int position);
    }
}
