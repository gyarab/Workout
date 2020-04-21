package com.example.workout.ui.Program;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.workout.DBHandler;
import com.example.workout.R;

import java.util.ArrayList;


public class ProgramAdapter extends ArrayAdapter<WeekData> {
    private ArrayList<WeekData> dataSet;
    Context myContext;

    public static class MyViewHolder {
        TextView textweek;
        TextView textday;
        TextView textexec;
        TextView textset;
        TextView textrep;
        TextView textweight;
        EditText textincweight;

    }

    public ProgramAdapter(ArrayList<WeekData> data, Context context) {
        super(context, R.layout.row_workout_item, data);
        this.dataSet = data;
        this.myContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeekData data = getItem(position);
        MyViewHolder viewHolder;
        float getWeight = 0;
        ArrayList<String> getMax = new ArrayList<>();
        DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        //getting max weight that the user sets
        String b = String.join(" ", data.getExec());
        if (b.toLowerCase().contains("squat")) {
            b = "Squat";
        } else if (b.toLowerCase().contains("deadlift")) {
            b = "Deadlift";
        } else {
            b = "Bench press";
        }
        String selectquery = "SELECT " + dbHandler.COL_MAXES_WEIGHT + " FROM " + dbHandler.TB_MAXES + " WHERE " + String.join(" ", dbHandler.COL_MAXES_EXEC) + "=" + "'" + b + "'";
        Cursor c = db.rawQuery(selectquery, null);
        if (c.moveToFirst()) {
            getMax.add(c.getString(c.getColumnIndex(dbHandler.COL_MAXES_WEIGHT)));

        }

        c.close();
        db.close();
        dbHandler.close();
        // if (convertView == null) {
        viewHolder = new MyViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        getWeight = (Float.parseFloat(getMax.get(0)) * Float.parseFloat(data.getMax())) / 100;
        if(data.getType()) {
            convertView = inflater.inflate(R.layout.row_workout_weightincrease,parent,false);
            viewHolder.textexec = convertView.findViewById(R.id.textViewExec2);
            viewHolder.textset = convertView.findViewById(R.id.textViewSets2);
            viewHolder.textincweight = convertView.findViewById(R.id.textViewReps2);
            viewHolder.textweight = convertView.findViewById(R.id.textViewAmount2);
            viewHolder.textincweight.setHint(data.getRep() + "+");
        }else if(!data.getType()) {
            convertView = inflater.inflate(R.layout.row_workout_item, parent, false);
            viewHolder.textexec = convertView.findViewById(R.id.textViewExec);
            viewHolder.textset = convertView.findViewById(R.id.textViewSets);
            viewHolder.textrep = convertView.findViewById(R.id.textViewReps);
            viewHolder.textweight = convertView.findViewById(R.id.textViewAmount);
            viewHolder.textrep.setText(data.getRep());
        }
        //
        convertView.setTag(viewHolder);
      /*  } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }*/
        String a = String.valueOf(getWeight);
        viewHolder.textexec.setText(data.getExec());
        viewHolder.textset.setText(data.getSet());
        viewHolder.textweight.setText(a);
        return convertView;
    }
}
