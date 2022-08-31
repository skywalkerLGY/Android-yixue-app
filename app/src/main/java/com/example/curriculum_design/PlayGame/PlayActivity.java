package com.example.curriculum_design.PlayGame;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.example.curriculum_design.R;
public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.play_main);
        WebView webView = (WebView) findViewById(R.id.play_webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
        /*这里是用来加速的*/

        webView.loadUrl("https://www.csie.ntu.edu.tw/~b01902112/65536/\n");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
}