package com.example.curriculum_design.mykcb;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

public class KcbMoreActivity extends AppCompatActivity {

    private Button goback;
    private TextView kc_name_txt;
    private TextView ke;
    private EditText class_location;
    private EditText zhou;
    private EditText class_teacher;
    private Button goto_chat;
    private Button btn_submit;
    KCBHelper kcbHelper;
    Course course;
    private EditText start;
    private EditText end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_kcb_more);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        kcbHelper = new KCBHelper(KcbMoreActivity.this);
        Intent intent = getIntent();
        course = new Course();
        course.kc_name = intent.getStringExtra("kc_name");
        course.kc_location = intent.getStringExtra("kc_location");
        course.kc_start = intent.getStringExtra("kc_start");
        course.kc_end = intent.getStringExtra("kc_end");
        course.kc_zhou = intent.getStringExtra("kc_zhou");
        course.kc_teacher = intent.getStringExtra("kc_teacher");
        initView();
    }

    private void initView() {
        goback = (Button) findViewById(R.id.goback);
        kc_name_txt =  findViewById(R.id.kc_name);
        ke = (TextView) findViewById(R.id.ke);
        class_location = (EditText) findViewById(R.id.class_location);
        zhou = (EditText) findViewById(R.id.zhou);
        class_teacher = (EditText) findViewById(R.id.class_teacher);
        goto_chat = (Button) findViewById(R.id.goto_chat);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        start = (EditText) findViewById(R.id.start);
        end = (EditText) findViewById(R.id.end);

        kc_name_txt.setText(course.kc_name);
        class_location.setText(course.kc_location);
        zhou.setText(course.kc_zhou);
        start.setText(course.kc_start);
        end.setText(course.kc_end);
        class_teacher.setText(course.kc_teacher);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.kc_name=kc_name_txt.getText().toString();
                course.kc_zhou=zhou.getText().toString();
                course.kc_location=class_location.getText().toString();
                course.kc_start=start.getText().toString();
                course.kc_end=end.getText().toString();
                course.kc_teacher=class_teacher.getText().toString();
                kcbHelper.update(course);
                finish();
            }
        });
    }
}