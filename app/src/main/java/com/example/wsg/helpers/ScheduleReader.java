package com.example.wsg.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleReader {

    private static final String EMPLOYEE_NAMES = "employeeNames";
    private String shift1;
    private String shift2;
    private String shift3;

    private List<DailyShifts> workingEmployees = new ArrayList<>();
    /**
     *  Η κλάση {@link ScheduleReader} αποθηκεύει σε μια λίστα τους υπαλλήλους
     *  που εργάζονται σε κάθε βάρδια.
     *
     * @param shifts Είναι ένα hashmap που έχει μέσα τα όνοματα των
     *               υπαλλήλων από όλες τις βάρδιες όλων των ημερών.
     */

    public ScheduleReader(Map <String, Map<String,Map<String,String>>> shifts){

        for(int i=0; i< shifts.size(); i++) {
            this.shift1 = shifts.get(DaysOfTheWeek.getDay(i)).get("shift1").get(EMPLOYEE_NAMES);
            this.shift2 = shifts.get(DaysOfTheWeek.getDay(i)).get("shift2").get(EMPLOYEE_NAMES);
            this.shift3 = shifts.get(DaysOfTheWeek.getDay(i)).get("shift3").get(EMPLOYEE_NAMES);
            workingEmployees.add(new DailyShifts(DaysOfTheWeek.getDay(i),shift1,shift2,shift3));
        }
    }

    public List<DailyShifts> getWorkingEmployees() {
        return workingEmployees;
    }

    public void setWorkingEmployees(List<DailyShifts> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }
}

