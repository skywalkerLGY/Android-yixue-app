package com.example.curriculum_design.Word_recite;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

public class Word_Recite extends AppCompatActivity implements View.OnClickListener {

    private ImageView siliuji_image;
    private TextView txt_siliuji;
    private TextView yixue;
    private ProgressBar progress_bar_h;
    private TextView plan_txt;
    private TextView wancheng;
    private TextView zimu;
    private TextView beison;
    private Button begin_to_recite;
    Button my_sc;
    private TextView progress_txt;
    private Button history_record;
    private EditText search_word;
    private ListView search_list;
    private Button daka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_word__recite);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);//黑色状态栏

        initView();
        SoundPoolUtil soundPoolUtil = SoundPoolUtil.getInstance(Word_Recite.this);
        soundPoolUtil.play(1);
    }

    private void initView() {
        siliuji_image = (ImageView) findViewById(R.id.siliuji_image);
        txt_siliuji = (TextView) findViewById(R.id.txt_siliuji);
        yixue = (TextView) findViewById(R.id.yixue);
        progress_bar_h = (ProgressBar) findViewById(R.id.progress_bar_h);
        plan_txt = (TextView) findViewById(R.id.plan_txt);
        wancheng = (TextView) findViewById(R.id.wancheng);
        zimu = (TextView) findViewById(R.id.zimu);
        beison = (TextView) findViewById(R.id.beison);
        begin_to_recite = (Button) findViewById(R.id.begin_to_recite);
        search_list = (ListView) findViewById(R.id.search_list);
        begin_to_recite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Word_Recite.this, Begin_Recite.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        my_sc = findViewById(R.id.my_sc);
        my_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Word_Recite.this, Sc_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        int show = (int) (100 * (Word_List.now_position_4 / (Word_List.english_list.size() + 0.0)));
        progress_bar_h.setProgress(show);
        progress_txt = (TextView) findViewById(R.id.progress_txt);
        progress_txt.setText(Word_List.now_position_4 + "/" + Word_List.english_list.size());
        plan_txt.setText(Now_User.User_Name + "的今日计划");
        String s = Word_List.english_list.get(Word_List.now_position_4);
        zimu.setText(s.substring(0, 1).toUpperCase());
        history_record = (Button) findViewById(R.id.history_record);
        history_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Word_Recite.this, History_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        search_word = (EditText) findViewById(R.id.search_word);
//        search_list.setVisibility(View.INVISIBLE);
        search_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final ArrayList<String> english = new ArrayList<>();
                final ArrayList<String> chinese = new ArrayList<>();
                System.out.println(s + "------------------");
                for (int i = 0; i < Word_List.english_list.size(); i++) {

                    if (!TextUtils.isEmpty(search_word.getText()) && Word_List.english_list.get(i).contains(s)) {
                        english.add(Word_List.english_list.get(i));
                        chinese.add(Word_List.chinese_list.get(i));
                    }
                }
                search_list.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return english.size();
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
                    public View getView(int position, View convertView, ViewGroup parent) {
                        convertView = LayoutInflater.from(Word_Recite.this).inflate(R.layout.search_lst_layout, parent, false);
                        TextView english_txt = convertView.findViewById(R.id.english_word);
                        TextView chinese_txt = convertView.findViewById(R.id.chinese_word);
                        english_txt.setText(english.get(position));
                        chinese_txt.setText(chinese.get(position));
                        return convertView;
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        daka = (Button) findViewById(R.id.daka);
        daka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Word_Recite.this, DaKa_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Int_46 int_46 = new Int_46();
        int_46.pos_4 = Word_List.now_position_4;
        int_46.pos_6 = Word_List.now_position_6;
        ObjectSaveUtils.saveObject(Word_Recite.this, "int_46", int_46);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Int_46 int_46 = new Int_46();
        int_46 = (Int_46) ObjectSaveUtils.getObject(Word_Recite.this, "int_46");
        if (int_46 != null) {
            if (int_46.pos_4 != -1 && int_46.pos_4 != 0)
                Word_List.now_position_4 = int_46.pos_4;
            if (int_46.pos_6 != -1 && int_46.pos_6 != 0)
                Word_List.now_position_6 = int_46.pos_6;
        }
        save_week saveWeek = null;
        saveWeek = (save_week) ObjectSaveUtils.getObject(Word_Recite.this, "save_week1");
        if (saveWeek != null) {
            for (int i = 0; i < 7; i++) {
                Static_Week.week_sum[i] = saveWeek.week_sum[i];
            }
        }
        save_month saveMonth=null;
        saveMonth= (save_month) ObjectSaveUtils.getObject(Word_Recite.this, "save_month");
        if (saveMonth != null) {
            for (int i = 0; i < 31; i++) {
                Static_Month.month[i] = saveMonth.month[i];
            }
        }
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_record:

                break;
            case R.id.daka:
                break;
        }
    }
}