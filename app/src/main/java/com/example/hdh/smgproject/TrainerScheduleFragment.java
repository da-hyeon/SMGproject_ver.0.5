package com.example.hdh.smgproject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//회원관리

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainerScheduleFragment extends Fragment {

    private ListView listView;
    private TrainersScheduleListAdapter adapter;
    private List<PT> ptList;

    public TrainerScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_trainer_manage, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        //리스트뷰 초기화
        listView = (ListView) getView().findViewById(R.id.TrainerForUserListView);

        ptList = new ArrayList<PT>();
        adapter = new TrainersScheduleListAdapter(getContext().getApplicationContext(), ptList , getActivity());
        listView.setAdapter(adapter);



        new BackGroundTaskForTrainersScheduleInfo().execute();
    }



    class BackGroundTaskForTrainersScheduleInfo extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/TrainersSchedule_SYG.php?trainerID=" + URLEncoder.encode(TrainerMainActivity.userID, "UTF-8");
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
                String userID, ptYear, ptMonth ,ptDay, ptTime , ptTrainer ;


                while (count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);

                    userID = "신청한 회원 : " + object.getString("userID");
                    ptID = object.getInt("ptID");
                    ptYear = object.getString("ptYear");
                    ptMonth = object.getString("ptMonth");
                    ptDay = object.getString("ptDay" );
                    ptTime = "PT TIME : " + object.getString("ptTime" );
                    ptTrainer = "트레이너 : " + object.getString("ptTrainer") ;

                    PT pt = new PT(userID, ptID, ptYear, ptMonth , ptDay, ptTime, ptTrainer);
                    ptList.add(pt);
                    count++;
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}