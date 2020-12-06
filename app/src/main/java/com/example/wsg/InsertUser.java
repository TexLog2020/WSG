package com.example.wsg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class InsertUser extends AppCompatActivity implements View.OnClickListener {


    private EditText insName, insAge, insEmail, insPassword;
    private Button InsBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);


        mAuth = FirebaseAuth.getInstance();


        insName = (EditText) findViewById(R.id.editTextTextPersonName);
        insAge = (EditText) findViewById(R.id.editTextTextPersonName2);
        insEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        insPassword = (EditText) findViewById(R.id.editTextTextPassword);

        InsBtn = (Button) findViewById(R.id.buttonIns);
        InsBtn.setOnClickListener(this);


    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.buttonIns:
                String empName = insName.getText().toString().trim();
                String empAge = insAge.getText().toString().trim();
                String empEmail = insEmail.getText().toString().trim();
                String empPassword = insPassword.getText().toString().trim();



                mAuth.createUserWithEmailAndPassword(empEmail, empPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Employee employee = new Employee(empName, empAge, empEmail);

                            FirebaseDatabase.getInstance().getReference("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(InsertUser.this, "User Has Been Inserted Successfully", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(InsertUser.this, "Failed", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    }
                });

        }
    }


}