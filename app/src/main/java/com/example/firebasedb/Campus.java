package com.example.firebasedb;

public class Campus {
    private String campusName;
    private String location;

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCampusName() {
        return campusName;
    }

    public String getLocation() {
        return location;
    }
}
