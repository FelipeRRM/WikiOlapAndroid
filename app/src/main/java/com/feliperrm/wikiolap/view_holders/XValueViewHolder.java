package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.models.DatasetMetadata;

/**
 * Created by felip on 23/03/2017.
 */

public class XValueViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    Spinner spinner;
    int position;
    ColumnHolder activeColumnHolder;
    CheckChangeInterface checkChangeInterface;

    public XValueViewHolder(View itemView, DatasetMetadata dataset1, final DatasetMetadata dataset2) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        spinner = (Spinner) itemView.findViewById(R.id.spinner);
        if (dataset2 == null) {
            spinner.setVisibility(View.GONE);
        } else {
            spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> yValuesAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_spinner_item, dataset2.getAliasColumns());
            yValuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(yValuesAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                    activeColumnHolder.setSpinnerSelection(pos);
                    activeColumnHolder.setColumn2Id(dataset2.getDbColumns().get(pos));
                    activeColumnHolder.setColumn2DisplayName(dataset2.getAliasColumns().get(pos));
                    if (checkChangeInterface != null) {
                        checkChangeInterface.onSpinnerItemChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    public void bind(final ColumnHolder columnHolder, int position, final CheckChangeInterface checkChangeInterface) {

        this.position = position;
        this.activeColumnHolder = columnHolder;
        this.checkChangeInterface = checkChangeInterface;

        checkBox.setText(columnHolder.getColumn1Id());

        if (columnHolder.isSelected()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                columnHolder.setSelected(checked);
                checkChangeInterface.onCheckChanged();
            }
        });

        spinner.setSelection(columnHolder.getSpinnerSelection());
    }

    public interface CheckChangeInterface {
        public void onCheckChanged();

        public void onSpinnerItemChanged();
    }
}
