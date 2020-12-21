package com.example.wsg.helpers;

public enum DaysOfTheWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    public static String getDay(int dayNum) {
        switch (dayNum) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            default:
                throw new IllegalArgumentException("Unknown requested day");

        }
    }
}
