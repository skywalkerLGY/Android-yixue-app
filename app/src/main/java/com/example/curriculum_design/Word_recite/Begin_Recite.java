package com.example.curriculum_design.Word_recite;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.curriculum_design.ChatRoom.ObjectSaveUtils;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Begin_Recite extends AppCompatActivity implements TextToSpeech.OnInitListener{

    //背单词的界面

    private ImageView select_tf;
    private TextView show_word;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn_pre;
    private Button btn_next;
    Button shoucang;
    int randNum1, randNum2, randNum3;
    boolean flag_true = false;
    SoundPoolUtil soundPoolUtil = SoundPoolUtil.getInstance(Begin_Recite.this);
    Word_Helper word_helper = new Word_Helper(Begin_Recite.this);
    private Button goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_begin__recite);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);//黑色状态栏


        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }

        initView();

        show_word.setText(Word_List.english_list.get(Word_List.now_position_4));
        int length = Word_List.english_list.size() - 1;
        Random rand = new Random();

        randNum1 = rand.nextInt(length);
        randNum2 = rand.nextInt(length);
        randNum3 = rand.nextInt(length);
        int i = rand.nextInt(4);
        set_word(i);

        final String finalMWay = mWay;
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_true == false) {
                    Toast.makeText(Begin_Recite.this, "请选择正确的选项", Toast.LENGTH_SHORT).show();
                } else {
                    setall_back();
                    Word_List.now_position_4++;
                    show_word.setText(Word_List.english_list.get(Word_List.now_position_4));
                    int length = Word_List.english_list.size() - 1;
                    Random rand = new Random();

                    randNum1 = rand.nextInt(length);
                    randNum2 = rand.nextInt(length);
                    randNum3 = rand.nextInt(length);
                    int i = rand.nextInt(4);
                    set_word(i);
                    select_tf.setVisibility(View.INVISIBLE);
                    flag_true = false;


                    textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    textToSpeech.speak(show_word.getText().toString(),
                            TextToSpeech.QUEUE_FLUSH, null);
                    for(int j=0;j<7;j++){
                        if(Static_Week.week_name[j].equals(finalMWay)){
                            Static_Week.week_sum[j]++;
                        }
                    }
                    final Calendar c = Calendar.getInstance();
                    c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                    final int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
                    Static_Month.month[mDay-1]=true;
                }

            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setall_back();
                Word_List.now_position_4--;
                show_word.setText(Word_List.english_list.get(Word_List.now_position_4));
                int length = Word_List.english_list.size() - 1;
                Random rand = new Random();
                randNum1 = rand.nextInt(length);
                randNum2 = rand.nextInt(length);
                randNum3 = rand.nextInt(length);
                int i = rand.nextInt(4);
                set_word(i);
                select_tf.setVisibility(View.INVISIBLE);
                flag_true = false;

                textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                textToSpeech.speak(show_word.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH, null);
                for(int j=0;j<7;j++){
                    if(Static_Week.week_name[j].equals(finalMWay)){
                        Static_Week.week_sum[j]--;
                    }
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setall_back();
                if (!btn1.getText().toString().equals(Word_List.chinese_list.get(Word_List.now_position_4))) {
                    btn1.setBackgroundResource(R.drawable.select_false);
                    soundPoolUtil.play(3);
                    flag_true = false;
                } else {
                    btn1.setBackgroundResource(R.drawable.select_true);
                    soundPoolUtil.play(2);
                    flag_true = true;
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setall_back();
                if (!btn2.getText().toString().equals(Word_List.chinese_list.get(Word_List.now_position_4))) {
                    btn2.setBackgroundResource(R.drawable.select_false);
                    soundPoolUtil.play(3);
                    flag_true = false;
                } else {
                    btn2.setBackgroundResource(R.drawable.select_true);
                    soundPoolUtil.play(2);
                    flag_true = true;
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setall_back();
                if (!btn3.getText().toString().equals(Word_List.chinese_list.get(Word_List.now_position_4))) {
                    btn3.setBackgroundResource(R.drawable.select_false);
                    soundPoolUtil.play(3);
                    flag_true = false;
                } else {
                    btn3.setBackgroundResource(R.drawable.select_true);
                    soundPoolUtil.play(2);
                    flag_true = true;
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setall_back();
                if (!btn4.getText().toString().equals(Word_List.chinese_list.get(Word_List.now_position_4))) {
                    btn4.setBackgroundResource(R.drawable.select_false);
                    soundPoolUtil.play(3);
                    flag_true = false;
                } else {
                    btn4.setBackgroundResource(R.drawable.select_true);
                    soundPoolUtil.play(2);
                    flag_true = true;
                }
            }
        });
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(word_helper.Container(Word_List.english_list.get(Word_List.now_position_4))==true){
                    Toast.makeText(Begin_Recite.this, "该词已收藏", Toast.LENGTH_SHORT).show();
                }else{
                    word_helper.insert("CET4/6", Word_List.english_list.get(Word_List.now_position_4), Word_List.chinese_list.get(Word_List.now_position_4));
                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                soundPoolUtil.play(1);
                overridePendingTransition(R.anim.null_in, R.anim.login_out);

            }
        });
    }

    void setall_back() {
        btn1.setBackgroundResource(R.drawable.cebian_menu);
        btn2.setBackgroundResource(R.drawable.cebian_menu);
        btn3.setBackgroundResource(R.drawable.cebian_menu);
        btn4.setBackgroundResource(R.drawable.cebian_menu);
    }

    private void initView() {
        select_tf = (ImageView) findViewById(R.id.select_tf);
        show_word = (TextView) findViewById(R.id.show_word);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn_pre = (Button) findViewById(R.id.btn_pre);
        btn_next = (Button) findViewById(R.id.btn_next);
        shoucang = findViewById(R.id.shoucang);
        goback = (Button) findViewById(R.id.goback);

        textToSpeech = new TextToSpeech(this, this);
    }

    void set_word(int i) {
        switch (i) {
            case 0:
                btn1.setText(Word_List.chinese_list.get(randNum1));
                btn2.setText(Word_List.chinese_list.get(randNum2));
                btn3.setText(Word_List.chinese_list.get(randNum3));
                btn4.setText(Word_List.chinese_list.get(Word_List.now_position_4));
                break;
            case 1:
                btn1.setText(Word_List.chinese_list.get(randNum1));
                btn2.setText(Word_List.chinese_list.get(randNum2));
                btn4.setText(Word_List.chinese_list.get(randNum3));
                btn3.setText(Word_List.chinese_list.get(Word_List.now_position_4));
                break;
            case 2:
                btn1.setText(Word_List.chinese_list.get(randNum1));
                btn4.setText(Word_List.chinese_list.get(randNum2));
                btn3.setText(Word_List.chinese_list.get(randNum3));
                btn2.setText(Word_List.chinese_list.get(Word_List.now_position_4));
                break;
            case 3:
                btn4.setText(Word_List.chinese_list.get(randNum1));
                btn2.setText(Word_List.chinese_list.get(randNum2));
                btn3.setText(Word_List.chinese_list.get(randNum3));
                btn1.setText(Word_List.chinese_list.get(Word_List.now_position_4));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Int_46 int_46 = new Int_46();
        int_46.pos_4 = Word_List.now_position_4;
        int_46.pos_6 = Word_List.now_position_6;
        ObjectSaveUtils.saveObject(Begin_Recite.this, "int_46", int_46);

        save_week saveWeek = new save_week();
        for(int j=0;j<7;j++){
            saveWeek.week_sum[j]=Static_Week.week_sum[j];
        }
        ObjectSaveUtils.saveObject(Begin_Recite.this, "save_week1",saveWeek);

        save_month saveMonth = new save_month();
        for(int j=0;j<31;j++){
            saveMonth.month[j]=Static_Month.month[j];
        }
        ObjectSaveUtils.saveObject(Begin_Recite.this, "save_month",saveMonth);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Int_46 int_46 = new Int_46();
        int_46 = (Int_46) ObjectSaveUtils.getObject(Begin_Recite.this, "int_46");
        if (int_46 != null) {
            if (int_46.pos_4 != -1 && int_46.pos_4 != 0)
                Word_List.now_position_4 = int_46.pos_4;
            if (int_46.pos_6 != -1 && int_46.pos_6 != 0)
                Word_List.now_position_6 = int_46.pos_6;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
    private TextToSpeech textToSpeech; // TTS对象
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
            textToSpeech.setPitch(1.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.speak(show_word.getText().toString(),
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}