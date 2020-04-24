package com.example.Workout.ui.Progress;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Workout.DBHandler;
import com.example.Workout.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    LineChart lineChart;
    ArrayList<Entry> lineEntryArrayList;
    ArrayList<String> labelsNames;
    ArrayList<ProgressData> progressDataArrayList = new ArrayList<>();
    String show_exercise_progress = "Bench press";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_graph, container, false);


        lineChart = root.findViewById(R.id.barChart);
        DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(dbHandler.TB_PROGRESS, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String date = cursor.getString(cursor.getColumnIndex(dbHandler.COL_PROGRESS_DATE));
            progressDataArrayList.add(new ProgressData(cursor.getString(cursor.getColumnIndex(dbHandler.COL_PROGRESS_DATE)), cursor.getString(cursor.getColumnIndex(dbHandler.COL_PROGRESS_NAME)), cursor.getFloat(cursor.getColumnIndex(dbHandler.COL_PROGRESS_AMOUNT))));
            cursor.moveToNext();
        }
        ArrayList<String> exercises = new ArrayList<>();
        cursor = db.query(dbHandler.TB_MAXES, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exercises.add(cursor.getString(cursor.getColumnIndex(dbHandler.COL_MAXES_EXEC)));
            cursor.moveToNext();
        }
        final Spinner spinner = root.findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, exercises);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView exec = view.findViewById(R.id.spinnerTextView);
                show_exercise_progress = exec.getText().toString();
                lineEntryArrayList = new ArrayList<>();
                for (int i = 0; i < progressDataArrayList.size(); i++) {
                    if (progressDataArrayList.get(i).name.equals(show_exercise_progress)) {
                        String date = progressDataArrayList.get(i).getDay();
                        Float weight = progressDataArrayList.get(i).getWeight();
                        lineEntryArrayList.add(new BarEntry(i, weight));
                    }
                }
                // chart initialization
                LineDataSet lineDataSet = new LineDataSet(lineEntryArrayList, "Weight lifted");
                Description description = new Description();
                description.setText("");
                lineDataSet.setDrawFilled(true);
                lineDataSet.setFillColor(Color.RED);
                lineDataSet.setColor(Color.RED);
                lineChart.setDescription(description);
                lineDataSet.setDrawFilled(true);
                lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineChart.animateXY(2000, 2000, Easing.EaseInSine);

                lineChart.setDrawBorders(true);
                lineChart.setBorderColor(Color.RED);
                lineChart.invalidate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }
}

