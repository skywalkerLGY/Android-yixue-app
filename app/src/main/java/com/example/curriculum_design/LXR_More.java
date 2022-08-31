package com.example.curriculum_design;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Msg_info;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.Tools.createQRCodeBitmap;
import com.githang.statusbar.StatusBarCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LXR_More extends AppCompatActivity implements View.OnClickListener {

    String s_name, s_qq, s_phone, s_school_id, s_sex;
    private RelativeLayout rel1;
    private TextView name;
    private ImageView sex,user_img;
    private Button dianhua_icon, btn_qq;
    private Button duanxin_icon, btn_phone, share_erweima;
    private TextView tvtv;
    private TextView qq;
    private TextView school_id;
    private RelativeLayout rel3;
    private Button btn_liuyan;
    private Button goback;
    String Select_UserPhoto_By_QQ(String qq){
        for(int i = 0; i< UserInfo_List.userInfo_List.size(); i++){
            if(qq.equals(UserInfo_List.userInfo_List.get(i).user_qq)){
                return UserInfo_List.userInfo_List.get(i).photo;
            }
        }
        return "123";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_lxr_more);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);

        initView();
        Intent it = getIntent();
        s_name = it.getStringExtra("name");
        s_qq = it.getStringExtra("qq");
        s_phone = it.getStringExtra("phone");
        s_school_id = it.getStringExtra("school_id");
        s_sex = it.getStringExtra("sex");
        if (s_qq != null) {
            name.setText(s_name);
            btn_phone.setText(s_phone);
            btn_qq.setText(s_qq);
            school_id.setText(s_school_id);
            if (s_sex.equals("男")) {
                sex.setImageResource(R.drawable.male_icon);
            } else
                sex.setImageResource(R.drawable.female_icon);
            Glide.with(LXR_More.this).load(Select_UserPhoto_By_QQ(s_qq)).into(user_img);
        }
        if (s_name.equals("YAO校园客服") || s_name.equals("测试员")) {
            name.setText(s_name);
            btn_phone.setText("18651700819");
            s_phone = "18651700819";
            btn_qq.setText("2474590974");
            s_qq = "2474590974";
            school_id.setText("3030184104");
        }
        dianhua_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s_phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        duanxin_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri smsUri = Uri.parse("smsto:" + s_phone);//指定发送人
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                intent.putExtra("sms_body", "你好我是" + Now_User.User_Name);//指定发送内容
                startActivity(intent);
            }
        });
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s_phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btn_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String qqnum = s_qq;
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqnum;//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Toast.makeText(LXR_More.this, "正在跳转至与" + s_name + "的QQ聊天", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LXR_More.this, "请检查是否安装QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        share_erweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void initView() {
        rel1 = (RelativeLayout) findViewById(R.id.rel1);
        name = (TextView) findViewById(R.id.name);
        sex = (ImageView) findViewById(R.id.sex);
        user_img = (ImageView) findViewById(R.id.user_img);
        dianhua_icon = (Button) findViewById(R.id.dianhua_icon);
        duanxin_icon = (Button) findViewById(R.id.duanxin_icon);
        tvtv = (TextView) findViewById(R.id.tvtv);
        qq = (TextView) findViewById(R.id.qq);
        school_id = (TextView) findViewById(R.id.school_id);
        rel3 = (RelativeLayout) findViewById(R.id.rel3);
        btn_phone = findViewById(R.id.btn_phone);
        btn_qq = findViewById(R.id.btn_qq);
        share_erweima = findViewById(R.id.share_erweima);
        btn_liuyan = (Button) findViewById(R.id.btn_liuyan);
        btn_liuyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog_liuyan();
            }
        });
        goback = (Button) findViewById(R.id.goback);
        goback.setOnClickListener(this);
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(LXR_More.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_lxr_erweima);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button cancel_btn = window.findViewById(R.id.quxiao);
        Button join_btn = window.findViewById(R.id.quedin);

        ImageView imageView=window.findViewById(R.id.erweima);
        user_img.setDrawingCacheEnabled(true);
        Bitmap logobitmap = Bitmap.createBitmap(user_img.getDrawingCache());
        user_img.setDrawingCacheEnabled(false);
        Bitmap bitmap= createQRCodeBitmap.createQRCodeBitmap(s_name+"-"+s_qq+"-"+s_phone+"-"+s_school_id+"-"+s_sex,
                800, 800,"UTF-8","H",
                "1", Color.BLACK, Color.WHITE,logobitmap,20);
        imageView.setImageBitmap(bitmap);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void showDialog_liuyan() {
        final Dialog dialog = new Dialog(LXR_More.this);
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
        txt_receive_name.setText(name.getText());
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Msg_info msg_info = new Msg_info();
                msg_info.send_name = Now_User.User_Name;
                msg_info.send_text = edt_send_text.getText().toString();
                msg_info.receive_name = name.getText().toString();
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                String date = sDateFormat.format(new Date());
                msg_info.send_time = date;
                msg_info.huifu = "无回复";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.Insert_Msg(msg_info);
                    }
                }).start();
                dialog.cancel();
                Toast.makeText(LXR_More.this, "发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback:
                finish();
                break;
        }
    }
}