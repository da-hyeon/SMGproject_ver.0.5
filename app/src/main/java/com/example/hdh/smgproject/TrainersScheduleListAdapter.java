package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TrainersScheduleListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Activity parentActivity;


    public String ptDate;

    public TrainersScheduleListAdapter(Context context , List<PT> ptList , Activity parentActivity ){
        this.context = context;
        this.ptList = ptList;
        this.parentActivity = parentActivity;
    }

    //현재 사용자의 개수
    @Override
    public int getCount() {
        return ptList.size();
    }

    //유저리스트의 특정 사용자를 반환
    @Override
    public Object getItem(int i) {
        return ptList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.trainers_schedule, null);

        final TextView userID = (TextView) v.findViewById(R.id.ptUserTextofTrainersSchedule);
        final TextView ptID = (TextView) v.findViewById(R.id.ptIDTextofTrainersSchedule);
        final TextView ptYear = (TextView) v.findViewById(R.id.ptYearTextofTrainersSchedule);
        final TextView ptMonth = (TextView) v.findViewById(R.id.ptMonthTextofTrainersSchedule);
        final TextView ptDay = (TextView) v.findViewById(R.id.ptDayTextofTrainersSchedule);
        final TextView ptTime = (TextView) v.findViewById(R.id.ptTimeTextofTrainersSchedule);
        final TextView ptTrainer = (TextView) v.findViewById(R.id.ptTrainerTextofTrainersSchedule);

        userID.setText(ptList.get(i).getUserID());
        ptID.setText(ptList.get(i).getPtID() + "");
        ptYear.setText(ptList.get(i).getPtYear());
        ptMonth.setText(ptList.get(i).getPtMonth());
        ptDay.setText(ptList.get(i).getPtDay());
        ptTime.setText(ptList.get(i).getPtTime());
        ptTrainer.setText(ptList.get(i).getPtTrainer());

        //특정유저의 아이디값을 반환
        v.setTag(ptList.get(i).getUserID());

        return v;
    }
}
