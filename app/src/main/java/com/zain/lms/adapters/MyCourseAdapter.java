package com.zain.lms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zain.lms.R;
import com.zain.lms.model.MyCourseModel;

import java.util.List;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.ViewHolder>{
    Context context;
    List<MyCourseModel> myCourseModelList;

    public MyCourseAdapter(Context context, List<MyCourseModel> myCourseModelList) {
        this.context = context;
        this.myCourseModelList = myCourseModelList;
    }

    @NonNull
    @Override
    public MyCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_course_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseAdapter.ViewHolder holder, int position) {
        MyCourseModel myCourseModel = myCourseModelList.get(position);
        holder.myCourseTextView.append(": " +myCourseModel.getMyCourse());
    }

    @Override
    public int getItemCount() {
        return myCourseModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myCourseTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myCourseTextView = itemView.findViewById(R.id.course_name);

        }
    }
}
