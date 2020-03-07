package com.example.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Ac2Adapter extends ArrayAdapter<ProgramData> implements View.OnClickListener {
    private ArrayList<ProgramData> dataSet;
    Context myContext;

    private static class ViewHolder {
        TextView textname;
        TextView textid;
        TextView txttype;
    }

    public Ac2Adapter(ArrayList<ProgramData> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object obj = getItem(position);
        ProgramData programData = (ProgramData) obj;
        switch (v.getId()) {
            case R.id.textView:
                Snackbar.make(v, "Program name " + obj, Snackbar.LENGTH_LONG).setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgramData programData = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.textname = convertView.findViewById(R.id.textView);
            viewHolder.txttype = convertView.findViewById(R.id.textView2);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.textname.setText(programData.getName());
        viewHolder.textid.setText(programData.getType());
        return convertView;
    }
}

