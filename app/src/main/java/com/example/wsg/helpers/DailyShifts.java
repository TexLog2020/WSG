package com.example.wsg.helpers;

public class DailyShifts {

    private String day;
    private String morningShift;
    private String afternoonShift;
    private String nightShift;

    public DailyShifts(String day, String morningShift, String afternoonShift, String nightShift) {
        this.day = day;
        this.morningShift = morningShift;
        this.afternoonShift = afternoonShift;
        this.nightShift = nightShift;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMorningShift() {
        return morningShift;
    }

    public void setMorningShift(String morningShift) {
        this.morningShift = morningShift;
    }

    public String getAfternoonShift() {
        return afternoonShift;
    }

    public void setAfternoonShift(String afternoonShift) {
        this.afternoonShift = afternoonShift;
    }

    public String getNightShift() {
        return nightShift;
    }

    public void setNightShift(String nightShift) {
        this.nightShift = nightShift;
    }
}

