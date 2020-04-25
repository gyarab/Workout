package com.example.Workout.ui.TrainingMax;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Workout.DBHandler;
import com.example.Workout.R;

import java.util.ArrayList;




public class MaxesFragment extends Fragment {
    private static MaxesAdapter adapter;
    ArrayList<MaxesData> maxesData;
    ListView listView;
    EditText max_weight;
    int listViewpos = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_maxes, container, false);

        listView = root.findViewById(R.id.listMaxes);
        maxesData = new ArrayList<>();
        final DBHandler dbHandler = new DBHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Switch mySwitch = root.findViewById(R.id.switch1);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int itemsCount = listView.getChildCount();
                //max_weight=root.findViewById(maxesWeight);
                for (int i = 0; i < itemsCount; i++) {
                    listView.getChildAt(i).findViewById(R.id.maxesWeight).setEnabled(isChecked);
                    max_weight = listView.getChildAt(i).findViewById(R.id.maxesWeight);

                    if (isChecked) {
                        max_weight.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                listViewpos = listView.getPositionForView(listView.getFocusedChild());

                                System.out.println(listViewpos);
                                System.out.println("funguje + " + s);
                                TextView a = root.findViewById(R.id.maxesWeight);
                                View w = listView.getChildAt(listViewpos);
                                TextView newMax = w.findViewById(R.id.maxesWeight);
                                TextView newName = w.findViewById(R.id.maxesName);
                                //System.out.println(b.getText().toString());

                                dbHandler.updateMax(newName.getText().toString(), Float.parseFloat(s.toString()));


                            }
                        });
                    }
                }
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
        db.close();
        dbHandler.close();
        adapter = new MaxesAdapter(maxesData, getActivity());
        listView.setAdapter(adapter);
        return root;

    }
}
