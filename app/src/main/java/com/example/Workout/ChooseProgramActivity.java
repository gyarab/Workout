package com.example.Workout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ChooseProgramActivity extends AppCompatActivity {

    private static ChooseProgramAdapter adapter;
    ArrayList<ChooseProgramData> programData;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_layout);
        listView = findViewById(R.id.list1);
        programData = new ArrayList<>();
        final DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBHandler.TB_PROGRAMS, null, null, null, null, null, null);
        if (cursor != null) {
            System.out.println(cursor.getCount());
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            programData.add(new ChooseProgramData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_ID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_NAME)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_TYPE)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_WEEKS)), cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_DAYS))));
            cursor.moveToNext();
        }
        db.close();
        dbHandler.close();
        cursor.close();
        adapter = new ChooseProgramAdapter(programData, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                TextView textView = listView.getChildAt(position).findViewById(R.id.textView);
                String val = textView.getText().toString();
                SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
                Cursor cursor1 = sqLiteDatabase.query(DBHandler.TB_CURR, null, null, null, null, null, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHandler.COL_CURR_PROGRAM, val);
                contentValues.put(DBHandler.COL_CURR_DAY, 1);
                contentValues.put(DBHandler.COL_CURR_WEEK, 1);
                if (cursor1.isAfterLast()) {

                    sqLiteDatabase.insert(DBHandler.TB_CURR, null, contentValues);
                } else {
                    sqLiteDatabase.update(DBHandler.TB_CURR, contentValues, null, null);
                }
                cursor1.close();
                sqLiteDatabase.close();
                Intent intent = new Intent(getApplicationContext(), com.example.Workout.NavBarActivity.class);
                startActivity(intent);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.)
        return true;
    }
}
