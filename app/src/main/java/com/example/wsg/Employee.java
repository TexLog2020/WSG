package com.example.wsg;

public class Employee {

    private int kodID;
    private String name;

    private int hours;

    public Employee(){

    }

    public Employee(int kodID, String name, int hours) {
        this.kodID = kodID;
        this.name = name;
        this.hours = hours;
    }

    public int getKodID() {
        return kodID;
    }
    public String getName(){
        return name;
    }
    public int getHours(){
        return hours;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

}

