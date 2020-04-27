package com.example.Workout.ui.Customization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Workout.R;

import java.util.ArrayList;

public class Phase2Adapter extends ArrayAdapter<Phase2Data> {
    private ArrayList<Phase2Data> dataSet;
    Context myContext;

    public static class MyViewHolder {
        TextView name;

    }

    public Phase2Adapter(ArrayList<Phase2Data> data, Context context) {
        super(context, R.layout.row_workout_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(R.layout.phase2_layout, parent, false);
        MyViewHolder viewHolder=  new MyViewHolder();
        viewHolder.name = convertView.findViewById(R.id.phase2row);
        viewHolder.name.setText(dataSet.get(position).getName());
        convertView.setTag(viewHolder);

        return convertView;

    }
}
