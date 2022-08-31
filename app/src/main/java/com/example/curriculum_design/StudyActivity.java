package com.example.curriculum_design;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;



public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_study);
        WebView webView = (WebView) findViewById(R.id.wv_webview);
        //访问网页
        webView.getSettings().setJavaScriptEnabled(true);
/*这里是用来加速的*/
        WebSettings webSettings = webView.getSettings();
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript


        webView.loadUrl("https://static-9e04f327-769b-4fdc-8c45-7eafb954fb2d.bspapp.com/#/");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//                }
//                if (Build.VERSION.SDK_INT < 18) {
//                    webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//                }
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
}