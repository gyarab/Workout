package com.example.Workout;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBHandler extends SQLiteOpenHelper {
    //database information
    Context myContext;
    private static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "WorkoutPrograms";
    public static final String TB_EXERCISES = "exercise";
    public static final String TB_WEEKS = "week";
    public static final String TB_PROGRAMS = "programs";
    public static final String TB_MAXES = "maxes";
    public static final String TB_CURR = "current";
    public static final String TB_INCREASES = "increases";
    public static final String TB_PROGRESS = "progress";

    public static final String COL_EXERCISES_ID = "ExerciseID";
    public static final String COL_EXERCISES_NAME = "ExerciseName";

    public static final String COL_PROGRAM_ID = "ProgramID";
    public static final String COL_PROGRAM_NAME = "ProgramName";
    public static final String COL_PROGRAM_TYPE = "ProgramType";
    public static final String COL_PROGRAM_DAYS = "ProgramDays";
    public static final String COL_PROGRAM_WEEKS = "ProgramWeeks";

    public static final String COL_PROGRESS_DATE = "ProgressDate";
    public static final String COL_PROGRESS_NAME = "ProgressName";
    public static final String COL_PROGRESS_AMOUNT = "ProgressAmount";

    public static final String COL_WEEK_WEEKID = "WeekID";
    public static final String COL_WEEK_DAYID = "DayID";
    public static final String COL_WEEK_EXEC = "Exercise";
    public static final String COL_WEEK_SETNUM = "Set_num";
    public static final String COL_WEEK_REPNUM = "Repetition_num";
    public static final String COL_WEEK_MAX = "Percentage_num";
    public static final String COL_WEEK_PRGNAME = "Week_program_name";
    public static final String COL_WEEK_INCREASESET = "Increase_set";

    public static final String COL_MAXES_EXEC = "Exec_max";
    public static final String COL_MAXES_WEIGHT = "Exec_weight";

    public static final String COL_CURR_WEEK = "Curr_week";
    public static final String COL_CURR_DAY = "Curr_day";
    public static final String COL_CURR_PROGRAM = "Curr_program";

    public static final String COL_INC_NAME = "Increase_name";
    public static final String COL_INC_AMOUNT = "Increase_amount";
    SQLiteDatabase dB;
    AssetManager assetManager;

    //initialization
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        assetManager = context.getAssets();
        //dB = getWritableDatabase();
        myContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbl_cre = "CREATE TABLE IF NOT EXISTS ";
        String EXEC_TABLE = tbl_cre + TB_EXERCISES + "( " + COL_EXERCISES_ID + "  INTEGER PRIMARY KEY, " + COL_EXERCISES_NAME + " TEXT )";
        String PROGRAMS_TABLE = tbl_cre + TB_PROGRAMS + "( " + COL_PROGRAM_ID + " INTEGER PRIMARY KEY, " + COL_PROGRAM_NAME + " TEXT ," + COL_PROGRAM_TYPE + " TEXT, " + COL_PROGRAM_WEEKS + " INTEGER, " + COL_PROGRAM_DAYS + " INTEGER )";
        String WEEKS_TABLE = tbl_cre + TB_WEEKS + "( " + "id" + " INTEGER PRIMARY KEY, " + COL_WEEK_PRGNAME + " TEXT, " + COL_WEEK_WEEKID + " INTEGER, " + COL_WEEK_DAYID + " INTEGER," + COL_WEEK_EXEC + " TEXT, " + COL_WEEK_SETNUM + " TEXT, " + COL_WEEK_REPNUM + " TEXT, " + COL_WEEK_MAX + " TEXT, " + COL_WEEK_INCREASESET + " TEXT, " + "FOREIGN KEY(" + COL_WEEK_PRGNAME + ") REFERENCES " + TB_PROGRAMS + "(" + COL_PROGRAM_NAME + ")," + "FOREIGN KEY(" + COL_WEEK_EXEC + ") REFERENCES " + TB_EXERCISES + "(" + COL_EXERCISES_NAME + "));";
        String MAXES_TABLE = tbl_cre + TB_MAXES + "( " + "id" + " INTEGER PRIMARY KEY, " + COL_MAXES_EXEC + " TEXT, " + COL_MAXES_WEIGHT + " FLOAT," + "FOREIGN KEY(" + COL_MAXES_EXEC + ") REFERENCES " + TB_EXERCISES + "(" + COL_EXERCISES_NAME + "));";
        String CURRENT_PROGRAM_TABLE = tbl_cre + TB_CURR + "( " + "id" + " INTEGER PRIMARY KEY, " + COL_CURR_PROGRAM + " TEXT, " + COL_CURR_WEEK + " INTEGER, " + COL_CURR_DAY + " INTEGER, " + "FOREIGN KEY(" + COL_CURR_PROGRAM + ") REFERENCES " + TB_PROGRAMS + "(" + COL_PROGRAM_NAME + "));";
        String PROGRAM_INCREASES_TABLE = tbl_cre + TB_INCREASES + "( " + "id" + " INTEGER PRIMARY KEY, " + COL_INC_NAME + " TEXT, " + COL_INC_AMOUNT + " FLOAT," + "FOREIGN KEY(" + COL_INC_NAME + ") REFERENCES " + TB_EXERCISES + "(" + COL_EXERCISES_NAME + "));";
        String PROGRESS_TABLE = tbl_cre + TB_PROGRESS + "( " + "id" + " INTEGER PRIMARY KEY, " + COL_PROGRESS_DATE + " TEXT, " + COL_PROGRESS_NAME + " TEXT, " + COL_PROGRESS_AMOUNT + " FLOAT, " + "FOREIGN KEY(" + COL_PROGRESS_NAME + ") REFERENCES " + TB_EXERCISES + "(" + COL_EXERCISES_NAME + "));";
        db.execSQL(EXEC_TABLE);
        db.execSQL(PROGRAMS_TABLE);
        db.execSQL(WEEKS_TABLE);
        db.execSQL(MAXES_TABLE);
        db.execSQL(CURRENT_PROGRAM_TABLE);
        db.execSQL(PROGRAM_INCREASES_TABLE);
        db.execSQL(PROGRESS_TABLE);

        ArrayList<String[]> arrayList = new ArrayList<>();
        arrayList.add(new String[]{"Bench press","0"});
        arrayList.add(new String[]{"Overhead press","0"});
        arrayList.add(new String[]{"Squat", "0"});
        arrayList.add(new String[]{"Deadlift", "0"});
        int iinc = 0;
        while(iinc<arrayList.size()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_INC_NAME,arrayList.get(iinc)[0]);
            contentValues.put(COL_INC_AMOUNT,arrayList.get(iinc)[1]);
            db.insert(TB_INCREASES,null,contentValues);
            iinc++;
        }

        //program insert
        List<String> programs = new ArrayList<>();
        programs.add("0");
        programs.add("nSuns");
        programs.add("Powerbuilding");
        programs.add("1");
        programs.add("5");
        programs.add("1");
        programs.add("5/3/1 BBB");
        programs.add("Powerlifting");
        programs.add("4");
        programs.add("5");
        programs.add("2");
        programs.add("Jacked & Tan");
        programs.add("Powerbuilding");
        programs.add("6");
        programs.add("6");


        for (int i = 0; i < programs.size() - 4; i = i + 5) {
            ContentValues val = new ContentValues();
            val.put(COL_PROGRAM_ID, programs.get(i));
            val.put(COL_PROGRAM_NAME, programs.get(i + 1));
            val.put(COL_PROGRAM_TYPE, programs.get(i + 2));
            val.put(COL_PROGRAM_WEEKS, programs.get(i + 3));
            val.put(COL_PROGRAM_DAYS, programs.get(i + 4));
            db.insert(TB_PROGRAMS, null, val);

            System.out.println(programs.size());
        }
        // exercise insert
        List<String> list = new ArrayList<>();
        list.add("Bench press ");
        list.add("Overhead press");
        list.add("Close grip bench");
        list.add("Overhead triceps extension");
        list.add("Triceps pushdown");
        list.add("Incline bench");
        list.add("Chest flyes");
        list.add("Skullcrushers");
        list.add("Push-ups");
        list.add("Pec deck");
        list.add("Cable row");
        list.add("Barbell row");
        list.add("Dumbbell row");
        list.add("Lat pulldown");
        list.add("Pullover");
        list.add("Shrugs");
        list.add("Deadlift");
        list.add("Stiff leg deadlift");
        list.add("Snatch grip deadlift");
        list.add("Sumo deadlift");
        list.add("T-bar row");
        list.add("Machine row");
        list.add("Lateral raises");
        list.add("Rear delt flye");
        list.add("Front delt raises");
        list.add("Ez bar curl");
        list.add("Barbell curl");
        list.add("Dumbbell curl");
        list.add("Spider curl");
        list.add("Incline curl");
        list.add("Preacher curl");
        list.add("Hammer curl");
        list.add("Squat");
        list.add("Front squat");
        list.add("Wide-stance squat");
        list.add("Leg press");
        list.add("Leg extension");
        list.add("Leg curl");
        list.add("Glute raises");
        list.add("Lunges");
        list.add("Straight leg calf raise");
        list.add("Seated calf raise");
        list.add("Abs");
        for (int i = 0; i < list.size(); i++) {
            ContentValues val = new ContentValues();
            val.put(COL_EXERCISES_ID, i + 1);
            val.put(COL_EXERCISES_NAME, list.get(i));
            db.insert(TB_EXERCISES, null, val);
        }

        //Workout insert
        ArrayList<String> workoutData = new ArrayList<>();
        try {
            InputStream inputStream = assetManager.open("data");
            Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter(",");
            System.out.println(scanner.delimiter());
            while (scanner.hasNext()) {
                workoutData.add(scanner.next());
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail");
        }
        for (int i = 0; i < workoutData.size() - 7; i = i + 7) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_WEEK_WEEKID, Integer.parseInt(workoutData.get(i)));
            contentValues.put(COL_WEEK_DAYID, Integer.parseInt(workoutData.get(i + 1)));
            contentValues.put(COL_WEEK_EXEC, workoutData.get(i + 2));
            contentValues.put(COL_WEEK_SETNUM, Integer.parseInt(workoutData.get(i + 3)));
            contentValues.put(COL_WEEK_REPNUM, Integer.parseInt(workoutData.get(i + 4)));
            contentValues.put(COL_WEEK_MAX, Float.parseFloat(workoutData.get(i + 5)));
            contentValues.put(COL_WEEK_INCREASESET, workoutData.get(i + 6));
            contentValues.put(COL_WEEK_PRGNAME, "nSuns");
            db.insert(TB_WEEKS, null, contentValues);
        }


        ContentValues cV = new ContentValues();
        cV.put(COL_MAXES_EXEC, "Bench press");
        cV.put(COL_MAXES_WEIGHT, "0");
        db.insert(TB_MAXES, null, cV);
        ContentValues cV2 = new ContentValues();
        cV2.put(COL_MAXES_EXEC, "Squat");
        cV2.put(COL_MAXES_WEIGHT, "0");
        db.insert(TB_MAXES, null, cV2);
        ContentValues cV3 = new ContentValues();
        cV3.put(COL_MAXES_EXEC, "Deadlift");
        cV3.put(COL_MAXES_WEIGHT, "0");
        db.insert(TB_MAXES, null, cV3);
        ContentValues cV4 = new ContentValues();
        cV4.put(COL_MAXES_EXEC, "Overhead press");
        cV4.put(COL_MAXES_WEIGHT, "0");
        db.insert(TB_MAXES, null, cV4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(dB);
    }


    public long addExercise(int id, String name) {
        ContentValues cv = new ContentValues();
        cv.put(COL_EXERCISES_ID, name);
        cv.put(COL_EXERCISES_NAME, name);
        return dB.insert(TB_EXERCISES, null, cv);

    }

    public long addWeek(int weekID, int dayID, String exercise, String sets, String reps, String max) {
        ContentValues cv = new ContentValues();
        cv.put(COL_WEEK_WEEKID, weekID);
        cv.put(COL_WEEK_DAYID, dayID);
        cv.put(COL_WEEK_EXEC, exercise);
        cv.put(COL_WEEK_SETNUM, sets);
        cv.put(COL_WEEK_REPNUM, reps);
        cv.put(COL_WEEK_MAX, max);
        return dB.insert(TB_WEEKS, null, cv);
    }

    public long addProgram(String name) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PROGRAM_NAME, name);
        return dB.insert(TB_PROGRAMS, null, cv);
    }
    public long addDateOfProgress(String date, String name, float amount, SQLiteDatabase dB){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PROGRESS_DATE,date);
        contentValues.put(COL_PROGRESS_NAME,name);
        contentValues.put(COL_PROGRESS_AMOUNT, amount);
        return dB.insert(TB_PROGRESS,null,contentValues);
    }

    public boolean updateMax(String exec, float newMax) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MAXES_EXEC, exec);
        contentValues.put(COL_MAXES_WEIGHT, newMax);
        String selection = COL_MAXES_EXEC + " LIKE ?";
        String[] selectionArgs = {exec};
        int i = db.update(TB_MAXES, contentValues, selection, selectionArgs);
        return i > 0;
    }

    public boolean updateAmount(String exec, float newAmount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INC_NAME, exec);
        contentValues.put(COL_INC_AMOUNT, newAmount);
        String selection = COL_INC_NAME + " LIKE ?";
        String[] selectionArgs = {exec};
        int i = db.update(TB_INCREASES, contentValues, selection, selectionArgs);
        return i > 0;

    }

    public boolean updateCurr(String week, String day) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CURR_WEEK, week);
        String selection = COL_CURR_WEEK + " LIKE?";
        String[] selectionArgs = {week};
        contentValues.put(COL_CURR_DAY, day);
        int i = db.update(TB_CURR, contentValues, selection, selectionArgs);
        return i > 0;
    }
}



