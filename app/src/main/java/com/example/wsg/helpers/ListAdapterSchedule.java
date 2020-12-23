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
import java.util.Map;

public class ListAdapterSchedule extends ArrayAdapter {

    private final Activity mContext;
    private final List<WeeklyShifts> scheduleList;

    public ListAdapterSchedule(Activity mContext, List<WeeklyShifts> scheduleList){
        super(mContext,R.layout.list_item,scheduleList);
        this.mContext = mContext;
        this.scheduleList= scheduleList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_weekly_schedule,null,true);

        TextView week = listItemView.findViewById(R.id.week);
        TextView morningShift = listItemView.findViewById(R.id.morningShift);
        TextView afternoonShift = listItemView.findViewById(R.id.afternoonShift);
        TextView nightShift = listItemView.findViewById(R.id.nightShift);
        int pos=position+1;
        week.setText("Day "+pos);
        morningShift.setText(scheduleList.get(position).getMorningShift());
        afternoonShift.setText(scheduleList.get(position).getAfternoonShift());
        nightShift.setText(scheduleList.get(position).getNightShift());

        return listItemView;
    }
}

