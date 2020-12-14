package com.example.wsg;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private Button logout,delUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

        delUser = (Button) findViewById(R.id.buttonDelete);
        delUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlistviewEmployees();
            }
        });

    }

    public void openlistviewEmployees(){
        Intent intent = new Intent(this,SelectEmplToDelete.class);
        startActivity(intent);
    }
}