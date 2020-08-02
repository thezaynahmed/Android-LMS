package com.zain.lms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAttendance extends AppCompatActivity {
    private static final String TAG = "Volley";
    StringRequest stringRequest;
    RequestQueue queue;
    TextView selectBatch;
    TextView fromDate;
    TextView toDate;
    TextView selectStudent;
    String date;
    AlertDialog dialog;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    Button getStudents;
    private ListView listView;
    private ListView listViewAttendance;
    int userId;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> data1 = new ArrayList<>();
    ArrayList<String> listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Student Attendance");
        setContentView(R.layout.activity_view_attendance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectBatch = findViewById(R.id.selectBatch);
        fromDate = findViewById(R.id.fromtDate);
        toDate = findViewById(R.id.toDate);
        getStudents = findViewById(R.id.get_students);
        selectStudent = findViewById(R.id.select_student);
        listViewAttendance = findViewById(R.id.list_view_attendance);
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
        stringRequest = new StringRequest(Request.Method.GET, Constants.BATCHES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Toast.makeText(ViewAttendance.this, "" + response, Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject  = null;
                            for (int i = 0; i < jsonArray.length(); i++){
                                jsonObject =  jsonArray.getJSONObject(i);
                                data.add(jsonObject.getString("batch_name"));
                                data1.add(jsonObject.getString("user_name"));
                                userId = jsonObject.getInt("user_id");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewAttendance.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewAttendance.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

        selectBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog("batch");
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog("end");
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog("start");
            }
        });
        selectStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog("student");
            }
        });
        getStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest = new StringRequest(Request.Method.POST, Constants.STUDENT_ATTENDANCE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "onResponse: " + response);
                                //Toast.makeText(ViewAttendance.this, "" + response, Toast.LENGTH_SHORT).show();
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = null;
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        jsonObject =  jsonArray.getJSONObject(i);
                                        listData.add(jsonObject.getString("attendance_date") + "P");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "onErrorResponse: " + e.getMessage());
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("id", String.valueOf(userId));
                        map.put("fdate", fromDate.getText().toString());
                        map.put("tdate", fromDate.getText().toString());
                        return map;
                    }
                };
                queue = Volley.newRequestQueue(ViewAttendance.this);
                queue.add(stringRequest);
                ArrayAdapter adapter = new ArrayAdapter(ViewAttendance.this, android.R.layout.simple_expandable_list_item_1, listData);
                adapter.notifyDataSetChanged();
                listViewAttendance.setAdapter(adapter);

            }
        });

    }
    public void alertDialog(final String text){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
       if(text.equals("end") || text.equals("start")){
           final View view = getLayoutInflater().inflate(R.layout.date_popup,null);
           builder.setTitle("Select "+ text +" Date")
                   .setView(view)
                   .setPositiveButton("Set Date", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           DatePicker datePicker = view.findViewById(R.id.date_picker);
                           date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                           if(text.equals("end"))
                           {
                               toDate.setText(date);
                               selectStudent.setEnabled(true);
                           }
                           else {
                               fromDate.setText(date);
                               toDate.setEnabled(true);
                           }
                       }
                   });
       }else if(text.equals("batch") || text.equals("student")){
           if(text.equals("batch")){
           final View view = getLayoutInflater().inflate(R.layout.select_batch_popup,null);
           listView = view.findViewById(R.id.batches_list);
           ArrayAdapter adapter = new ArrayAdapter(this,
                   android.R.layout.simple_expandable_list_item_1,
                   data);
           listView.setAdapter(adapter);
           builder.setTitle("Select "+ text)
                   .setView(view)
                   .setPositiveButton("ok", null);
                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           selectBatch.setText(data.get(position));
                           fromDate.setEnabled(true);
                       }
                   });
           }else{
               final View view = getLayoutInflater().inflate(R.layout.select_batch_popup,null);
               listView = view.findViewById(R.id.batches_list);
               ArrayAdapter adapter = new ArrayAdapter(this,
                       android.R.layout.simple_expandable_list_item_1,
                       data1);
               listView.setAdapter(adapter);
               builder.setTitle("Select "+ text)
                       .setView(view)
                       .setPositiveButton("ok", null);
               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       selectStudent.setText(data1.get(position));
                       getStudents.setEnabled(true);
                   }
               });
           }
       }
         dialog  = builder.create();
        dialog.show();
    }
    //Checking Internet Connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    //Setting On BackPressed To True
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}