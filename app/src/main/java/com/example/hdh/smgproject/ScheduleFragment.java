package com.example.hdh.smgproject;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {
    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    private ListView ptListView;
    private SchedulePTListAdapter adapter;
    private List<PT> ptList;
    int totalCredit = 0;

    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);
        ptListView = (ListView) getView().findViewById(R.id.ptListView);
        ptList = new ArrayList<PT>();
        adapter = new SchedulePTListAdapter(getContext().getApplicationContext() , ptList , this);
        ptListView.setAdapter(adapter);

        TextView userRemainPtNum = (TextView) getView().findViewById((R.id.userRemainPtNum));

        userRemainPtNum.setText(UserMainActivity.userPTNum + "번");

        new BackGroundTask().execute();

    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target , target1;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/SchedulePTList_SYG.php?userID=" + URLEncoder.encode(UserMainActivity.userID, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int ptID;
                String ptYear;
                String ptMonth;
                String ptDay;
                String ptTrainer;
                String ptTime;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ptID = object.getInt("ptID");
                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay");
                    ptTrainer = object.getString("ptTrainer");
                    ptTime = object.getString("ptTime");

                    PT pt = new PT(ptID, ptYear, ptMonth, ptDay, ptTrainer, ptTime);
                    ptList.add(pt);
                    count++;
                }
                adapter.notifyDataSetChanged();
            }

            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
