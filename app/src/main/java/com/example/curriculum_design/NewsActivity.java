package com.example.curriculum_design;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.githang.statusbar.StatusBarCompat;

public class NewsActivity extends AppCompatActivity {

    private TextView title;
    private ImageView images1;
    private TextView image_text;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        initView();
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        Glide.with(NewsActivity.this).load(intent.getStringExtra("images1")).into(images1);
        content.setText(intent.getStringExtra("content"));
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        images1 = (ImageView) findViewById(R.id.images1);
        image_text = (TextView) findViewById(R.id.image_text);
        content = (TextView) findViewById(R.id.content);
    }
}