package com.example.hdh.smgproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserInfoChange extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userGender;
    private String userHeight;
    private String userWeight;
    private String userAge;
    private String userValue;
    private String userPT;
    public String PassuserID;

    static public boolean adminCheck = false;

    UserMainActivity userMainActivity = new UserMainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        Intent intent = getIntent();
        final String getUserID = intent.getStringExtra("userID");
        String getUserPassword = intent.getStringExtra("userPassword");
        String getUserName = intent.getStringExtra("userName");
        String getUserEmail = intent.getStringExtra("userEmail");
        String getUserGender = intent.getStringExtra("userGender");
        String getUserHeight = intent.getStringExtra("userHeight");
        String getUserWeight = intent.getStringExtra("userWeight");
        String getUserAge = intent.getStringExtra("userAge");
        int getUserPTNum = intent.getIntExtra("userPT" , 0);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int gengerGroupID = genderGroup.getCheckedRadioButtonId();  //남자인지 여자인지 확인하는변수
        userGender = ((RadioButton) findViewById(gengerGroupID)).getText().toString(); //현재 선택된 젠더의 ID를 가져옴

        //라디오 버튼을 클릭했을때 대한 이벤트 처리
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderButton = (RadioButton) findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });

        final EditText heightText = (EditText) findViewById(R.id.heightText);
        final EditText weightText = (EditText) findViewById(R.id.weightText);
        final EditText ageText = (EditText) findViewById(R.id.ageText);

        final TextView ptNumText = (TextView) findViewById(R.id.ptIDText);
        final EditText ptNumEdit = (EditText) findViewById(R.id.ptNUmEdit);

        if(!adminCheck){
            ptNumText.setVisibility(View.GONE);
            ptNumEdit.setVisibility(View.GONE);
        }

        idText.setText(getUserID);
        PassuserID = getUserID;
        idText.setEnabled(false);
        passwordText.setText(getUserPassword);
        nameText.setText(getUserName);
        emailText.setText(getUserEmail);

        if(getUserGender.equals("남성")){
            genderGroup.check(R.id.genderMan);
        } else{
            genderGroup.check(R.id.genderWoman);
        }

        heightText.setText(getUserHeight);
        weightText.setText(getUserWeight);
        ageText.setText(getUserAge.substring(0, getUserAge.length()-1));
        ptNumEdit.setText(String.valueOf(getUserPTNum));

        Button changeButton = (Button) findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String userHeight = heightText.getText().toString();
                String userWeight = weightText.getText().toString();
                String userAge = ageText.getText().toString();
                int userPTNum = Integer.parseInt(ptNumEdit.getText().toString());

                if(userPassword.equals("") ||
                        userName.equals("") ||
                        userGender.equals("") ||
                        userEmail.equals("") ||
                        userHeight.equals("") ||
                        userWeight.equals("") ||
                        userAge.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoChange.this);
                    builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    builder.show();
                    return;

                }


                //응답받기
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoChange.this);
                                builder.setMessage("회원 변경에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new BackGroundTask().execute();
                                            }
                                        })
                                        .create();
                                builder.show();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoChange.this);
                                builder.setMessage("회원 변경에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                builder.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                if(!adminCheck) {
                    UserInfoChangeRequest userInfoChangeRequest = new UserInfoChangeRequest(userID, userPassword, userName, userEmail, userGender, userHeight, userWeight, userAge, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(UserInfoChange.this);
                    queue.add(userInfoChangeRequest);
                }else{
                    UserInfoChangeRequest userInfoChangeRequest = new UserInfoChangeRequest(userID, userPassword, userName, userEmail, userGender, userHeight, userWeight, userAge , userPTNum, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(UserInfoChange.this);
                    queue.add(userInfoChangeRequest);
                }
            }
        });
    }



    class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://kjg123kg.cafe24.com/UserList_SYG.php";

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
            if(!adminCheck) {
                Intent intentToUserMain = new Intent(UserInfoChange.this, UserMainActivity.class);
                intentToUserMain.putExtra("userID", PassuserID);
                intentToUserMain.putExtra("userListofChange", result);
                UserInfoChange.this.startActivity(intentToUserMain);
                userMainActivity.userIDCheck = false;
                finish();
            } else{
                Intent intentToUserMain = new Intent(UserInfoChange.this, CEOMainActivity.class);
                //intentToUserMain.putExtra("userID", PassuserID);
                intentToUserMain.putExtra("userListofChange", result);
                UserInfoChange.this.startActivity(intentToUserMain);
                adminCheck = false;
                finish();
            }
        }


    }
}