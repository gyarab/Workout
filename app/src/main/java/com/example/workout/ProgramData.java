package com.example.workout;

public class ProgramData {
    String name;
    int id;
    String type;

    public ProgramData(int id, String name, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
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
}
