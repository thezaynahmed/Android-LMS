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

import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {
    Context context;
    List<Homework> homeworkList;

    public HomeworkAdapter(Context context, List<Homework> homeworkList) {
        this.context = context;
        this.homeworkList = homeworkList;
    }

    @NonNull
    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homework_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkAdapter.ViewHolder holder, int position) {
    Homework homework = homeworkList.get(position);
    holder.assignmentNameHomework.setText(homework.getAssignment_name_homework());
    holder.assignmentLinkHomework.setText(homework.getAssignment_link_homework());
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentNameHomework;
        public TextView assignmentLinkHomework;;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentNameHomework = itemView.findViewById(R.id.assignment_name_homework);
            assignmentLinkHomework = itemView.findViewById(R.id.assignment_link_homework);
        }
    }
}
