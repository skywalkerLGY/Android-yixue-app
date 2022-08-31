package com.example.curriculum_design.Eat_CanTing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

public class More_Info_Activity extends AppCompatActivity implements View.OnClickListener {

    Intent it;
    int position = 0;
    private RelativeLayout rel0;
    private TextView eat_name;
    private TextView the_time;
    private TextView eat_money;
    private TextView the_sum;
    private TextView shou_name;
    private TextView shou_phone;
    private Button goback;
    private ImageView eat_image;
    private TextView hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_more__info_);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        initView();
        it = getIntent();
        position = it.getIntExtra("position", 0);
        eat_name.setText(Static_DingDan.eat_name.get(position));
        the_time.setText(Static_DingDan.time.get(position));
        eat_money.setText("￥" + Static_DingDan.eat_money.get(position) + "元");
        the_sum.setText(Static_DingDan.eat_sum.get(position) + "份");
        shou_name.setText(Now_User.User_Name);
        shou_phone.setText(Now_User.user_phone);
        eat_image.setImageResource(Static_DingDan.eat_image.get(position));
        if(Static_DingDan.hp.get(position)==true)
            hp.setText("是");
        else
            hp.setText("否");
    }

    private void initView() {
        rel0 = (RelativeLayout) findViewById(R.id.rel0);
        eat_name = (TextView) findViewById(R.id.eat_name);
        the_time = (TextView) findViewById(R.id.the_time);
        eat_money = (TextView) findViewById(R.id.eat_money);
        the_sum = (TextView) findViewById(R.id.the_sum);
        shou_name = (TextView) findViewById(R.id.shou_name);
        shou_phone = (TextView) findViewById(R.id.shou_phone);
        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(this);
        eat_image = (ImageView) findViewById(R.id.eat_image);
        hp = (TextView) findViewById(R.id.hp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
                break;
        }
    }
}