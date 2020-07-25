package com.lyj.project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MaskParser {
    MaskVO vo;
    //검색어
    String myAdd = "서울특별시 ";
    String str, receiveMsg;
    public ArrayList<MaskVO> connectMask(ArrayList<MaskVO> list) {
        try {
           /* myAdd += URLEncoder.encode(AreaActivity.add, "UTF8");

            Log.i("my","제발 : "+ myAdd);

            //서버에 요청할 URI
            String urlStr = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address=" + myAdd;

            //경로 접근 URL로 변환
            URL url = new URL(urlStr);

            Log.i("my","url : "+ url);

            // URL 클래스의 연결 정보를 connection 객체에게 전달 -> 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if(connection.getResponseCode() == connection.HTTP_OK){
                InputStreamReader is = new InputStreamReader(connection.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(is);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

                JSONObject jsonObject = new JSONObject(receiveMsg);
                //String address = jsonObject.get("address").toString();
                //String count = jsonObject.get("count").toString();

                //Log.i("my","1 : " +address);
                //Log.i("my","2 : " +count);
                JSONArray jsonArray = jsonObject.getJSONArray("stores");*/

                receiveMsg = "{'count': 83,'stores': [{'addr':'서울특별시 강남구 논현로95길 29-13 2층 (역삼동)','code': '12848069','created_at': '2020/07/03 14:15:00','lat': 37.5010881,'lng': 127.0342169,'name': '경희자연한약국','remain_stat': 'plenty','stock_at': '2020/06/30 12:52:00','type': '01'},{'addr':'서울특별시 강남구 논현로95길 29-13 2층 (역삼동)','code': '12848069','created_at': '2020/07/03 14:15:00','lat': 37.5010881,'lng': 127.0342169,'name': '유진약국','remain_stat': 'empty','stock_at': '2020/06/30 12:52:00','type': '01'},{'addr':'서울특별시 강남구 논현로95길 29-13 2층 (역삼동)','code': '12848069','created_at': '2020/07/03 14:15:00','lat': 37.5010881,'lng': 127.0342169,'name': '코리아아카데미약국','remain_stat': 'some','stock_at': '2020/06/30 12:52:00','type': '01'},{'addr':'서울특별시 강남구 논현로95길 29-13 2층 (역삼동)','code': '12848069','created_at': '2020/07/03 14:15:00','lat': 37.5010881,'lng': 127.0342169,'name': '덤탱이약국','remain_stat': 'few','stock_at': '2020/06/30 12:52:00','type': '01'}]}";

                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray jsonArray = jsonObject.getJSONArray("stores");
                //Log.i("my","3 : " + jsonArray);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject maskObject = jsonArray.getJSONObject(i);
                    vo = new MaskVO();
                    String addr = maskObject.get("addr").toString();
                    String lat = maskObject.get("lat").toString();
                    String lng = maskObject.get("lng").toString();
                    String name = maskObject.get("name").toString();
                    String remain_stat = maskObject.get("remain_stat").toString();
                    String type = maskObject.get("type").toString();

                    switch (remain_stat){
                        case "empty":
                            remain_stat = "품절";
                            break;
                        case "plenty":
                            remain_stat = "100개 이상 넉넉!";
                            break;
                        case "few":
                            remain_stat = "2 ~ 30 마감임박!";
                            break;
                        case "some":
                            remain_stat = "30 ~ 100";
                            break;
                        case "break":
                            remain_stat = "판매중지";
                            break;
                    }

                    vo.setM_add(addr);
                    vo.setM_lat(lat);
                    vo.setM_lng(lng);
                    vo.setM_name(name);
                    vo.setM_remain_stat(remain_stat);
                    vo.setM_type(type);

                    list.add(vo);
                }




    }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
