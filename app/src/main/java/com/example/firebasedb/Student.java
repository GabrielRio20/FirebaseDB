package com.example.firebasedb;

public class Student {
    private String studentName;
    private String studentAddress;

    public String getName() {
        return studentName;
    }

    public String getAddress() {
        return studentAddress;
    }

    public void setName(String name) {
        this.studentName = name;
    }

    public void setAddress(String address) {
        this.studentAddress = address;
    }
}
