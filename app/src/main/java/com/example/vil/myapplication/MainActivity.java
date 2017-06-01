package com.example.vil.myapplication;

import android.os.Handler;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    String Server_IP = "172.17.65.18";
    int Server_port = 200;
    String msg = "";
    EditText et;
    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)findViewById(R.id.etmsg);
        bt = (Button)findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = et.getText().toString();
                if(!myThread.isAlive()){
                    myThread.setDaemon(true);
                    myThread.start();
                }
            }
        });
    }

    Handler myHandler = new Handler();
    Thread myThread = new Thread(){
        @Override
        public void run() {
            try {
                Socket aSocket = new Socket(Server_IP, Server_port);

                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(msg);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                final Object obj = instream.readObject();
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server>> " + (String) obj, Toast.LENGTH_SHORT).show();
                    }
                });

                aSocket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}