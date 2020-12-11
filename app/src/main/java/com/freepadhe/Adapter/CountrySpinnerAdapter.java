package com.freepadhe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.freepadhe.GsonModel.CountryModels.DatumCountry;
import com.freepadhe.R;

import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<DatumCountry> {
    LayoutInflater inflater;
    List<DatumCountry> mlist;
    Context context;
    public CountrySpinnerAdapter(@NonNull Context context, int resource, List<DatumCountry> mDistrict) {
        super(context, resource, mDistrict);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mlist = mDistrict;
        this.context=context;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows_dropdown, null, false);
        TextView label = (TextView) row.findViewById(R.id.tvtitle);
        label.setText(mlist.get(position).getName().toString());
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows, null, false);
        TextView label = (TextView) row.findViewById(R.id.tvtitle);
        label.setText(mlist.get(position).getName().toString());
        return row;

    }


    @Nullable
    @Override
    public DatumCountry getItem(int position) {
        return super.getItem(position);
    }
}
