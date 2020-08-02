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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zain.lms.adapters.RecyclerViewAdapter;
import com.zain.lms.model.Classwork;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClassworkActivity extends AppCompatActivity {
    private static final String TAG = "Volley";
    StringRequest stringRequest;
    RequestQueue queue;
    private TextView assignmentName;
    private TextView assignmentLink;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Classwork> classworkArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwork);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Classwork");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        assignmentName.setClickable(true);
//        assignmentLink.setMovementMethod(LinkMovementMethod.getInstance());
//        assignmentLink.setClickable(true);
//        assignmentLink.setMovementMethod(LinkMovementMethod.getInstance());

        final SharedPreferences sharedPreferences = getSharedPreferences("zain.txt",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        classworkArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view_classwork);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassworkActivity.this));

        final Classwork classwork = new Classwork();
        stringRequest = new StringRequest(Request.Method.POST, Constants.ASSIGNMENTS_DATA_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("[]")){
                            //Toast.makeText(ClassworkActivity.this, "Record Not Found!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + "Record Not Found!");
                        }else {
                            //Toast.makeText(ClassworkActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = null;

                            for (int i = 0; i < jsonArray.length(); i++){
                                jsonObject = jsonArray.getJSONObject(i);

                                classworkArrayList.add(new Classwork(jsonObject.getString("assignments_name"),jsonObject.getString("attachment")));
                            }

                            String assignments_names = jsonObject.getString("assignments_name");
                            String assignments_links = jsonObject.getString("attachment");

                            recyclerViewAdapter = new RecyclerViewAdapter(ClassworkActivity.this,classworkArrayList);
                            recyclerView.setAdapter(recyclerViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(ClassworkActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + e.getMessage());
                        }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ClassworkActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + error.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                //map.put("id", String.valueOf(sharedPreferences.getInt("id",0)));
                map.put("id", String.valueOf(sharedPreferences.getInt("id",0)));
                map.put("type","Classwork");
                return map;
            }
        };
        try {
            queue = Volley.newRequestQueue(ClassworkActivity.this);
            queue.add(stringRequest);
        }catch (Exception ex){
            Log.d(TAG, "onResponse: " + ex.getMessage());
            //Toast.makeText(ClassworkActivity.this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}