package com.example.wsg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class RetriveDataActivity extends AppCompatActivity {

    ListView myListview;
    List<Employee> employeeList;

    DatabaseReference employeeDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive_data);

        myListview = findViewById(R.id.myListView);
        employeeList = new ArrayList<>();

        employeeDbRef = FirebaseDatabase.getInstance().getReference("Employees");

        employeeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();

                for(DataSnapshot employeeDatasnap : dataSnapshot.getChildren()){
                    Employee employee = employeeDatasnap.getValue(Employee.class);
                    employeeList.add(employee);
                }

                ListAdapter adapter = new ListAdapter(RetriveDataActivity.this,employeeList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employeeList.get(position);
                showUpdateDialog(employee.getKodID(),employee.getName());

                return false;
            }
        });
    }
    private void showUpdateDialog(final String kodID,String name){

        final AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_dialog, null,true);

        mDialog.setView(mDialogView);

        final EditText tvUpdateEmployee = mDialogView.findViewById(R.id.tvUpdateEmployee);
        final EditText tvUpdatehoursEmployee = mDialogView.findViewById(R.id.tvUpdatehoursEmployee);
        final EditText tvUpdateFullName = mDialogView.findViewById(R.id.tvUpdateFullName);
        Button btnUpdate = mDialogView.findViewById(R.id.btnUpdate);
        Button btnDelete = mDialogView.findViewById(R.id.btnDelete);

        mDialog.setTitle("Updating " + name +" record");
        final AlertDialog alertDialog = mDialog.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newkodID = tvUpdateEmployee.getText().toString();
                String newhours = tvUpdateFullName.getText().toString();
                String newName = tvUpdatehoursEmployee.getText().toString();


                updateData(newkodID,newhours,newName);

                Toast.makeText(RetriveDataActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord(kodID);
                alertDialog.dismiss();
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void deleteRecord(String kodID) {
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Employees").child(kodID);

        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Error deleting Employee");
            }
        });
    }

    private void updateData(String kodID, String name, String hours){

        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Employees").child(kodID);
        Employee employee = new Employee(kodID, name, hours);
        DbRef.setValue(employee);

    }
}