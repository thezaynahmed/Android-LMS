package com.zain.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zain.lms.adapters.ViewAllTeachersAdapter;
import com.zain.lms.model.getAllTeachers;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllTeachers extends AppCompatActivity {
    private static final String TAG = "Volley";
    RecyclerView recyclerView;
    ViewAllTeachersAdapter viewAllTeachersAdapter;
    StringRequest stringRequest;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    RequestQueue queue;
    List<getAllTeachers> getAllTeachersList;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_techers);
        Objects.requireNonNull(getSupportActionBar()).setTitle("All Teachers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProgress = new ProgressDialog(AllTeachers.this);
        mProgress.setTitle("Loading Teachers");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);
        recyclerView = findViewById(R.id.ViewAllTeachersRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllTeachersList = new ArrayList<>();

        if(!isNetworkConnected()){
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

        mProgress.show();
        stringRequest = new StringRequest(Request.Method.GET, Constants.GET_ALL_TEACHERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject;
                            //Toast.makeText(AllTeachers.this, "" + response, Toast.LENGTH_SHORT).show();
                            for(int i =0; i < jsonArray.length(); i++){
                                try {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    getAllTeachersList.add(new getAllTeachers(jsonObject.getString("user_name"),jsonObject.getString("user_email"),jsonObject.getInt("user_contact")));
                                    mProgress.dismiss();
                                }catch (Exception e){
                                    Toast.makeText(AllTeachers.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: " + e.getMessage());
                                }

                            }
                            viewAllTeachersAdapter = new ViewAllTeachersAdapter(AllTeachers.this,getAllTeachersList);
                            recyclerView.setAdapter(viewAllTeachersAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AllTeachers.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllTeachers.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + error.getMessage());
            }
        });
        queue = Volley.newRequestQueue(AllTeachers.this);
        queue.add(stringRequest);

    }
    //Checking Internet Connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}