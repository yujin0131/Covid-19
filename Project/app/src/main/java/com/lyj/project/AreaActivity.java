package com.lyj.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AreaActivity extends Activity {

    static String add;
    private Spinner spinner_add;
    Button btn_add;

    //static String user_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        btn_add = findViewById(R.id.btn_add);


        //user_add = pref.getString(LoginMainActivity.name + "_add", "");
        //user_add = "";


        //spinner 가져오기
        spinner_add = (Spinner)findViewById(R.id.spinner_add);


        //뭐 선택했는지
        spinner_add.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String position = spinner_add.getItemAtPosition(i).toString();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //user_add = LoginMainActivity.name + "_" + add;
                        add = position;
                        if(LoginMainActivity.type.equals("first")) {
                            Intent i = new Intent(AreaActivity.this, MenuActivity.class);
                            startActivity(i);
                            finish();
                        }else if(LoginMainActivity.type.equals("weather")){
                            Intent i = new Intent(AreaActivity.this, WeatherActivity.class);
                            startActivity(i);
                            finish();
                        }else if(LoginMainActivity.type.equals("menu")){
                            Intent i = new Intent(AreaActivity.this, MenuActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                });

            }

            @Override //아무것도 선택 안했을 때
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AreaActivity.this,"주소를 선택하세요",Toast.LENGTH_LONG).show();

            }
        });
    }//onCreate()

    @Override
    protected void onPause() {
        super.onPause();

        LoginMainActivity.pref = getSharedPreferences("ADD", MODE_PRIVATE);
        SharedPreferences.Editor edit = LoginMainActivity.pref.edit();
        edit.putString(""+LoginMainActivity.name, add);
        Log.i("user", LoginMainActivity.name + " : " + add);
        edit.commit();
    }
}