package com.lyj.project;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MsgParser {
    String str;
    String receiveMsg;
    String key = "kaKy0gopW9hInLkFjNBERY7rsiVbvuzMxwbDC7e6xXyZCiYAXif%2FBH6NyoXnQrtcCOBXZpKlNPSzsFKGzB4geA%3D%3D";
    MsgVO msgVO;


    public ArrayList<MsgVO> connectMsg(ArrayList<MsgVO> list){

        Log.i("list", "list:"+list);
        try {
            String urlstr = "http://apis.data.go.kr/1741000/DisasterMsg2/getDisasterMsgList?serviceKey=" + key ;
            URL url = new URL(urlstr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // URL 연결
            Log.i("yu", "여기까지 오나3" + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(connection.getInputStream(), null);

            int parserEvent = parser.getEventType();

            while(parserEvent != XmlPullParser.END_DOCUMENT) {//끝나면 1 *parserEvent : tag값*
                if (parserEvent == XmlPullParser.START_TAG) {//요것들은 2
                    String tagName = parser.getName();

                    if (tagName.equalsIgnoreCase("create_date")) {
                        msgVO = new MsgVO();
                        String date = parser.nextText();
                        if (date.isEmpty())
                            date = "로딩중";
                        msgVO.setCreate_date(date);

                    } else if (tagName.equalsIgnoreCase("Location_id")) {
                        String id = parser.nextText();

                        if (id.isEmpty())
                            id = "로딩중";
                        msgVO.setLocation_id(id);
                    } else if (tagName.equalsIgnoreCase("Location_name")) {
                        String name = parser.nextText();
                        Log.i("name","name : " +name);
                        if (name.isEmpty())
                            name = "로딩중";
                        msgVO.setLocation_name(name);

                    } else if (tagName.equalsIgnoreCase("msg")) {
                        String msg = parser.nextText();
                        Log.i("name","msg : " +msg);
                        if (msg.isEmpty())
                            msg = "로딩중";
                        msgVO.setMsg(msg);
                        list.add(msgVO);
                    }


                }parserEvent = parser.next();
            }
                //while

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
