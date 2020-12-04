package com.example.firebasedatabasesimos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.IDN;

public class MainActivity extends AppCompatActivity {

    final DatabaseReference drEmployees = FirebaseDatabase.getInstance().getReference("Employees");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button deleteButton = (Button) findViewById(R.id.deleteButton);

        String ID = "1";

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(ID);
            }
        });

    }

    private void deleteEmployee(String id) {

        drEmployees.child(id);

        drEmployees.removeValue();

        Toast.makeText(this,"The employeer is deleted...",Toast.LENGTH_LONG).show();
    }
}