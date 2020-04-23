package com.example.workout.ui.Program;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.workout.DBHandler;
import com.example.workout.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgramFragment extends Fragment {
    private static ProgramAdapter adapter;
    ArrayList<WeekData> workoutData;
    ListView listView;
    ArrayList<DateData> currentData = new ArrayList<>();
    ArrayList<ProgramData> programs = new ArrayList<>();
    Button btn;
    EditText increaseSet;
    boolean change = false;

    public ProgramFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout, container, false);
        listView = root.findViewById(R.id.showWorkoutList);
        btn = root.findViewById(R.id.programButton);
        final DBHandler dbHandler = new DBHandler(getContext());
        final SQLiteDatabase db = dbHandler.getReadableDatabase();
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
                workoutData.add(new WeekData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_WEEKID)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_WEEK_DAYID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_SETNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_REPNUM)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_EXEC)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_MAX)), Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DBHandler.COL_WEEK_INCREASESET)))));
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

        //change day or week of the program once button is clicked
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
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
                                change = true;
                                new Thread() {
                                    @Override
                                    public void run() {
                                        Cursor c = sqLiteDatabase.query(DBHandler.TB_INCREASES, null, null, null, null, null, null);
                                        Cursor c2 = sqLiteDatabase.query(DBHandler.TB_MAXES, null, null, null, null, null, null);
                                        Map<String, Float> map = new HashMap<>();
                                        c2.moveToFirst();
                                        while (!c2.isAfterLast()) {
                                            map.put(c2.getString(c2.getColumnIndex(DBHandler.COL_MAXES_EXEC)), c2.getFloat(c2.getColumnIndex(DBHandler.COL_MAXES_WEIGHT)));
                                            c2.moveToNext();
                                        }
                                        c.moveToFirst();
                                        while (!c.isAfterLast()) {
                                            String name = c.getString(c.getColumnIndex(dbHandler.COL_INC_NAME));
                                            float newMax = map.get(name) + c.getFloat(c.getColumnIndex(dbHandler.COL_INC_AMOUNT));
                                            dbHandler.updateMax(name, newMax);
                                            Date date = Calendar.getInstance().getTime();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                            String formattedDate = simpleDateFormat.format(date);
                                            dbHandler.addDateOfProgress(formattedDate, name, newMax, sqLiteDatabase);
                                            c.moveToNext();
                                        }
                                        c.close();
                                        c2.close();
                                    }
                                }.start();
                                currentData.get(0).week = 1;

                            }
                        }
                        dbHandler.updateCurr(String.valueOf(current_data.get(0).week),String.valueOf(current_data.get(0).day));
                    }
                }
                listView.setSelectionAfterHeaderView();

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(ProgramFragment.this).attach(ProgramFragment.this).commit();
            }
        });
        db.close();
        dbHandler.close();
        return root;
    }
}

