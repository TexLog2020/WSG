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
import android.widget.Toast;

import com.example.wsg.helpers.Employee;
import com.example.wsg.helpers.ListAdapterEmployee;
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
import java.util.List;

public class RetrieveDataActivity extends AppCompatActivity {

    private  static final String TABLE_EMPLOYEES = "Employees";
    private ListView myListview;
    private List<Employee> employeeList;

    DatabaseReference employeeDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        myListview = findViewById(R.id.myListView);
        employeeList = new ArrayList<>();

        employeeDbRef = FirebaseDatabase.getInstance().getReference(TABLE_EMPLOYEES);

        employeeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeeList.clear();

                for (DataSnapshot employeeDatasnap : dataSnapshot.getChildren()) {
                    Employee employee = employeeDatasnap.getValue(Employee.class);
                    employeeList.add(employee);
                }

                ListAdapterEmployee adapter = new ListAdapterEmployee(RetrieveDataActivity.this, employeeList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Required for interface implementation
            }
        });

        myListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employeeList.get(position);
                showUpdateDialog(employee.getKodID(), employee.getName());

                return false;
            }
        });
    }
    private void showUpdateDialog(final int kodID,String name){

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

                int newkodID = Integer.parseInt(tvUpdateEmployee.getText().toString());
                int newhours = Integer.parseInt(tvUpdateFullName.getText().toString());
                String newName = tvUpdatehoursEmployee.getText().toString();


                updateData(newkodID,newName,newhours);

                Toast.makeText(RetrieveDataActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
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

    private void deleteRecord(int kodID) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(TABLE_EMPLOYEES).child(String.valueOf(kodID));

        Task<Void> mTask = dbRef.removeValue();
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

    private void updateData(int kodID, String name, int hours){

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(TABLE_EMPLOYEES).child(String.valueOf(kodID));
        Employee employee = new Employee(kodID, name, hours);
        dbRef.setValue(employee);

    }
}