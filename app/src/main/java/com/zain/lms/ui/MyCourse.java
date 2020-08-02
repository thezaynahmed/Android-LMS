package com.zain.lms.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zain.lms.R;
import com.zain.lms.adapters.MyCourseAdapter;
import com.zain.lms.model.MyCourseModel;
import com.zain.lms.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCourse extends Fragment {
    RecyclerView recyclerView;
    MyCourseAdapter myCourseAdapter;
    StringRequest stringRequest;
    RequestQueue queue;
    List<MyCourseModel> courseModelList;
//    final SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("zain.txt",MODE_PRIVATE);
//    final SharedPreferences.Editor editor = sharedPreferences.edit();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view =inflater.inflate(R.layout.my_course,null);
        courseModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.myCourseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stringRequest = new StringRequest(Request.Method.POST, Constants.GET_ALL_COURSES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                courseModelList.add(new MyCourseModel(jsonObject.getString("course_name")));
                            }
                            myCourseAdapter = new MyCourseAdapter(getContext(),courseModelList);
                            recyclerView.setAdapter(myCourseAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id", "10");
                return map;
            }
        };
        queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
        return view;
    }
}
