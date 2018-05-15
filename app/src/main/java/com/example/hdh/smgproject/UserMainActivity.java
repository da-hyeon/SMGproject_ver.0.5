package com.example.hdh.smgproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public  Bundle bundle;
    public static String userID;
    public static String userPTNum;
    public Intent intent;
    public String getExtra;
    public static String tempguestID, tempguestPassword, tempguestName, tempguestEmail, tempguestGender, tempguestHeight, tempguestWeight, tempguestAge, tempguestPT;
    public static String guestID, guestPassword, guestName, guestEmail, guestGender, guestHeight, guestWeight, guestAge, guestPT;
    private ListView ptListView;
    private PTListAdapter ptListAdapter;
    private List<PT> ptList;
    static Fragment fragment = new MyinfoFragment();
    public static boolean userIDCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //현재 스마트폰의 화면을 세로로고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        intent = getIntent();

        //로그인화면을 통해 인텐트 되면 true , 회원정보변경을 통해서 인텐트되면 false
        if(userIDCheck) {
            bundle = getIntent().getExtras(); // 로그인 액티비티로 부터 유저 아이디 가져오기
            userID = bundle.getString("keyUserID");
            getExtra = intent.getStringExtra("userList");
        } else{
            userID = intent.getStringExtra("userID");
            getExtra = intent.getStringExtra("userListofChange");
        }




        //게스트 정보 받아오는부분.
        getGuestData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 네비게이션 헤더 이름 변경
        View nav_header_view = navigationView.getHeaderView(0);

        TextView nav_header_id_text = (TextView) nav_header_view.findViewById(R.id.idTextfromNav);
        nav_header_id_text.setText(tempguestID + "님 안녕하세요.");

        ImageView logoImageView = (ImageView) nav_header_view.findViewById(R.id.logoImageView);

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserMainActivity.this, "asd", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserMainActivity.this, UserMainActivity.class);
                UserMainActivity.this.startActivity(intent);
            }
        });


    }

    private long lastTimeBackPressed;


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
        l.setVisibility(View.VISIBLE);


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this , "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_myinfo) {
            LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new MyinfoFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_reservation) {
            LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new ReservationFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_schdulefix) {
            LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new ScheduleFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_feedback) {
            LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new FeedbackFragment()).addToBackStack(null).commit();
        } else if (id == R.id.Recommend) {
            LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_user_main, new RecommendFragment()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getGuestData(){
        try {
            JSONObject jsonObject;
            if(userIDCheck) {
                jsonObject = new JSONObject(intent.getStringExtra("userList"));
            } else{
                jsonObject = new JSONObject(intent.getStringExtra("userListofChange"));
            }
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            int count = 0;



            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);

                guestID = object.getString("userID");
                guestPassword = object.getString("userPassword");
                guestName = object.getString("userName");
                guestEmail = object.getString("userEmail");
                guestGender = object.getString("userGender");
                guestHeight = object.getString("userHeight");
                guestWeight = object.getString("userWeight");
                guestAge = object.getString("userAge") + "세";
                guestPT = object.getString("userPT");

                if (guestName.equals("")) {
                    guestName = "정보없음";
                }
                if (guestGender.equals("")) {
                    guestGender = "정보없음";
                }
                if (guestAge.equals("세")) {
                    guestAge = "정보없음";
                }

                if (userID.equals(guestID)) {
                    tempguestID = guestID;
                    tempguestPassword = guestPassword;
                    tempguestName = guestName;
                    tempguestEmail = guestEmail;
                    tempguestGender = guestGender;
                    tempguestHeight = guestHeight;
                    tempguestWeight = guestWeight;
                    tempguestAge = guestAge;
                    tempguestPT = guestPT;

                    userPTNum = guestPT;
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
