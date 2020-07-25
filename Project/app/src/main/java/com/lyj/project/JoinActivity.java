package com.lyj.project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class JoinActivity extends Activity{

    EditText join_name_et, join_id_et, join_pwd_et, join_pwd_check_et, join_email_et, join_phone_et;
    Button btn_join_id_check, btn_join_email_check, btn_join_join, btn_join_return;
    TextView join_pwd_check_msg, join_email_check_msg;

    String id, email;

    int email_check_num = 0;

    String id_join_check = "";

    SweetAlertDialog sweetAlertDialog;

    private String emailCode;

    private String finalemail;

    //LayoutInflater dialog; -> 이렇게 하면 작아짐 왜지,,,
    View dialogLayout;
    Dialog emailcheckdialog;


    //다이얼로그용
    EditText dialog_email_check_et;
    Button dialog_email_check_btn;
    TextView dialog_email_time_counter;
    CountDownTimer countDownTimer;
    final int TOTALCOUNT = 50 * 1000; //나중에 180으로 바꿔라!!!!
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드 호출할 간격


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        join_name_et = findViewById(R.id.join_name_et);
        join_id_et = findViewById(R.id.join_id_et);
        join_pwd_et = findViewById(R.id.join_pwd_et);
        join_pwd_check_et = findViewById(R.id.join_pwd_check_et);
        join_email_et = findViewById(R.id.join_email_et);
        join_phone_et = findViewById(R.id.join_phone_et);
        btn_join_id_check = findViewById(R.id.btn_join_id_check);
        btn_join_email_check = findViewById(R.id.btn_join_email_check);
        btn_join_join = findViewById(R.id.btn_join_join);
        btn_join_return = findViewById(R.id.btn_join_return);
        join_pwd_check_msg = findViewById(R.id.join_pwd_check_msg);
        join_email_check_msg = findViewById(R.id.join_email_check_msg);

        //아이디 중복체크
        btn_join_id_check.setOnClickListener(id_check);

        //비밀번호 일치 확인
        join_pwd_check_et.setOnKeyListener(pwd_check);

        //돌아가기 버튼 감지자 등록
        btn_join_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(JoinActivity.this, LoginMainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //이메일 체크
        btn_join_email_check.setOnClickListener(email_check);

        //회원가입 버튼 감지자 등록
        btn_join_join.setOnClickListener(join);

    }//onCreate()

    //아이디 중복 감지
    View.OnClickListener id_check = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            id = join_id_et.getText().toString();

            if(id.equals("")){
                Toast.makeText(JoinActivity.this,"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
            }else {

                new JoinTask().execute("id=" + id, "type_id_check");
            }
        }
    };//id_check

    //비밀번호 일치 감지
    View.OnKeyListener pwd_check = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {

            if(!(join_pwd_et.getText().toString().equals("")) &&
                    !(join_pwd_check_et.getText().toString().equals(join_pwd_et.getText().toString()))) {
                join_pwd_check_msg.setText("비밀번호가 일치하지 않습니다!");
                join_pwd_check_msg.setTextColor(Color.parseColor("#770303"));
            }else if(!(join_pwd_et.getText().toString().equals("")) &&
                    !(join_pwd_check_et.getText().toString().equals("")) &&
                    join_pwd_check_et.getText().toString().equals(join_pwd_et.getText().toString())){
                join_pwd_check_msg.setText("비밀번호가 일치합니다!");
                join_pwd_check_msg.setTextColor(Color.parseColor("#005C31"));
            }

            return false;
        }
    };//pwd_check

    //이메일 체크 버튼
    View.OnClickListener email_check = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            email = join_email_et.getText().toString();

            Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
            Matcher m = p.matcher(join_email_et.getText().toString());

            if (!m.matches()) {
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("이메일 오류");
                sweetAlertDialog.setContentText("이메일 형식이 올바르지 않습니다.\n 다시 입력 해 주세요.");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                join_email_et.setText("");
                /*join_email_check_msg.setText("E-mail 형식이 올바르지 않습니다! 다시 입력해 주세요");
                join_email_check_msg.setTextColor(Color.parseColor("#770303"));*/
            } else {
                new JoinTask().execute("email=" + email, "type_email_check");
                Log.i("my", "email_check_num1 : " + email_check_num);

                }
        }
    };

    //마지막 판별
    public void FinalEmail(){
        if(email_check_num == 1) {

            emailCode = createEmailCode();
            try {

                Log.i("my", "인증코드 : " + emailCode);
                JoinGMailSender joingMailSender = new JoinGMailSender("lyujin0131@gmail.com", "dbwls0131@");
                joingMailSender.sendMail("코리아 자가 지킴이 인증코드", "인증코드를 입력해주세요.\n" + emailCode, "lyujin0131@gmail.com", join_email_et.getText().toString());
                Log.i("my", "성공");
            } catch (SendFailedException e) {
                Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (MessagingException e) {
                Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //dialog = LayoutInflater.from(JoinActivity.this);
            //dialogLayout = dialog.inflate(R.layout.email_check_dialog, null);//view로 보여줌
            emailcheckdialog = new Dialog(JoinActivity.this);//dialog 객체 생성
            emailcheckdialog.setContentView(R.layout.email_check_dialog);//dialog에 inflate한 view탑재
            emailcheckdialog.show();
            countDownTimer();
        }else if(email_check_num == 2){
            sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("이메일 오류");
            sweetAlertDialog.setContentText("이미 가입된 이메일 입니다.\n 다른 이메일을 입력해 주세요.");
            sweetAlertDialog.setConfirmText("확인");
            sweetAlertDialog.show();
            join_email_et.setText("");
            join_email_check_msg.setText("이미 가입 된 E-mail 입니다.");
            join_email_check_msg.setTextColor(Color.parseColor("#770303"));
        }
    }

    //이메일 코드
    public String createEmailCode(){
        //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 6; x++){
            int random = (int)(Math.random() * str.length);
            newCode += str[random];
        }
        Log.i("my","이까지 온다 안온다");
        return newCode;
    }


    //카운트 다운 메서드
    public void countDownTimer(){
        //줄어드는 시간을 나타내는 TV
        dialog_email_time_counter = (TextView)emailcheckdialog.findViewById(R.id.dialog_email_time_counter);
        //사용자 인증 번호 Et
        dialog_email_check_et = (EditText)emailcheckdialog.findViewById(R.id.dialog_email_check_et);
        //인증하기 버튼
        dialog_email_check_btn = (Button)emailcheckdialog.findViewById(R.id.dialog_email_check_btn);

        /*dialog_email_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("plz","여긴오나");
            }
        });*/

        countDownTimer = new CountDownTimer(TOTALCOUNT, COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long l) {//3분에서 1초마다 줄어듦
                Log.i("plz", "여기 오긴하니");
                long email_check_time_count = l / 1000;
                Log.i("plz", "time : " + email_check_time_count);

                if ((email_check_time_count - ((email_check_time_count / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    dialog_email_time_counter.setText((email_check_time_count / 60) + " : " + (email_check_time_count - ((email_check_time_count / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    dialog_email_time_counter.setText((email_check_time_count / 60) + " : 0" + (email_check_time_count - ((email_check_time_count / 60) * 60)));
                }

                //email_check_time_count는 종료까지 남은 시간 1분 = 60초 되므로,
                //분을 나타내려면 종료까지 남은 총 시간에 60을 나누면 그 몫이 분
                //분을 제외하고 남은 초는, (총 남은 시간 - (분*60) = 남은 초)

            }

            @Override
            public void onFinish() {
                if(email_check_num == 3) {
                    emailcheckdialog.cancel();
                }else {
                    sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("이메일 인증 실패");
                    sweetAlertDialog.setContentText("인증시간이 초과되었습니다.");
                    sweetAlertDialog.setConfirmText("확인");
                    sweetAlertDialog.show();
                    emailcheckdialog.cancel();
                    join_email_check_msg.setText("E-mail인증을 해주세요");
                    join_email_check_msg.setTextColor(Color.parseColor("#404040"));
                }

            }
        }.start();

        dialog_email_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_answer = dialog_email_check_et.getText().toString();
                if (user_answer.equals(emailCode)){
                    join_email_check_msg.setText("E-mail 인증 완료");
                    join_email_check_msg.setTextColor(Color.parseColor("#005C31"));
                    btn_join_email_check.setEnabled(false);
                    join_email_et.setEnabled(false);
                    emailcheckdialog.cancel();
                    email_check_num = 3;
                    countDownTimer.onFinish();

                }else {
                   // Toast.makeText(JoinActivity.this,"실패",Toast.LENGTH_SHORT).show();
                    sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("인증코드 오류");
                    sweetAlertDialog.setContentText("인증코드를 확인해주세요");
                    sweetAlertDialog.setConfirmText("확인");
                    sweetAlertDialog.show();
                    dialog_email_check_et.setText("");
                    join_email_check_msg.setText("인증코드를 확인해 주세요");
                    join_email_check_msg.setTextColor(Color.parseColor("#770303"));
                }
            }
        });
    }



    //회원가입 버튼 감지
    View.OnClickListener join = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(id_join_check.equals("")) {
                if(join_id_et.getText().toString().equals("")){
                    //Toast.makeText(JoinActivity.this, "id를 입력하세요", Toast.LENGTH_SHORT).show();
                    sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("회원가입 실패");
                    sweetAlertDialog.setContentText("ID를 입력하세요");
                    sweetAlertDialog.setConfirmText("확인");
                    sweetAlertDialog.show();
                }else {
                    //Toast.makeText(JoinActivity.this, "id중복체크를 하세요", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(JoinActivity.this, "id를 입력하세요", Toast.LENGTH_SHORT).show();
                    sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.setTitleText("회원가입 실패");
                    sweetAlertDialog.setContentText("ID를 중복체크를 해주세요");
                    sweetAlertDialog.setConfirmText("확인");
                    sweetAlertDialog.show();
                }
            }else if(id_join_check.equals("already")){
                //Toast.makeText(JoinActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("회원가입 실패");
                sweetAlertDialog.setContentText("ID를 입력하세요");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();

            }else if(id_join_check.equals("yes")) {
                String name = join_name_et.getText().toString();
                String id = join_id_et.getText().toString();
                String pwd = join_pwd_et.getText().toString();
                String email = join_email_et.getText().toString();
                String phone = join_phone_et.getText().toString();
                String result = "name=" + name + "&id=" + id + "&pwd=" + pwd + "&email=" + email + "&phone=" + phone;

                new JoinTask().execute(result, "type_join");
            }
        }
    };//join




    //회원가입,id중복체크 , email중복체크크 용 Async
    class JoinTask extends AsyncTask<String, Void, String>{

        String ip = "192.168.102.18";
        String sendMsg, receiveMsg;
        String serverip = "http://" + ip + ":9090/AndroidJSPProject/list.jsp";

        @Override
        protected String doInBackground(String... strings) {

            String str = "";

            try {
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");//전송방식
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //서버로 전달할 내용
                //type = type_id_check
                if(strings[1].equals("type_id_check")) {

                    sendMsg = strings[0] + "&type=" + strings[1];
                    Log.i("my","id : " + strings[0]);
                    Log.i("my","id : " + sendMsg);
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

                        if (result.equals("idok")) {
                            receiveMsg = "idok";
                        } else {
                            receiveMsg = "idno";
                        }

                    }
                }else if(strings[1].equals("type_email_check")){

                    sendMsg = strings[0] + "&type=" + strings[1];

                    osw.write(sendMsg);
                    osw.flush();

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader is = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(is);

                        String buffer = "";
                        while ((str = reader.readLine()) != null) {
                            buffer += str;
                        }

                        receiveMsg = buffer;

                        JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                        JSONObject jObject = jarray.getJSONObject(0);
                        String result = jObject.optString("result");

                        Log.i("my", "result : " + result);
                        if (result.equals("emailok")) {
                            receiveMsg = "emailok";
                        } else {
                            receiveMsg = "emailno";
                        }

                    }

                }else {//type = type_join
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

                        //Log.i("my", "join : " + result);
                        if (result.equals("joinok")) {
                            receiveMsg = "joinok";
                        } else {
                            receiveMsg = "joinno";
                        }

                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("my","s : "+s);

            if(s.equals("idok")) {
                //Toast.makeText(JoinActivity.this, "???id사용 가능!", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("ID중복체크");
                sweetAlertDialog.setContentText("사용가능 한 ID 입니다");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                btn_join_id_check.setEnabled(false);
                join_id_et.setEnabled(false);
                id_join_check = "yes";

            }else if(s.equals("idno")){
                //Toast.makeText(JoinActivity.this, "이미 있는 id!!", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("ID중복체크");
                sweetAlertDialog.setContentText("이미 사용중인 ID 입니다");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                join_id_et.setText("");
                id_join_check = "";

            }else if(s.equals("emailno")){
                email_check_num = 2;
                Log.i("my", "email_check_num2 : " + email_check_num);
                FinalEmail();
            }else if(s.equals("emailok")){
                email_check_num = 1;
                Log.i("my", "email_check_num2 : " + email_check_num);
                FinalEmail();
            }else if(s.equals("joinok")){
                //Toast.makeText(JoinActivity.this, "가입성공!!", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setTitleText("회원가입");
                sweetAlertDialog.setContentText("회원가입이 되셨습니다.");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent i = new Intent(JoinActivity.this, LoginMainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

            }else if(s.equals("joinno")){
                //Toast.makeText(JoinActivity.this, "가입실패!!", Toast.LENGTH_SHORT).show();
                sweetAlertDialog = new SweetAlertDialog(JoinActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("회원가입");
                sweetAlertDialog.setContentText("문제가있다.");
                sweetAlertDialog.setConfirmText("확인");
                sweetAlertDialog.show();
            }else{
                Log.i("my","실패");
            }

        }
    }
}

