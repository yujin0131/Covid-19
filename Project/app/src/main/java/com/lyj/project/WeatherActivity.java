package com.lyj.project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

public class WeatherActivity extends Activity {

    LinearLayout weather_info, weather_info_area_lay, weather_pm10, weather_pm25, weather_no2;
    TextView weather_info_area, weather_info_date, weather_info_name, weather_pm10_name, weather_pm25_name, weather_no2_name;
    ImageView weather_info_img, weather_pm10_img, weather_pm25_img, weather_no2_img;

    ImageView btn_map_marker, btn_info_close;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<WeatherVO> list;
    WeatherParser weather_parser;
    WeatherVO vo;
    Dialog weatherinfodialog;

    static String pm10grade, pm25grade, no2grade, pm10value, pm25value, no2value, w_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weather_info = findViewById(R.id.weather_info);
        weather_info_area_lay = findViewById(R.id.weather_info_area_lay);
        weather_pm10 = findViewById(R.id.weather_pm10);
        weather_pm25 = findViewById(R.id.weather_pm25);
        weather_no2 = findViewById(R.id.weather_no2);
        weather_info_area = findViewById(R.id.weather_info_area);
        weather_info_date = findViewById(R.id.weather_info_date);
        weather_info_name = findViewById(R.id.weather_info_name);
        weather_pm10_name = findViewById(R.id.weather_pm10_name);
        weather_pm25_name = findViewById(R.id.weather_pm25_name);
        weather_no2_name = findViewById(R.id.weather_no2_name);
        weather_info_img = findViewById(R.id.weather_info_img);
        weather_pm10_img = findViewById(R.id.weather_pm10_img);
        weather_pm25_img = findViewById(R.id.weather_pm25_img);
        weather_no2_img = findViewById(R.id.weather_no2_img);
        btn_map_marker = findViewById(R.id.btn_map_marker);


        //지도마커
        btn_map_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMainActivity.type = "weather";
                Intent i = new Intent(WeatherActivity.this, AreaActivity.class);
                startActivity(i);
                finish();
            }
        });

        weather_info_area_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMainActivity.type = "weather";
                Intent i = new Intent(WeatherActivity.this, AreaActivity.class);
                startActivity(i);
                finish();
            }
        });


        list = new ArrayList<>();
        new weatherAsync().execute();

        weather_parser = new WeatherParser();

        weather_info.setOnClickListener(info); //정보알려주기

        weather_pm10.setOnClickListener(pm10);

        weather_pm25.setOnClickListener(pm25);

        weather_no2.setOnClickListener(no2);

    }//onCreate()

    View.OnClickListener info = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            weatherinfodialog = new Dialog(WeatherActivity.this);
            weatherinfodialog.setContentView(R.layout.weather_info_dialog);
            weatherinfodialog.show();

            btn_info_close = weatherinfodialog.findViewById(R.id.btn_info_close);

            btn_info_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    weatherinfodialog.cancel();
                }
            });
        }
    };

    View.OnClickListener pm10 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(WeatherActivity.this, WeatherDrawActivity.class);
            pm10value = vo.getW_pm10value();
            pm10grade = vo.getW_pm10();
            w_type = "pm10";
            startActivity(i);
        }
    };

    View.OnClickListener pm25 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(WeatherActivity.this, WeatherDrawActivity.class);
            pm25value = vo.getW_pm25value();
            pm25grade = vo.getW_pm25();
            w_type = "pm25";
            startActivity(i);
        }
    };

    View.OnClickListener no2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(WeatherActivity.this, WeatherDrawActivity.class);
            no2value = vo.getW_no2value();
            no2grade = vo.getW_no2();
            w_type = "no2";
            startActivity(i);
        }
    };

    class weatherAsync extends AsyncTask<String, Void, ArrayList<WeatherVO>>{

        @Override
        protected ArrayList<WeatherVO> doInBackground(String... strings) {
            Log.i("my","여기오냐");
            return weather_parser.connectWeather(list);
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherVO> list) {
            vo = list.get(0);
            Log.i("my","여기오냐 : " + vo);
            PrintVO(vo);
        }
    }//weatherAsync()

    //PrintVO
    public void PrintVO(WeatherVO vo){

        weather_info_area.setText("서울특별시 " + AreaActivity.add);
        weather_info_date.setText(vo.getW_dataTime());

        Log.i("my", "list : " + vo.getW_dataTime());

        switch (vo.getW_khai()) {
            case "1":
                weather_info_name.setText("좋음");
                weather_info_name.setTextColor(Color.parseColor("#3498db"));
                weather_info_img.setImageResource(R.drawable.face1);
                break;
            case "0":
            case "2":
                weather_info_name.setText("보통");
                weather_info_name.setTextColor(Color.parseColor("#DFC104"));
                weather_info_img.setImageResource(R.drawable.face2);
                break;
            case "3":
                weather_info_name.setText("나쁨");
                weather_info_name.setTextColor(Color.parseColor("#EF4040"));
                weather_info_img.setImageResource(R.drawable.face3);
                break;
            case "4":
                weather_info_name.setText("매우나쁨");
                weather_info_name.setTextColor(Color.parseColor("#6B40B8"));
                weather_info_img.setImageResource(R.drawable.face4);
                break;
        }

        switch (vo.getW_pm10()) {
            case "1":
                weather_pm10_name.setText("좋음");
                weather_pm10_img.setImageResource(R.drawable.face1);
                break;
            case "0":
            case "2":
                weather_pm10_name.setText("보통");
                weather_pm10_img.setImageResource(R.drawable.face2);
                break;
            case "3":
                weather_pm10_name.setText("나쁨");
                weather_pm10_img.setImageResource(R.drawable.face3);
                break;
            case "4":
                weather_pm10_name.setText("매우나쁨");
                weather_pm10_img.setImageResource(R.drawable.face4);
                break;
        }

        switch (vo.getW_pm25()) {
            case "1":
                weather_pm25_name.setText("좋음");
                weather_pm25_img.setImageResource(R.drawable.face1);
                break;
            case "0":
            case "2":
                    weather_pm25_name.setText("보통");
                    weather_pm25_img.setImageResource(R.drawable.face2);
                    break;
            case "3":
                weather_pm25_name.setText("나쁨");
                weather_pm25_img.setImageResource(R.drawable.face3);
                break;
            case "4":
                weather_pm25_name.setText("매우나쁨");
                weather_pm25_img.setImageResource(R.drawable.face4);
                break;
        }

        switch (vo.getW_no2()) {
            case "1":
                weather_no2_name.setText("좋음");
                weather_no2_img.setImageResource(R.drawable.face1);
                break;
            case "0":
            case "2":
                weather_no2_name.setText("보통");
                weather_no2_img.setImageResource(R.drawable.face2);
                break;
            case "3":
                weather_no2_name.setText("나쁨");
                weather_no2_img.setImageResource(R.drawable.face3);
                break;
            case "4":
                weather_no2_name.setText("매우나쁨");
                weather_no2_img.setImageResource(R.drawable.face4);
                break;
        }


    }//PrintVO

    //뒤로가기 버튼
    @Override
    public void onBackPressed(){
        Intent i = new Intent(WeatherActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}