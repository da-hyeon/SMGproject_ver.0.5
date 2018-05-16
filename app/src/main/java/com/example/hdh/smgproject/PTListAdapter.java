package com.example.hdh.smgproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PTListAdapter extends BaseAdapter {

    private Context context;
    private List<PT> ptList;
    private Fragment parent;

    public String ptDate;

    public PTListAdapter(Context context, List<PT> ptList, Fragment parent) {
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
        View v = View.inflate(context, R.layout.pt, null);

        ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                ptList.get(i).getPtTime().substring(3, 5) + "분";

        TextView ptYearText = (TextView) v.findViewById(R.id.ptYearText);
        TextView ptMonthText = (TextView) v.findViewById(R.id.ptMonthText);
        TextView ptDayText = (TextView) v.findViewById(R.id.ptDayText);
        TextView ptTimeText = (TextView) v.findViewById(R.id.ptTimeText);
        TextView ptTrainerText = (TextView) v.findViewById(R.id.ptTrainerText);
        TextView ptIDText = (TextView) v.findViewById(R.id.ptIDText);

        ptYearText.setText(ptList.get(i).getPtYear());
        ptMonthText.setText(ptList.get(i).getPtMonth());
        ptDayText.setText(ptList.get(i).getPtDay());
        ptTimeText.setText("PT TIME : " + ptList.get(i).getPtTime());
        ptTrainerText.setText("트레이너 - " + ptList.get(i).getPtTrainer());

        if (!DateUtil.compareDate(getDateString(), ptDate)) {
            ptIDText.setText("ptID : " + ptList.get(i).getPtID());
            //getItem(i)
        } else {
            ptIDText.setText("날짜가 지났습니다.");
        }


        v.setTag(ptList.get(i).getPtID());

        final Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = UserMainActivity.userID;

                ptDate = ptList.get(i).getPtYear().substring(0, ptList.get(i).getPtYear().length() - 1) + "년 " +
                        ptList.get(i).getPtMonth().substring(0, ptList.get(i).getPtMonth().length() - 1) + "월 " +
                        ptList.get(i).getPtDay().substring(0, ptList.get(i).getPtDay().length() - 1) + "일 " +
                        ptList.get(i).getPtTime().substring(0, 2) + "시 " +
                        ptList.get(i).getPtTime().substring(3, 5) + "분";

                if(!DateUtil.compareDate(getDateString(), ptDate)) {
                    if (ReservationFragment.userPT > 0) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                        builder.setTitle("PT횟수가 차감됩니다.");
                                        builder.setMessage("정말로 신청하시겠습니까?");
                                        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                                AlertDialog dialog1 = builder.setMessage("PT가 신청되었습니다.")
                                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                Response.Listener<String> responseListenerOfPTNum = new Response.Listener<String>() {

                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        try {
                                                                            JSONObject jsonResponse = new JSONObject(response);
                                                                            boolean success = jsonResponse.getBoolean("success");
                                                                            if (success) {
                                                                                FragmentTransaction ft = (parent.getFragmentManager()).beginTransaction();
                                                                                ft.detach(parent)
                                                                                        .attach(parent)
                                                                                        .commit();
                                                                            }
                                                                        } catch (Exception e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    }
                                                                };
                                                                PTNumUpdateRequest ptNumUpdateRequest = new PTNumUpdateRequest(UserMainActivity.userID, ReservationFragment.userPT - 1, responseListenerOfPTNum);
                                                                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                                                                queue.add(ptNumUpdateRequest);

                                                            }
                                                        })
                                                        .create();
                                                dialog1.show();
                                            }
                                        });
                                        builder.setPositiveButton("아니오", null);
                                        builder.create();
                                        builder.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                        AlertDialog dialog = builder.setMessage("PT신청에 실패하였습니다.")
                                                .setNegativeButton("확인", null)
                                                .create();
                                        dialog.show();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        PTAddRequest ptAddRequest = new PTAddRequest(userID, ptList.get(i).getPtID() + "", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                        queue.add(ptAddRequest);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        AlertDialog dialog = builder.setMessage("PT횟수가 부족합니다.")
                                .setNegativeButton("확인", null)
                                .create();
                        dialog.show();
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                    AlertDialog dialog = builder.setMessage("날짜가 지났습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        return v;
    }

    public String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREA);
        String str_date = df.format(new Date());

        return str_date;
    }

}
