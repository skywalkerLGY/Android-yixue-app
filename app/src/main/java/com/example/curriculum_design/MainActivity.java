package com.example.curriculum_design;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.ChatRoom.Static_Msg;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Init;
import com.example.curriculum_design.DB_Help.InitSql;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.PyqList;
import com.example.curriculum_design.DB_Help.Pyq_Info;
import com.example.curriculum_design.DB_Help.UserInfo;
import com.example.curriculum_design.Library.BookActivity;
import com.example.curriculum_design.Lock.LockScreenService;
import com.example.curriculum_design.Lock.Static_LOCK;
import com.example.curriculum_design.Word_recite.SoundPoolUtil;
import com.example.curriculum_design.Word_recite.Word_List;
import com.githang.statusbar.StatusBarCompat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mengpeng.mphelper.ToastUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static RadioButton btn_home, btn_my, btn_message, btn_find;
    RadioGroup rd_group_bottom;
    static Button login;
    HomeFragment homeFragment;
    MessageFragment messageFragment;
    FindFragment findFragment;
    MyFragment myFragment;
    FragmentManager fragmentManager;
    TextView msg_sum;


    int sum1 = 0;
    public static Context context_this;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x00) {
                showDialog_About();
            }
        }
    };
    static String namename = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("new_user", Context.MODE_PRIVATE);


        if (sp.getBoolean("is_new_user", true) == true) {
            Message message = new Message();
            message.what = 0x00;
            handler.sendMessageDelayed(message, 2000);
            SharedPreferences sp1 = getSharedPreferences("new_user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp1.edit();
            editor.putBoolean("is_new_user", false);
            editor.commit();
        }
        context_this = MainActivity.this;
        startLockScreen();
        Static_LOCK.flag = true;
        SoundPoolUtil.getInstance(MainActivity.this);//预加载音频文件
        Word_List.Init(MainActivity.this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        InitBTN();
        fragmentManager = getSupportFragmentManager();
        Set_Check_Click();
        btn_home.setChecked(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
//                    InitSql.Init_Sql();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        UserInfo userInfo = new UserInfo();
        userInfo = (UserInfo) ObjectSaveUtils.getObject(MainActivity.this, "nowuser1");

        if (userInfo != null) {
            Now_User.Set_Value(userInfo);
            if (userInfo.flag == false) {
                Now_User.Flag = false;
                login.setBackgroundResource(R.drawable.tab_menu_login);
            } else {
                login.setBackgroundResource(R.drawable.tab_menu_logout);
            }
        }
        UserInfo userInfo1 = (UserInfo) ObjectSaveUtils.getObject(MainActivity.this, "Temp_User3");
        if (userInfo1 != null && Now_User.User_Name != null) {
            if (userInfo1.user_name.equals(Now_User.User_Name)) {
                Now_User.nicheng = userInfo1.nicheng;
                Now_User.qianming = userInfo1.qianming;
            }
        }

        ArrayList<Pyq_Info> pyq_list = null;
        pyq_list = (ArrayList<Pyq_Info>) ObjectSaveUtils.getObject(MainActivity.this, "pyq_list");
        if (pyq_list != null) {
            PyqList.list = pyq_list;
        }
        msg_sum = findViewById(R.id.msg_sum);
        msg_sum.setVisibility(View.INVISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Static_Msg.message_sum > 0) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                msg_sum.setVisibility(View.VISIBLE);
                                if (Static_Msg.message_sum > 99) {
                                    msg_sum.setText("99");
                                } else
                                    msg_sum.setText(Static_Msg.message_sum + "");
                            }
                        });
                    }
                }
            }
        }).start();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            manager.createNotificationChannel(new NotificationChannel("gjy", "gjy", NotificationManager.IMPORTANCE_LOW));
            Notification notification = new Notification.Builder(MainActivity.this, "gjy")
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.drawable.app)
                    .setContentTitle(null)
                    .setContentText("成为更好的自己")
                    .setContentIntent(PendingIntent.getActivity(MainActivity.this, 0, new Intent(), 0))
                    .setAutoCancel(true)
                    .build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            manager.notify(1, notification);
        }
    }

    void InitBTN() {
        btn_home = findViewById(R.id.home_btn);
        btn_my = findViewById(R.id.my_btn);
        btn_message = findViewById(R.id.message_btn);
        btn_find = findViewById(R.id.find_btn);
        rd_group_bottom = findViewById(R.id.rd_group_bottom);
        login = findViewById(R.id.login);
    }

    void Hide_All(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null)
            fragmentTransaction.hide(homeFragment);
        if (messageFragment != null)
            fragmentTransaction.hide(messageFragment);
        if (findFragment != null)
            fragmentTransaction.hide(findFragment);
        if (myFragment != null)
            fragmentTransaction.hide(myFragment);
    }

    void Set_Check_Click() {
        rd_group_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (Now_User.Flag == false) {
                    btn_home.setChecked(true);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (i == R.id.home_btn) {
                        Hide_All(fragmentTransaction);
                        homeFragment = new HomeFragment();
                        fragmentTransaction.add(R.id.framelayout, homeFragment);
                        fragmentTransaction.commit();
                    } else {
                        Intent intent = new Intent(MainActivity.this, My_Login.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.login_in, R.anim.null_out);
                    }
                } else {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Hide_All(fragmentTransaction);
                    switch (i) {
                        case R.id.home_btn:
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.framelayout, homeFragment);
                            break;
                        case R.id.message_btn:
                            msg_sum.setVisibility(View.INVISIBLE);
                            Static_Msg.message_sum = 0;
                            messageFragment = new MessageFragment();
                            fragmentTransaction.add(R.id.framelayout, messageFragment);
                            break;
                        case R.id.find_btn:
                            findFragment = new FindFragment();
                            fragmentTransaction.add(R.id.framelayout, findFragment);
                            break;
                        case R.id.my_btn:
                            myFragment = new MyFragment();
                            fragmentTransaction.add(R.id.framelayout, myFragment);
                            break;
                    }
                    fragmentTransaction.commit();
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Now_User.Flag == false) {//应该跳出登录
                    login.setSelected(true);
                    Intent intent = new Intent(MainActivity.this, My_Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.login_in, R.anim.null_out);
                } else {//应该注销当前用户
                    Now_User.Flag = false;
                    btn_home.setChecked(true);
                    login.setBackgroundResource(R.drawable.tab_menu_login);
                }
            }
        });
        login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ViewGroup.LayoutParams params = login.getLayoutParams();
                params.width = params.height = (sum1 % 2 == 1) ? 220 : 150;
                login.getBackground().setAlpha((sum1 % 2 == 1) ? 255 : 150);
                sum1++;
                login.setLayoutParams(params);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InitSql.Init_Sql();
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InitSql.Init_Sql();
            }
        }).start();
        UserInfo userInfo = new UserInfo();
        userInfo.flag = Now_User.Flag;
        userInfo.user_name = Now_User.User_Name;
        userInfo.user_phone = Now_User.user_phone;
        userInfo.photo = Now_User.photo;
        userInfo.user_sex = Now_User.user_sex;
        userInfo.user_birth = Now_User.user_birth;
        userInfo.user_qq = Now_User.user_qq;
        userInfo.user_pass = Now_User.user_pass;
        userInfo.user_id = Now_User.User_id;
        userInfo.user_school_number = Now_User.user_school_number;
        ObjectSaveUtils.saveObject(MainActivity.this, "nowuser1", userInfo);

        UserInfo userInfo1 = new UserInfo();
        userInfo1.nicheng = Now_User.nicheng;
        userInfo1.qianming = Now_User.qianming;
        userInfo1.user_name = Now_User.User_Name;
        ObjectSaveUtils.saveObject(MainActivity.this, "Temp_User3", userInfo1);
        ArrayList<Pyq_Info> pyq_list = PyqList.list;
        ObjectSaveUtils.saveObject(MainActivity.this, "pyq_list", pyq_list);
    }

    public void showDialog_About() {
        final Dialog dialog = new Dialog(MainActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_about);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.about_dialog);
        window.setGravity(Gravity.CENTER);
    }

    static Intent intent_lock;

    /**
     * 开启锁屏服务
     */
    public static void startLockScreen() {
        intent_lock = new Intent(context_this, LockScreenService.class);
        context_this.startService(intent_lock);
    }

    /**
     * 关闭锁屏服务
     */
    public static void stopLockScreen() {
        context_this.stopService(intent_lock);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //==是否扫到内容
            if (result.getContents() != null) {
                if (result.getContents().contains("http")) {
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("web_url", result.getContents());
                    startActivity(intent);
                } else {
                    if (result.getContents().contains("-")) {
                        String[] informations = result.getContents().split("-");
                        Intent intent = new Intent(MainActivity.this, LXR_More.class);
                        intent.putExtra("name", informations[0]);
                        intent.putExtra("qq", informations[1]);
                        intent.putExtra("phone", informations[2]);
                        intent.putExtra("school_id", informations[3]);
                        intent.putExtra("sex", informations[4]);
                        startActivity(intent);
                    } else
                        Toast.makeText(MainActivity.this, "扫描结果：" + result.getContents(), Toast.LENGTH_SHORT).show();
                }
            } else {
                ToastUtils.onErrorShowToast("取消扫码");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}