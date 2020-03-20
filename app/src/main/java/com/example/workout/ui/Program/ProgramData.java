package com.example.workout.ui.Program;

public class ProgramData {
    int week;
    int day;
    String set;
    String rep;
    String max;
    String exec;

    public ProgramData(int week, int day, String set, String rep, String exec, String max) {
        this.week = week;
        this.day = day;
        this.set = set;
        this.rep = rep;
        this.max = max;
        this.exec = exec;

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
}
