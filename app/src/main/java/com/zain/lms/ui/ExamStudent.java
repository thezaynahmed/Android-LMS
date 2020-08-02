package com.zain.lms.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zain.lms.R;

public class ExamStudent extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.exams_student,null);
        WebView organizeOnlineExamWebView = view.findViewById(R.id.student_exams_webView);
        WebSettings webSettings = organizeOnlineExamWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        organizeOnlineExamWebView.setWebViewClient(new WebViewClient());
        organizeOnlineExamWebView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScH-OrUKUazhT94Ilb2PoA4b-QstxKhbOYVxrKDG_X5pu9h0w/viewform?usp=sf_link");
        return view;
    }
}
