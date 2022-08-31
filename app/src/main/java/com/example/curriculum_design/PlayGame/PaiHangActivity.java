package com.example.curriculum_design.PlayGame;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

public class PaiHangActivity extends AppCompatActivity {

    VideoView play_back;
    private LinearLayout line0;
    private ListView list_paihang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        getWindow().setFlags(                           //去除状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pai_hang);
        initView();
        play_back = this.findViewById(R.id.play_back);
//        String uri = "android.resource://" + getPackageName() + "/" + R.raw.play_back;
        String uri="https://yaoblog.obs.cn-east-3.myhuaweicloud.com:443/%E8%AF%BE%E7%A8%8B%E8%AE%BE%E8%AE%A1/play.mp4";
        play_back.setVideoURI(Uri.parse(uri));
        play_back.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
                mp.start();
            }
        });
    }

    private void initView() {
        line0 = (LinearLayout) findViewById(R.id.line0);
        list_paihang = (ListView) findViewById(R.id.list_paihang);
        list_paihang.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return Games_List.names.size();
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
                view= LayoutInflater.from(PaiHangActivity.this).inflate(R.layout.list_paihang_layout, parent,false);
                TextView name,across,speed,mingci;
                mingci=view.findViewById(R.id.mingci);
                name=view.findViewById(R.id.name);
                across=view.findViewById(R.id.across);
                speed=view.findViewById(R.id.speed);
                name.setText(Games_List.names.get(position));
                across.setText(Games_List.across.get(position));
                speed.setText(Games_List.speeds.get(position));
                mingci.setText((position+1)+"");
                return view;
            }
        });
    }
}