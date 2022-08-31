package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.githang.statusbar.StatusBarCompat;

public class WebActivity extends AppCompatActivity{

    private WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activityweb);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        Intent intent = getIntent();
        webview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行JavaScript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);       // 这个要加上
        //加载URL内容
        webview.loadUrl(intent.getStringExtra("web_url"));
        //设置web视图客户端
        webview.setWebViewClient(new WebActivity.MyWebViewClient());
    }

    //设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //web视图客户端
    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}