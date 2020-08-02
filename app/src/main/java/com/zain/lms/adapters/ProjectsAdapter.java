package com.zain.lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zain.lms.R;
import com.zain.lms.model.Homework;
import com.zain.lms.model.Projects;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    Context context;
    List<Projects> projectsList;

    public ProjectsAdapter(Context context, List<Projects> projectsList) {
        this.context = context;
        this.projectsList = projectsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.projects_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Projects projects = projectsList.get(position);
        holder.assignmentNameProjects.setText(projects.getAssignment_name_projects());
        holder.assignmentLinkProjects.setText(projects.getAssignment_link_projects());
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentNameProjects;
        public TextView assignmentLinkProjects;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentNameProjects = itemView.findViewById(R.id.assignment_project_name_list);
            assignmentLinkProjects = itemView.findViewById(R.id.assignment_project_link_list);
        }
    }
}
