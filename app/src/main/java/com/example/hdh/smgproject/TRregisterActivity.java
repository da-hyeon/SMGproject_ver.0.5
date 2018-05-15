package com.example.hdh.smgproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class TRregisterActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userGender;
    private String userHeight;
    private String userWeight;
    private String userAge;
    private String userValue;
    CEOMainActivity ceoMainActivity = new CEOMainActivity();

    private boolean validate = false;   //사용할수 있는 회원 아이디인지 검사하는 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trregister);

        userValue = "0";

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



        //회원 중복체크 버튼을 눌렀을때 이벤트처리
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                    builder.setMessage("아이디는 빈칸일 수 없습니다")
                            .setPositiveButton("확인", null)
                            .create();
                    builder.show();
                    return;
                }
                if(userID.contains("admin"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                    builder.setMessage("아이디에 admin이 들어갈 수 없습니다.")
                            .setPositiveButton("확인", null)
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                                builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                builder.show();
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                return;
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                                builder.setMessage("사용할 수 없는 아이디입니다.")
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
                ValidateRequest validateRequest = new ValidateRequest(userID , responseListener);
                RequestQueue queue = Volley.newRequestQueue(TRregisterActivity.this);
                queue.add(validateRequest);
            }
        });
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userEmail = emailText.getText().toString();
                String userHeight = heightText.getText().toString();
                String userWeight = weightText.getText().toString();
                String userAge = ageText.getText().toString();
                String userValue = "0";
                String userPT = "0";
                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                    builder.setMessage("먼저 중복체크를 해주세요")
                            .setNegativeButton("확인", null)
                            .create();
                    builder.show();
                    return;
                }
                if(userID.equals("") ||
                        userPassword.equals("") ||
                        userName.equals("") ||
                        userGender.equals("") ||
                        userEmail.equals("") ||
                        userHeight.equals("") ||
                        userWeight.equals("") ||
                        userAge.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(TRregisterActivity.this , LoginActivity.class);    //LoginActivity에서 RegisterActivity로 화면전환
                                                TRregisterActivity.this.startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                builder.show();

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(TRregisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
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

                RegisterRequest registerRequest = new RegisterRequest(userID , userPassword , userName, userEmail, userGender , userHeight , userWeight , userAge , userValue , userPT , responseListener);
                RequestQueue queue = Volley.newRequestQueue(TRregisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
