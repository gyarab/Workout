package com.example.workout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Ac2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac2);


       DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBHandler.TB_PROGRAMS,null,null,null,null,null,null);
        List itemIds = new ArrayList<>();
        int iterate = 0;
        if(cursor!=null) {
         cursor.moveToFirst();

            itemIds.add(cursor.getInt(cursor.getColumnIndex(DBHandler.COL_PROGRAM_ID)));

            itemIds.add(cursor.getString(cursor.getColumnIndex(DBHandler.COL_PROGRAM_NAME)));

        }
        List itemid = new ArrayList<>();
        Cursor cursor2 = db.query(DBHandler.TB_EXERCISES,null,null,null,null,null,null);
        if(cursor2!=null){
            cursor2.moveToFirst();
            itemid.add(cursor2.getInt(cursor2.getColumnIndex(DBHandler.COL_EXERCISES_ID)));
            itemid.add(cursor2.getString(cursor2.getColumnIndex(DBHandler.COL_EXERCISES_NAME)));

        }while(cursor2.moveToNext()){

        }
        cursor.close();
        cursor2.close();
        System.out.println(itemid.size());
        System.out.println(itemIds.get(1));
    }
}
