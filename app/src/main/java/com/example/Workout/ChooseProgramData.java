package com.example.Workout;

public class ChooseProgramData {
    String name;
    int id;
    String type;
    int weeks;
    int days;

    public ChooseProgramData(int id, String name, String type, int weeks, int days) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.weeks = weeks;
        this.days = days;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getDays() {
        return days;
    }

    public int getWeeks() {
        return weeks;
    }
}
