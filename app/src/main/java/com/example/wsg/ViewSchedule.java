package com.example.wsg;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wsg.helpers.ListAdapterSchedule;
import com.example.wsg.helpers.ScheduleReader;
import com.example.wsg.helpers.WeeklyShifts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewSchedule extends AppCompatActivity {

    private static final String TABLE_SCHEDULE = "Schedule";
    private ListView myListview;
    private List<WeeklyShifts> weeklyShiftList = new ArrayList<>();

    private DatabaseReference employeeDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        myListview = findViewById(R.id.scheduleList);

        employeeDbRef = FirebaseDatabase.getInstance().getReference(TABLE_SCHEDULE).child("Weeks");

        employeeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot weekDatasnap : dataSnapshot.getChildren()) {
                    ScheduleReader currentWeek = new ScheduleReader((HashMap <String,Object>) weekDatasnap.child("Monday").getValue());
                    weeklyShiftList.add(currentWeek.getWorkingEmployees());
                }

                ListAdapterSchedule adapter = new ListAdapterSchedule(ViewSchedule.this, weeklyShiftList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Required for interface implementation
            }
        });
    }
}