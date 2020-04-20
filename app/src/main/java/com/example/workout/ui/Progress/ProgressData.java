package com.example.workout.ui.Progress;

public class ProgressData {
    String day;
    String name;
    float weight;

    public ProgressData(String day, String name, float weight) {
        this.day = day;
        this.name = name;
        this.weight = weight;
    }

    public String getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }
}

