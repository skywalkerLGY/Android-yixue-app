package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.Now_User;
import com.githang.statusbar.StatusBarCompat;

public class SchoolCardAcitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_schoolcard);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);
        TextView xm=findViewById(R.id.xm);
        TextView xb=findViewById(R.id.xb);
        TextView xh=findViewById(R.id.xh);
        TextView fanhui=findViewById(R.id.goback);
        ImageView user_photo=findViewById(R.id.user_photo);
        xm.setText(Now_User.User_Name);
        xb.setText(Now_User.user_sex);
        xh.setText(Now_User.user_school_number);
        Glide.with(this).load("https://q1.qlogo.cn/g?b=qq&nk="+Now_User.user_qq+"&s=140").into(user_photo);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}