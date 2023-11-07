package com.example.roomradar.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roomradar.R;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    Animation botan, topan;
    ImageView image;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topan = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        botan = AnimationUtils.loadAnimation(this, R.anim.bottom_ani);
        image = findViewById(R.id.splash_img);
        text = findViewById(R.id.splash_text);
        image.setAnimation(topan);
        text.setAnimation(botan);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}