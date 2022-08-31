package com.example.curriculum_design.Find;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.Pyq_Info;
import com.example.curriculum_design.FindFragment;
import com.example.curriculum_design.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Send_Pyq extends AppCompatActivity {

    Button send,goback;
    EditText send_text,send_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_send__pyq);


        final String send_name=getIntent().getStringExtra("send_name");
        send=findViewById(R.id.send);
        goback=findViewById(R.id.goback);
        send_text=findViewById(R.id.send_text);
        send_time=findViewById(R.id.send_time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        send_time.setText(simpleDateFormat.format(date));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Pyq_Info pyq_info=new Pyq_Info();
                pyq_info.pyq_text=send_text.getText().toString();
                pyq_info.pyq_photo="0";
                pyq_info.pyq_name=send_name;
                pyq_info.pyq_time=send_time.getText().toString();
                pyq_info.qq= Now_User.user_qq;
                Toast.makeText(Send_Pyq.this, "发表成功", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        DbHelper.Insert_Pyq(pyq_info);
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            // 隐藏软键盘
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                        overridePendingTransition(R.anim.null_in, R.anim.login_out);
                    }
                }).start();
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
    }
}