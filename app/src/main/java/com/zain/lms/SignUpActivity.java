package com.zain.lms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    AutoCompleteTextView editTextFilledExposedDropdownRole;
    private static final String TAG = "Volley";
    private StringRequest stringRequest;
    private RequestQueue queue;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passTextInputLayout;
    private TextInputLayout contactIextInputLayout;
    private TextInputLayout roleTextInputLayout;
    private TextInputEditText nameInputEditText;
    private TextInputEditText emailInputEditText;
    private TextInputEditText passInputEditText;
    private TextInputEditText contactInputEditText;
    private AutoCompleteTextView roleInputEditText;
    Button signUpBtn;
    private TextView alreadyHaveAnAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        changeStatusBarColor();
        nameTextInputLayout = findViewById(R.id.text_input_name);
        emailTextInputLayout = findViewById(R.id.text_input_email);
        passTextInputLayout = findViewById(R.id.text_input_password);
        contactIextInputLayout =findViewById(R.id.text_input_contact);
        roleTextInputLayout = findViewById(R.id.text_input_role);

        nameInputEditText = findViewById(R.id.text_name);
        emailInputEditText = findViewById(R.id.text_email);
        passInputEditText = findViewById(R.id.text_password);
        contactInputEditText = findViewById(R.id.text_contact);
        roleInputEditText = findViewById(R.id.filled_exposed_dropdown_role);


        signUpBtn =findViewById(R.id.btn_sign_up);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateName();
                validateEmail();
                validatePassword();
                validateContact();
                validateRole();
                        if(!nameInputEditText.getText().toString().trim().isEmpty()
                                && !emailInputEditText.getText().toString().trim().isEmpty()
                                && !passInputEditText.getText().toString().trim().isEmpty()
                                && passInputEditText.getText().toString().trim().length() >= 8
                                && !contactInputEditText.getText().toString().trim().isEmpty()
                                && !roleInputEditText.getText().toString().trim().isEmpty()){

                            stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_USER,
                                    new Response.Listener<String>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(SignUpActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(SignUpActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("name", nameInputEditText.getText().toString().trim());
                                    map.put("email", emailInputEditText.getText().toString().trim());
                                    map.put("pass", passInputEditText.getText().toString().trim());
                                    map.put("contact", contactInputEditText.getText().toString().trim());
                                    map.put("role", roleInputEditText.getText().toString().trim());
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
                                queue = Volley.newRequestQueue(SignUpActivity.this);
                                queue.add(stringRequest);
                            }catch (Exception ex){
                                Toast.makeText(SignUpActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            //startActivity(new Intent(SignUpActivity.this,StudentDashboard.class));
                            //Snackbar.make(v,"Account Registered Successfully",Snackbar.LENGTH_SHORT);
                            Toast.makeText(SignUpActivity.this, "Account Registered Successfully" , Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                }
                            },1000);
                        }
            }
        });

        alreadyHaveAnAccount = findViewById(R.id.already_have_an_account);

        //Roles Dropdown
        String[] Role = new String[] {"Parent", "Student"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                this,R.layout.dropdown_role,Role
        );
        editTextFilledExposedDropdownRole = findViewById(R.id.filled_exposed_dropdown_role);
        editTextFilledExposedDropdownRole.setAdapter(stringArrayAdapter);


        String coloredText = getColoredSpanned("Sign In", "#673ab7");
        alreadyHaveAnAccount.setText(Html.fromHtml("Already have an account?" + " " + coloredText));
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });



    }
    //Validating Name
    public void validateName(){
        String nameInput = Objects.requireNonNull(nameInputEditText.getText()).toString().trim();
        if(nameInput.isEmpty()){
            nameTextInputLayout.setError("Name Cannot Be Empty");
        }else {
            nameTextInputLayout.setError(null);
            nameTextInputLayout.setErrorEnabled(false);
        }
    }

    //Validating Email
    public void validateEmail() {
        String emailInput = Objects.requireNonNull(emailInputEditText.getText()).toString().trim();
        if (emailInput.isEmpty()) {
            emailTextInputLayout.setError("Email cannot be empty");
        }  else {
            emailTextInputLayout.setError(null);
            emailTextInputLayout.setErrorEnabled(false);
        }
    }

    //validating Password
    private void validatePassword(){
        String passwordInput = passInputEditText.getText().toString().trim();
        if(passwordInput.length() < 8){
                passTextInputLayout.setError("Password must contains at least 8 characters");
            }else{
                passTextInputLayout.setError(null);
                passTextInputLayout.setErrorEnabled(false);
            }
        }

    //validating Phone
    public void validateContact(){
        String contactInput = nameInputEditText.getText().toString().trim();
        if(contactInput.isEmpty()){
            contactIextInputLayout.setError("Phone cannot be empty");
        }else {
            contactIextInputLayout.setError(null);
            contactIextInputLayout.setErrorEnabled(false);
        }
    }

    //validating Role
    public void validateRole(){
        String roleInput = roleInputEditText.getText().toString().trim();
        if(roleInput.isEmpty()){
            roleTextInputLayout.setError("Role cannot be empty");
        }else if(!roleInput.equals("Student") && !roleInput.equals("student") && !roleInput.equals("Parent") && !roleInput.equals("parent")){
            roleTextInputLayout.setError("Please enter a valid role");
        }else {
            roleTextInputLayout.setError(null);
            roleTextInputLayout.setErrorEnabled(false);
        }
    }

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