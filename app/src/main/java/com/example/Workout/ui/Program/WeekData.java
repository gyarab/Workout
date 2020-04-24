package com.example.Workout.ui.Program;

public class WeekData {
    int week;
    int day;
    String set;
    String rep;
    String max;
    String exec;
    boolean type;

    public WeekData(int week, int day, String set, String rep, String exec, String max, boolean type) {
        this.week = week;
        this.day = day;
        this.set = set;
        this.rep = rep;
        this.max = max;
        this.exec = exec;
        this.type = type;
    }

    public Integer getWeek() {
        return week;
    }

    public Integer getDay() {
        return day;
    }

    public String getSet() {
        return set;
    }

    public String getRep() {
        return rep;
    }

    public String getMax() {
        return max;
    }

    public String getExec() {
        return exec;
    }

    public boolean getType() { return type; }
}
