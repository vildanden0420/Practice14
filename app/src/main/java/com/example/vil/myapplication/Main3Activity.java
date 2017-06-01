package com.example.vil.myapplication;

import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HandshakeCompletedListener;

public class Main3Activity extends AppCompatActivity {
    String urlstr = "https://www.google.co.kr/";
    TextView tv;
    Button button;
    EditText et;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        et = (EditText)findViewById(R.id.etUrl);
        et.setText(urlstr);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlstr = et.getText().toString();
                loadHtml(urlstr);
            }

        });
    }

    void loadHtml(final String inputUrl) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                final StringBuffer sb = new StringBuffer();
                try {
                    URL url = new URL(inputUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "euc-kr"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null) break;
                            sb.append(line + "\n");
                        }
                        br.close();
                    }
                    urlConnection.disconnect();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(sb.toString());
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


    }
}
