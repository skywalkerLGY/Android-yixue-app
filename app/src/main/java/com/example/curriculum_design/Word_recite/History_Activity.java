package com.example.curriculum_design.Word_recite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

public class History_Activity extends AppCompatActivity implements View.OnClickListener {

    private Button go_back;
    private TextView title2;
    private ImageView image1;
    private RelativeLayout title;
    private TextView word_sum;
    private LinearLayout line0;
    private LinearLayout line1;
    private ListView sc_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_history);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);//黑色状态栏

        initView();
        sc_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return Word_List.now_position_4+1;
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
                convertView= LayoutInflater.from(History_Activity.this).inflate(R.layout.sc_list_layout, parent,false);
                TextView name=convertView.findViewById(R.id.name);
                TextView english=convertView.findViewById(R.id.english_word);
                TextView chinese=convertView.findViewById(R.id.chinese_word);
                name.setText("CET4/6");
                english.setText(Word_List.english_list.get(position));
                chinese.setText(Word_List.chinese_list.get(position));
                return convertView;
            }
        });
        sc_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View hidden_btn = view.findViewById(R.id.hidden_btn);
                if (hidden_btn.getVisibility() == View.VISIBLE)
                    hidden_btn.setVisibility(View.INVISIBLE);
                else if (hidden_btn.getVisibility() == View.INVISIBLE)
                    hidden_btn.setVisibility(View.VISIBLE);
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
        word_sum.setText(Word_List.now_position_4+"个单词");
    }

    private void initView() {
        go_back = (Button) findViewById(R.id.go_back);
        title2 = (TextView) findViewById(R.id.title2);
        image1 = (ImageView) findViewById(R.id.image1);
        title = (RelativeLayout) findViewById(R.id.title);
        word_sum = (TextView) findViewById(R.id.word_sum);
        line0 = (LinearLayout) findViewById(R.id.line0);
        line1 = (LinearLayout) findViewById(R.id.line1);
        sc_list = (ListView) findViewById(R.id.sc_list);

        go_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back:

                break;
        }
    }
}