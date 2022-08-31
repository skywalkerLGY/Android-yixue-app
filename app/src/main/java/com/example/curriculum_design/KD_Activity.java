package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import com.example.curriculum_design.ChatRoom.ChatRoom;
import com.example.curriculum_design.DB_Help.KD_List;
import com.example.curriculum_design.DB_Help.Kd_information;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.Static_KD;
import com.example.curriculum_design.search.PoiCitySearchDemo;
import com.githang.statusbar.StatusBarCompat;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class KD_Activity extends AppCompatActivity{

    Button btn_yuyue,btn_back;
    RelativeLayout ji_btn,shou_btn;
    static String ji_name,ji_phone,ji_address;
    static String shou_name,shou_phone,shou_address;
    TextView t_ji_name,t_ji_phone,t_ji_address;
    TextView t_shou_name,t_shou_phone,t_shou_address;
    String flag="123";//true代表寄件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_kd);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);

        Intent intent=getIntent();
        if(intent.getStringExtra("activity")!=null&&
                !intent.getStringExtra("activity").equals("")&&
                intent.getStringExtra("activity").equals("寄件信息")){
            ji_name=intent.getStringExtra("ji_name");
            ji_phone=intent.getStringExtra("ji_phone");
            ji_address=intent.getStringExtra("ji_address");
            flag="寄件信息";
        }else if(intent.getStringExtra("activity")!=null&&
                !intent.getStringExtra("activity").equals("")&&
                intent.getStringExtra("activity").equals("收件信息")){
            shou_name=intent.getStringExtra("shou_name");
            shou_phone=intent.getStringExtra("shou_phone");
            shou_address=intent.getStringExtra("shou_address");
            flag="收件信息";
        }
        Init();
    }
    void Init(){
        t_ji_name=findViewById(R.id.ji_name);
        t_ji_address=findViewById(R.id.ji_address);
        t_ji_phone=findViewById(R.id.ji_phone);
        t_shou_name=findViewById(R.id.shou_name);
        t_shou_phone=findViewById(R.id.shou_phone);
        t_shou_address=findViewById(R.id.shou_address);
        if(flag.equals("123")) {
            t_ji_name.setText(Now_User.User_Name);
            t_ji_phone.setText(Now_User.user_phone);
            t_shou_name.setText(Now_User.User_Name);
            t_shou_phone.setText(Now_User.user_phone);
        }else{
            t_ji_name.setText(ji_name);
            t_ji_address.setText(ji_address);
            t_ji_phone.setText(ji_phone);
            t_shou_name.setText(shou_name);
            t_shou_address.setText(shou_address);
            t_shou_phone.setText(shou_phone);
        }
        btn_yuyue=findViewById(R.id.btn_yuyue);
        btn_back=findViewById(R.id.go_back);
        btn_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_KD.ji_name=t_ji_name.getText().toString();
                Static_KD.ji_phone=t_ji_phone.getText().toString();
                Static_KD.ji_address=t_ji_address.getText().toString();
                Static_KD.shou_name=t_shou_name.getText().toString();
                Static_KD.shou_phone=t_shou_phone.getText().toString();
                Static_KD.shou_address=t_shou_address.getText().toString();
                Intent it = new Intent(KD_Activity.this, PoiCitySearchDemo.class);
                startActivity(it);
                finish();
                overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        ji_btn=findViewById(R.id.ji_btn);
        ji_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KD_Activity.this,Ji_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        shou_btn=findViewById(R.id.shou_btn);
        shou_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KD_Activity.this,Shou_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
    }
}