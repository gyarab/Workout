package com.example.workout.ui.TrainingMax;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.workout.DBHandler;
import com.example.workout.R;

import java.util.ArrayList;

public class MaxesFragment extends Fragment {
    private static MaxesAdapter adapter;
    ArrayList<MaxesData> maxesData;
    ListView listView;
    EditText a;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_maxes, container, false);
        listView = root.findViewById(R.id.listMaxes);
        maxesData = new ArrayList<>();
        DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Switch mySwitch = root.findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                a = root.findViewById(R.id.maxesWeight);
                a.setEnabled(isChecked);
            }
        });

        Cursor cursor = db.query(DBHandler.TB_MAXES, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            maxesData.add(new MaxesData(cursor.getString(cursor.getColumnIndex(DBHandler.COL_MAXES_EXEC)), cursor.getFloat(cursor.getColumnIndex(DBHandler.COL_MAXES_WEIGHT))));
            cursor.moveToNext();
        }
        cursor.close();
        adapter = new MaxesAdapter(maxesData, getActivity());
        listView.setAdapter(adapter);
        return root;

    }
}
