package com.example.wsg.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wsg.R;

import java.util.List;

public class ListAdapterEmployee extends ArrayAdapter {

    private Activity mContext;
    private List<Employee> employeeList;

    public ListAdapterEmployee(Activity mContext, List<Employee> employeeList){
        super(mContext,R.layout.list_item,employeeList);
        this.mContext = mContext;
        this.employeeList= employeeList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);

        TextView tvEmployee = listItemView.findViewById(R.id.tvEmployee);
        TextView tvFullName = listItemView.findViewById(R.id.tvFullName);
        TextView tvhoursEmployee = listItemView.findViewById(R.id.tvhoursEmployee);
        TextView tvWeeksOffEmployee = listItemView.findViewById(R.id.tvWeeksOff);

        Employee employee = employeeList.get(position);

        tvEmployee.setText(String.valueOf(employee.getKodID()));
        tvFullName.setText(employee.getName());
        tvhoursEmployee.setText(String.valueOf(employee.getHours()));
        tvWeeksOffEmployee.setText(String.valueOf(employee.getWeeksOff()));

        return listItemView;
    }
}

