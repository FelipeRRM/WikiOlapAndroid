package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.DatasetMetadata;

/**
 * Created by felip on 23/02/2017.
 */

public class DatasetMetadataViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView source;
    private TextView description;

    public DatasetMetadataViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        source = (TextView) itemView.findViewById(R.id.source);
        description = (TextView) itemView.findViewById(R.id.description);
    }

    public void bind(DatasetMetadata datasetMetadata) {
        title.setText(datasetMetadata.getTitle());
        source.setText(datasetMetadata.getSource());
        description.setText(datasetMetadata.getDescription());
    }
}
