package com.example.Workout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;



public class ChooseProgramAdapter extends ArrayAdapter<ChooseProgramData> implements View.OnClickListener {
    private ArrayList<ChooseProgramData> dataSet;
    Context myContext;

    private static class ViewHolder {
        TextView textname;
        TextView textid;
        TextView txttype;
    }

    public ChooseProgramAdapter(ArrayList<ChooseProgramData> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object obj = getItem(position);
        ChooseProgramData programData = (ChooseProgramData) obj;
        switch (v.getId()) {
            case R.id.textView:
                Snackbar.make(v, "Program name " + obj, Snackbar.LENGTH_LONG).setAction("No action", null).show();
                break;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChooseProgramData programData = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.textname = convertView.findViewById(R.id.textView);
            viewHolder.txttype = convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textname.setText(programData.getName());
        viewHolder.txttype.setText(programData.getType());
        return convertView;
    }
}

