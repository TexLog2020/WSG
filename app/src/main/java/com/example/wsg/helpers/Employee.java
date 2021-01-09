package com.example.wsg.helpers;

public class Employee {

    private int kodID;
    private String name;
    private int weeksOff;
    private int hours;

    public Employee(){

    }


    public Employee(int kodID, String name, int hours, int weeksOff) {
        this.kodID = kodID;
        this.name = name;
        this.hours = hours;
        this.weeksOff = weeksOff;
    }

    public Employee(int kodID, String name, int weeksOff) {
        this.kodID = kodID;
        this.name = name;
        this.hours = 0;
        this.weeksOff = weeksOff;

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

    public int getWeeksOff() {
        return weeksOff;
    }

    public void setWeeksOff(int weeksOff) {
        this.weeksOff = weeksOff;
    }
}
