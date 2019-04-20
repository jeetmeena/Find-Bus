package com.example.jeetmeena.findbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Thread th=new Thread(){
            @Override
            public void run() {

                try {
                    sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        };
        th.start();



          }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
