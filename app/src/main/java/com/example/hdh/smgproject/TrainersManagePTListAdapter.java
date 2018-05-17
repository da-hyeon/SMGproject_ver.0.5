package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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

public class TrainersManagePTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;

    public Button deleteButtonofTrainersPTSchedule;


    public TrainersManagePTListAdapter(Context context, List<PT> ptList, Fragment parent) {
        this.context = context;
        this.ptList = ptList;
        this.parent = parent;
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
        View v = View.inflate(context, R.layout.trainers_pt_schedule, null);

        final TextView ptID = (TextView) v.findViewById(R.id.ptIDTextofTrainersPTSchedule);
        final TextView ptYear = (TextView) v.findViewById(R.id.ptYearTextofTrainersPTSchedule);
        final TextView ptMonth = (TextView) v.findViewById(R.id.ptMonthTextofTrainersPTSchedule);
        final TextView ptDay = (TextView) v.findViewById(R.id.ptDayTextofTrainersPTSchedule);
        final TextView ptTime = (TextView) v.findViewById(R.id.ptTimeTextofTrainersPTSchedule);

        ptID.setText(ptList.get(i).getPtID() + "");
        ptYear.setText(ptList.get(i).getPtYear());
        ptMonth.setText(ptList.get(i).getPtMonth());
        ptDay.setText(ptList.get(i).getPtDay());
        ptTime.setText(ptList.get(i).getPtTime());

        //특정유저의 아이디값을 반환
        v.setTag(ptList.get(i).getUserID());

        deleteButtonofTrainersPTSchedule = (Button) v.findViewById(R.id.deleteButtonofTrainersPTSchedule);

        deleteButtonofTrainersPTSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                builder.setTitle(Html.fromHtml("<strong><font color=\"#ff0000\"> " + "주의"));
                AlertDialog dialog = builder.setMessage(Html.fromHtml("</font></strong><br>PT를 정말 삭제하시겠습니까?"))
                        .setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);

                                            boolean success = jsonResponse.getBoolean("success");

                                            if (success) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                AlertDialog dialog1 = builder.setMessage("수업이 삭제되었습니다.")
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                FragmentTransaction ft = (parent.getFragmentManager()).beginTransaction();
                                                                ft.detach(parent)
                                                                        .attach(parent)
                                                                        .commit();
                                                                ptList.remove(i);
                                                                notifyDataSetChanged();
                                                            }
                                                        })
                                                        .create();
                                                dialog1.show();
                                            } else {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                AlertDialog dialog = builder.setMessage("수업삭제에 실패하였습니다.")
                                                        .setNegativeButton("다시 시도", null)
                                                        .create();
                                                dialog.show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                DeleteRequest deleteRequest = new DeleteRequest(TrainerMainActivity.userID, ptList.get(i).getPtID(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                queue.add(deleteRequest);
                            }
                        }).setPositiveButton("아니오", null)
                        .create();
                dialog.show();
            }
        });
        return v;
    }
}
