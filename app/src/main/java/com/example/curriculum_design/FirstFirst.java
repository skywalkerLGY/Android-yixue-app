package com.example.curriculum_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.curriculum_design.Word_recite.SoundPoolUtil;
import com.example.curriculum_design.Word_recite.Word_Recite;
import com.githang.statusbar.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstFirst extends AppCompatActivity {
    private static final int MESSAGE_WHAT = 0x00;
    double i = 0, j = 0;
    int x = 0, y = 0;
    boolean flag = false, flag1 = false;
    boolean flag_skip=false;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==MESSAGE_WHAT){
                Intent it;
                SharedPreferences sp=getSharedPreferences("relax", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                boolean isEnter=sp.getBoolean("isEnter", false);
                if(isEnter){
                    it=new Intent(FirstFirst.this,MainActivity.class);
                }
                else {
                    it = new Intent(FirstFirst.this, First_Activity.class);
                    editor.putBoolean("isEnter", true);
                    editor.commit();
                }
                startActivity(it);
                overridePendingTransition(R.anim.activity_in, R.anim.null_out);
                finish();
            }

        }
    };
    TextView timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_first_first);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FirstFirst.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.activity_in, R.anim.null_out);
                flag_skip=true;
                finish();
            }
        });
        timer=findViewById(R.id.timer);
        init_first();
        final TextView yaoxiaoyuan = findViewById(R.id.yao);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        final int width = metric.widthPixels;

    }
    void init_first(){
        SharedPreferences pref_read = getSharedPreferences("first_loading", MODE_PRIVATE);
        boolean the_first = pref_read.getBoolean("the_first", true);//第二个参数为默认值
        if (the_first == true) {
            showDialog();
        }else{
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        for(int ii=0;ii<3;ii++) {
                            final int finalIi = ii;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    timer.setText((3-finalIi)+"");
                                }
                            });
                            Thread.sleep(1000);
                        }
                        timer.setText(0+"");
                        if(flag_skip==false)
                            handler.sendEmptyMessageDelayed(MESSAGE_WHAT,0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public void showDialog() {
        final Dialog dialog = new Dialog(FirstFirst.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_private);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_scale);
        window.setGravity(Gravity.CENTER);
        final TextView textView = window.findViewById(R.id.text_private);
        final Button disagree = window.findViewById(R.id.disagree);
        final Button agree = window.findViewById(R.id.agree);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstFirst.this.finish();
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                SharedPreferences pref_write = FirstFirst.this.getSharedPreferences("first_loading", MODE_PRIVATE);
                SharedPreferences.Editor editor_write = pref_write.edit();
                editor_write.putBoolean("the_first", false);
                editor_write.commit();
                dialog.cancel();
                init_first();
            }
        });
    }
}