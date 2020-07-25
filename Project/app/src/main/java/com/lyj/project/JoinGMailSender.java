package com.lyj.project;


import android.content.Intent;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JoinGMailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;


    public JoinGMailSender(String user, String password){
        this.user = user;
        this.password = password;
        Properties props = new Properties();
        //properties : db에 대한 연결정보를 파일로 저장해놓고 사용하는 용도로 많이 쓰임
        //map형식으로 저장되는듯,,(key = value, key:value key value ...형태는 많음)
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        //구글에서 지원하는 amtp정보를 받아와서 MimeMessage객체에 전달
        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        //해당 메서드에서 사용자의 계정(id & pwd)받아 인증받으며 실패시 기본값으로 반환
        return new PasswordAuthentication(user, password);
    }

    public synchronized  void sendMail(String subject, String body, String sender, String recipients)throws Exception{
        try{
            MimeMessage message = new MimeMessage(session);
        //본문 내용을 byte단위로 쪼개 전달
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        //내 이메일 설정
        message.setSender(new InternetAddress(sender));
        //해당 이메일의 본문 설정
        message.setSubject(subject);
        message.setDataHandler(handler);
        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        Transport.send(message); //메시지 전달
        }catch (Exception e){
            Log.i("plz", " 안돼 man");
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

/*
        public ByteArrayDataSource(byte[] data)
        {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }
*/

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}



