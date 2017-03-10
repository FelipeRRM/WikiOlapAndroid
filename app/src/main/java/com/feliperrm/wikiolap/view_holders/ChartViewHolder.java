package com.feliperrm.wikiolap.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.ChartsAdapter;
import com.feliperrm.wikiolap.models.Chart;

/**
 * Created by felip on 23/02/2017.
 */

public class ChartViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private ImageView imageView;
    private LinearLayout linearToClick;

    public ChartViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        linearToClick = (LinearLayout) itemView.findViewById(R.id.linear_to_click);
    }

    public void bind(final Chart chart, final ChartsAdapter.ChartInterface chartInterface) {
        title.setText(chart.getTitle());

        Glide.with(imageView.getContext())
                .load(chart.getThumbnail())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        linearToClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chartInterface.onChartClicked(chart);
            }
        });

    }
}
