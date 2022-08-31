package com.example.curriculum_design.Word_recite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.Calendar;
import java.util.TimeZone;

public class DaKa_Activity extends AppCompatActivity {

    private PColumn column_one;
    private PColumn column_two;
    private PColumn column_three;
    private PColumn column_four;
    private PColumn column_five;
    private PColumn column_six;
    private PColumn column_seven;
    private Button goback;
    private TextView month;
    private GridView rili_view;
    int day = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_daka);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);//黑色状态栏
    }

    private void initAllViews() {
        column_one = (PColumn) findViewById(R.id.column_one);
        column_two = (PColumn) findViewById(R.id.column_two);
        column_three = (PColumn) findViewById(R.id.column_three);
        column_four = (PColumn) findViewById(R.id.column_four);
        column_five = (PColumn) findViewById(R.id.column_five);
        column_six = (PColumn) findViewById(R.id.column_six);
        column_seven = (PColumn) findViewById(R.id.column_seven);
        column_one.mColor = getResources().getColor(R.color.zhou1);
        column_one.initPaint();
        column_two.mColor = getResources().getColor(R.color.zhou2);
        column_two.initPaint();
        column_three.mColor = getResources().getColor(R.color.zhou3);
        column_three.initPaint();
        column_four.mColor = getResources().getColor(R.color.zhou4);
        column_four.initPaint();
        column_five.mColor = getResources().getColor(R.color.zhou5);
        column_five.initPaint();
        column_six.mColor = getResources().getColor(R.color.zhou6);
        column_six.initPaint();
        column_seven.mColor = getResources().getColor(R.color.zhou7);
        column_seven.initPaint();

        column_one.setData(Static_Week.week_sum[0], getMax());
        column_two.setData(Static_Week.week_sum[1], getMax());
        column_three.setData(Static_Week.week_sum[2], getMax());
        column_four.setData(Static_Week.week_sum[3], getMax());
        column_five.setData(Static_Week.week_sum[4], getMax());
        column_six.setData(Static_Week.week_sum[5], getMax());
        column_seven.setData(Static_Week.week_sum[6], getMax());


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        month.setText(mMonth + "月");
    }

    private void initView() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mWay = c.get(Calendar.DAY_OF_WEEK);
        if (mWay > 1) {
            mWay--;
        } else
            mWay = 7;
        final int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        int week_one = 0;
        if(mWay-(mDay-1)%7<0)
            week_one=mWay-(mDay-1)%7+7;
        else
            week_one=mWay-(mDay-1)%7;
        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaKa_Activity.this, Word_Recite.class);
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        month = (TextView) findViewById(R.id.month);
        rili_view = (GridView) findViewById(R.id.rili_view);
        final int finalWeek_one = week_one;
        rili_view.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 35;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(DaKa_Activity.this).inflate(R.layout.grid_rili_layout, parent, false);
                TextView rili_txt = convertView.findViewById(R.id.ri_txt);
                int value = position + 1;
                if (finalWeek_one > value) {
                } else if (day <= 31) {
                    rili_txt.setText(day + "");
                    if (day < mDay) {
                        if (Static_Month.month[day - 1] == true) {
                            rili_txt.setBackgroundResource(R.drawable.rili_study_back);
                        } else
                            rili_txt.setBackgroundResource(R.drawable.rili_normal_back);
                    } else if (day == mDay) {
                        if (Static_Month.month[day - 1] == true) {
                            rili_txt.setBackgroundResource(R.drawable.rili_study_back);
                        } else
                            rili_txt.setBackgroundResource(R.drawable.rili_today_back);
                    }
                    day++;

                }
                return convertView;
            }
        });
        TextView havestudy=findViewById(R.id.have_study);
        if(Static_Month.month[mDay-1]==true)
            havestudy.setText("今日已打卡");
        else
            havestudy.setText("今日未打卡");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initAllViews();
    }

    int getMax() {
        int max = Static_Week.week_sum[0];
        for (int i = 0; i < 7; i++) {
            if (Static_Week.week_sum[i] > max)
                max = Static_Week.week_sum[i];
        }
        return max + max/6;
    }
}