package com.freepadhe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.freepadhe.R;

import java.util.List;

public class FilterSpinnerAdapter extends ArrayAdapter<String> {
    LayoutInflater inflater;
    List<String> mlist;
    Context context;
    public FilterSpinnerAdapter(@NonNull Context context, int resource, List<String> mDistrict) {
        super(context, resource, mDistrict);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mlist = mDistrict;
        this.context=context;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows_dropdown, null, false);
        TextView label = (TextView) row.findViewById(R.id.tvtitle);
        label.setText(mlist.get(position));
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows, null, false);
        TextView label = (TextView) row.findViewById(R.id.tvtitle);
        label.setText(mlist.get(position));
        return row;

    }
}
