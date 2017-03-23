package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.ColumnHolder;

/**
 * Created by felip on 23/03/2017.
 */

public class XValueViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;

    public XValueViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }

    public void bind(final ColumnHolder columnHolder, final CheckChangeInterface checkChangeInterface) {

        checkBox.setText(columnHolder.getId());

        if(columnHolder.isSelected()){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                columnHolder.setSelected(checked);
                checkChangeInterface.onCheckChanged();
            }
        });
    }

    public interface CheckChangeInterface {
        public void onCheckChanged();
    }
}
