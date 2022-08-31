package com.example.curriculum_design.message;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Msg_List;
import com.example.curriculum_design.DB_Help.Msg_info;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.My_KdActivity;
import com.example.curriculum_design.R;
import com.example.curriculum_design.ViewPagerScroller;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.curriculum_design.HomeFragment.convertStringToIcon;

public class MyMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button receive_btn;
    private Button sended_btn;
    private ImageView don;
    private ViewPager viewpager_message;
    ArrayList<Msg_info> msg_send_list = new ArrayList<>();
    ArrayList<Msg_info> msg_receive_list = new ArrayList<>();
    BaseAdapter receive_ada, send_ada;
    private ImageView user_photo;
    private TextView user_name;
    private Button btn_refresh;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x00) {
                msg_receive_list = new ArrayList<>();
                msg_send_list = new ArrayList<>();
                for (int i = 0; i < Msg_List.msg_list.size(); i++) {
                    Msg_info msg_info = Msg_List.msg_list.get(i);
                    if (msg_info.receive_name.equals(Now_User.User_Name)) {
                        msg_receive_list.add(msg_info);
                    }
                    if (msg_info.send_name.equals(Now_User.User_Name)) {
                        msg_send_list.add(msg_info);
                    }
                }
                send_ada.notifyDataSetChanged();
                receive_ada.notifyDataSetChanged();
                if(msg_receive_list.size()>0){
                    none_msg_receive.setVisibility(View.GONE);
                }else{
                    none_msg_receive.setVisibility(View.VISIBLE);
                }
                if(msg_send_list.size()>0){
                    none_message_send.setVisibility(View.GONE);
                }else{
                    none_message_send.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_my_message);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        initView();
        MYinit();
    }
    String get_photo(String username){
        for(int i=0;i< UserInfo_List.userInfo_List.size();i++){
            if(UserInfo_List.userInfo_List.get(i).user_name.equals(username)){
                return UserInfo_List.userInfo_List.get(i).photo;
            }
        }
        return "https://q1.qlogo.cn/g?b=qq&nk=1469199086&s=140";
    }
    ImageView none_msg_receive,none_message_send;
    void MYinit() {
        for (int i = 0; i < Msg_List.msg_list.size(); i++) {
            Msg_info msg_info = Msg_List.msg_list.get(i);
            if (msg_info.receive_name.equals(Now_User.User_Name)) {
                msg_receive_list.add(msg_info);
            }
            if (msg_info.send_name.equals(Now_User.User_Name)) {
                msg_send_list.add(msg_info);
            }
        }
        final ArrayList<View> views = new ArrayList();
        views.add(LayoutInflater.from(MyMessageActivity.this).inflate(R.layout.my_receiver, null));
        views.add(LayoutInflater.from(MyMessageActivity.this).inflate(R.layout.my_send, null));

        none_msg_receive=views.get(0).findViewById(R.id.none_message);
        none_message_send=views.get(1).findViewById(R.id.none_message);
        ViewPagerScroller scroller = new ViewPagerScroller(MyMessageActivity.this);
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(viewpager_message); //这个是设置切换过渡时间为2秒
        viewpager_message.setAdapter(new PagerAdapter() {
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
        });
        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_message.setCurrentItem(0);
            }
        });
        sended_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_message.setCurrentItem(1);
            }
        });
        viewpager_message.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//当页面滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) don.getLayoutParams();
                    float danwei_one = (float) ((viewpager_message.getWidth() - don.getWidth()));
                    params.setMargins((int) ((positionOffset + position) * (danwei_one)), 0, 0, 0);// 通过自定义坐标来放置你的控件
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
        ListView receiver_list = views.get(0).findViewById(R.id.receiver_list);
        ListView send_list = views.get(1).findViewById(R.id.send_list);
        if(msg_receive_list.size()>0){
            none_msg_receive.setVisibility(View.GONE);
        }else{
            none_msg_receive.setVisibility(View.VISIBLE);
        }
        receive_ada = new BaseAdapter() {
            @Override
            public int getCount() {
                return msg_receive_list.size();
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
            public View getView(int position, View view, ViewGroup parent) {
                view = LayoutInflater.from(MyMessageActivity.this).inflate(R.layout.message_receive_layout, parent, false);
                TextView msg_text = view.findViewById(R.id.msg_text);
                TextView xnliuyan = view.findViewById(R.id.xnliuyan);
                TextView msg_name = view.findViewById(R.id.msg_name);
                TextView send_time = view.findViewById(R.id.send_time);
                TextView huifu = view.findViewById(R.id.huifu);
                LinearLayout line = view.findViewById(R.id.line);
                ImageView msg_photo = view.findViewById(R.id.msg_photo);
                Msg_info msg_info = msg_receive_list.get(position);
                msg_name.setText(msg_info.send_name);
                msg_text.setText(msg_info.send_text);
                send_time.setText(msg_info.send_time);
                if (msg_info.huifu.equals("无回复")) {
                    line.setVisibility(View.GONE);
                    xnliuyan.setText("  通过易学教育app向你留言");
                } else {
                    huifu.setText("“" + msg_info.huifu + "”");
                    xnliuyan.setText("  回复了你");
                }
//                switch (position % 5) {
//                    case 0:
//                        msg_photo.setImageResource(R.drawable.pyq_p1);
//                        break;
//                    case 1:
//                        msg_photo.setImageResource(R.drawable.pyq_p2);
//                        break;
//                    case 2:
//                        msg_photo.setImageResource(R.drawable.pyq_p3);
//                        break;
//                    case 3:
//                        msg_photo.setImageResource(R.drawable.pyq_p4);
//                        break;
//                    case 4:
//                        msg_photo.setImageResource(R.drawable.pyq_p5);
//                        break;
//                }
                Glide.with(MyMessageActivity.this).load(get_photo(msg_info.send_name)).into(msg_photo);
                return view;
            }
        };
        receiver_list.setAdapter(receive_ada);
        receiver_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView msg_text = view.findViewById(R.id.msg_text);
                TextView msg_name = view.findViewById(R.id.msg_name);
                showDialog_ask(msg_name.getText().toString(), msg_text.getText().toString());
            }
        });
        if(msg_send_list.size()>0){
            none_message_send.setVisibility(View.GONE);
        }else{
            none_message_send.setVisibility(View.VISIBLE);
        }
        send_ada = new BaseAdapter() {
            @Override
            public int getCount() {
                return msg_send_list.size();
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
            public View getView(int position, View view, ViewGroup parent) {
                view = LayoutInflater.from(MyMessageActivity.this).inflate(R.layout.message_send_layout, parent, false);
                TextView msg_text = view.findViewById(R.id.msg_text);
                TextView rec_name = view.findViewById(R.id.rec_name);
                TextView msg_name = view.findViewById(R.id.msg_name);
                TextView send_time = view.findViewById(R.id.send_time);
                TextView huifu = view.findViewById(R.id.huifu);
                LinearLayout huifu_gone = view.findViewById(R.id.huifu_gone);
                ImageView rec_photo = view.findViewById(R.id.rec_photo);
                ImageView msg_photo = view.findViewById(R.id.msg_photo);
                Msg_info msg_info = msg_send_list.get(position);
                msg_name.setText(msg_info.send_name);
                msg_text.setText(msg_info.send_text);
                rec_name.setText(msg_info.receive_name);
                send_time.setText(msg_info.send_time);
                if (msg_info.huifu.equals("无回复")) {
                    huifu_gone.setVisibility(View.GONE);
                } else {
                    huifu.setText("“" + msg_info.huifu + "”");
                }
//                switch (position % 4) {
//                    case 0:
//                        rec_photo.setImageResource(R.drawable.tv1);
//                        break;
//                    case 1:
//                        rec_photo.setImageResource(R.drawable.tv2);
//                        break;
//                    case 2:
//                        rec_photo.setImageResource(R.drawable.tv3);
//                        break;
//                    case 3:
//                        rec_photo.setImageResource(R.drawable.tv4);
//                        break;
//                }
                Glide.with(MyMessageActivity.this).load(get_photo(msg_info.receive_name)).into(rec_photo);
                Glide.with(MyMessageActivity.this).load(Now_User.photo).into(msg_photo);
                return view;
            }
        };
        send_list.setAdapter(send_ada);
        send_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView msg_text = view.findViewById(R.id.msg_text);
                TextView msg_name = view.findViewById(R.id.msg_name);
                showDialog_ask(msg_name.getText().toString(), msg_text.getText().toString());
            }
        });

        /**
         * 侧边菜单
         */
        final DrawerLayout drawerLayout;
        final NavigationView navigationView;
        Button menu;
        drawerLayout = findViewById(R.id.activity_na);
        navigationView = findViewById(R.id.nav);
        user_name.setText(Now_User.nicheng);

