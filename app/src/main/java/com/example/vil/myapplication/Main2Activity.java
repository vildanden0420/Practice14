package com.example.vil.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onClick(View v){
        if(v.getId() == R.id.button1){
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.button2){
            Intent intent = new Intent(this, Main4Activity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, Main5Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
