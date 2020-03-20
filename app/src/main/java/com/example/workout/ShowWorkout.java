package com.example.workout;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowWorkout extends NavBarActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_workout);
        DBHandler dbHandler = new DBHandler(getApplication().getApplicationContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();
      /*  recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new ShowWorkoutAdapter(db);
        recyclerView.setAdapter(recyclerViewAdapter);*/


        try {
            AssetManager assetManager = getAssets();
            String[] files = assetManager.list("");
            InputStream input = assetManager.open("data");
            Scanner read = new Scanner(input);
            read.useDelimiter("");
            int week;
            int day;
            String exec;
            String set;
            String rep;
            String max;
            List<String> prog = new ArrayList<>();
            read.nextLine();
            //   while(read.hasNextLine()){

            week = Integer.parseInt(read.next());
            day = Integer.parseInt(read.next());
            exec = read.next();
            set = read.next();
            rep = read.next();
            max = read.next();
            prog.add(String.valueOf(week));
            dbHandler.addWeek(week, day, exec, set, rep, max);

            //    }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
