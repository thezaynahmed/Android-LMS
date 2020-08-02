package com.zain.lms;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.zain.lms.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminDashboard extends AppCompatActivity {
    StringRequest stringRequest;
    RequestQueue queue;
    String date;
    MaterialCardView addTeacherCard;
    MaterialCardView viewAttendance;
    MaterialCardView organizeOnlineExam;
    MaterialCardView generate_user_certificates;
    TextInputEditText addTeacherText;
    TextInputEditText addTeacherEmail;
    TextInputEditText addTeacherPassword;
    TextInputEditText addTeacherContact;
    TextInputEditText addSubjectText;
    TextView totalStudents;
    TextView adminName;
    Button btnSave;
    Button approveBtn;
    Button viewAllTeachers;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    BottomNavigationView adminBottomNavigation;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
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

        mProgress = new ProgressDialog(AdminDashboard.this);
        mProgress.setTitle("Signing Out");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        addTeacherCard =findViewById(R.id.add_teacher_card);
        viewAttendance = findViewById(R.id.view_attendance_card);
        organizeOnlineExam = findViewById(R.id.projectsCard);
        generate_user_certificates = findViewById(R.id.generate_user_certificates_card);
        adminBottomNavigation =findViewById(R.id.admin_bottom_navigation);
        approveBtn = findViewById(R.id.btn_approve_students);
        totalStudents = findViewById(R.id.total_students);
        viewAllTeachers = findViewById(R.id.view_all_teachers);

        viewAllTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this,AllTeachers.class));
            }
        });

        adminName = findViewById(R.id.admin_name);
        adminName.setText(sharedPreferences.getString("name",""));

        totalStudents.append(" " + sharedPreferences.getInt("studentCount",0));


        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(AdminDashboard.this,ApproveStudents.class));
            }
        });

        addTeacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeacherPopup();
            }
        });
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this,ViewAttendance.class));
            }
        });

        organizeOnlineExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this,OrganizeOnlineExam.class));
            }
        });
        generate_user_certificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboard.this,GenerateCertificatesActivity.class));
            }
        });

        adminBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_admin:
                        break;
                    case R.id.logot_admin:
                        materialAlertDialogBuilder=new MaterialAlertDialogBuilder(AdminDashboard.this)
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
                                Toast.makeText(AdminDashboard.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                startActivity(new Intent(AdminDashboard.this,LoginActivity.class));
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

    private void addTeacherPopup() {
        builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.add_teacher_popup,null);

        addTeacherText = view.findViewById(R.id.add_teacher_text);
        addTeacherEmail = view.findViewById(R.id.teacher_text_email);
        addTeacherPassword = view.findViewById(R.id.teacher_text_password);
        addTeacherContact = view.findViewById(R.id.teacher_text_contact);;
        btnSave = view.findViewById(R.id.add_teacher_btn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addTeacherText.getText().toString().isEmpty()
                    && !addTeacherEmail.getText().toString().isEmpty()
                        && !addTeacherPassword.getText().toString().isEmpty()
                        && !addTeacherContact.getText().toString().isEmpty()){
                    Snackbar.make(view,"Teacher Added", Snackbar.LENGTH_SHORT);

                    stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_USER,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onResponse(String response) {
                                    //Toast.makeText(AdminDashboard.this, ""+response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AdminDashboard.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("name", addTeacherText.getText().toString().trim());
                            map.put("email", addTeacherEmail.getText().toString().trim());
                            map.put("pass", addTeacherPassword.getText().toString().trim());
                            map.put("contact", addTeacherContact.getText().toString().trim());
                            map.put("role", "Teacher");

                            // Parsing the input date
                            SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                            Date date = new Date();
                            // Create the MySQL datetime string
                            fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateString = fmt.format(date);
                            map.put("date", dateString);
                            return map;
                        }
                    };
                    try {
                        queue = Volley.newRequestQueue(AdminDashboard.this);
                        queue.add(stringRequest);
                    }catch (Exception ex){
                        Toast.makeText(AdminDashboard.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Snackbar.make(view,"Teacher Added Successfully",Snackbar.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    },1500);
                }else{
                    Snackbar.make(v,"Empty Fields Are Not Allowed",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }
    //Checking Internet Connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}