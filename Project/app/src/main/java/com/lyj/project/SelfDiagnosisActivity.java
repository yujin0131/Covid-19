package com.lyj.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelfDiagnosisActivity extends Activity {

    Button btn_fever_yes, btn_cough_yes, btn_st_yes, btn_dy_yes, btn_finish, btn_end,
            btn_fever_no, btn_cough_no, btn_st_no, btn_dy_no;
    CheckBox check;

    int feverCount = -1;
    int coughCount = -1;
    int stCount = -1;
    int dyCount = -1;

    boolean feverClicked, coughClicked, stClicked, dyClicked  = false;
    boolean noFeverClicked, noCoughClicked, noStClicked, noDyClicked  = false;
    boolean allchecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_diagnosis);

        btn_fever_yes = findViewById(R.id.btn_fever_yes);
        btn_cough_yes = findViewById(R.id.btn_cough_yes);
        btn_st_yes = findViewById(R.id.btn_st_yes);
        btn_dy_yes = findViewById(R.id.btn_dy_yes);

        btn_fever_no = findViewById(R.id.btn_fever_no);
        btn_cough_no = findViewById(R.id.btn_cough_no);
        btn_st_no = findViewById(R.id.btn_st_no);
        btn_dy_no = findViewById(R.id.btn_dy_no);

        btn_finish = findViewById(R.id.btn_finish);
        btn_end = findViewById(R.id.btn_end);
        check = findViewById(R.id.check);

        btn_fever_yes.setOnClickListener(click);
        btn_cough_yes.setOnClickListener(click);
        btn_st_yes.setOnClickListener(click);
        btn_dy_yes.setOnClickListener(click);

        btn_fever_no.setOnClickListener(click);
        btn_cough_no.setOnClickListener(click);
        btn_st_no.setOnClickListener(click);
        btn_dy_no.setOnClickListener(click);

        btn_finish.setOnClickListener(click);
        btn_end.setOnClickListener(click);
        check.setOnClickListener(click);

    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.check: // 모두 아니오로 일괄 선택
                    if(!allchecked || feverClicked || coughClicked || stClicked || dyClicked) {
                        noFeverClicked = true;
                        noCoughClicked = true;
                        noStClicked = true;
                        noDyClicked = true;

                        feverClicked = false;
                        coughClicked = false;
                        stClicked = false;
                        dyClicked = false;

                        btn_fever_no.setBackgroundResource(R.drawable.button_background2);
                        btn_cough_no.setBackgroundResource(R.drawable.button_background2);
                        btn_st_no.setBackgroundResource(R.drawable.button_background2);
                        btn_dy_no.setBackgroundResource(R.drawable.button_background2);

                        btn_fever_yes.setBackgroundResource(R.drawable.button_background);
                        btn_cough_yes.setBackgroundResource(R.drawable.button_background);
                        btn_st_yes.setBackgroundResource(R.drawable.button_background);
                        btn_dy_yes.setBackgroundResource(R.drawable.button_background);

                        feverCount = 0;
                        coughCount = 0;
                        stCount = 0;
                        dyCount = 0;

                        allchecked = true;
                    } else {
                        btn_fever_no.setBackgroundResource(R.drawable.button_background);
                        btn_cough_no.setBackgroundResource(R.drawable.button_background);
                        btn_st_no.setBackgroundResource(R.drawable.button_background);
                        btn_dy_no.setBackgroundResource(R.drawable.button_background);

                        noFeverClicked = false;
                        noCoughClicked = false;
                        noStClicked = false;
                        noDyClicked = false;

                        feverCount = -1;
                        coughCount = -1;
                        stCount = -1;
                        dyCount = -1;

                        allchecked = false;
                    }
                    break;

                case R.id.btn_fever_yes:
                    if(noFeverClicked) {
                        noFeverClicked = false;
                        btn_fever_no.setBackgroundResource(R.drawable.button_background);
                    }

                    if (!feverClicked) {
                        feverCount = 1;
                        btn_fever_yes.setBackgroundResource(R.drawable.button_background2);
                        feverClicked = true;
                        check.setChecked(false);
                    } else {
                        btn_fever_yes.setBackgroundResource(R.drawable.button_background);
                        feverCount = -1;
                        feverClicked = false;
                    }
                    Log.d("우석", "feverCount : " + feverCount);
                    break;

                case R.id.btn_cough_yes:
                    if(noCoughClicked) {
                        noCoughClicked = false;
                        btn_cough_no.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!coughClicked) {
                        coughCount = 1;
                        btn_cough_yes.setBackgroundResource(R.drawable.button_background2);
                        coughClicked = true;
                        check.setChecked(false);
                    } else {
                        btn_cough_yes.setBackgroundResource(R.drawable.button_background);
                        coughCount = -1;
                        coughClicked = false;
                    }
                    Log.d("우석", "coughCount : " + coughCount);
                    break;

                case R.id.btn_st_yes:
                    if(noStClicked) {
                        noStClicked = false;
                        btn_st_no.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!stClicked) {
                        stCount = 1;
                        btn_st_yes.setBackgroundResource(R.drawable.button_background2);
                        stClicked = true;
                        check.setChecked(false);
                    } else {
                        btn_st_yes.setBackgroundResource(R.drawable.button_background);
                        stCount = -1;
                        stClicked = false;
                    }
                    Log.d("우석", "stCount : " + stCount);
                    break;

                case R.id.btn_dy_yes:
                    if(noDyClicked) {
                        noDyClicked = false;
                        btn_dy_no.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!dyClicked) {
                        dyCount = 1;
                        btn_dy_yes.setBackgroundResource(R.drawable.button_background2);
                        dyClicked = true;
                        check.setChecked(false);
                    } else {
                        btn_dy_yes.setBackgroundResource(R.drawable.button_background);
                        dyCount = -1;
                        dyClicked = false;
                    }
                    Log.d("우석", "dyCount : " + dyCount);
                    break;

                case R.id.btn_fever_no:
                    if(feverClicked) {
                        feverClicked = false;
                        btn_fever_yes.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!noFeverClicked) {
                        feverCount = 0;
                        btn_fever_no.setBackgroundResource(R.drawable.button_background2);
                        noFeverClicked = true;
                    } else {
                        btn_fever_no.setBackgroundResource(R.drawable.button_background);
                        feverCount = -1;
                        noFeverClicked = false;
                    }
                    Log.d("우석", "feverCount : " + feverCount);
                    break;

                case R.id.btn_cough_no:
                    if(coughClicked) {
                        coughClicked = false;
                        btn_cough_yes.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!noCoughClicked) {
                        btn_cough_no.setBackgroundResource(R.drawable.button_background2);
                        coughCount= 0;
                        noCoughClicked = true;
                    } else {
                        btn_cough_no.setBackgroundResource(R.drawable.button_background);
                        coughCount= -1;
                        noCoughClicked = false;
                    }
                    Log.d("우석", "coughCount : " + coughCount);
                    break;

                case R.id.btn_st_no:
                    if(stClicked) {
                        stClicked = false;
                        btn_st_yes.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!noStClicked) {
                        btn_st_no.setBackgroundResource(R.drawable.button_background2);
                        stCount = 0;
                        noStClicked = true;
                    } else {
                        btn_st_no.setBackgroundResource(R.drawable.button_background);
                        stCount = -1;
                        noStClicked = false;
                    }
                    Log.d("우석", "stCount : " + stCount);
                    break;

                case R.id.btn_dy_no:
                    if(dyClicked) {
                        dyClicked = false;
                        btn_dy_yes.setBackgroundResource(R.drawable.button_background);
                    }
                    if (!noDyClicked) {
                        btn_dy_no.setBackgroundResource(R.drawable.button_background2);
                        dyCount = 0;
                        noDyClicked = true;
                    } else {
                        btn_dy_no.setBackgroundResource(R.drawable.button_background);
                        dyCount = -1;
                        noDyClicked = false;
                    }
                    Log.d("우석", "dyCount : " + dyCount);
                    break;

                case R.id.btn_finish:
                    int countSum = feverCount + coughCount + stCount + dyCount;
                    Log.d("우석", "feverCount + coughCount + stCount + dyCount : " + feverCount + coughCount + stCount + dyCount);
                    if(feverCount == -1 || coughCount == -1 || stCount == -1 || dyCount == -1) {
                        Toast.makeText(getApplicationContext(),
                                "증상을 모두 선택해주세요", Toast.LENGTH_SHORT).show();
                    }  else {
                        if (countSum >= 1) {
                            Toast.makeText(getApplicationContext(),
                                    "가까운 선별진료소에 방문하여, 검사를 받으시는걸 추천드립니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "통과하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.d("우석", "feverCount + coughCount + stCount + dyCount : " + feverCount + coughCount + stCount + dyCount);
                    Log.d("우석", "countSum : " + countSum);
                    break;
                case R.id.btn_end:
                    Intent i = new Intent(SelfDiagnosisActivity.this, MenuActivity.class);
                    startActivity(i);
                    finish();
            }


        }
    };

}