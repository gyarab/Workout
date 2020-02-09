package com.example.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    LayoutInflater myInflater;
    List<String> ex_name;
    List<String> reps_sets;
    List<Double> weight;
    public ItemAdapter(Context c, List ex, List rep, List weight){
        myInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ex_name = ex;
        reps_sets=rep;
        this.weight = weight;

    }


    @Override
    public int getCount() {
    return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v   = myInflater.inflate(R.layout.item_adapter_layout,null);
        return v;
    }
}
