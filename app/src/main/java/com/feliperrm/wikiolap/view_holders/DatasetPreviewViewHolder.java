package com.feliperrm.wikiolap.view_holders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;

/**
 * Created by felip on 27/03/2017.
 */

public class DatasetPreviewViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public DatasetPreviewViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }

    public void bind(String string, int row) {
        if (row == 0) {
            textView.setTypeface(null, Typeface.BOLD);
        } else {
            textView.setTypeface(null, Typeface.NORMAL);
        }
        textView.setText(string);
    }
}
