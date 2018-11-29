package com.yourbcabus.yourbcabus_android;

import java.util.Calendar;

public class BusModel {

    private String name;
    private String location;
    private String invalidateTime;
    private String id;

    public BusModel(String name, String location, String invalidateTime, String id) {
        this.name = name;
        this.location = location;
        this.invalidateTime = invalidateTime;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInvalidateTime() {
        return invalidateTime;
    }

    public void setInvalidateTime(String invalidateTime) {
        this.invalidateTime = invalidateTime;
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }
}