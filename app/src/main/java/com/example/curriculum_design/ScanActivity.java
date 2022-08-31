package com.example.curriculum_design;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends AppCompatActivity {
    private CaptureManager capture;
    private ImageButton ibFlashlight;
    private DecoratedBarcodeView barcodeScannerView;
    private boolean bTorch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        //==设置布局、获取控件
        setContentView(R.layout.activity_scan);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        barcodeScannerView = findViewById(R.id.dbv);
        ibFlashlight= findViewById(R.id.ib_flashlight_close);

        //==保存扫描条到Application里
        View view = findViewById(R.id.scan_the);
        MyApplication myApplication= (MyApplication) getApplication();
        myApplication.setView(view);

        //==监听： 根据barcodeScannerView设置闪光灯ibFlashlight状态
        barcodeScannerView.setTorchListener(new DecoratedBarcodeView.TorchListener() {
            @Override
            public void onTorchOn() {//开灯

                //R.drawable.ic_flashlight_open)  开灯显示的图片 自行找图片样式
                ibFlashlight.setBackground(getResources().getDrawable(R.drawable.ic_flashlight_open));
                bTorch = true;
            }

            @Override
            public void onTorchOff() {//关灯

                //R.drawable.ic_flashlight_close)  关灯显示的图片 自行找图片样式
                ibFlashlight.setBackground(getResources().getDrawable(R.drawable.ic_flashlight_close));
                bTorch = false;
            }
        });

        //==开或关灯
        ibFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bTorch){
                    barcodeScannerView.setTorchOff();
                } else {
                    barcodeScannerView.setTorchOn();
                }

            }
        });

        //==初始化活动
        capture = new CaptureManager(this, barcodeScannerView);

        capture.initializeFromIntent(getIntent(), savedInstanceState);

        capture.decode();
    }


    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
        barcodeScannerView.setTorchOff();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
