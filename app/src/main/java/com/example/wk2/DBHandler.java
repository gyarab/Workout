package com.example.wk2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    //database information
    private static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "WorkoutPrograms";
    public static final String TB_EXERCISES = "exercise";
    public static final String TB_WEEKS = "weeks";
    public static final String TB_PROGRAMS = "programs";

    public static final String COL_EXERCISES_ID = "ExerciseID";
    public static final String COL_EXERCISES_NAME = "ExerciseName";
    public static final String COL_EXERCISES_MUSCLE_TARGET = "Muscle_target";

    public static final String COL_PROGRAM_ID = "ProgramID";
    public static final String COL_PROGRAM_NAME = "ProgramName";

    public static final String COL_WEEK_PID = "ParentID";
    public static final String COL_WEEK_WEEKID = "WeekID";
    public static final String COL_WEEK_DAYID = "DayID";
    public static final String COL_WEEK_EXEC = "Exercise";
    public static final String COL_WEEK_SETNUM = "Set_num";
    public static final String COL_WEEK_REPNUM = "Repetition_num";
    SQLiteDatabase dB;

    //initialization
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DATABASE_VERSION);
        dB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbl_cre = "CREATE TABLE IF NOT EXISTS ";
        String EXEC_TABLE = tbl_cre + "exercise" + "( " + "ExerciseID" + " INTEGER PRIMARY KEY, " + "ExerciseName" + " TEXT, " + "Muscle_target " + " TEXT )";
        String PROGRAM_TABLE = tbl_cre + TB_PROGRAMS + "( " + COL_PROGRAM_ID + "INTEGER PRIMARY KEY, " + COL_PROGRAM_NAME + "TEXT ):";
        String WEEK_TABLE = tbl_cre + TB_WEEKS + "( " + COL_WEEK_PID + "INTEGER PRIMARY KEY," + COL_WEEK_WEEKID + "INTEGER," + COL_WEEK_DAYID + "INTEGER," + COL_WEEK_EXEC + "TEXT, " + COL_WEEK_SETNUM + " TEXT, " + COL_WEEK_REPNUM + "TEXT, " + "FOREIGN KEY(" + COL_WEEK_EXEC + ") REFERENCES " + TB_EXERCISES + "(" + COL_EXERCISES_NAME + ")" + "FOREIGN KEY(" + COL_WEEK_PID + ") REFERENCES " + TB_PROGRAMS + "(" + COL_PROGRAM_ID + "));";

        db.execSQL(EXEC_TABLE);
        db.execSQL(PROGRAM_TABLE);
        db.execSQL(WEEK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String loadHandler(String table_name) {
        String result = "";
        String SELECT_QUERY = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        while (cursor.moveToNext()) {
            int res = cursor.getInt(0);
            String res2 = cursor.getString(2);
            result = String.valueOf(res) + " " + res2 + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public long addExercise(String name, String muscle) {
        ContentValues cv = new ContentValues();
        cv.put(COL_EXERCISES_NAME, name);
        cv.put(COL_EXERCISES_MUSCLE_TARGET, muscle);
        return dB.insert(TB_EXERCISES, null, cv);

    }

    public long addWeek(int weekID, int dayID, String exercise, String sets, String reps) {
        ContentValues cv = new ContentValues();
        cv.put(COL_WEEK_WEEKID, weekID);
        cv.put(COL_WEEK_DAYID, dayID);
        cv.put(COL_WEEK_EXEC, exercise);
        cv.put(COL_WEEK_SETNUM, sets);
        cv.put(COL_WEEK_REPNUM, reps);
        return dB.insert(TB_WEEKS, null, cv);
    }

    public long addProgram(String name) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PROGRAM_NAME, name);
        return dB.insert(TB_PROGRAMS, null, cv);
    }
}


