package com.example.workout.ui.Program;

public class ProgramData {
    String name;
    int week;
    int day;
    String type;

    public ProgramData(String name, int week, int day, String type) {
        this.name = name;
        this.day = day;
        this.week = week;
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public int getWeek() {
        return week;
    }

    public String getName() {
        return name;
    }
    public String getType(){
        return type;
    }
}
