package com.zain.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GenerateCertificatesActivity extends AppCompatActivity {
    MaterialAlertDialogBuilder materialAlertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_certificates);
        getSupportActionBar().setTitle("Generate Certificates");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(!isNetworkConnected()){
            materialAlertDialogBuilder =new MaterialAlertDialogBuilder(this).setTitle("No internet connection")
                    .setMessage("Please cheach your internet connection and try again")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            materialAlertDialogBuilder.show();
        }
        WebView generateUserCertificate = findViewById(R.id.generate_user_certificates_webView);
        WebSettings webSettings = generateUserCertificate.getSettings();
        webSettings.setJavaScriptEnabled(true);

        generateUserCertificate.setVerticalScrollBarEnabled(true);
        generateUserCertificate.setHorizontalScrollBarEnabled(true);
        generateUserCertificate.setWebViewClient(new WebViewClient());
        generateUserCertificate.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSd3cA_0vD6ydC4Kw6ooR96baiCveifmlCXSsy-JuAA0MLqpLg/viewform?usp=sf_link");
    }
    //Checking Internet Connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}