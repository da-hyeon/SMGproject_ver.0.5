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
import java.util.List;

//회원관리

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainerManageFragment extends Fragment {

    private ListView listView;
    private TrainerForUserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;

    public TrainerManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainer_manage, container, false);
    }


    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

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
                String userID , userPassword , userName, userEmail ,userGender, userHeight, userWeight, userAge ;
                int userPT;



                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    userID = object.getString("userID");
                    userPassword = object.getString("userPassword");
                    userName = object.getString("userName");
                    userEmail = object.getString("userEmail");
                    userGender = object.getString("userGender");
                    userHeight = object.getString("userHeight");
                    userWeight = object.getString("userWeight");
                    userAge = object.getString("userAge") + "세";
                    userPT = object.getInt("userPT");

                    if (userName.equals("")) {
                        userName = "정보없음";
                    }
                    if (userGender.equals("")) {
                        userGender = "정보없음";
                    }
                    if (userAge.equals("세")) {
                        userAge = "정보없음";
                    }

                    User user = new User(userID, userPassword, userName, userEmail, userGender, userHeight, userWeight, userAge, userPT);
                    if (!userID.contains("admin")) {
                        userList.add(user);
                        saveList.add(user);
                    }
                    count++;
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}