package com.example.curriculum_design.Eat_CanTing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SubMit_ZD_Activity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {
    Intent it;
    private ImageView eat_image;
    private TextView txt_eat_name;
    private TextView eat_money;
    private Button add_to_buy;
    private TextView money_sum;
    float sum_money = 0;
    private Button cut_sum;
    private TextView now_sum;
    private Button add_sum;
    int now = 0;
    private Button submit_dingdan;
    int image_source = 0;
    String eat_name;
    private Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_submit_zd);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        initView();
        it = getIntent();
        image_source = it.getIntExtra("image_source", 0);
        eat_name = it.getStringExtra("eat_name");
        eat_image.setImageDrawable(null);
        eat_image.setBackgroundResource(0);
        eat_image.setImageResource(image_source);
        txt_eat_name.setText(eat_name);
        Random rand = new Random();
        int randNum1 = rand.nextInt(20) + 5;
        int randNum2 = rand.nextInt(10) + 5;
        eat_money.setText(randNum1 + "." + randNum2);
    }

    private void initView() {
        eat_image = (ImageView) findViewById(R.id.eat_image);
        txt_eat_name = (TextView) findViewById(R.id.txt_eat_name);
        eat_money = (TextView) findViewById(R.id.eat_money);
        add_to_buy = (Button) findViewById(R.id.add_to_buy);
        mAdd = (TextView) findViewById(R.id.zan);
        add_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumAdd(v);
                float value = Float.valueOf(eat_money.getText().toString());
                sum_money += value;
                money_sum.setText(String.valueOf(sum_money));
                now++;
                now_sum.setText(String.valueOf(now));
            }
        });
        money_sum = (TextView) findViewById(R.id.money_sum);
        money_sum.setText(String.valueOf(sum_money));
        cut_sum = (Button) findViewById(R.id.cut_sum);
        now_sum = (TextView) findViewById(R.id.now_sum);
        add_sum = (Button) findViewById(R.id.add_sum);
        cut_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now > 0) {
                    float value = Float.valueOf(eat_money.getText().toString());
                    sum_money -= value;
                    money_sum.setText(String.valueOf(sum_money));
                    now--;
                    now_sum.setText(String.valueOf(now));
                }
            }
        });
        add_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float value = Float.valueOf(eat_money.getText().toString());
                sum_money += value;
                money_sum.setText(String.valueOf(sum_money));
                now++;
                now_sum.setText(String.valueOf(now));
            }
        });
        submit_dingdan = (Button) findViewById(R.id.submit_dingdan);
        submit_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);
                Static_DingDan.time.add(time);
                Static_DingDan.eat_sum.add(now_sum.getText().toString());
                Static_DingDan.eat_money.add(money_sum.getText().toString());
                Static_DingDan.eat_name.add(txt_eat_name.getText().toString());
                Static_DingDan.eat_image.add(image_source);
                Static_DingDan.status.add(new Boolean(false));
                Static_DingDan.hp.add(new Boolean(false));
                finish();
                Toast.makeText(SubMit_ZD_Activity.this, "已下单", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
    }

    private TextView mAdd;

    public void sumAdd(View view) {
        mAdd.setVisibility(View.VISIBLE);
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.btn_add);
        mAdd.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mAdd.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_dingdan:

                break;
            case R.id.go_back:
                break;
        }
    }
}