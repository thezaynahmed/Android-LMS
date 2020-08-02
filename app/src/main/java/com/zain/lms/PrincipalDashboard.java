package com.zain.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class PrincipalDashboard extends AppCompatActivity {
    MaterialCardView addTeacherCard;
    TextInputEditText addTeacherText;
    TextView totalTeachersPrinciple;
    TextView totalStudentsPrinciple;
    TextView principalName;
    Button btnSave;
    BottomNavigationView principleBottomNavigation;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        getSupportActionBar().setElevation(0);

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

        mProgress = new ProgressDialog(PrincipalDashboard.this);
        mProgress.setTitle("Signing Out");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        principleBottomNavigation =findViewById(R.id.principle_bottom_navigation);
        principalName = findViewById(R.id.principal_name);
        principalName.setText(sharedPreferences.getString("name",""));
        totalTeachersPrinciple = findViewById(R.id.total_teachers_principle);
        totalStudentsPrinciple = findViewById(R.id.total_students_principle);
        totalTeachersPrinciple.append(" " +sharedPreferences.getInt("teacherCount",0));
        totalStudentsPrinciple.append(" " +sharedPreferences.getInt("studentCount",0));


        principleBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_admin:
                        break;
                    case R.id.logot_admin:
                        materialAlertDialogBuilder=new MaterialAlertDialogBuilder(PrincipalDashboard.this)
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
                                        Toast.makeText(PrincipalDashboard.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        startActivity(new Intent(PrincipalDashboard.this,LoginActivity.class));
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