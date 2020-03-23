package com.example.workout.ui.Program;

public class DateData {
    String name;
    int week;
    int day;
    public DateData(String name, int week, int day){
        this.name = name;
        this.week = week;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }
}
