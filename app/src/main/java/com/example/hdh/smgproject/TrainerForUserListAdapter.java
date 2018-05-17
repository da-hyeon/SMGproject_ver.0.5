package com.example.hdh.smgproject;

import android.app.Activity;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TrainerForUserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private List<User> saveList;
    private Activity parentActivity;


    public String ptDate;

    public TrainerForUserListAdapter(Context context , List<User> userList , Activity parentActivity ){
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }

    //현재 사용자의 개수
    @Override
    public int getCount() {
        return userList.size();
    }

    //유저리스트의 특정 사용자를 반환
    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.trainer_userlist, null);

        final TextView userID = (TextView) v.findViewById(R.id.trainerForuserID);
        final TextView userName = (TextView) v.findViewById(R.id.trainerForuserName);
        final TextView userGender = (TextView) v.findViewById(R.id.trainerForuserGender);
        final TextView userEmail = (TextView) v.findViewById(R.id.trainerForuserEmail);
        final TextView userHeight = (TextView) v.findViewById(R.id.trainerForuserHeight);
        final TextView userWeight = (TextView) v.findViewById(R.id.trainerForuserWeight);
        final TextView userAge = (TextView) v.findViewById(R.id.trainerForuserAge);

        userID.setText(userList.get(i).getUserID());
        userName.setText(userList.get(i).getUserName());
        userGender.setText(userList.get(i).getUserGender());
        userEmail.setText(userList.get(i).getUserEmail());
        userHeight.setText(userList.get(i).getUserHeight());
        userWeight.setText(userList.get(i).getUserWeight());
        userAge.setText(userList.get(i).getUserAge());

        //특정유저의 아이디값을 반환
        v.setTag(userList.get(i).getUserID());

        return v;
    }
}
