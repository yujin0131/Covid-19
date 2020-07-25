package com.lyj.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginMainActivity extends Activity {

    EditText login_id_et, login_pwd_et;
    Button btn_login, btn_join;

    SweetAlertDialog sweetAlertDialog;

    static String type = "first";
    static String name = "";

    static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        pref = getSharedPreferences("ADD" , MODE_PRIVATE);


        login_id_et = findViewById(R.id.login_id_et);
        login_pwd_et = findViewById(R.id.login_pwd_et);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Intent i = new Intent(LoginMainActivity.this, JoinActivity.class);
                    startActivity(i);
                    finish();


            }
        });

        btn_login.setOnClickListener(login);
    }//onCreate()

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String id = login_id_et.getText().toString();
            String pwd = login_pwd_et.getText().toString();
            String result = "id=" + id + "&pwd=" + pwd;

            new LoginTask().execute(result, "type_login");
        }
    };

    class LoginTask extends AsyncTask<String, Void, String> {

        String ip = "192.168.102.18";//내 ip. 학원 : 192.168.102.18 / 집 :192.168.123.110
        String sendMsg, receiveMsg;
        String serverip = "http://" + ip + ":9090/AndroidJSPProject/list.jsp";

        @Override
        protected String doInBackground(String... strings) {

            String str = "";

            try {
                URL url = new URL(serverip);


                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");//전송방식
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = strings[0] + "&type=" + strings[1];

                //서버로 값 전송
                osw.write(sendMsg);
                osw.flush();//얼른가라!!

                //전송완료 후 서버에서 처리해준 결과값 받기
                //getResponseCode() : 200(정상전송)
                //getResponseCode() : 404, 500등,,,(비정상전송)
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader is = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(is);

                    String buffer = "";//한 줄씩 읽어옴
                    while ((str = reader.readLine()) != null) {//한 줄 씩 읽어서 넘긴다.
                        buffer += str;
                    }

                    //최종적으로 돌려받은 JSON형식의 결과값
                    receiveMsg = buffer;

                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");
                    String name = jObject.optString("name");
                    Log.i("my", "name : " + name);

                    Log.i("my", "" + result);
                    if (result.equals("loginok")) {
                        receiveMsg = name;
                    } else if(result.equals("loginno")){
                        receiveMsg = "";
                    } else if(result.equals("loginpwdno")){
                        receiveMsg = "loginpwdno";
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();

            }//try/catch
            return receiveMsg;
        }//doin

        @Override
        protected void onPostExecute(String s) {

            Log.i("my", "s : " + s);
            if(s.equals("")) {
                //Toast.makeText(LoginMainActivity.this, "로그인 안됨", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(LoginMainActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("로그인 실패");
                sweetAlertDialog.setContentText("ID가 일치하지 않습니다.");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                login_id_et.setText("");
                login_pwd_et.setText("");

            }else if(s.equals("loginpwdno")){
                //Toast.makeText(LoginMainActivity.this,s + "pwd오류", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(LoginMainActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("로그인 실패");
                sweetAlertDialog.setContentText("password가 일치하지 않습니다.");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                login_pwd_et.setText("");

            }else {
                //Toast.makeText(LoginMainActivity.this,s + "환영", Toast.LENGTH_SHORT).show();
                name = s;
                Log.i("my", "name : " + name);

                Log.i("user", "가져온거 : " + pref.getString("" + LoginMainActivity.name, ""));
                if(pref.getString("" + LoginMainActivity.name, "").equals("")) {
                    Intent i = new Intent(LoginMainActivity.this, AreaActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(LoginMainActivity.this, MenuActivity.class);
                    startActivity(i);
                    AreaActivity.add = pref.getString("" + LoginMainActivity.name, "");
                    finish();
                }

            }
        }
    }
}