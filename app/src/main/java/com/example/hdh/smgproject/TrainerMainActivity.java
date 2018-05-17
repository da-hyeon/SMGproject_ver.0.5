package com.example.hdh.smgproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class TrainerMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public  Bundle bundle;
    public static String userID;
    public Intent intent;
    public String getExtra;
    public static boolean userIDCheck = true;
    public static String tempguestID, tempguestPassword, tempguestName, tempguestEmail, tempguestGender, tempguestHeight, tempguestWeight, tempguestAge, tempguestPT;

    //뷰페이저를 위한 생성
    ViewPager viewPager;
    TrainerSwipeAdapter swipeAdapter;

    //도트를 위한 생성
    LinearLayout sliderDotspanel;
    int dotCounts;
    ImageView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //뷰페이저
        viewPager = findViewById(R.id.trainerViewPager);
        viewPager.setOffscreenPageLimit(1);
        swipeAdapter = new TrainerSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);

        //도트

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        dotCounts = swipeAdapter.getCount();
        dots = new ImageView[dotCounts];

        for(int i=0; i < dotCounts; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i=0; i < dotCounts; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

        //게스트 정보 받아오는 부분
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
        nav_header_id_text.setText(tempguestName + "님 안녕하세요.");

        ImageView logoImageView = (ImageView) nav_header_view.findViewById(R.id.logoImageView);

        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserMainActivity.this, "asd", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TrainerMainActivity.this, TrainerMainActivity.class);
                TrainerMainActivity.this.startActivity(intent);
            }
        });
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        RelativeLayout l = (RelativeLayout)findViewById(R.id.relativelayout);
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
        getMenuInflater().inflate(R.menu.trainer_main, menu);
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



        if (id == R.id.nav_trainerManage) {
            RelativeLayout l = (RelativeLayout) findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_trainer_main, new TrainerManageFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_trainerSchedule) {
            RelativeLayout l = (RelativeLayout)findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_trainer_main, new TrainerScheduleFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_trainerManagePT) {
            RelativeLayout l = (RelativeLayout)findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_trainer_main, new TrainerManagePTFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_trainerEnrollPT) {
            RelativeLayout l = (RelativeLayout)findViewById(R.id.relativelayout);
            l.setVisibility(View.GONE);
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            manager.beginTransaction().replace(R.id.content_trainer_main, new TrainerEnrollPTFragment()).addToBackStack(null).commit();
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

            String guestID, guestPassword, guestName, guestEmail, guestGender, guestHeight, guestWeight, guestAge, guestPT;

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
                }
                count++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}