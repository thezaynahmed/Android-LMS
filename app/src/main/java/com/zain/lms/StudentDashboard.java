package com.zain.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.zain.lms.ui.ExamStudent;
import com.zain.lms.ui.HomeStudent;
import com.zain.lms.ui.MyCourse;
import com.zain.lms.ui.SelfStudy;

import java.util.Objects;

public class StudentDashboard extends AppCompatActivity {
    private BottomNavigationView studentBottomNavigationView;
    private MaterialCardView cardView;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    TextView stdName;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        getSupportActionBar().setElevation(0);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            getSupportActionBar().hide();
        }

        if(!isNetworkConnected()){
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
           materialAlertDialogBuilder =new MaterialAlertDialogBuilder(this).setTitle("No internet connection")
                    .setMessage("Please cheach your internet connection and try again")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            materialAlertDialogBuilder.show();
        }
        final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        stdName = findViewById(R.id.std_name);
        stdName.setText(sharedPreferences.getString("name",""));

        mProgress = new ProgressDialog(StudentDashboard.this);
        mProgress.setTitle("Signing Out");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        studentBottomNavigationView = findViewById(R.id.student_bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.frame,new HomeStudent()).commit();
        studentBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeStudent()).commit();
                        break;
                    case R.id.myCourse:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new MyCourse()).commit();
                        break;
                    case R.id.selfStudy:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new SelfStudy()).commit();
                        break;
                    case R.id.assignments:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new ExamStudent()).commit();
                        break;
                    case R.id.mailBox:
                        materialAlertDialogBuilder=new MaterialAlertDialogBuilder(StudentDashboard.this)
                                .setTitle("Confirm Logout")
                                .setMessage("Are You Sure You Wan't To Logout?")
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mProgress.show();
                                        editor.putString("name",null);
                                        editor.putString("role",null);
                                        editor.putInt("id",0);
                                        editor.apply();
                                        Toast.makeText(StudentDashboard.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        startActivity(new Intent(StudentDashboard.this,LoginActivity.class));
                                        finish();
                                    }
                                });
                        materialAlertDialogBuilder.show();
                        break;
                }
                return true;
            }
        });
    }
    //Checking Internet Connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}