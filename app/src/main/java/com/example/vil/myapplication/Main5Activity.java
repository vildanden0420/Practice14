package com.example.vil.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main5Activity extends AppCompatActivity {

    TextView tv;
    EditText id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        tv = (TextView)findViewById(R.id.text);
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);


    }

    public void onClick(View v){
        if(id.getText().toString().equals("") || password.getText().toString().equals("")){
            tv.setText("아이디와 비밀번호를 입력하세요");
        }else{
            thread.start();
        }

    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php" );
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                String postData = "userid=" + URLEncoder.encode(id.getText().toString()) + "&password=" + URLEncoder.encode(password.getText().toString());
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream inputStream;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    inputStream = httpURLConnection.getInputStream();
                else
                    inputStream = httpURLConnection.getErrorStream();

                final String result = loginResult(inputStream);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("FAIL"))
                            tv.setText("로그인이 실패했습니다 .");
                        else
                            tv.setText(result + "님 로그인 성공");
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    String loginResult(InputStream in) {
        String data = "";
        Scanner s = new Scanner(in);
        while (s.hasNext()) data += s.nextLine();
        s.close();
        return data;

    }
}
