package com.example.hdh.smgproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class SchedulePTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;

    private boolean validate = false;   //사용할수 있는 회원 아이디인지 검사하는 변수

    public SchedulePTListAdapter(Context context , List<PT> ptList , Fragment parent){
        this.context = context;
        this.ptList = ptList;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return ptList.size();
    }

    @Override
    public Object getItem(int position) {
        return ptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.schedule, null);
        TextView ptYearText = (TextView) v.findViewById(R.id.ptYearTextofSchedule);
        TextView ptMonthText = (TextView) v.findViewById(R.id.ptMonthTextofSchedule);
        TextView ptDayText = (TextView) v.findViewById(R.id.ptDayTextofSchedule);
        TextView ptTimeText = (TextView) v.findViewById(R.id.ptTimeTextofSchedule);
        TextView ptTrainerText = (TextView) v.findViewById(R.id.ptTrainerTextofSchedule);
        TextView ptIDText = (TextView) v.findViewById(R.id.ptIDTextofSchedule);

        ptYearText.setText(ptList.get(i).getPtYear());
        ptMonthText.setText(ptList.get(i).getPtMonth());
        ptDayText.setText(ptList.get(i).getPtDay());
        ptTimeText.setText("PT TIME : " + ptList.get(i).getPtTime());
        ptTrainerText.setText("트레이너 - " + ptList.get(i).getPtTrainer());
        ptIDText.setText("ptID : " + ptList.get(i).getPtID());

        v.setTag(ptList.get(i).getPtID());

        return v;
    }
}
