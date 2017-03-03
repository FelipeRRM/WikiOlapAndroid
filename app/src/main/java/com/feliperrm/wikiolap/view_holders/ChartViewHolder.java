package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.models.Chart;
import com.feliperrm.wikiolap.models.DatasetMetadata;

/**
 * Created by felip on 23/02/2017.
 */

public class ChartViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ImageView imageView;

    public ChartViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(Chart chart) {
        title.setText(chart.getTitle());
        Glide.with(imageView.getContext())
                .load(chart.getThumbnail())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
