package com.zain.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button createAccountBtn;
    Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
            Objects.requireNonNull(getSupportActionBar()).hide();
            createAccountBtn = findViewById(R.id.create_account_btn);
            signInBtn = findViewById(R.id.login_btn);

            createAccountBtn.setOnClickListener(this);
            signInBtn.setOnClickListener(this);

//            createAccountBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(WelcomeActivity.this,SignUpActivity.class));
//                }
//            });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_account_btn:
                startActivity(new Intent(WelcomeActivity.this,SignUpActivity.class));
            break;
            case R.id.login_btn:
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                break;
        }
    }
}