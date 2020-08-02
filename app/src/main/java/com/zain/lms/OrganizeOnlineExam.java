package com.zain.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

public class OrganizeOnlineExam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize_online_exam);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Organize Online Exam");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        WebView organizeOnlineExamWebView = findViewById(R.id.organizeOnlineExamWebView);
        WebSettings webSettings = organizeOnlineExamWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        organizeOnlineExamWebView.setWebViewClient(new WebViewClient());
        organizeOnlineExamWebView.loadUrl("https://docs.google.com/forms/d/1cHCxXO2hCsYEvdLUjpHznAGz1VmDRVUJgVgaYdk-X2w/edit");

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}