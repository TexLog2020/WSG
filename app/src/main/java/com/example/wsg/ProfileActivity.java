package com.example.wsg;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private Button logout,btnInsertData,btnRetreiveData;
    private EditText kodEmployee,fullName,hoursEmployee;

    DatabaseReference EmployeeDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        kodEmployee  = (EditText) findViewById(R.id.kodEmployee);
        fullName  = (EditText) findViewById(R.id.fullName);
        hoursEmployee = (EditText) findViewById(R.id.hoursEmployee);
        btnInsertData = (Button) findViewById(R.id.btnInsertData);
        btnRetreiveData = (Button) findViewById(R.id.btnRetreiveData);



        EmployeeDbRef = FirebaseDatabase.getInstance().getReference().child("Employees");

        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEmployeeData();
            }
        });
        btnRetreiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                startActivity(new Intent(ProfileActivity.this,RetriveDataActivity.class));
            }
        });


    }

    private void insertEmployeeData() {
        String kodID = kodEmployee.getText().toString();
        String name = fullName.getText().toString();
        String hours = hoursEmployee.getText().toString();

        Employee employee = new Employee(kodID,name,hours);
        assert kodID != null;
        EmployeeDbRef.push().setValue(employee);

        Toast.makeText(ProfileActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
    }
}