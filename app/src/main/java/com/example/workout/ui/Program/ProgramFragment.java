package com.example.workout.ui.Program;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.workout.DBHandler;
import com.example.workout.R;

import java.util.ArrayList;

public class ProgramFragment extends Fragment {
    private static ProgramAdapter adapter;
    ArrayList<WeekData> workoutData;
    ListView listView;
    ArrayList<DateData> currentData = new ArrayList<>();
    ArrayList<ProgramData> programs = new ArrayList<>();
    Button btn;

    public ProgramFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        listView = root.findViewById(R.id.showWorkoutList);
        btn = root.findViewById(R.id.programButton);
        final DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        System.out.println("ive been called again");
        final Cursor cursor_current = db.query(DBHandler.TB_CURR, null, null, null, null, null, null);
        if (cursor_current.moveToFirst()) {
            currentData.add(new DateData(cursor_current.getString(cursor_current.getColumnIndex(DBHandler.COL_CURR_PROGRAM)), cursor_current.getInt(cursor_current.getColumnIndex(DBHandler.COL_CURR_WEEK)), cursor_current.getInt(cursor_current.getColumnIndex(DBHandler.COL_CURR_DAY))));

        }
        cursor_current.close();
        if (workoutData == null) {
            workoutData = new ArrayList<>();
            Cursor cursor = db.query(DBHandler.TB_WEEKS, null, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                workoutData.add(new WeekData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_WEEKID)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_DAYID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_SETNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_REPNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_EXEC)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_MAX))));
                cursor.moveToNext();
            }
            cursor.close();
        }
        final ArrayList<WeekData> current_data = new ArrayList<>();
        int i = 0;
        while (i < workoutData.size()) {
            if (currentData.get(0).getWeek() == workoutData.get(i).getWeek() && currentData.get(0).getDay() == workoutData.get(i).getDay()) {
                current_data.add(workoutData.get(i));

            }
            i++;
        }
        adapter = new ProgramAdapter(current_data, getActivity());
        listView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
                if (programs.size() == 0) {
                    Cursor cursor_programs = sqLiteDatabase.query(DBHandler.TB_PROGRAMS, null, null, null, null, null, null);
                    cursor_programs.moveToFirst();
                    while (!cursor_programs.isAfterLast()) {
                        programs.add(new ProgramData(cursor_programs.getString(cursor_programs.getColumnIndex(DBHandler.COL_PROGRAM_NAME)), cursor_programs.getInt(cursor_programs.getColumnIndex(DBHandler.COL_PROGRAM_WEEKS)), cursor_programs.getInt(cursor_programs.getColumnIndex(DBHandler.COL_PROGRAM_DAYS)), cursor_programs.getString(cursor_programs.getColumnIndex(DBHandler.COL_PROGRAM_TYPE))));
                        cursor_programs.moveToNext();
                    }
                    cursor_programs.close();
                }
                for (int i = 0; i < programs.size(); i++) {
                    if (programs.get(i).getName().equals(currentData.get(0).getName())) {
                        if (currentData.get(0).getDay() < programs.get(i).getDay()) {
                            currentData.get(0).day += 1;
                        } else {
                            currentData.get(0).day = 1;
                            if (currentData.get(0).getWeek() < programs.get(i).getWeek()) {
                                currentData.get(0).week += 1;

                            } else {
                                currentData.get(0).week = 1;
                            }
                        }

                    }
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(ProgramFragment.this).attach(ProgramFragment.this).commit();
            }
        });
        db.close();
        dbHandler.close();
        return root;
    }
}

