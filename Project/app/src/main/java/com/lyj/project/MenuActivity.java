package com.lyj.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamite.DynamiteModule;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class MenuActivity extends Activity {

    LinearLayout btn_mask, btn_covid, btn_self_check, btn_covid_info, btn_weather, btn_msg;
    ImageView btn_map_marker, menu_open_drawer;

    //서랍
    TextView drawer_name, drawer_logout, drawer_area_txt;
    LinearLayout drawer_area, drawer_help, drawer;

    SharedPreferences pref;

    SweetAlertDialog sweetAlertDialog;

    DrawerLayout drawerLayout;



    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_covid_info = findViewById(R.id.btn_covid_info);
        btn_self_check = findViewById(R.id.btn_self_check);
        btn_covid = findViewById(R.id.btn_covid);
        btn_mask = findViewById(R.id.btn_mask);
        btn_weather = findViewById(R.id.btn_weather);
        btn_msg = findViewById(R.id.btn_msg);
        drawer_name = findViewById(R.id.drawer_name);
        drawer_logout = findViewById(R.id.drawer_logout);
        drawer_area = findViewById(R.id.drawer_area);
        drawer_help = findViewById(R.id.drawer_help);
        drawer_area_txt = findViewById(R.id.drawer_area_txt);
        btn_map_marker = findViewById(R.id.btn_map_marker);
        menu_open_drawer = findViewById(R.id.menu_open_drawer);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawer = findViewById(R.id.drawer);

        drawer_name.setText(LoginMainActivity.name);
        drawer_area_txt.setText("서울특별시 " +AreaActivity.add);
        Log.i("ha", "area : " + AreaActivity.add);

        menu_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawer);
            }
        });

        btn_covid_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, WepViewActivity.class);
                startActivity(i);
            }
        });

        btn_self_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SelfDiagnosisActivity.class);
                startActivity(i);
            }
        });

        btn_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, MaskActivity.class);
                startActivity(i);
            }
        });

        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, WeatherActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CovidActivity.class);
                startActivity(i);
            }
        });

        btn_map_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMainActivity.type = "menu";
                Intent i = new Intent(MenuActivity.this, AreaActivity.class);
                startActivity(i);
                finish();
            }
        });

        drawer_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMainActivity.type = "menu";
                Intent i = new Intent(MenuActivity.this, AreaActivity.class);
                startActivity(i);
                finish();
            }
        });

        drawer_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, LoginMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        drawer_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sweetAlertDialog = new SweetAlertDialog(MenuActivity.this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("도움 원해?");
                sweetAlertDialog.setContentText("스스로 찾아보는 습관 가지렴");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
            }
        });

        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, MsgActivity.class);
                startActivity(i);
            }
        });
    }//onCreate()

    //뒤로가기 버튼
    @Override
    public void onBackPressed(){

        if(count <= 5 && count != 0){
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "한 번더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

        back.sendEmptyMessage(0);

    }

    Handler back = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            back.sendEmptyMessageDelayed(0,1000);
            count++;
            if (count >= 5){
                back.removeMessages(0);
                count = 0;
            }
        }
    };
}