package com.example.curriculum_design;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import java.util.Calendar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class kaoyanActivity extends AppCompatActivity {
    ImageView img_gaokao,img_zhuanye,img_zixun,img_zhiyuan,img_chengji;
    WebView web_gaokaopage;
    private ImageView dot1, dot2;
    private float startX;
    private MyGestureListener myGestureListener;
    private GestureDetector gestureDetector;
    private ViewFlipper viewimage;
    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private List<View> views;
    //把所有图片资源的id放在一个int数组中
    private int[] resId = {R.drawable.kaoyan1, R.drawable.kaoyan2, R.drawable.kaoyan3};
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private RelativeLayout countDown;
    // 倒计时
    private TextView mDays_Tv, mHours_Tv, mMinutes_Tv, mSeconds_Tv;
    Calendar cal = Calendar.getInstance();
    private long mMonth = cal.get(Calendar.MONTH);
    private long mDay = cal.get(Calendar.DATE);// 天
    private long mHour = 24-cal.get(Calendar.HOUR_OF_DAY);//小时,
    private long mMin = 60-cal.get(Calendar.MINUTE);//分钟,
    private long mSecond = 60-cal.get(Calendar.SECOND);//秒
    private long mDay2;
    private Timer mTimer;
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mDay>=19&&mDay<=31){
                mDay2=237-mDay;
                if (msg.what == 1) {
                    computeTime();
                    mDays_Tv.setText(mDay2+"");//天数不用补位
                    mHours_Tv.setText(getTv(mHour));
                    mMinutes_Tv.setText(getTv(mMin));
                    mSeconds_Tv.setText(getTv(mSecond));
                }
            }

        }
    };

    private String getTv(long l){
        if(l>=10){
            return l+"";
        }else{
            return "0"+l;//小于10,,前面补位一个"0"
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_kaoyan);
        img_gaokao=(ImageView)findViewById(R.id.img_yuanxiao);
        img_gaokao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(kaoyanActivity.this,KaoyanYuanxiaoActivity.class);
                startActivity(it);
            }
        });
        img_zhuanye=(ImageView)findViewById(R.id.img_zhuanye);
        img_zhuanye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(kaoyanActivity.this,kaoyanzhuanyeActivity.class);
                startActivity(it);
            }
        });
        img_zixun=(ImageView)findViewById(R.id.img_zixun);
        img_zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(kaoyanActivity.this,kaoyanzixunActivity.class);
                startActivity(it);
            }
        });
        img_zhiyuan=(ImageView)findViewById(R.id.img_zhiyuan);
        img_zhiyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(kaoyanActivity.this,kaoyantiaojiActivity.class);
                startActivity(it);
            }
        });
        img_chengji=(ImageView)findViewById(R.id.img_chengji);
        img_chengji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(kaoyanActivity.this,kaoyangonggaoActivity.class);
                startActivity(it);
            }
        });
        web_gaokaopage=findViewById(R.id.gaokaopage_webview);
        web_gaokaopage.getSettings().setJavaScriptEnabled(true);
        web_gaokaopage.loadUrl("https://www.kaoyan.cn/\n");
        ///////////////////////////////考研页面课程课程滑动//////////////////////////////
        dot1 = findViewById(R.id.gaokao_dot_1);
        dot2 = findViewById(R.id.gaokao_dot_2);
        views = new ArrayList<>();
        views.add(LayoutInflater.from(kaoyanActivity.this).inflate(R.layout.kaoyanlayout1_1, null));
        views.add(LayoutInflater.from(kaoyanActivity.this).inflate(R.layout.kaoyanlayout1_2, null));
        viewPager = findViewById(R.id.gaokao_viewPager);
        pagerAdapter = new MyPagerAdapter(views, kaoyanActivity.this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//当页面滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override//当页面选中时
            public void onPageSelected(int position) {
                setDot(position == 0, position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //////////////////////////////////////////////////////////////////////////////

        mTimer = new Timer();
        countDown = (RelativeLayout) findViewById(R.id.countdown_layout);
        mDays_Tv = (TextView) findViewById(R.id.days_tv);
        mHours_Tv = (TextView) findViewById(R.id.hours_tv);
        mMinutes_Tv = (TextView) findViewById(R.id.minutes_tv);
        mSeconds_Tv = (TextView) findViewById(R.id.seconds_tv);
        startRun();
        viewimage = findViewById(R.id.gaokao_scroll);
        Image_Scroll();
    }
    private void setDot(boolean a, boolean b) {
        if (a) dot1.setBackgroundResource(R.drawable.point_on);
        else dot1.setBackgroundResource(R.drawable.point_off);
        if (b) dot2.setBackgroundResource(R.drawable.point_on);
        else dot2.setBackgroundResource(R.drawable.point_off);
    }

    void Image_Scroll() {
        myGestureListener = new MyGestureListener(kaoyanActivity.this, viewimage);
        gestureDetector = new GestureDetector(kaoyanActivity.this, myGestureListener);
        for (int i = 0; i < resId.length; i++) {
            //在往viewFlipper中加入imageView
            ImageView img = new ImageView(kaoyanActivity.this);
            img.setImageResource(resId[i]);
            viewimage.addView(img);
        }
        //动态设置切换时间为3000ms
        viewimage.setFlipInterval(3000);
        //开始自动滚动
        viewimage.startFlipping();
        //手势控制进行监听
        viewimage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startX = event.getX();
                        break;
                    }
                    //当手离开时
                    case MotionEvent.ACTION_UP:
                        //向右滑动
                        if (event.getX() - startX > 100) {
                            viewimage.setInAnimation(kaoyanActivity.this, R.anim.left_in);
                            viewimage.setOutAnimation(kaoyanActivity.this, R.anim.left_out);
                            viewimage.showNext();
                        }
                        //向左滑动
                        if (startX - event.getX() > 100) {
                            viewimage.setInAnimation(kaoyanActivity.this, R.anim.right_in);
                            viewimage.setOutAnimation(kaoyanActivity.this, R.anim.right_out);
                            viewimage.showPrevious();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if(mDay < 0){
                        // 倒计时结束
                        mDay = 0;
                        mHour= 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }
}