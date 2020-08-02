package com.zain.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    ImageView splashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Objects.requireNonNull(getSupportActionBar()).hide();
        splashImage = findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashscreenimage_animation);
        splashImage.startAnimation(animation);
        splashScreenTimer();

    }

    private void splashScreenTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();

                if(sharedPreferences.getString("role", "").equals("Admin")){
                    startActivity(new Intent(SplashScreen.this, AdminDashboard.class));
                    finish();
                }else if(sharedPreferences.getString("role", "").equals("Principal")){
                    startActivity(new Intent(SplashScreen.this, PrincipalDashboard.class));
                    finish();
                } else if(sharedPreferences.getString("role", "").equals("Teacher")){
                    startActivity(new Intent(SplashScreen.this, TeachersDashboard.class));
                    finish();
                }else if(sharedPreferences.getString("role", "").equals("Student")){
                    startActivity(new Intent(SplashScreen.this, StudentDashboard.class));
                    finish();
                }else if(sharedPreferences.getString("role", "").equals("Parent")){
                    startActivity(new Intent(SplashScreen.this, ParentsDashboard.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                    finish();
                }
            }
        },2000);
    }
}