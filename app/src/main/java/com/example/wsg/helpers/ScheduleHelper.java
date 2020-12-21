package com.example.wsg.helpers;

public class ScheduleHelper {
    private String shiftNames;
    private int shiftCounter;
    private boolean isFull;

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public String getShiftNames() {
        return shiftNames;
    }

    public void setShiftNames(String shiftNames) {
        this.shiftNames = shiftNames;
    }

    public int getShiftCounter() {
        return shiftCounter;
    }

    public ScheduleHelper(String shiftNames, int shiftCounter) {
        this.shiftNames = shiftNames;
        this.shiftCounter = shiftCounter;
    }

    public void shiftCounterIncrease(){
        this.shiftCounter++;
    }

    public ScheduleHelper(String shiftNames) {
        this.shiftNames = shiftNames;
        this.shiftCounter = 0;
    }
}
