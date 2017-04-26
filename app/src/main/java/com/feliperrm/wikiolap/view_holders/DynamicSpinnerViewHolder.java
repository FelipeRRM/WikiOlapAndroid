package com.feliperrm.wikiolap.view_holders;

import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.DynamicSpinnerAdapter;
import com.feliperrm.wikiolap.interfaces.ChartUpdateInterface;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;

import java.util.ArrayList;

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
    DynamicSpinnerAdapter.DynamicSpinnerItem activeItem;

    public DynamicSpinnerViewHolder(final View itemView, final ChartUpdateInterface updateInterface, MetadataProvider metaProvider, final ItemChangedInterface changedInterface) {
        super(itemView);
        this.metadataProvider = metaProvider;
        this.chartUpdateInterface = updateInterface;
        this.itemChangedInterface = changedInterface;
        spinner = (Spinner) itemView.findViewById(R.id.yValueSpinner);
        ArrayList<String> dbColumns;
        if (metadataProvider.getDataset2Metadata() == null) {
            dbColumns = new ArrayList<>(metadataProvider.getDataset1Metadata().getAliasColumns());
        } else {
            dbColumns = new ArrayList<>();
            for (String str : metadataProvider.getDataset1Metadata().getAliasColumns()) {
                dbColumns.add(str + " (" + metadataProvider.getDataset1Metadata().getTitle() + ")");
            }
            for (String str : metadataProvider.getDataset2Metadata().getAliasColumns()) {
                dbColumns.add(str + " (" + metadataProvider.getDataset2Metadata().getTitle() + ")");
            }

        }
        ArrayAdapter<String> yValuesAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, dbColumns);
        yValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(yValuesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                int db1ColumnsSize = metadataProvider.getDataset1Metadata().getDbColumns().size();
                activeItem.setPosition(pos);
                String table1Id = metadataProvider.getDataset1Metadata().getTableId();
                String table2Id;
                if (metadataProvider.getDataset2Metadata() != null) {
                    table2Id = metadataProvider.getDataset2Metadata().getTableId();
                    String prefix;
                    if (pos > db1ColumnsSize - 1) {
                        prefix = table2Id;
                        int posOnSecond = pos - (db1ColumnsSize);
                        metadataProvider.getChartMetadata().getyColumnIds().set(position, prefix + "_" + metadataProvider.getDataset2Metadata().getDbColumns().get(posOnSecond));
                        metadataProvider.getChartMetadata().getyAlias().set(position, metadataProvider.getDataset2Metadata().getAliasColumns().get(posOnSecond));
                    } else {
                        prefix = table1Id;
                        metadataProvider.getChartMetadata().getyColumnIds().set(position, prefix + "_" + metadataProvider.getDataset1Metadata().getDbColumns().get(pos));
                        metadataProvider.getChartMetadata().getyAlias().set(position, metadataProvider.getDataset1Metadata().getAliasColumns().get(pos));
                    }
                } else {
                    metadataProvider.getChartMetadata().getyColumnIds().set(position, metadataProvider.getDataset1Metadata().getDbColumns().get(pos));
                    metadataProvider.getChartMetadata().getyAlias().set(position, metadataProvider.getDataset1Metadata().getAliasColumns().get(pos));
                }
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
                metadataProvider.getChartMetadata().getyColumnIds().remove(position);
                metadataProvider.getChartMetadata().getyAlias().remove(position);
                metadataProvider.getChartMetadata().getyColors().remove(position);
                itemChangedInterface.onItemRemoved(position);
                chartUpdateInterface.onChartUpdated();
            }
        });
        btnColor = (AppCompatButton) itemView.findViewById(R.id.btnColor);
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AmbilWarnaDialog(itemView.getContext(), metadataProvider.getChartMetadata().getyColors().get(position), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        metadataProvider.getChartMetadata().getyColors().set(position, color);
                        btnColor.setSupportBackgroundTintList(ColorStateList.valueOf(metadataProvider.getChartMetadata().getyColors().get(position)));
                        updateInterface.onChartUpdated();
                    }
                }).show();
            }
        });
    }

    public void bind(DynamicSpinnerAdapter.DynamicSpinnerItem item, int position, int itemCount) {
//        int i = 0;
//        if(metadataProvider.getDataset2Metadata() != null){
//            String table1Id = metadataProvider.getDataset1Metadata().getTableId();
//            String table2Id = metadataProvider.getDataset2Metadata().getTableId();
//            int db1ColumnsSize = metadataProvider.getDataset1Metadata().getDbColumns().size();
//            String prefix;
//            int length = metadataProvider.getDataset1Metadata().getDbColumns().size() + metadataProvider.getDataset2Metadata().getDbColumns().size();
//            for (i = 0; i < length; i++) {
//                if (i > db1ColumnsSize - 1) {
//                    prefix = table2Id;
//                    int posOnSecond = i - (db1ColumnsSize);
//                    if (s.equals(prefix + "_" + metadataProvider.getDataset2Metadata().getDbColumns().get(posOnSecond))) {
//                        break;
//                    }
//                } else {
//                    prefix = table1Id;
//                    if (s.equals(prefix + "_" + metadataProvider.getDataset1Metadata().getDbColumns().get(i))) {
//                        break;
//                    }
//                }
//            }
//        }
//        else {
//            int length = metadataProvider.getDataset1Metadata().getDbColumns().size();
//            for (i = 0; i < length; i++) {
//                if (s.equals(metadataProvider.getDataset1Metadata().getDbColumns().get(i))) {
//                    break;
//                }
//            }
//        }
        activeItem = item;
        spinner.setSelection(item.getPosition());
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
