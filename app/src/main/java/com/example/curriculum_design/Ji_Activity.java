package com.example.curriculum_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.curriculum_design.DB_Help.Now_User;
import com.githang.statusbar.StatusBarCompat;

import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class Ji_Activity extends AppCompatActivity implements CityPickerListener {

    private CityPicker cityPicker;
    TextView mshow;
    Button btn_baocun,btn_back;
    EditText ji_name,ji_phone,more_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_ji);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        cityPicker = new CityPicker(Ji_Activity.this,this);
        mshow=findViewById(R.id.myshow);
        mshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.show();
            }
        });
        ji_name=findViewById(R.id.ji_name);
        ji_name.setText(Now_User.User_Name);
        ji_phone=findViewById(R.id.ji_phone);
        ji_phone.setText(Now_User.user_phone);
        more_address=findViewById(R.id.more_address);
        btn_baocun=findViewById(R.id.btn_baocun);
        btn_back=findViewById(R.id.go_back);
        btn_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Ji_Activity.this,KD_Activity.class);
                intent.putExtra("activity","寄件信息");
                intent.putExtra("ji_name",ji_name.getText().toString());
                intent.putExtra("ji_phone",ji_phone.getText().toString());
                intent.putExtra("ji_address",mshow.getText().toString()+more_address.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
    }

    @Override
    public void getCity(String s) {
        mshow.setText(s);
    }
    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()) {
            cityPicker.close();
            return;
        }
        super.onBackPressed();

    }
}