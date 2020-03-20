package com.example.workout.ui.Program;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.workout.R;

import java.util.ArrayList;


public class ProgramAdapter extends ArrayAdapter<ProgramData> {
    private ArrayList<ProgramData> dataSet;
    Context myContext;

    public static class MyViewHolder {
        TextView textweek;
        TextView textday;
        TextView textexec;
        TextView textset;
        TextView textrep;
        TextView textmax;

    }

    public ProgramAdapter(ArrayList<ProgramData> data, Context context) {
        super(context, R.layout.row_workout_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgramData data = getItem(position);
        MyViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_workout_item, parent, false);
            viewHolder.textexec = convertView.findViewById(R.id.textViewExec);
            viewHolder.textset = convertView.findViewById(R.id.textViewSet);
            viewHolder.textrep = convertView.findViewById(R.id.textViewRep);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.textexec.setText(data.getExec());
        viewHolder.textset.setText(data.getSet());
        viewHolder.textrep.setText(data.getRep());

        return convertView;
    }
}
