package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ZhuanyeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanye);
        WebView webView = (WebView) findViewById(R.id.zhuanye_webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://gaokao.chsi.com.cn/zyk/zybk/wap/\n");
    }
}