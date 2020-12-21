package com.example.wsg.helpers;

import java.util.HashMap;

public class ScheduleReader {

    private HashMap <String,String> shift1;
    private HashMap <String,String> shift2;
    private HashMap <String,String> shift3;

    private WeeklyShifts workingEmployees;

    public ScheduleReader(HashMap <String,Object> shifts){
        this.shift1 = (HashMap <String,String>)shifts.get("shift1");
        this.shift2 = (HashMap <String,String>) shifts.get("shift2");
        this.shift3 = (HashMap <String,String>) shifts.get("shift3");

        workingEmployees = new WeeklyShifts(shift1.get("employeeNames"),shift2.get("employeeNames"),shift3.get("employeeNames"));
    }

    public WeeklyShifts getWorkingEmployees(){
        return workingEmployees;
    }
}