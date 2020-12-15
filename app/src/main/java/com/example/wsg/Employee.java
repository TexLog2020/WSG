package com.example.wsg;

public class Employee {

    String kodID;
    String name;
    String hours;

    public Employee(){

    }

    public Employee(String kodID, String name, String hours) {
        this.kodID = kodID;
        this.name = name;
        this.hours = hours;
    }

    public String getKodID() {
        return kodID;
    }
    public String getName(){
        return name;
    }
    public String getHours(){
        return hours;
    }

    public void setName(String name) {
        this.name = name;
    }
}
