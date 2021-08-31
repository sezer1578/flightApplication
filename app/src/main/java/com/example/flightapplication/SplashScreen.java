package com.example.flightapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private static int time = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        imageView = findViewById(R.id.imgView);
        textView = findViewById(R.id.txtLogo);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        imageView.startAnimation(animation);
        textView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,Login_Page.class);
                startActivity(intent);
                finish();


            }
        },time);
    }
}