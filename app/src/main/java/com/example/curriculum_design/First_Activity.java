package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

public class First_Activity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> views;
    private MyPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.first_layout);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);
        views = new ArrayList<>();
        viewPager = findViewById(R.id.first_viewpager);
        views.add(LayoutInflater.from(this).inflate(R.layout.first_1, null));
        views.add(LayoutInflater.from(this).inflate(R.layout.first_2, null));
        pagerAdapter = new MyPagerAdapter(views, this);
        viewPager.setAdapter(pagerAdapter);
        final ImageView don = findViewById(R.id.don);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//当页面滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                    RelativeLayout.LayoutParams params  = (RelativeLayout.LayoutParams) don.getLayoutParams();
                    float danwei_one= (float) ((viewPager.getWidth() - don.getWidth()));
                    params.setMargins((int) ((positionOffset+position) * (danwei_one)),0, 0, don.getHeight()/2);// 通过自定义坐标来放置你的控件
                    don.setLayoutParams(params);
                }
            }

            @Override//当页面选中时
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//        Button button = views.get(2).findViewById(R.id.into);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(First_Activity.this, MainActivity.class);
//                startActivity(it);
//                overridePendingTransition(R.anim.activity_in, R.anim.null_out);
//                finish();
//            }
//        });
        Button skip1 = views.get(0).findViewById(R.id.skip);
        skip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(First_Activity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.activity_in, R.anim.null_out);
                finish();
            }
        });

        Button button = views.get(1).findViewById(R.id.into);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(First_Activity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(R.anim.activity_in, R.anim.null_out);
                finish();
            }
        });
    }
}