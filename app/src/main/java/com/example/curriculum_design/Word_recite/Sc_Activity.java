package com.example.curriculum_design.Word_recite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

public class Sc_Activity extends AppCompatActivity implements View.OnClickListener {
    private ListView sc_list;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    Word_Helper word_helper = new Word_Helper(Sc_Activity.this);
    private TextView word_sum;
    private Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_sc);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);//黑色状态栏

        initView();
        cursor = word_helper.query();
        word_sum.setText(word_helper.getlength() + "");
        adapter = new SimpleCursorAdapter(Sc_Activity.this,
                R.layout.sc_list_layout, cursor,
                new String[]{"name", "english_word", "chinese_word"},
                new int[]{R.id.name, R.id.english_word, R.id.chinese_word}, 0);
        sc_list.setAdapter(adapter);
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
        sc_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Button tanchu = view.findViewById(R.id.tanchu);
                Button close = view.findViewById(R.id.close);
                final LinearLayout linearLayout = view.findViewById(R.id.line_vis);
                final TextView english_view = view.findViewById(R.id.english_word);
                if (linearLayout.getVisibility() == View.INVISIBLE) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.INVISIBLE);
                }
                tanchu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        word_helper.delete(english_view.getText().toString());
                        cursor = word_helper.query();
                        adapter = new SimpleCursorAdapter(Sc_Activity.this,
                                R.layout.sc_list_layout, cursor,
                                new String[]{"name", "english_word", "chinese_word"},
                                new int[]{R.id.name, R.id.english_word, R.id.chinese_word}, 0);
                        sc_list.setAdapter(adapter);
                        linearLayout.setVisibility(View.INVISIBLE);
                        word_sum.setText(word_helper.getlength() + "");
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayout.setVisibility(View.INVISIBLE);
                    }
                });
                return true;
            }
        });
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.null_in, R.anim.login_out);
            }
        });
    }

    private void initView() {
        sc_list = (ListView) findViewById(R.id.sc_list);
        word_sum = (TextView) findViewById(R.id.word_sum);
        go_back = (Button) findViewById(R.id.go_back);
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