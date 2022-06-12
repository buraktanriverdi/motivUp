package com.example.motivup.database;

public class Target {
    private int target_id;
    private String name;
    private String type;
    private String lastDate;

    public Target() {}

    public Target(int target_id, String name, String type) {
        this.target_id = target_id;
        this.name = name;
        this.type = type;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
