package com.zain.lms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zain.lms.R;
import com.zain.lms.model.GetApproval;
import com.zain.lms.utils.Constants;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class SetApproveStatusRecyclerView extends RecyclerView.Adapter<SetApproveStatusRecyclerView.ViewHolder> {
    private static final String TAG = "ApprovalRequest";
    Context context;
    List<GetApproval> getApprovalList;
    StringRequest stringRequest;
    RequestQueue queue;

    public SetApproveStatusRecyclerView(Context context, List<GetApproval> getApprovallList) {
        this.context = context;
        this.getApprovalList = getApprovallList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.get_approval_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GetApproval getApproval = getApprovalList.get(position);
        holder.name_approval.setText(getApproval.getName());
        holder.contact_approval.setText(getApproval.getContact());
        try {
            holder.btn_approval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stringRequest = new StringRequest(Request.Method.POST, Constants.SET_APPROVAL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("uid", String.valueOf(getApprovalList.get(position).getId()));
                            getApprovalList.remove(position);
                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, getItemCount());
                            notifyDataSetChanged();
                            return map;
                        }
                    };
                    queue = Volley.newRequestQueue(context.getApplicationContext());
                    queue.add(stringRequest);
                }
            });

        }catch (Exception e){
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return getApprovalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name_approval;
        public TextView contact_approval;
        public Button btn_approval;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_approval = itemView.findViewById(R.id.name_approval);
            contact_approval = itemView.findViewById(R.id.contact_approval);
            btn_approval = itemView.findViewById(R.id.btn_approve_std);

        }
    }
}
