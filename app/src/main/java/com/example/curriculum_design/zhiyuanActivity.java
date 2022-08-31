package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class zhiyuanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiyuan);
        WebView webView = (WebView) findViewById(R.id.zhiyuan_webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.chsi.com.cn/zthz/yggk.jsp\n");
    }
}