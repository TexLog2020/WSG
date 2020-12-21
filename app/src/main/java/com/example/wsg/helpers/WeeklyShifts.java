package com.example.wsg.helpers;

public class WeeklyShifts {

    private String morningShift;
    private String afternoonShift;
    private String nightShift;

    public WeeklyShifts(String morningShift, String afternoonShift, String nightShift) {
        this.morningShift = morningShift;
        this.afternoonShift = afternoonShift;
        this.nightShift = nightShift;
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
