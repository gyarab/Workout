package com.example.workout;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class ShowWorkout extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity3);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        recyclerViewAdapter= new MyAdapter(db);
        recyclerView.setAdapter(recyclerViewAdapter);
        Map<Integer,String> itemid = new HashMap<>();
        Cursor cursor2 = db.query(DBHandler.TB_EXERCISES,null,null,null,null,null,null);
        if(cursor2!=null){
            cursor2.moveToFirst();


        }while(!cursor2.isAfterLast()){
            itemid.put(cursor2.getInt(cursor2.getColumnIndex(DBHandler.COL_EXERCISES_ID)),cursor2.getString(cursor2.getColumnIndex(DBHandler.COL_EXERCISES_NAME)));
            // itemid.add(cursor2.getString(cursor2.getColumnIndex(DBHandler.COL_EXERCISES_NAME)));
            cursor2.moveToNext();

        }

        cursor2.close();
    }
}
