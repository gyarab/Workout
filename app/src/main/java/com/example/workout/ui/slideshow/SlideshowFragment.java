package com.example.workout.ui.slideshow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.workout.DBHandler;
import com.example.workout.R;
import com.example.workout.ShowWorkoutAdapter;
import com.example.workout.ShowWorkoutData;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {
    private static ShowWorkoutAdapter adapter;
    ArrayList<ShowWorkoutData> workoutData;
    ListView listView;
    public SlideshowFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        listView =  root.findViewById(R.id.showWorkoutList);

            workoutData = new ArrayList<>();
            DBHandler dbHandler = new DBHandler(getContext());
            SQLiteDatabase db = dbHandler.getReadableDatabase();
            Cursor cursor = db.query(DBHandler.TB_WEEKS, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                workoutData.add(new ShowWorkoutData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_WEEKID)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_DAYID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_SETNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_REPNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_EXEC)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_MAX))));
                cursor.moveToNext();
            }
            cursor.close();
            adapter = new ShowWorkoutAdapter(workoutData, getActivity());
            listView.setAdapter(adapter);

            return root;
        }
    }

