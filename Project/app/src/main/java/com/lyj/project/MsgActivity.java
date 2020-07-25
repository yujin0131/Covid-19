package com.lyj.project;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MsgActivity extends Activity {

    Button btn_my, btn_all;
    ListView msg_list;
    TextView location_id, location_name, create_date, msg;
    ArrayList<MsgVO> list;
    MsgAdapter adapter;
    MsgParser msg_parser;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        //initView();
        Log.i("yu", "여기까지 오나1");
        //new MsgTask().execute().get(); //  API 통신 시작
        new MsgTask().execute();
        list = new ArrayList<>();
        msg_list = findViewById(R.id.msg_list);
        msg_parser = new MsgParser();
    }


    class MsgTask extends AsyncTask<String, Void, ArrayList<MsgVO>> {


        @Override
        protected ArrayList<MsgVO> doInBackground(String... params) { // Background 작업을 실행하는 부분
            Log.i("yu", "여기까지 오나2");
            return msg_parser.connectMsg(list);
        }

        @Override
        protected void onPostExecute(ArrayList<MsgVO> messageVOS) { // Background 작업이 끝난 후 UI 관련 작업을 실행하는 부분
            //super.onPostExecute(list);
            if(adapter == null) {
                adapter = new MsgAdapter(MsgActivity.this, R.layout.msg, messageVOS, msg_list);
                msg_list.setAdapter(adapter);


                Log.i("yu", "여기까지 오나5 : " + msg_list);

            }adapter.notifyDataSetChanged();
           /* create_date.setText(msgVO.getCreate_date());
            location_id.setText(msgVO.getLocation_id());
            location_name.setText(msgVO.getLocation_name());
            msg.setText(msgVO.getMsg());*/

        }
    }

/*    private void initView() {

        create_date = findViewById(R.id.create_date);
        location_id = findViewById(R.id.location_id);
        location_name = findViewById(R.id.location_name);
        msg = findViewById(R.id.msg);

    }*/


}