package com.example.workout.ui.TrainingMax;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.workout.R;

import java.util.ArrayList;

public class MaxesAdapter extends ArrayAdapter<MaxesData> {
    private ArrayList<MaxesData> dataSet;
    Context myContext;

    public static class MyViewHolder {
        TextView txtname;
        TextView txtweight;

    }

    public MaxesAdapter(ArrayList<MaxesData> data, Context context) {
        super(context, R.layout.row_maxes_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    private int lastposition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MaxesData data = getItem(position);
        MyViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_maxes_item, parent, false);
            viewHolder.txtname = convertView.findViewById(R.id.maxesName);
            viewHolder.txtweight = convertView.findViewById(R.id.maxesWeight);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        lastposition = position;
        viewHolder.txtname.setText(data.getName());
        // viewHolder.txtweight.setText(String.valueOf(data.getWeight()));
        viewHolder.txtweight.setHint(String.valueOf(data.getWeight()));

        return convertView;
    }
}

