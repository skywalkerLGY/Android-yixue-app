package com.example.curriculum_design;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.InitSql;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.UserInfo;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.Face_Camera.CameraSurfaceHolder;
import com.example.curriculum_design.message.HiddenAnimUtils;
import com.example.curriculum_design.message.NoScrollViewPager;
import com.githang.statusbar.StatusBarCompat;
import com.iflytek.cloud.FaceRequest;
import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.mengpeng.mphelper.ToastUtils;
import com.mob.wrappers.UMSSDKWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class My_Login extends AppCompatActivity {
    private static final String TAG = "SmsYanZheng";
    NoScrollViewPager viewPager;
    ArrayList<View> viewlist = new ArrayList<>();
    Button btn_login, btn_zhuce, btn_exit, btn_face;
    float x_start = 0, x_end = 0;
    Button btn_yzm, btn_password;
    EditText edt_yzm, edt_password;
    EditText EdY;
    EditText EdS;
    EventHandler eventHandler;
    String strPhoneNumber;
    Button BtnH;
    Button BtnD;
    Button btn_forget;
    LinearLayout line_gone, line_gone_pass;
    CheckBox remember;
    boolean flag_forget = false;
    Spinner sp_year, sp_month, sp_day, sp_sex;
    int sum_view = 0;
    VideoView login_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_my__login);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);

        Init();
        Init_Login();
        Init_Zhuce();
