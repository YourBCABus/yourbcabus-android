package com.yourbcabus.yourbcabus_android;

import java.util.Calendar;

public class BusModel {

    private String name;
    private String location;
    private String invalidateTime;

    public BusModel(String name, String location, String invalidateTime) {
        this.name = name;
        this.location = location;
        this.invalidateTime = invalidateTime;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getInvalidateTime() {
        return invalidateTime;
    }
}