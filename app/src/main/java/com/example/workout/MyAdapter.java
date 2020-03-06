package com.example.workout;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    SQLiteDatabase db;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
        public MyAdapter(SQLiteDatabase sqLiteDatabase){
        db=sqLiteDatabase;
        }
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.acitivity3,parent,false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder h, int position){
        h.textView.setText("");
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
