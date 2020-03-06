package com.example.workout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Ac2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac2);


       DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBHandler.TB_PROGRAMS,null,null,null,null,null,null);
        Map<Integer,String> itemIds = new HashMap<>();
        if(cursor!=null){
            System.out.println(cursor.getCount());
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            itemIds.put(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_ID)),cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_NAME)));
            cursor.moveToNext();
        }
        cursor.close();

    }
}
