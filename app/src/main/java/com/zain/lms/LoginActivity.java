package com.zain.lms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.StatusBarManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Volley";
    private StringRequest stringRequest;
    private RequestQueue queue;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private TextInputEditText textEmail;
    private TextInputEditText textPassword;
    private Button btnLogin;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        changeStatusBarColor();
        TextView dontHaveAnAccount = findViewById(R.id.dont_have_an_account);

        textInputEmail = findViewById(R.id.text_input_email_login);
        textInputPassword = findViewById(R.id.text_input_password_login);

        textEmail = findViewById(R.id.text_email_login);
        textPassword = findViewById(R.id.text_password_login);

        btnLogin = findViewById(R.id.btn_login);

        mProgress = new ProgressDialog(LoginActivity.this);
        mProgress.setTitle("Signing In...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getString("role", "").equals("Admin")){
            finish();
            startActivity(new Intent(LoginActivity.this, AdminDashboard.class));
        }else if(sharedPreferences.getString("role", "").equals("Principal")){
            finish();
            startActivity(new Intent(LoginActivity.this, PrincipalDashboard.class));
        } else if(sharedPreferences.getString("role", "").equals("Teacher")){
            finish();
            startActivity(new Intent(LoginActivity.this, TeachersDashboard.class));
        }else if(sharedPreferences.getString("role", "").equals("Student")){
            finish();
            startActivity(new Intent(LoginActivity.this, StudentDashboard.class));
        }else if(sharedPreferences.getString("role", "").equals("Parent")){
            finish();
            startActivity(new Intent(LoginActivity.this, ParentsDashboard.class));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
                validatePassword();
                stringRequest = new StringRequest(Request.Method.POST, Constants.SIGN_IN,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: " + response);
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(2);

                                    int studentCount = jsonObject1.getInt("count(user_name)");
                                    int teacherCount = jsonObject2.getInt("count(user_name)");
                                    editor.putString("name",jsonObject.getString("user_name"));
                                    editor.putString("role",jsonObject.getString("user_role"));
                                    editor.putInt("studentCount",studentCount);
                                    editor.putInt("teacherCount",teacherCount);
                                    editor.putInt("id",jsonObject.getInt("user_id"));
                                    editor.apply();

                                       mProgress.show();
                                       if(sharedPreferences.getString("role", "").equals("Admin")){
                                           finish();
                                           startActivity(new Intent(LoginActivity.this, AdminDashboard.class));
                                       }else if(sharedPreferences.getString("role", "").equals("Principal")){
                                           finish();
                                           startActivity(new Intent(LoginActivity.this, PrincipalDashboard.class));
                                       } else if(sharedPreferences.getString("role", "").equals("Teacher")){
                                           finish();
                                           startActivity(new Intent(LoginActivity.this, TeachersDashboard.class));
                                       }else if(sharedPreferences.getString("role", "").equals("Student")){
                                           finish();
                                           startActivity(new Intent(LoginActivity.this, StudentDashboard.class));
                                       }else if(sharedPreferences.getString("role", "").equals("Parent")){
                                           finish();
                                           startActivity(new Intent(LoginActivity.this, ParentsDashboard.class));
                                       }else {
                                           Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                       }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: " + e.getMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + error.getMessage());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("name", Objects.requireNonNull(textEmail.getText()).toString().trim());
                        map.put("pass", Objects.requireNonNull(textPassword.getText()).toString().trim());
                        return map;
                    }
                };
                try {
                    queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(stringRequest);
                }catch (Exception ex){
                   // Toast.makeText(LoginActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + ex.getMessage());
                }
            }
        });

        String coloredText = getColoredSpanned("Sign Up", "#673ab7");
        dontHaveAnAccount.setText(Html.fromHtml("Don't have an account?" + " " + coloredText));
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }
    //Validating Email
    public void validateEmail() {
        String emailInput = textEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Email cannot be empty");
        } else {
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
        }
    }
    //Validating Password
    private void validatePassword(){
        String passwordInput = textPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Password cannot be empty");
        } else if (!Constants.PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            if(passwordInput.length() < 8){
                textInputPassword.setError("Password must contains at least 8 characters");
            }else{
                textInputPassword.setError(null);
                textInputPassword.setErrorEnabled(false);
            }
        } else {
            textInputPassword.setError(null);
            textInputPassword.setErrorEnabled(false);
        }
    }

//    private boolean validateEmail() {
//            String emailInput = textInputEmail.getEditText().getText().toString().trim();
//            if (emailInput.isEmpty()){
//                textInputEmail.setError("HI");
//                return false;
//            }else{
//                textInputEmail.setError(null);
//                textInputEmail.setErrorEnabled(false);
//                return true;
//            }
//    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
    private void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ce96ff"));
        }
    }
}