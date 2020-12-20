package com.example.wsg;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wsg.helpers.DaysOfTheWeek;
import com.example.wsg.helpers.ScheduleHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@SuppressWarnings("java:S110")
public class Schedule extends AppCompatActivity {

    private static final int NUMBER_OF_WEEKS = 4;
    private static final int PEOPLE_ON_MORNING_SHIFT = 3;
    private static final int PEOPLE_ON_AFTERNOON_SHIFT = 2;
    private static final int PEOPLE_ON_NIGHT_SHIFT = 1;
    private static final String TABLE_SCHEDULE = "Schedule";
    private static final String WEEK_WEEK = "Weeks/Week";
    private static final String TABLE_EMPLOYEES = "Employees";
    private static final String MORNING_SHIFT = "morningShift";
    private static final String AFTERNOON_SHIFT = "afternoonShift";
    private static final String NIGHT_SHIFT = "nightShift";

    @Override

    private void scheduleAndInputToDb(DataSnapshot dataSnapshot, @NonNull List<Employee> workingEmployeeList, boolean isFirst) {

        Map<String, ScheduleHelper> scheduleMap = new HashMap<>();
        if (isFirst) {
            initMap(scheduleMap);
            for (DataSnapshot entry : dataSnapshot.getChildren()) {
                Employee employeePerson = entry.getValue(Employee.class);

                prepareShifts(scheduleMap, employeePerson);
                workingEmployeeList.add(employeePerson);
            }
            prepareAndInputData(1, scheduleMap.get(MORNING_SHIFT).getShiftNames(), scheduleMap.get(AFTERNOON_SHIFT).getShiftNames(), scheduleMap.get(NIGHT_SHIFT).getShiftNames());

        } else {
            for (int currentWeekNumber = 2; currentWeekNumber < NUMBER_OF_WEEKS + 1; currentWeekNumber++) {
                initMap(scheduleMap);
                Collections.sort(workingEmployeeList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Employee p1 = (Employee) o1;
                        Employee p2 = (Employee) o2;
                        return Integer.compare(p1.getHours(), p2.getHours());
                    }
                });
                Iterator<Employee> employeeIterator = workingEmployeeList.iterator();
                while (employeeIterator.hasNext() && !allShiftsAreFull(scheduleMap)) {
                    Employee currentEmployee = employeeIterator.next();
                    prepareShifts(scheduleMap, currentEmployee);
                }
                prepareAndInputData(currentWeekNumber, scheduleMap.get(MORNING_SHIFT).getShiftNames(), scheduleMap.get(AFTERNOON_SHIFT).getShiftNames(), scheduleMap.get(NIGHT_SHIFT).getShiftNames());
            }
        }
    }

    private void initMap(Map<String, ScheduleHelper> scheduleMap) {
        scheduleMap.put(MORNING_SHIFT, new ScheduleHelper("", 0));
        scheduleMap.put(AFTERNOON_SHIFT, new ScheduleHelper("", 0));
        scheduleMap.put(NIGHT_SHIFT, new ScheduleHelper("", 0));
    }


    private void prepareAndInputData(int currentWeekNumber, String morningShiftNames, String afternoonShiftNames, String nightShiftNames) {
        morningShiftNames = StringUtils.substring(morningShiftNames, 1);
        afternoonShiftNames = StringUtils.substring(afternoonShiftNames, 1);
        nightShiftNames = StringUtils.substring(nightShiftNames, 1);

        inputWeekDataToDb(currentWeekNumber, morningShiftNames, afternoonShiftNames, nightShiftNames);
    }

    private void inputWeekDataToDb(int currentWeekNumber, String morningShiftNames, String afternoonShiftNames, String nightShiftNames) {
        for (int currentDayAsNumber = 0; currentDayAsNumber < 5; currentDayAsNumber++) {
            DaySchedule currentDayMorning = new DaySchedule(morningShiftNames);
            DaySchedule currentDayAfternoon = new DaySchedule(afternoonShiftNames);
            DaySchedule currentDayNight = new DaySchedule(nightShiftNames);

            FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                    DaysOfTheWeek.getDay(currentDayAsNumber), "/shift1")).setValue(currentDayMorning);
            FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                    DaysOfTheWeek.getDay(currentDayAsNumber), "/shift2")).setValue(currentDayAfternoon);
            FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                    DaysOfTheWeek.getDay(currentDayAsNumber), "/shift3")).setValue(currentDayNight).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Schedule.this, "Job Done", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Schedule.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}