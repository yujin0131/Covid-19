package com.lyj.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

public class WeatherDrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_draw);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int)(dm.widthPixels * 0.8);
        int height = (int) (dm.heightPixels * 0.4);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        setContentView(new WeatherDraw(this));






    }



}