//        String uri = "android.resource://" + getPackageName() + "/" + R.raw.log_back;

        String uri = "https://v1.kwaicdn.com/ksc1/iFsSqxo0WvHsrMA4ytx2txqlKNxqQn41yaHCT2bitDcMidOxIRCUTP9fUMGM1ER1bZFdwowPU8r97DkS6WAzAfmfnu0MtrKTWleW6NmmkUOaPTT4ueSq_o9980s2BzKz881YqYV5jdEmkFMhA7PRzJD4-OXTFZrZUTLi2naq4_pGlgrEm2d_nTx8rLe_d1eQ.mp4?pkey=AAXSFagMRjXVs-RYuUIllXQ_6WNphLj7knAQj4ja8zAblACnkgpHavOqAeAYW6bqWHMUUFrfIs3zuOJG3p1LYPneTRsQrqbu1nqF_G2qnoduAiYomamOXmkoO6mZkq9W9yE&tag=1-1653540336-tube-0-uyqlswtblp-fcb7b3b11f9c2e42&clientCacheKey=3xjzh6phdjx8djc_b.mp4&tt=b&di=5146f5ea&bp=14081";
        login_back.setVideoURI(Uri.parse(uri));
        login_back.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
                mp.start();
            }
        });
    }

    void Init() {

        viewPager = findViewById(R.id.login_viewpager);
        btn_login = findViewById(R.id.login_btn);
        btn_zhuce = findViewById(R.id.zhuce_btn);
        btn_exit = findViewById(R.id.exit);
        btn_login.setTextColor(Color.DKGRAY);
        btn_zhuce.setTextColor(Color.LTGRAY);
        login_back = findViewById(R.id.login_back);
        viewlist.add(LayoutInflater.from(My_Login.this).inflate(R.layout.login, null));
        viewlist.add(LayoutInflater.from(My_Login.this).inflate(R.layout.zhuce, null));
        viewPager.setAdapter(new MyPagerAdapter(viewlist, My_Login.this));
        viewPager.setScrollable(false);
        ViewPagerScroller scroller = new ViewPagerScroller(My_Login.this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(viewPager); //这个是设置切换过渡时间为2秒
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setTextColor(Color.DKGRAY);
                btn_zhuce.setTextColor(Color.LTGRAY);
                viewPager.setCurrentItem(0);
            }
        });
        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setTextColor(Color.LTGRAY);
                btn_zhuce.setTextColor(Color.DKGRAY);
                viewPager.setCurrentItem(1);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });

        findViewById(R.id.line).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x_start = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x_end = event.getX();
                        if (x_end - x_start > 300) {
                            finish();
                            overridePendingTransition(R.anim.null_in, R.anim.login_out);
                            x_end = x_start = 0;
                        }
                        break;
                }
                return true;
            }
        });
    }

    void Init_Login() {
        btn_yzm = viewlist.get(0).findViewById(R.id.yzm_btn);
        btn_password = viewlist.get(0).findViewById(R.id.password_btn);
        edt_yzm = viewlist.get(0).findViewById(R.id.login_yzm);
        edt_password = viewlist.get(0).findViewById(R.id.login_password);
        line_gone = viewlist.get(0).findViewById(R.id.line_gone);
        line_gone_pass = viewlist.get(0).findViewById(R.id.line_gone_pass);
        remember = viewlist.get(0).findViewById(R.id.remember);
        btn_face = viewlist.get(0).findViewById(R.id.btn_face);
        final Button viewpass = viewlist.get(0).findViewById(R.id.view_pass);
        btn_face.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                showDialog_Face();
            }
        });
        btn_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiddenAnimUtils.newInstance(My_Login.this, line_gone, new View(My_Login.this), 40).toggle();
                HiddenAnimUtils.newInstance(My_Login.this, line_gone_pass, new View(My_Login.this), 40).toggle();
                btn_yzm.setVisibility(View.GONE);
                btn_password.setVisibility(View.VISIBLE);

            }
        });
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiddenAnimUtils.newInstance(My_Login.this, line_gone, new View(My_Login.this), 40).toggle();
                HiddenAnimUtils.newInstance(My_Login.this, line_gone_pass, new View(My_Login.this), 40).toggle();
                btn_yzm.setVisibility(View.VISIBLE);
                btn_password.setVisibility(View.GONE);
            }
        });
        edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum_view % 2 == 0) {
                    edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    viewpass.setBackgroundResource(R.drawable.hide_pass);
                } else {
                    edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    viewpass.setBackgroundResource(R.drawable.view_pass);
                }
                sum_view++;
            }
        });
        EdY = viewlist.get(0).findViewById(R.id.login_yzm);
        BtnH = viewlist.get(0).findViewById(R.id.Huoqu);
        BtnD = viewlist.get(0).findViewById(R.id.btn_login);
        EdS = viewlist.get(0).findViewById(R.id.login_phone);
        btn_forget = viewlist.get(0).findViewById(R.id.forget_btn);
        BtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPhoneNumber = EdS.getText().toString();
                if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
                    ToastUtils.onErrorShowToast("号码长度有误");
                    return;
                }
                SMSSDK.getVerificationCode("86", strPhoneNumber);
                BtnH.setClickable(false);
                //开启线程去更新butto的text
                new Thread() {
                    @Override
                    public void run() {
                        int totalTime = 60;
                        for (int i = 0; i < totalTime; i++) {
                            Message message = myHandler.obtainMessage(0x01);
                            message.arg1 = totalTime - i;
                            myHandler.sendMessage(message);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        myHandler.sendEmptyMessage(0x02);
                    }
                }.start();
            }
        });
        BtnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line_gone.getVisibility() == View.VISIBLE) {
                    String strCode = EdY.getText().toString();
                    if (null != strCode && strCode.length() == 6) {
                        Log.d(TAG, EdY.getText().toString());
                        SMSSDK.submitVerificationCode("86", strPhoneNumber, EdY.getText().toString());
                    } else {
                        ToastUtils.onErrorShowToast("验证码长度不正确");
                    }
                } else if (line_gone_pass.getVisibility() == View.VISIBLE) {
                    UserInfo userInfo = Phone_IsInSql(EdS.getText().toString());
                    if (userInfo != null) {
                        if (edt_password.getText().toString().equals(userInfo.user_pass)) {
                            Now_User.Set_Value(userInfo);
                            ToastUtils.onSuccessShowToast("欢迎进入易学教育app");
                            MainActivity.login.setBackgroundResource(R.drawable.tab_menu_logout);

                            UserInfo userInfo1 = (UserInfo) ObjectSaveUtils.getObject(My_Login.this, "Temp_User2");
                            if (userInfo1 != null && Now_User.User_Name != null) {
                                if (userInfo1.user_name.equals(Now_User.User_Name)) {
                                    Now_User.nicheng = userInfo1.nicheng;
                                    Now_User.qianming = userInfo1.qianming;
                                } else {
                                    Now_User.nicheng = Now_User.User_Name;
                                    Now_User.qianming = "创建你的签名";
                                }
                            } else {
                                Now_User.nicheng = Now_User.User_Name;
                                Now_User.qianming = "创建你的签名";
                            }
                            finish();
                            overridePendingTransition(R.anim.null_in, R.anim.login_out);
                        } else {
                            ToastUtils.onErrorShowToast("您的密码有误");
                        }
                    } else {
                        ToastUtils.onErrorShowToast("您的号码尚未注册");
                    }
                }
            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line_gone.getVisibility() == View.VISIBLE) {
                    String strCode = EdY.getText().toString();
                    if (null != strCode && strCode.length() == 6) {
                        Log.d(TAG, EdY.getText().toString());
                        flag_forget = true;
                        SMSSDK.submitVerificationCode("86", strPhoneNumber, EdY.getText().toString());
                    } else {
                        ToastUtils.onErrorShowToast("验证码长度不正确");
                    }
                } else {
                    ToastUtils.onErrorShowToast("请先进行短信验证，再点击找回密码");
                }
            }
        });
        EventHandler eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message message = myHandler.obtainMessage(0x00);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }


    boolean Find_User_By_Phone_Or_Name(String username,String phone){
        for(int i=0;i<UserInfo_List.userInfo_List.size();i++){
            UserInfo userInfo=UserInfo_List.userInfo_List.get(i);
            if(userInfo.user_name.equals(username)||userInfo.user_phone.equals(phone)){
                return true;
            }
        }
        return false;
    }
    void Init_Zhuce() {
        sp_year = viewlist.get(1).findViewById(R.id.s_year);
        sp_month = viewlist.get(1).findViewById(R.id.s_month);
        sp_day = viewlist.get(1).findViewById(R.id.s_day);
        sp_sex = viewlist.get(1).findViewById(R.id.s_sex);
        String[] string_year = new String[101];
        int j = 0;
        for (int i = 1920; i <= 2020; i++) {
            string_year[j] = i + "";
            j++;
        }
        String[] string_month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] string_day = new String[31];
        j = 0;
        for (int i = 1; i <= 31; i++) {
            string_day[j] = i + "";
            j++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, string_year);
        sp_year.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, string_month);
        sp_month.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, string_day);
        sp_day.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new String[]{"男", "女"});
        sp_sex.setAdapter(adapter);
        sp_year.setSelection(80);
        sp_month.setSelection(10);
        sp_day.setSelection(18);
        final EditText edt_phone, edt_name, edt_password, edt_password2, edt_school_id, edt_qq;

        Button btn_zhuce;
        edt_phone = viewlist.get(1).findViewById(R.id.phone_number);
        edt_name = viewlist.get(1).findViewById(R.id.edt_name);
        edt_password = viewlist.get(1).findViewById(R.id.edt_pass);
        edt_password2 = viewlist.get(1).findViewById(R.id.edt_pass_again);
        edt_school_id = viewlist.get(1).findViewById(R.id.user_school_id);
        edt_qq = viewlist.get(1).findViewById(R.id.user_qq);
        btn_zhuce = viewlist.get(1).findViewById(R.id.btn_zhuce);
        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserInfo userInfo = new UserInfo();
                String birth, sex;
                if (edt_phone.getText().toString().equals("") ||
                        edt_name.getText().toString().equals("") ||
                        edt_password.getText().toString().equals("") ||
                        edt_password2.getText().toString().equals("")||
                        edt_qq.getText().toString().equals("")) {
                    if (edt_phone.getText().toString().equals("")) {
                        edt_phone.setHint("手机号不能为空");
                        edt_phone.setHintTextColor(Color.RED);
                        ToastUtils.onErrorShowToast("手机号不能为空");
                    }
                    else if (edt_name.getText().toString().equals("")) {
                        edt_name.setHint("用户名不能为空");
                        edt_name.setHintTextColor(Color.RED);
                        ToastUtils.onErrorShowToast("用户名不能为空");
                    }
                    else if (edt_password.getText().toString().equals("")) {
                        edt_password.setHint("密码不能为空");
                        edt_password.setHintTextColor(Color.RED);
                        ToastUtils.onErrorShowToast("密码不能为空");
                    }
                    else if (edt_password2.getText().toString().equals("")) {
                        edt_password2.setHint("确认密码不能为空");
                        edt_password2.setHintTextColor(Color.RED);
                        ToastUtils.onErrorShowToast("确认密码不能为空");
                    }
                    else if (edt_qq.getText().toString().equals("")) {
                        edt_qq.setHint("QQ不能为空");
                        edt_qq.setHintTextColor(Color.RED);
                        ToastUtils.onErrorShowToast("QQ不能为空");
                    }
                } else {
                    if(Find_User_By_Phone_Or_Name(edt_name.getText().toString(),edt_phone.getText().toString())){
                        ToastUtils.onErrorShowToast("已存在该用户，直接登录");
                        btn_login.setTextColor(Color.DKGRAY);
                        My_Login.this.btn_zhuce.setTextColor(Color.LTGRAY);
                        viewPager.setCurrentItem(0);
                    }else{
                        birth = sp_year.getSelectedItem().toString() + "." + sp_month.getSelectedItem().toString() + "." + sp_day.getSelectedItem().toString();
                        sex = sp_sex.getSelectedItem().toString();
                        userInfo.user_name = edt_name.getText().toString();
                        userInfo.user_phone = edt_phone.getText().toString();
                        userInfo.user_pass = edt_password.getText().toString();
                        userInfo.user_birth = birth;
                        userInfo.user_sex = sex;
                        if (edt_school_id.getText().toString().equals("")) {
                            userInfo.user_school_number = "3030184104";
                        } else {
                            userInfo.user_school_number = edt_school_id.getText().toString();
                        }
                        if (edt_qq.getText().toString().equals("")) {
                            userInfo.user_qq = "2474590974";
                        } else {
                            userInfo.user_qq = edt_qq.getText().toString();
                        }
                        userInfo.photo = "http://q1.qlogo.cn/g?b=qq&nk="+userInfo.user_qq+"&s=100";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DbHelper.Insert_User(userInfo);
                                    InitSql.Init_Sql();
                                } catch (Exception e) {

                                }
                            }
                        }).start();
                        ToastUtils.onSuccessShowToast("已成功注册，去登录");
                        btn_login.setTextColor(Color.DKGRAY);
                        My_Login.this.btn_zhuce.setTextColor(Color.LTGRAY);
                        viewPager.setCurrentItem(0);
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> views;
        Context context;

        public MyPagerAdapter(List<View> views, Context context) {
            this.views = views;
            this.context = context;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    }

    UserInfo Phone_IsInSql(String strPhoneNumber) {
        for (int i = 0; i < UserInfo_List.userInfo_List.size(); i++) {
            UserInfo userInfo = UserInfo_List.userInfo_List.get(i);
            String phone = userInfo.user_phone;
            if (strPhoneNumber.equals(phone)) {
                return userInfo;
            }
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = My_Login.this.getSharedPreferences("remember", Context.MODE_PRIVATE);
        EdS.setText(sp.getString("username", ""));
        edt_password.setText(sp.getString("password", ""));
        remember.setChecked(sp.getBoolean("ischecked", false));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = My_Login.this.getSharedPreferences("remember", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (remember.isChecked()) {
            editor.putString("username", EdS.getText().toString());
            if (edt_password.getText().toString() != null)
                editor.putString("password", edt_password.getText().toString());
            else
                editor.putString("password", "");
            editor.putBoolean("ischecked", true);
        } else {
            editor.putString("username", "");
            editor.putString("password", "");
            editor.putBoolean("ischecked", false);
        }
        editor.commit();
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result:" + result + ",event:" + event + ",data:" + data);
                    //回调  当返回的结果是complete
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //获取验证码
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                            Toast.makeText(My_Login.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证
                            Log.d(TAG, "submit code successful");
                            UserInfo userInfo = Phone_IsInSql(EdS.getText().toString());
                            if (userInfo == null) {
                                ToastUtils.onErrorShowToast("此手机未注册,无法登录或找回");
                            } else {
                                if (flag_forget == true) {
                                    Toast.makeText(My_Login.this, "找回成功,注意查收通知", Toast.LENGTH_SHORT).show();
                                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    if (Build.VERSION.SDK_INT >= 26) {
                                        manager.createNotificationChannel(new NotificationChannel("c1", "c2", NotificationManager.IMPORTANCE_LOW));
                                        Notification notification = new Notification.Builder(My_Login.this, "c1")
                                                .setCategory(Notification.CATEGORY_MESSAGE)
                                                .setSmallIcon(R.drawable.app)
                                                .setContentTitle("手机号为" + EdS.getText().toString() + "的密码是")
                                                .setContentText(userInfo.user_pass + ",于60s内完成登录,不要泄露他人")
                                                .setContentIntent(PendingIntent.getActivity(My_Login.this, 0, new Intent(), 0))
                                                .setAutoCancel(true)
                                                .build();
                                        manager.notify(1, notification);
                                    }
                                    flag_forget = false;
                                } else {
                                    Now_User.Set_Value(userInfo);
                                    MainActivity.login.setBackgroundResource(R.drawable.tab_menu_logout);
                                    ToastUtils.onSuccessShowToast("欢迎进入易学app");
                                    UserInfo userInfo1 = (UserInfo) ObjectSaveUtils.getObject(My_Login.this, "Temp_User2");
                                    if (userInfo1 != null && userInfo1.user_name != null && Now_User.User_Name != null) {
                                        if (userInfo1.user_name.equals(Now_User.User_Name)) {
                                            Now_User.nicheng = userInfo1.nicheng;
                                            Now_User.qianming = userInfo1.qianming;
                                        } else {
                                            Now_User.nicheng = Now_User.User_Name;
                                            Now_User.qianming = "创建你的签名";
                                        }
                                    } else {
                                        Now_User.nicheng = Now_User.User_Name;
                                        Now_User.qianming = "创建你的签名";
                                    }
                                    finish();
                                    overridePendingTransition(R.anim.null_in, R.anim.login_out);
                                }

                            }
                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else {
                        //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码

                            Log.e(TAG, "status:" + status + ",detail:" + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                ToastUtils.onErrorShowToast("验证码错误,请重输");
                                if (flag_forget == true)
                                    flag_forget = false;
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    BtnH.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    BtnH.setText("获取验证码");
                    BtnH.setClickable(true);
                    break;
            }

        }
    };

    /**
     * 识别的弹窗-------------------------------------------------------------------------------开始
     */
    //数据测试用
    String[] auth_id = new String[10];
    private static final int LOGIN = 0x2;
    SurfaceView face_view;
    Button catch_btn;
    //    ImageView imageView;
    CameraSurfaceHolder mCameraSurfaceHolder = new CameraSurfaceHolder();
    Bitmap mScreenBitmap = null;
    Dialog dialog;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showDialog_Face() {
        dialog = new Dialog(My_Login.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_face);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_scale);
        window.setGravity(Gravity.CENTER);
        face_view = window.findViewById(R.id.face_view);
//        imageView = window.findViewById(R.id.show_image);
        setSurfaceViewCorner(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Request();
        }
        Button cancel_btn = window.findViewById(R.id.cancel_dialog);
        Button join_btn = window.findViewById(R.id.join_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                screenshot(face_view);
//登录
                Bitmap bitmap = mScreenBitmap;
                int i;
                for (i = 0; i < UserInfo_List.userInfo_List.size(); i++) {
                    UserInfo userInfo = UserInfo_List.userInfo_List.get(i);
                    FaceRequest face = new FaceRequest(My_Login.this);
                    face.setParameter(SpeechConstant.MFV_SST, "verify");
                    face.setParameter(SpeechConstant.AUTH_ID, userInfo.user_id);
                    // 设置验证模式，单一验证模式：sin
                    face.setParameter(SpeechConstant.MFV_VCM, "sin");
                    //把bitmap转换成字节数组
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    byte[] imgData = out.toByteArray();
                    face.sendRequest(imgData, mRequestListener);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);

                            if (flag_face == false) {
                                dialog.cancel();
                                ToastUtils.onErrorShowToast("查无此人");
                            }
                        } catch (Exception e) {
                        }
                    }
                }).start();
            }
        });
    }

    UserInfo Find_By_uuid(String uid) {
        for (int i = 0; i < UserInfo_List.userInfo_List.size(); i++) {
            UserInfo userInfo = UserInfo_List.userInfo_List.get(i);
            if (userInfo.uuid != null && !userInfo.uuid.equals("")) {
                if (userInfo.uuid.equals(uid)) {
                    return userInfo;
                }
            }
        }
        return null;
    }

    boolean flag_face = false;
    /**
     * 登录验证回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public void onEvent(int i, Bundle bundle) {
        }

        @Override
            public void onBufferReceived(byte[] bytes) {
            //获取数据
            String json = new String(bytes);
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (((Double) jsonObject.get("score")) > 90.0) {
                    flag_face = true;
                    String uuid = (String) jsonObject.get("uid");
                    UserInfo userInfo = null;
                    if ((userInfo = Find_By_uuid(uuid)) != null) {
                        ToastUtils.onSuccessShowToast("人脸识别成功\n" + userInfo.user_name + "，欢迎来到易学app");
                        Now_User.Set_Value(userInfo);
                        Now_User.nicheng = Now_User.User_Name;
                        Now_User.qianming = "创建你的签名";
                        Now_User.photo = userInfo.photo;
//                        System.out.println(userInfo);
                        MainActivity.login.setBackgroundResource(R.drawable.tab_menu_logout);
                        dialog.cancel();
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            System.out.println(json);

        }

        @Override
        public void onCompleted(SpeechError speechError) {
        }
    };

    /**
     * 获取相机权限的回调
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtils.onSuccessShowToast("相机权限已申请");
                showDialog_Face();
            } else {
                ToastUtils.onErrorShowToast("相机权限已被禁止,请在设置中打开");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1000);
            } else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
                mCameraSurfaceHolder.setCameraSurfaceHolder(My_Login.this, face_view);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void screenshot(SurfaceView view) {
        //需要截取的长和宽
        int outWidth = view.getWidth();
        int outHeight = view.getHeight();
        mScreenBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        PixelCopy.request(view, mScreenBitmap, new PixelCopy.OnPixelCopyFinishedListener() {
            @Override
            public void onPixelCopyFinished(int copyResult) {
            }
        }, new Handler());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setSurfaceViewCorner(final float radius) {

        face_view.setOutlineProvider(new ViewOutlineProvider() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                int leftMargin = 0;
                int topMargin = 0;
                Rect selfRect = new Rect(leftMargin, topMargin,
                        rect.right - rect.left - leftMargin,
                        rect.bottom - rect.top - topMargin);
                outline.setRoundRect(selfRect, radius);
            }
        });
        face_view.setClipToOutline(true);
    }
    /**
     * 识别的弹窗-------------------------------------------------------------------------------结束
     */
}