//        if (Now_User.photo != null)
//            user_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
        Glide.with(MyMessageActivity.this).load(Now_User.photo).into(user_photo);
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
        View header = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        TextView head_qianming = header.findViewById(R.id.head_qianming);
        TextView head_nicheng = header.findViewById(R.id.head_nicheng);
        ImageView person_photo = header.findViewById(R.id.person_photo);

        head_qianming.setText(Now_User.qianming);
        head_nicheng.setText(Now_User.nicheng);
//        if (Now_User.photo != null)
//            person_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
        Glide.with(MyMessageActivity.this).load(Now_User.photo).into(person_photo);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(getActivity(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                if (item.getItemId() == R.id.ip_change) {
                    showDialog_IP();
                }
                if (item.getItemId() == R.id.my_kuaidi) {
                    Intent intent = new Intent(MyMessageActivity.this, My_KdActivity.class);
                    MyMessageActivity.this.startActivity(intent);
                }
                if (item.getItemId() == R.id.exit_yao) {
                    MyMessageActivity.this.finish();
                }
                if (item.getItemId() == R.id.my_message) {
                    Intent intent = new Intent(MyMessageActivity.this, MyMessageActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.about_us) {
                    showDialog_About();
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DbHelper.Query_Msg();
                            handler.sendEmptyMessageDelayed(0x00, 100);
                        } catch (Exception e) {

                        }
                    }
                }).start();
                // 按钮旋转
                final RotateAnimation animation = new RotateAnimation(0.0f, 180.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(300);
                btn_refresh.startAnimation(animation);
            }
        });
        btn_refresh.performClick();
    }

    public void showDialog_IP() {
        final Dialog dialog = new Dialog(MyMessageActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_ip);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.chat_Scale);
        window.setGravity(Gravity.CENTER);
        final EditText edt_address = window.findViewById(R.id.edit_address);
        SharedPreferences sp = MyMessageActivity.this.getSharedPreferences("ip_address", Context.MODE_PRIVATE);
        edt_address.setText(sp.getString("ip_add", ""));
        Button cancel_btn = window.findViewById(R.id.cancel_dialog);
        Button join_btn = window.findViewById(R.id.join_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyMessageActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                IP.file_ip=edt_address.getText().toString();
                Toast.makeText(MyMessageActivity.this, "已切换此IP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        receive_btn = (Button) findViewById(R.id.receive_btn);
        sended_btn = (Button) findViewById(R.id.sended_btn);
        don = (ImageView) findViewById(R.id.don);
        viewpager_message = (ViewPager) findViewById(R.id.viewpager_message);
        receive_btn.setOnClickListener(this);
        sended_btn.setOnClickListener(this);
        user_photo = (ImageView) findViewById(R.id.user_photo);
        user_photo.setOnClickListener(this);
        user_name = (TextView) findViewById(R.id.user_name);
        user_name.setOnClickListener(this);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
    }

    public void showDialog_About() {
        final Dialog dialog = new Dialog(MyMessageActivity.this);
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

    public void showDialog_liuyan(final String s_name, final String huifu) {
        final Dialog dialog = new Dialog(MyMessageActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_message_liuyan);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        TextView txt_receive_name = window.findViewById(R.id.txt_receive_name);
        Button send_btn = window.findViewById(R.id.send_btn);
        Button cancel_btn_pl = window.findViewById(R.id.cancel_btn_pl);
        final EditText edt_send_text = window.findViewById(R.id.send_text);
        cancel_btn_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        txt_receive_name.setText(s_name);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Msg_info msg_info = new Msg_info();
                msg_info.send_name = Now_User.User_Name;
                msg_info.send_text = edt_send_text.getText().toString();
                msg_info.receive_name = s_name;
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String date = sDateFormat.format(new Date());
                msg_info.send_time = date;
                msg_info.huifu = huifu;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.Insert_Msg(msg_info);
                    }
                }).start();
                dialog.cancel();
                Toast.makeText(MyMessageActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog_ask(final String s_name, final String huifu) {
        final Dialog dialog = new Dialog(MyMessageActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_msg_ask);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button huifu_btn = window.findViewById(R.id.huifu);
        Button delete_btn = window.findViewById(R.id.delete);
        Button cancel = window.findViewById(R.id.cancel);
        huifu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (s_name.equals(Now_User.User_Name)) {
                    Toast.makeText(MyMessageActivity.this, "发送界面不可回复", Toast.LENGTH_SHORT).show();
                } else
                    showDialog_liuyan(s_name, huifu);
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < Msg_List.msg_list.size(); i++) {
                    Msg_info msg_info = Msg_List.msg_list.get(i);
                    if (msg_info.send_text.equals(huifu)) {
                        Msg_List.msg_list.remove(msg_info);
                    }
                }
                msg_receive_list = new ArrayList<>();
                msg_send_list = new ArrayList<>();
                for (int i = 0; i < Msg_List.msg_list.size(); i++) {
                    Msg_info msg_info = Msg_List.msg_list.get(i);
                    if (msg_info.receive_name.equals(Now_User.User_Name)) {
                        msg_receive_list.add(msg_info);
                    }
                    if (msg_info.send_name.equals(Now_User.User_Name)) {
                        msg_send_list.add(msg_info);
                    }
                }
                receive_ada.notifyDataSetChanged();
                send_ada.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.receive_btn:
                break;
            case R.id.sended_btn:
                break;
        }
    }
}