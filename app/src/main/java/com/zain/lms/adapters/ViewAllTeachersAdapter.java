package com.zain.lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zain.lms.R;
import com.zain.lms.model.getAllTeachers;

import java.util.List;

public class ViewAllTeachersAdapter extends RecyclerView.Adapter<ViewAllTeachersAdapter.ViewHolder> {
    Context context;
    List<getAllTeachers> getAllTeachersList;

    public ViewAllTeachersAdapter(Context context, List<getAllTeachers> getAllTeachersList) {
        this.context = context;
        this.getAllTeachersList = getAllTeachersList;
    }

    @NonNull
    @Override
    public ViewAllTeachersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teachers_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllTeachersAdapter.ViewHolder holder, int position) {
        getAllTeachers getAllTeachers = getAllTeachersList.get(position);
        holder.teacherName.setText("Name: "+ getAllTeachers.getTeacherName());
        holder.teacherEmail.setText("Email: "+ getAllTeachers.getTeacherEmail());
        holder.teacherContact.setText("Contact" + getAllTeachers.getTeacherContact());
    }

    @Override
    public int getItemCount() {
        return getAllTeachersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherName;
        public TextView teacherEmail;
        public TextView teacherContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.teachers_name);
            teacherEmail = itemView.findViewById(R.id.teachers_email);
            teacherContact = itemView.findViewById(R.id.teachers_contact);
        }
    }
}
