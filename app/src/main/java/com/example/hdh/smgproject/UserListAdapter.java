package com.example.hdh.smgproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import org.w3c.dom.Text;

import java.util.List;

import static android.support.v4.os.LocaleListCompat.create;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private List<User> saveList;
    private Activity parentActivity;

    CEOMainActivity ceoMainActivity = new CEOMainActivity();

    UserInfoChange userInfoChange = new UserInfoChange();

    public UserListAdapter(Context context, List<User> userList, Activity parentActivity, List<User> saveList) {
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
        this.saveList = saveList;
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
    public View getView(final int i, View convertView, final ViewGroup parent) {
        View v = View.inflate(context, R.layout.user, null);


        final TextView userID = (TextView) v.findViewById(R.id.userID);
        final TextView userPassword = (TextView) v.findViewById(R.id.userPassword);
        final TextView userName = (TextView) v.findViewById(R.id.userName);
        final TextView userGender = (TextView) v.findViewById(R.id.userGender);
        final TextView userAge = (TextView) v.findViewById(R.id.userAge);

        userID.setText(userList.get(i).getUserID());
        userPassword.setText(userList.get(i).getUserPassword());
        userName.setText(userList.get(i).getUserName());
        userGender.setText(userList.get(i).getUserGender());
        userAge.setText(userList.get(i).getUserAge());

        //특정유저의 아이디값을 반환
        v.setTag(userList.get(i).getUserID());


        Button userChangeOfadmin = (Button) v.findViewById(R.id.userChangeOfadmin);
        userChangeOfadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoChange.adminCheck = true;
                Intent intent = new Intent(parentActivity, UserInfoChange.class);

                intent.putExtra("userID", userList.get(i).getUserID());
                intent.putExtra("userPassword", userList.get(i).getUserPassword());
                intent.putExtra("userName", userList.get(i).getUserName());
                intent.putExtra("userEmail", userList.get(i).getUserEmail());
                intent.putExtra("userGender", userList.get(i).getUserGender());
                intent.putExtra("userHeight", userList.get(i).getUserHeight());
                intent.putExtra("userWeight", userList.get(i).getUserWeight());
                intent.putExtra("userAge", userList.get(i).getUserAge());
                intent.putExtra("userPT", userList.get(i).getUserPT());
                parentActivity.startActivity(intent);
            }
        });


        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                builder.setTitle(Html.fromHtml("<strong><font color=\"#ff0000\"> " + "주의"));
                builder.setMessage(Html.fromHtml("</font></strong><br>정말로 삭제하시겠습니까?"));
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {


                                        userList.remove(i);

                                        for (int i = 0; i < saveList.size(); i++) {
                                            if (saveList.get(i).getUserID().equals(userID.getText().toString())) {
                                                saveList.remove(i);
                                                break;
                                            }
                                        }
                                        notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(parentActivity);
                        queue.add(deleteRequest);
                    }
                });

                builder.setPositiveButton("아니오", null);
                builder.create();
                builder.show();
            }
        });
        return v;
    }
}
