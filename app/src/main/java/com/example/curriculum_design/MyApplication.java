package com.example.curriculum_design;

import android.app.Application;
import android.view.View;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.mengpeng.mphelper.ToastUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.getInstance().initToast(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=7b3898ae");
    }
    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
