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
    public int PTNum;

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

        Button deleteButtonofSchedule = (Button) v.findViewById(R.id.deleteButtonofSchedule);
        deleteButtonofSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = UserMainActivity.userID;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                builder.setTitle("주의!!");
                                builder.setMessage("정말로 PT를 취소하시겠습니까?");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                        AlertDialog dialog1 = builder.setMessage("PT가 취소되었습니다.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Response.Listener<String> responseListenerOfPTNum = new Response.Listener<String>() {

                                                            @Override
                                                            public void onResponse(String response) {
                                                                try{
                                                                    JSONObject jsonResponse = new JSONObject(response);
                                                                    boolean success = jsonResponse.getBoolean("success");
                                                                    if(success){
                                                                        FragmentTransaction ft = (parent.getFragmentManager()).beginTransaction();
                                                                        ft.detach(parent)
                                                                                .attach(parent)
                                                                                .commit();
                                                                    }
                                                                }catch (Exception e){
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        };
                                                        PTNumUpdateRequest ptNumUpdateRequest = new PTNumUpdateRequest(UserMainActivity.userID, ScheduleFragment.userPT + 1 , responseListenerOfPTNum);
                                                        RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                                        queue.add(ptNumUpdateRequest);

                                                        ptList.remove(i);
                                                        notifyDataSetChanged();
                                                    }
                                                })
                                                .create();
                                        dialog1.show();
                                    }
                                });
                                builder.setNegativeButton("아니오" , null);
                                builder.create();
                                builder.show();
                            }




                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("PT취소에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                ScheduleDeleteRequest scheduleDeleteRequest = new ScheduleDeleteRequest(userID , ptList.get(i).getPtID() , responseListener);

                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(scheduleDeleteRequest);
            }
        });

        return v;
    }
}
