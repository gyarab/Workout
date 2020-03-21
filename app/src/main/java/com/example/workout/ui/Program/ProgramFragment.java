package com.example.workout.ui.Program;

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

import java.util.ArrayList;

public class ProgramFragment extends Fragment {
    private static ProgramAdapter adapter;
    ArrayList<ProgramData> workoutData;
    ListView listView;

    public ProgramFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        listView = root.findViewById(R.id.showWorkoutList);
        DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor2 = db.query(DBHandler.TB_CURR, null, null, null, null, null, null);
        ArrayList<Integer> curr_date = new ArrayList<>();
        if (cursor2 != null) {
            cursor2.moveToFirst();
        }


        while (!cursor2.isAfterLast()) {
            curr_date.add(cursor2.getInt(cursor2.getColumnIndex(DBHandler.COL_CURR_WEEK)));
            curr_date.add(cursor2.getInt(cursor2.getColumnIndex(DBHandler.COL_CURR_DAY)));
            cursor2.moveToNext();
        }
        cursor2.close();
        if (workoutData == null) {
            workoutData = new ArrayList<>();

            String selection = curr_date.get(0) + "=" + DBHandler.COL_WEEK_WEEKID + " AND WHERE " + curr_date.get(1) + "=" + DBHandler.COL_WEEK_DAYID;
            String[] selectionArgs = {curr_date.get(0).toString(), curr_date.get(1).toString()};
            Cursor cursor = db.query(DBHandler.TB_WEEKS, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                workoutData.add(new ProgramData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_WEEKID)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_DAYID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_SETNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_REPNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_EXEC)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_MAX))));
                cursor.moveToNext();
            }
            cursor.close();
        }
        ArrayList<ProgramData> curr_data = new ArrayList<>();
        int i = 0;
        while (i < workoutData.size()) {
            if (curr_date.get(0) == workoutData.get(i).getWeek() && curr_date.get(1) == workoutData.get(i).getDay()) {
                curr_data.add(workoutData.get(i));

            }
            i++;
        }
        adapter = new ProgramAdapter(curr_data, getActivity());
        listView.setAdapter(adapter);

        return root;
    }
}

