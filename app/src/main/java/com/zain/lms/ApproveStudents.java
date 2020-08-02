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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.zain.lms.adapters.RecyclerViewAdapter;
import com.zain.lms.adapters.SetApproveStatusRecyclerView;
import com.zain.lms.model.Classwork;
import com.zain.lms.model.GetApproval;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApproveStudents extends AppCompatActivity {

    private static final String TAG = "Volley";
    StringRequest stringRequest;
    RequestQueue queue;
    Button setApproveStudentsButton;
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    private RecyclerView recyclerView;
    private SetApproveStatusRecyclerView getSetApproveStatusRecyclerView;
    private List<GetApproval> getApprovalArrayList;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_students);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Approve Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        getApprovalArrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.approve_students_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApproveStudents.this));

        mProgress = new ProgressDialog(ApproveStudents.this);
        mProgress.setTitle("Loading Students That Needs To Be Approved");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);

        final GetApproval getApproval = new GetApproval();
        mProgress.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_APPROVAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = null;
                    for (int i = 0; i < jsonArray.length(); i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        //Toast.makeText(ApproveStudents.this, "" +jsonObject.getString("user_contact") , Toast.LENGTH_SHORT).show();
                        getApprovalArrayList.add(new GetApproval(jsonObject.getInt("user_id"),jsonObject.getString("user_name"),jsonObject.getString("user_contact")));
                        mProgress.dismiss();
                    }
                    getSetApproveStatusRecyclerView = new SetApproveStatusRecyclerView(ApproveStudents.this,getApprovalArrayList);
                    recyclerView.setAdapter(getSetApproveStatusRecyclerView);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApproveStudents.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApproveStudents.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + error.getMessage());
            }
        });
        queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
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