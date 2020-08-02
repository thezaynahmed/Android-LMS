package com.zain.lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zain.lms.R;
import com.zain.lms.model.Activities;
import com.zain.lms.model.Homework;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder>  {
    Context context;
    List<Activities> activitiesList;

    public ActivitiesAdapter(Context context, List<Activities> activitiesList) {
        this.context = context;
        this.activitiesList = activitiesList;
    }

    @NonNull
    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activities_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesAdapter.ViewHolder holder, int position) {
        Activities activities = activitiesList.get(position);
        holder.assignmentActivitiesNameList.setText(activities.getAssignment_name_activities());
        holder.assignmentActivitiesLinkList.setText(activities.getAssignment_link_activities());

    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentActivitiesNameList;
        public TextView assignmentActivitiesLinkList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentActivitiesNameList = itemView.findViewById(R.id.assignment_activities_name_list);
            assignmentActivitiesLinkList = itemView.findViewById(R.id.assignment_activities_link_list);
        }
    }
}
