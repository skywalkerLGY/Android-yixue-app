package com.example.curriculum_design.Lock;

import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.R;
import com.example.curriculum_design.Word_recite.Word_List;
import com.githang.statusbar.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LockScreenActivity extends AppCompatActivity {

    private TextView dian;
    private TextView riqi;
    private TextView the1;
    private TextView the2;
    private TextView the3;
    private TextView the4;
    private TextView the5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前Activity的锁屏显示
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = km.newKeyguardLock("1");
        keyguardLock.disableKeyguard();
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_lock_screen);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        initView();
        SlideRightViewDragHelper dragHelper;
        dragHelper = (SlideRightViewDragHelper) findViewById(R.id.jiesuo);
        dragHelper.setOnReleasedListener(new SlideRightViewDragHelper.OnReleasedListener() {
            @Override
            public void onReleased() {
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
    private void initView() {
        dian = (TextView) findViewById(R.id.dian);
        riqi = (TextView) findViewById(R.id.riqi);
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日-HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        String s[] = str.split("-");
        dian.setText(s[1]);
        riqi.setText(s[0]);

        int length = Word_List.english_list.size() - 1;
        Random rand = new Random();
        int randNum1 = rand.nextInt(length);
        int randNum2 = rand.nextInt(length);
        int randNum3 = rand.nextInt(length);
        int randNum4 = rand.nextInt(length);
        int randNum5 = rand.nextInt(length);

        the1 = (TextView) findViewById(R.id.the1);
        the2 = (TextView) findViewById(R.id.the2);
        the3 = (TextView) findViewById(R.id.the3);
        the4 = (TextView) findViewById(R.id.the4);
        the5 = (TextView) findViewById(R.id.the5);
        the1.setText(Word_List.english_list.get(randNum1)+":"+Word_List.chinese_list.get(randNum1));
        the2.setText(Word_List.english_list.get(randNum2)+":"+Word_List.chinese_list.get(randNum2));
        the3.setText(Word_List.english_list.get(randNum3)+":"+Word_List.chinese_list.get(randNum3));
        the4.setText(Word_List.english_list.get(randNum4)+":"+Word_List.chinese_list.get(randNum4));
        the5.setText(Word_List.english_list.get(randNum5)+":"+Word_List.chinese_list.get(randNum5));
    }
}