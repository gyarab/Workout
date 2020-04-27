package com.example.Workout.ui.Customization;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.Workout.DBHandler;
import com.example.Workout.R;
import com.example.Workout.ui.Program.ProgramFragment;
import com.example.Workout.ui.Program.WeekData;

import java.util.ArrayList;

public class CustomizationFragment_2 extends Fragment {
    ArrayList<Phase2Data> dataSet = new ArrayList<>();
    private Phase2Adapter adapter;
    ArrayList<WeekData> weekData;
    int week;
    int day;
    boolean didAdd = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_customization_phase2, container, false);
        final ListView listView = root.findViewById(R.id.customization_exercises);
        final DBHandler dbHandler = new DBHandler(getContext());
        weekData = new ArrayList<>();
        final SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor custom = db.query(dbHandler.TB_CUSTOM, null, null, null, null, null, null);
        custom.moveToFirst();
        while (!custom.isAfterLast()) {
            week = custom.getInt(custom.getColumnIndex(dbHandler.COL_CUSTOM_WEEK));
            day = custom.getInt(custom.getColumnIndex(dbHandler.COL_CUSTOM_DAY));
            custom.moveToNext();
        }
        Cursor cursor = db.query(dbHandler.TB_EXERCISES, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (dataSet.size() == 0) {
            while (!cursor.isAfterLast()) {
                dataSet.add(new Phase2Data(cursor.getString(cursor.getColumnIndex(DBHandler.COL_EXERCISES_NAME))));
                cursor.moveToNext();
            }
            adapter = new Phase2Adapter(dataSet, getActivity());
        }
        cursor.close();
        custom.close();
        db.close();
        listView.setAdapter(adapter);
        final EditText exec_name = root.findViewById(R.id.phase2_name);
        final TextView curr_week = root.findViewById(R.id.phase2_week_info);
        final TextView curr_day = root.findViewById(R.id.phase2_day_info);
        curr_week.setText(String.valueOf(week));
        curr_day.setText(String.valueOf(day));
        final EditText exec_reps = root.findViewById(R.id.phase2_reps);
        final EditText exec_sets = root.findViewById(R.id.phase2_sets);
        final EditText exec_weight = root.findViewById(R.id.phase2_weight);
        final CheckBox checkBox = root.findViewById(R.id.isMaxCheck);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phase2Data data = (Phase2Data) listView.getItemAtPosition(position);
                exec_name.setText(data.getName());
            }
        });
        Button button = root.findViewById(R.id.add_day_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();
                String name = bundle.getString("name");
                String type = bundle.getString("type");
                int max_week = Integer.parseInt(bundle.getString("weeks"));
                int max_day = Integer.parseInt(bundle.getString("days"));
                //
                if (!(weekData.size() == 0)) {
                    if (max_week > week | max_day > day) {
                        if (max_day > day) {
                            day++;
                        } else if (max_week > week) {
                            week++;
                            day = 1;
                        }
                        curr_week.setText(String.valueOf(week));
                        curr_day.setText(String.valueOf(day));
                        checkBox.setChecked(false);
                        dbHandler.updateDate(week, day);
                        didAdd=false;
                    } else if(didAdd){
                        dbHandler.addProgram(name, type, max_week, max_day);
                        for(int i=0;i<weekData.size();i++){
                            dbHandler.addWeek(name, weekData.get(i).getWeek(), weekData.get(i).getDay(), weekData.get(i).getExec(), weekData.get(i).getSet(), weekData.get(i).getRep(), weekData.get(i).getMax(), String.valueOf(weekData.get(i).getType()));
                        }
                        Toast.makeText(getContext(),"Successfully added",Toast.LENGTH_LONG).show();
                        ProgramFragment fragment2 = new ProgramFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, fragment2, "tag").addToBackStack(null)
                                .commit();
                    }
                }
            }

        });
        Button check_button = root.findViewById(R.id.add_exec_button);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exec_name.getText().toString().equals("") | exec_reps.getText().toString().equals("") | exec_sets.getText().toString().equals("") | exec_weight.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please fill all values", Toast.LENGTH_LONG).show();

                } else {
                    weekData.add(new WeekData(week, day, exec_sets.getText().toString(), exec_reps.getText().toString(), exec_name.getText().toString(), exec_weight.getText().toString(), checkBox.isChecked()));
                    exec_name.setText("");
                    exec_reps.setText("");
                    exec_sets.setText("");
                    exec_weight.setText("");
                    didAdd=true;
                }
            }
        });
        return root;

    }
}
