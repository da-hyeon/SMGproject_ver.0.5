package com.example.hdh.smgproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MyinfoFragment extends Fragment {

    public String userID, userPassword, userName, userEmail, userGender, userHeight, userWeight, userAge;
    static public int userPT;

    public MyinfoFragment() {

    }

    public static MyinfoFragment newInstance(String param1, String param2) {
        MyinfoFragment fragment = new MyinfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        new BackGroundTask().execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_myinfo, container, false);

        Button feedbackButton = (Button) v.findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackFragment feedbackFragment = new FeedbackFragment();
                FragmentManager manager = getFragmentManager();
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                manager.beginTransaction()
                        .replace(R.id.content_user_main, feedbackFragment, feedbackFragment.getTag())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return v;
    }


    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;
        TextView guestIDText;
        TextView guestPasswordText;
        TextView guestNameText;
        TextView guestEmailText;
        TextView guestGenderText;
        TextView guestHeightText;
        TextView guestWeightText;
        TextView guestAgeText;
        TextView guestPTText;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://kjg123kg.cafe24.com/UserSelect_SYG.php?userID=" + URLEncoder.encode(UserMainActivity.userID, "UTF-8");

                guestIDText = (TextView) getView().findViewById(R.id.guestID);
                guestPasswordText = (TextView) getView().findViewById(R.id.guestPassword);
                guestNameText = (TextView) getView().findViewById(R.id.guestName);
                guestEmailText = (TextView) getView().findViewById(R.id.guestEmail);
                guestGenderText = (TextView) getView().findViewById(R.id.guestGender);
                guestHeightText = (TextView) getView().findViewById(R.id.guestHeight);
                guestWeightText = (TextView) getView().findViewById(R.id.guestWeight);
                guestAgeText = (TextView) getView().findViewById(R.id.guestAge);
                guestPTText = (TextView) getView().findViewById(R.id.guestPT);

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

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    userID = object.getString("userID").toString();
                    userPassword = object.getString("userPassword");
                    userName = object.getString("userName");
                    userEmail = object.getString("userEmail");
                    userGender = object.getString("userGender");
                    userHeight = object.getString("userHeight");
                    userWeight = object.getString("userWeight");
                    userAge = object.getString("userAge") + "세";
                    userPT = object.getInt("userPT");
                    count++;
                }


                guestIDText.setText("아이디 : " + userID);
                guestPasswordText.setText("비밀번호 : " + userPassword);
                guestNameText.setText("이름 : " + userName);
                guestEmailText.setText("이메일 : " + userEmail);
                guestGenderText.setText("성별 : " + userGender);
                guestHeightText.setText("키 : " + userHeight + "cm");
                guestWeightText.setText("몸무게 : " + userWeight + "kg");
                guestAgeText.setText("나이 : " + userAge);
                guestPTText.setText("PT 잔여 신청권 갯수 : " + userPT + "번");


                Button userInfoChangeButton = (Button) getView().findViewById(R.id.UserInfoChangeButton);
                userInfoChangeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), UserInfoChange.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPassword", userPassword);
                        intent.putExtra("userName", userName);
                        intent.putExtra("userEmail", userEmail);
                        intent.putExtra("userGender", userGender);
                        intent.putExtra("userHeight", userHeight);
                        intent.putExtra("userWeight", userWeight);
                        intent.putExtra("userAge", userAge);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}