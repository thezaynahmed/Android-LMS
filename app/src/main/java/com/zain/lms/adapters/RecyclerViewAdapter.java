package com.zain.lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.zain.lms.R;
import com.zain.lms.model.Classwork;
import com.zain.lms.model.GetApproval;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List <Classwork> classworkList;

    public RecyclerViewAdapter(Context context, List<Classwork> classworkList) {
        this.context = context;
        this.classworkList = classworkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.classwork_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Classwork classwork = classworkList.get(position);
        holder.assignmentNameClassworkList.setText(classwork.getAssignment_name_classwork());
        holder.assignmentLinkClassworkList.setText(classwork.getAssignment_link_classwork());
    }

    @Override
    public int getItemCount() {
        return classworkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentNameClassworkList;
        public TextView assignmentLinkClassworkList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentNameClassworkList = itemView.findViewById(R.id.assignment_name_list);
            assignmentLinkClassworkList = itemView.findViewById(R.id.assignment_link_list);
        }
    }
}
