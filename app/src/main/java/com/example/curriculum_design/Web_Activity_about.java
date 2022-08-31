package com.example.curriculum_design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;

import static com.example.curriculum_design.UploadMessage.FILE_CHOOSER_RESULT_CODE;

public class Web_Activity_about extends AppCompatActivity implements View.OnClickListener {

    private WebView webview;
    private Button goback;
    FileChooserWebChromeClient fileChooserWebChromeClient;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_web_about);
        initView();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);


        Intent intent = getIntent();
        webview = (WebView) findViewById(R.id.wv_webview);
        //设置WebView属性，能够执行JavaScript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);       // 这个要加上
        //加载URL内容
        webview.loadUrl(intent.getStringExtra("web_url"));
        //设置web视图客户端
        webview.setWebViewClient(new MyWebViewClient());
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fileChooserWebChromeClient = FileChooserWebChromeClient.createBuild(new FileChooserWebChromeClient.ActivityCallBack() {
            @Override
            public void FileChooserBack(Intent intent) {
                startActivityForResult(intent, FILE_CHOOSER_RESULT_CODE);
            }
        });
        webview.setWebChromeClient(fileChooserWebChromeClient);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            fileChooserWebChromeClient.getUploadMessage().onActivityResult(requestCode, resultCode, data);
        }
    }
    //设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        goback = (Button) findViewById(R.id.goback);

        goback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:

                break;
        }
    }

    //web视图客户端
    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}