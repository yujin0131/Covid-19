package com.lyj.project;


import android.util.Log;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WeatherParser {

    WeatherVO vo;
    String myAdd = "";
    String str, receiveMsg;
    String ServiceKey = "LfuQBPeUWYEtGx55cenl6IUnNpmf0mEkIgpoejEFVxruqXd1inP3UP0fm4wKbGLWwgQdPCWiUesbCbJeETZQiA%3D%3D";
    String khai, pm10, pm25, no2, no2value, pm10value, pm25value;

    public ArrayList<WeatherVO> connectWeather(ArrayList<WeatherVO> list){

        try{
            myAdd += URLEncoder.encode(AreaActivity.add, "UTF8");

            String urlStr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName="
                    + myAdd + "&dataTerm=daily&ServiceKey=" + ServiceKey + "&ver=1.3";

            URL url = new URL(urlStr);
            Log.i("my", "url : " + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(connection.getInputStream(), null);

            int parserEvent = parser.getEventType();

            while(parserEvent != XmlPullParser.END_DOCUMENT){//끝나면 1 *parserEvent : tag값*
                if(parserEvent == XmlPullParser.START_TAG){//요것들은 2
                   String tagName = parser.getName();


                    if(tagName.equalsIgnoreCase("dataTime")){
                        vo = new WeatherVO();
                        String dataTime = parser.nextText();//태그 내부의 실제 값
                        vo.setW_dataTime(dataTime);

                    }else if(tagName.equalsIgnoreCase("no2Value")){
                        no2value = parser.nextText();

                        if(no2value.isEmpty())
                            no2value = "0";

                        Log.i("my", "no2value : " + no2value);
                        vo.setW_no2value(no2value);

                    }else if(tagName.equalsIgnoreCase("pm10Value")){
                        pm10value = parser.nextText();

                        if(pm10value.isEmpty())
                            pm10value = "0";

                        Log.i("my", "pm10value : " + pm10value);
                        vo.setW_pm10value(pm10value);

                    }else if(tagName.equalsIgnoreCase("pm25Value")){
                        pm25value = parser.nextText();

                        if(pm25value.isEmpty())
                            pm25value = "0";

                        Log.i("my", "pm25value : " + pm25value);
                        vo.setW_pm25value(pm25value);

                    }else if(tagName.equalsIgnoreCase("khaiGrade")){
                       khai = parser.nextText();

                       if(khai.isEmpty())
                           khai = "0";

                       Log.i("my", "khai : " + khai);
                       vo.setW_khai(khai);

                    }else if(tagName.equalsIgnoreCase("no2Grade")){
                        no2 = parser.nextText();

                        if(no2.isEmpty())
                            no2 = khai;
                        Log.i("my", "no2 : " + no2);
                        vo.setW_no2(no2);

                    }else if(tagName.equalsIgnoreCase("pm10Grade")){
                        pm10 = parser.nextText();

                        if(pm10.isEmpty())
                            pm10 = khai;

                        Log.i("my", "pm10 : " + pm10);
                        vo.setW_pm10(pm10);

                    }else if(tagName.equalsIgnoreCase("pm25Grade")){
                        pm25 = parser.nextText();

                        if(pm25.isEmpty())
                            pm25 = khai;

                        Log.i("my", "pm25 : " + pm25);
                        vo.setW_pm25(pm25);
                        list.add(vo);

                    }
                }parserEvent = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("my", "parser list : " + list);
        return list;
    }
}
