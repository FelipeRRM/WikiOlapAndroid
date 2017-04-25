package com.feliperrm.wikiolap.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.feliperrm.wikiolap.R;
import com.feliperrm.wikiolap.adapters.XValuesAdapter;
import com.feliperrm.wikiolap.enums.AggregationFunctions;
import com.feliperrm.wikiolap.interfaces.MetadataProvider;
import com.feliperrm.wikiolap.models.ColumnHolder;
import com.feliperrm.wikiolap.utils.ChartUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetUpAppearanceFragment extends Fragment {

    /**
     * Views
     */
    private AppCompatCheckBox drawXLines;
    private AppCompatCheckBox drawYLines;
    private TextInputEditText xLabel;
    private TextInputEditText yLabel;
    private TextInputEditText title;
    private TextInputEditText description;

    /**
     * Attributes
     */
    private MetadataProvider metadataProvider;


    public SetUpAppearanceFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        metadataProvider = (MetadataProvider) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_up_appearance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setUpViews();
    }

    private void findViews(View v){
        drawXLines = (AppCompatCheckBox) v.findViewById(R.id.checkbox_draw_x_lines);
        drawYLines = (AppCompatCheckBox) v.findViewById(R.id.checkbox_draw_y_lines);
        xLabel = (TextInputEditText) v.findViewById(R.id.x_label);
        yLabel = (TextInputEditText) v.findViewById(R.id.y_label);
        title = (TextInputEditText) v.findViewById(R.id.title);
        description = (TextInputEditText) v.findViewById(R.id.description);
    }

    private void setUpViews(){
        drawXLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                metadataProvider.getChartMetadata().setDrawXLines(b);
            }
        });

        drawYLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                metadataProvider.getChartMetadata().setDrawYLines(b);
            }
        });

        xLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                metadataProvider.getChartMetadata().setxTitle(editable.toString());
            }
        });

        yLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                metadataProvider.getChartMetadata().setyTitle(editable.toString());
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                metadataProvider.getChartMetadata().setTitle(editable.toString());
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                metadataProvider.getChartMetadata().setDescription(editable.toString());
            }
        });
    }

}
