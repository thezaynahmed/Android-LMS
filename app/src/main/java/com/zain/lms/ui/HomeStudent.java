package com.zain.lms.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.zain.lms.ActivitiesActivity;
import com.zain.lms.ClassworkActivity;
import com.zain.lms.HomeworkActivity;
import com.zain.lms.ProjectsActivity;
import com.zain.lms.R;
import com.zain.lms.StudentDashboard;

public class HomeStudent extends Fragment implements View.OnClickListener {
    MaterialCardView classworkCardView;
    MaterialCardView homeworkCardView;
    MaterialCardView activitiesCardView;
    MaterialCardView projectCardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_student,null);
        classworkCardView = view.findViewById(R.id.classWordCard);
        homeworkCardView = view.findViewById(R.id.homeWorkCard);
        activitiesCardView = view.findViewById(R.id.activitiesCard);
        projectCardView = view.findViewById(R.id.projectsCard);

        classworkCardView.setOnClickListener(this);
        homeworkCardView.setOnClickListener(this);
        activitiesCardView.setOnClickListener(this);
        projectCardView.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classWordCard:
                startActivity(new Intent(getContext(), ClassworkActivity.class));
                break;
            case R.id.homeWorkCard:
                startActivity(new Intent(getContext(), HomeworkActivity.class));
                break;
            case R.id.activitiesCard:
                startActivity(new Intent(getContext(), ActivitiesActivity.class));
                break;
            case R.id.projectsCard:
                startActivity(new Intent(getContext(), ProjectsActivity.class));
                break;
        }
    }
}
