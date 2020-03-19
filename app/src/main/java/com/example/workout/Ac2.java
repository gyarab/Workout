package com.example.workout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Ac2 extends AppCompatActivity {

    private static Ac2Adapter adapter;
    ArrayList<ProgramData> programData;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_layout);
        listView = findViewById(R.id.list1);
        programData = new ArrayList<>();
        DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBHandler.TB_PROGRAMS, null, null, null, null, null, null);
        if (cursor != null) {
            System.out.println(cursor.getCount());
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            programData.add(new ProgramData(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_ID)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_NAME)), cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_TYPE))));
            cursor.moveToNext();
        }
        cursor.close();
        adapter = new Ac2Adapter(programData, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Nav.class);
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
