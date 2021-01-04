package com.example.wsg;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wsg.helpers.DaySchedule;
import com.example.wsg.helpers.DaysOfTheWeek;
import com.example.wsg.helpers.Employee;
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

    public static final int NUMBER_OF_WEEKS = 2;
    private static final int PEOPLE_ON_MORNING_SHIFT = 2;
    private static final int PEOPLE_ON_AFTERNOON_SHIFT = 3;
    private static final int PEOPLE_ON_NIGHT_SHIFT = 1;
    private static final String TABLE_SCHEDULE = "Schedule";
    private static final String WEEK_WEEK = "Weeks/Week";
    private static final String TABLE_EMPLOYEES = "Employees";
    private static final String MORNING_SHIFT = "morningShift";
    private static final String AFTERNOON_SHIFT = "afternoonShift";
    private static final String NIGHT_SHIFT = "nightShift";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        Query cleanupQuery = db.getReference(TABLE_SCHEDULE).child("Weeks");
        cleanupQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot week : dataSnapshot.getChildren()) {
                        week.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Required from interface implementation.
            }
        });

        Query query = db.getReference(TABLE_EMPLOYEES);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Employee> workingEmployeeList = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    getEmployeesFromDatabase(dataSnapshot, workingEmployeeList);
                    scheduleAndInputToDb(workingEmployeeList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Required from interface implementation.
            }
        });
    }


    private void getEmployeesFromDatabase(DataSnapshot dataSnapshot, @NonNull List<Employee> workingEmployeeList) {
        for (DataSnapshot entry : dataSnapshot.getChildren()) {
            Employee employeePerson = new Employee(entry.getValue(Employee.class).getKodID(), entry.getValue(Employee.class).getName());
            workingEmployeeList.add(employeePerson);
        }
    }

    private void scheduleAndInputToDb(@NonNull List<Employee> workingEmployeeList) {
        Map<String, ScheduleHelper> scheduleMap = new HashMap<>();

        for (int currentWeekNumber = 1; currentWeekNumber < NUMBER_OF_WEEKS + 1; currentWeekNumber++) {
            for (int dayOfTheWeek = 0; dayOfTheWeek < 5; dayOfTheWeek++) {
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
                prepareAndInputData(currentWeekNumber, dayOfTheWeek, scheduleMap.get(MORNING_SHIFT).getShiftNames(), scheduleMap.get(AFTERNOON_SHIFT).getShiftNames(), scheduleMap.get(NIGHT_SHIFT).getShiftNames());
            }
        }
        updateEmployeeHours(workingEmployeeList);
    }

    private void updateEmployeeHours(@NonNull List<Employee> workingEmployeeList) {

        for (Employee employee : workingEmployeeList) {
            FirebaseDatabase.getInstance().getReference()
                    .child(TABLE_EMPLOYEES + "/" + employee.getKodID()).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Schedule.this, "Employees updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Schedule.this, "Failed to update employees", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initMap(Map<String, ScheduleHelper> scheduleMap) {
        scheduleMap.put(MORNING_SHIFT, new ScheduleHelper("", 0));
        scheduleMap.put(AFTERNOON_SHIFT, new ScheduleHelper("", 0));
        scheduleMap.put(NIGHT_SHIFT, new ScheduleHelper("", 0));
    }

    private boolean allShiftsAreFull(Map<String, ScheduleHelper> scheduleMap) {
        return scheduleMap.get(MORNING_SHIFT).isFull() && scheduleMap.get(AFTERNOON_SHIFT).isFull() && scheduleMap.get(NIGHT_SHIFT).isFull();
    }

    private void prepareShifts(Map<String, ScheduleHelper> scheduleMap, Employee employee) {
        if (scheduleMap.get(MORNING_SHIFT).getShiftCounter() < PEOPLE_ON_MORNING_SHIFT) {
            scheduleMap.get(MORNING_SHIFT).setShiftNames(StringUtils.join(scheduleMap.get(MORNING_SHIFT).getShiftNames(), ",", employee.getName()));
            employee.setHours(employee.getHours() + 8);
            scheduleMap.get(MORNING_SHIFT).shiftCounterIncrease();
        } else if (scheduleMap.get(AFTERNOON_SHIFT).getShiftCounter() < PEOPLE_ON_AFTERNOON_SHIFT) {
            scheduleMap.get(AFTERNOON_SHIFT).setShiftNames(StringUtils.join(scheduleMap.get(AFTERNOON_SHIFT).getShiftNames(), ",", employee.getName()));
            employee.setHours(employee.getHours() + 8);
            scheduleMap.get(AFTERNOON_SHIFT).shiftCounterIncrease();
        } else if (scheduleMap.get(NIGHT_SHIFT).getShiftCounter() < PEOPLE_ON_NIGHT_SHIFT) {
            scheduleMap.get(NIGHT_SHIFT).setShiftNames(StringUtils.join(scheduleMap.get(NIGHT_SHIFT).getShiftNames(), ",", employee.getName()));
            employee.setHours(employee.getHours() + 8);
            scheduleMap.get(NIGHT_SHIFT).shiftCounterIncrease();
        }
    }

    private void prepareAndInputData(int currentWeekNumber, int dayNumber, String morningShiftNames, String afternoonShiftNames, String nightShiftNames) {
        morningShiftNames = StringUtils.substring(morningShiftNames, 1);
        afternoonShiftNames = StringUtils.substring(afternoonShiftNames, 1);
        nightShiftNames = StringUtils.substring(nightShiftNames, 1);

        inputWeekDataToDb(currentWeekNumber, dayNumber, morningShiftNames, afternoonShiftNames, nightShiftNames);
    }

    private void inputWeekDataToDb(int currentWeekNumber, int currentDayAsNumber, String morningShiftNames, String afternoonShiftNames, String nightShiftNames) {
        DaySchedule currentDayMorning = new DaySchedule(morningShiftNames);
        DaySchedule currentDayAfternoon = new DaySchedule(afternoonShiftNames);
        DaySchedule currentDayNight = new DaySchedule(nightShiftNames);

        FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                DaysOfTheWeek.getDay(currentDayAsNumber), "/shift1")).setValue(currentDayMorning);
        FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                DaysOfTheWeek.getDay(currentDayAsNumber), "/shift2")).setValue(currentDayAfternoon);
        FirebaseDatabase.getInstance().getReference().child(StringUtils.join(TABLE_SCHEDULE, "/", WEEK_WEEK, currentWeekNumber, "/",
                DaysOfTheWeek.getDay(currentDayAsNumber), "/shift3")).setValue(currentDayNight);
    }
}