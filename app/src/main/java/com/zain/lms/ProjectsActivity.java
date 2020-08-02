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
import com.zain.lms.adapters.ProjectsAdapter;
import com.zain.lms.model.Activities;
import com.zain.lms.model.Homework;
import com.zain.lms.model.Projects;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectsActivity extends AppCompatActivity {
    private static final String TAG = "Volley";
    StringRequest stringRequest;
    RequestQueue queue;
    private TextView assignmentNameProjects;
    private TextView assignmentLinkProjects;
    private RecyclerView recyclerView;
    private ProjectsAdapter projectsAdapter;
    private ArrayList<Projects> projectsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Projects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        projectsArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_projects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProjectsActivity.this));

        stringRequest = new StringRequest(Request.Method.POST, Constants.ASSIGNMENTS_DATA_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("[]")){
                           // Toast.makeText(ProjectsActivity.this, "Record Not Found!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + "Record Not Found!");
                        }else {
                            //Toast.makeText(ProjectsActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response);

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = null;

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    projectsArrayList.add(new Projects(jsonObject.getString("assignments_name"), jsonObject.getString("attachment")));
                                }

                                String assignments_names = jsonObject.getString("assignments_name");
                                String assignments_links = jsonObject.getString("attachment");

                                projectsAdapter = new ProjectsAdapter(ProjectsActivity.this,projectsArrayList);
                                recyclerView.setAdapter(projectsAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                //Toast.makeText(ProjectsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: " + e.getMessage());
                            }}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ProjectsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id", String.valueOf(sharedPreferences.getInt("id",0)));
                map.put("type","Project");
                return map;
            }
        };
        queue = Volley.newRequestQueue(ProjectsActivity.this);
        queue.add(stringRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}