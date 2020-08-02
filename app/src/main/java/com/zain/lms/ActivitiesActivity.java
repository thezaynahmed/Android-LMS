package com.zain.lms;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zain.lms.adapters.ActivitiesAdapter;
import com.zain.lms.adapters.HomeworkAdapter;
import com.zain.lms.model.Activities;
import com.zain.lms.model.Homework;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActivitiesActivity extends AppCompatActivity {
    private static final String TAG = "Volley";
    StringRequest stringRequest;
    RequestQueue queue;
    private TextView assignmentNameActivities;
    private TextView assignmentLinkActivities;
    private RecyclerView recyclerView;
    private ActivitiesAdapter activitiesAdapter;
    private ArrayList<Activities> activitiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Activities");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        assignmentNameActivities = findViewById(R.id.assignment_name_activities);
//        assignmentLinkActivities = findViewById(R.id.assignment_link_activities);
//        assignmentNameActivities.setClickable(true);
//        assignmentLinkActivities.setMovementMethod(LinkMovementMethod.getInstance());
//        assignmentLinkActivities.setClickable(true);
//        assignmentLinkActivities.setMovementMethod(LinkMovementMethod.getInstance());

        final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        activitiesList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_activities);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivitiesActivity.this));

        stringRequest = new StringRequest(Request.Method.POST, Constants.ASSIGNMENTS_DATA_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("[]")){
                            //Toast.makeText(ActivitiesActivity.this, "Record Not Found!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + "Record Not Found!");
                        }else {
                            //Toast.makeText(ActivitiesActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response);

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = null;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    activitiesList.add(new Activities(jsonObject.getString("assignments_name"), jsonObject.getString("attachment")));
                                }

                                String assignments_names = jsonObject.getString("assignments_name");
                                String assignments_links = jsonObject.getString("attachment");

                                activitiesAdapter = new ActivitiesAdapter(ActivitiesActivity.this, activitiesList);
                                recyclerView.setAdapter(activitiesAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(ActivitiesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: " + e.getMessage());
                            }}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ActivitiesActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id", String.valueOf(sharedPreferences.getInt("id",0)));
                map.put("type","Activities");
                return map;
            }
        };
        queue = Volley.newRequestQueue(ActivitiesActivity.this);
        queue.add(stringRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}