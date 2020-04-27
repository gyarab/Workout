package com.example.Workout.ui.Customization;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.Workout.DBHandler;
import com.example.Workout.R;

import java.util.ArrayList;

public class CustomizationFragment_2 extends Fragment {
    ArrayList<Phase2Data> dataSet = new ArrayList<>();
    private Phase2Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customization_phase2, container, false);
        ListView listView = root.findViewById(R.id.customization_exercises);
        DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(dbHandler.TB_EXERCISES, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (dataSet.size() == 0) {
            while (cursor.isAfterLast()) {
                dataSet.add(new Phase2Data(cursor.getString(cursor.getColumnIndex(DBHandler.COL_EXERCISES_NAME))));
            }
            adapter = new Phase2Adapter(dataSet, getActivity());
        }
        listView.setAdapter(adapter);
        EditText exec_name = root.findViewById(R.id.phase2_name);
        EditText exec_reps = root.findViewById(R.id.phase2_reps);
        EditText exec_sets = root.findViewById(R.id.phase2_sets);
        SearchView searchView = root.findViewById(R.id.searchView);
        return root;
    }
}
