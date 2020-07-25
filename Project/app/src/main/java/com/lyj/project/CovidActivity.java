package com.lyj.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CovidActivity extends Activity {
    private TextView tv_updateTime, tv_nowCase, tv_newCase, tv_TodayRecovered,
            tv_caseCount, tv_TotalRecovered, tv_TotalDeath, tv_TotalChecking,
            tv_checkingCounter, tv_notcaseCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        initView();

        try {
            // API 통신 시작
            new getTotalDataTask().execute().get(); // 국내 Total 카운터 데이터를 받아오는 API 통신 시작
            new getAreaDataTask().execute().get(); // 시도별 카운터 데이터를 받아오는 API 통신 시작
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private class getTotalDataTask extends AsyncTask<String, String, String> {
        private String str = "";
        private String receiveMsg = "";
        CovidTotalCoronaData totalCoronaData = new CovidTotalCoronaData();

        @Override
        protected String doInBackground(String... params) { // Background 작업을 실행하는 부분
            URL url = null;
            try {
                url = new URL("http://api.corona-19.kr/korea/?serviceKey=a3de9293d5e8842a78ae05ced6cc9029f");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL 연결

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    // json 데이터 -> StringBuffer -> String 타입의 변수(receiveMsg) 에 담는 과정
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg : ", receiveMsg);

                    // receiveMsg 의 key 에 해당하는 값으로 value 를 추츨하여 VO 에 저장하는 과정
                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    totalCoronaData.setUpdateTime(jsonObject.optString("updateTime")); // 최신 업데이트 날짜
                    totalCoronaData.setNowCase(jsonObject.optString("NowCase")); // 격리중
                    totalCoronaData.setTodayRecovered(jsonObject.optString("TodayRecovered")); // 일일 완치자

                    totalCoronaData.setTodayDeath(jsonObject.optString("TodayDeath")); // today 사망자
                    totalCoronaData.setCaseCount(jsonObject.optString("caseCount")); // 확진환자
                    totalCoronaData.setTotalRecovered(jsonObject.optString("TotalRecovered")); // 완치(격리해제)
                    totalCoronaData.setTotalDeath(jsonObject.optString("TotalDeath")); // 사망자
                    totalCoronaData.setTotalChecking(jsonObject.optString("TotalChecking")); // 누적 검사수
                    totalCoronaData.setCheckingCounter(jsonObject.optString("checkingCounter")); // 검사중
                    totalCoronaData.setNotcaseCount(jsonObject.optString("notcaseCount")); // 결과음성

                    reader.close(); // BufferedReader 를 더이상 사용하지 않을 때 이것을 써서 시스템 자원 해제
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) { // Background 작업이 끝난 후 UI 관련 작업을 실행하는 부분
            super.onPostExecute(s);
            tv_updateTime.setText(totalCoronaData.getUpdateTime());
            tv_nowCase.setText(totalCoronaData.getNowCase());
            tv_TodayRecovered.setText(totalCoronaData.getTodayRecovered());

            tv_caseCount.setText(totalCoronaData.getCaseCount());
            tv_TotalRecovered.setText(totalCoronaData.getTodayRecovered());
            tv_TotalDeath.setText(totalCoronaData.getTodayDeath());
            tv_TotalChecking.setText(totalCoronaData.getTotalChecking());
            tv_checkingCounter.setText(totalCoronaData.getCheckingCounter());
            tv_notcaseCount.setText(totalCoronaData.getNotcaseCount());
        }
    }



    private class getAreaDataTask extends AsyncTask<String, String, String> {
        private String str = "";
        private String receiveMsg = "";
        private String newCaseNum = ""; // Total 일일 확진자 수
        ArrayList<CovidAreaCoronaData> item = new ArrayList<CovidAreaCoronaData>();

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL("http://api.corona-19.kr/korea/country/new/?serviceKey=a3de9293d5e8842a78ae05ced6cc9029f");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    JSONObject jsonObject = new JSONObject(receiveMsg);

                    // json 데이터의 countryName 에 해당하는 값을 String 배열변수에 담음(key 로 사용하기 위함)
                    String[] countryNameArray = {"seoul", "busan", "daegu" , "incheon", "gwangju", "daejeon", "ulsan",
                            "sejong", "gyeonggi", "gangwon", "chungbuk", "chungnam", "jeonbuk", "jeonnam",
                            "gyeongbuk", "gyeongnam", "jeju"};

                    // key 에 해당하는 countryName 을 통해 value 를 추출하여 VO 에 저장하는 과정
                    for (int i = 0; i < countryNameArray.length; i++) { // 배열변수에서 하나씩 꺼내 써야 하므로 for문 사용
                        String nameKey = jsonObject.optString(countryNameArray[i]);
                        Log.i("countryNameArray", countryNameArray[i]);

                        JSONObject subObject = new JSONObject(nameKey);
                        subObject.optString("countryName");
                        subObject.optString("newCase");
                        subObject.optString("totalCase");
                        subObject.optString("recovered");
                        subObject.optString("death");
                        subObject.optString("percentage");
                        subObject.optString("newFcase");
                        subObject.optString("newCcase");

                        CovidAreaCoronaData areaCoronaData = new CovidAreaCoronaData();

                        areaCoronaData.setCountryName(subObject.optString("countryName"));
                        areaCoronaData.setNewCase(subObject.optString("newCase"));
                        areaCoronaData.setTotalCase(subObject.optString("totalCase"));
                        areaCoronaData.setRecovered(subObject.optString("recovered"));
                        areaCoronaData.setDeath(subObject.optString("death"));
                        areaCoronaData.setPercentage(subObject.optString("percentage"));
                        areaCoronaData.setNewFcase(subObject.optString("newFcase"));
                        areaCoronaData.setNewCase(subObject.optString("newCcase"));

                        item.add(areaCoronaData);
                    }

                    // 국내 Total 카운터에 있는 일일 확진자 수는 여기서 구함
                    String totalCountData = jsonObject.optString("korea");
                    JSONObject totalCountObject = new JSONObject(totalCountData);
                    newCaseNum = totalCountObject.optString("newCase");

                    reader.close();
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            tv_newCase.setText(newCaseNum); // 위에서 구한 newCaseNum 를 세팅

            // AreaCoronaData 의 짝수 index 에 해당하는 지역 데이터 세팅(시도별 감염현황 데이터 중 왼쪽 줄)
            int z = 0;
            for(int i = 0; i < getLoopCount(item.size()); i++) { // 전체 지역들을 왼쪽, 오른쪽 반반씩 나눠서 세팅할 것이므로 item.size 의 절반만 count 함
                CovidLeftAreaItemView leftAreaItemView = new CovidLeftAreaItemView(getApplicationContext()); // 지역개수에 따라 동적으로 레이아웃을 생성해야 하므로 이런 방식을 사용함.
                LinearLayout layout_left_areaBoard = (LinearLayout)findViewById(R.id.layout_left_areaBoard);
                layout_left_areaBoard.addView(leftAreaItemView);

                leftAreaItemView.setItemView(item.get(z));
                z = z + 2;
            }

            // AreaCoronaData 의 홀수 index 에 해당하는 지역 데이터 세팅(시도별 감염현황 데이터 중 오른쪽 줄)
            int h = 1;
            for(int i = 1; i < getLoopCount(item.size()); i++) {
                CovidRightAreaItemView rightAreaItemView = new CovidRightAreaItemView(getApplicationContext());
                LinearLayout layout_right_areaBoard = (LinearLayout)findViewById(R.id.layout_right_areaBoard);
                layout_right_areaBoard.addView(rightAreaItemView);

                rightAreaItemView.setItemView(item.get(h));
                h = h + 2;
            }
        }
    }

    private void initView() {
        tv_updateTime = findViewById(R.id.tv_updateTime);
        tv_nowCase = findViewById(R.id.tv_nowCase);
        tv_newCase = findViewById(R.id.tv_newCase);
        tv_TodayRecovered = findViewById(R.id.tv_TodayRecovered);
        tv_caseCount = findViewById(R.id.tv_caseCount);
        tv_TotalRecovered = findViewById(R.id.tv_TotalRecovered);
        tv_TotalDeath = findViewById(R.id.tv_TotalDeath);
        tv_TotalChecking = findViewById(R.id.tv_TotalChecking);
        tv_checkingCounter = findViewById(R.id.tv_checkingCounter);
        tv_notcaseCount = findViewById(R.id.tv_notcaseCount);
    }

    private int getLoopCount(int itemCountNum) {
        if(itemCountNum % 2 == 0) {
            return itemCountNum / 2;
        } else {
            return itemCountNum / 2 + 1;
        }
    }

}