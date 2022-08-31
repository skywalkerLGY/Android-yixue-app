package com.example.curriculum_design.mykcb;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.curriculum_design.LXR_More;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;

import java.util.Random;

public class KCBActivity extends AppCompatActivity{

    LinearLayout left_line;
    private RelativeLayout monday;
    private RelativeLayout tuesday;
    private RelativeLayout wednesday;
    private RelativeLayout thursday;
    private RelativeLayout friday;
    KCBHelper kcbHelper;
    int item_height;
    private Button add_course_btn;
    Button goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_kcb);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        kcbHelper = new KCBHelper(this);
        initView();
        MyInit();
    }

    void MyInit() {
        Display display = getWindowManager().getDefaultDisplay();
        int height_pm = display.getHeight();
        int width_pm = display.getWidth();
        item_height = height_pm / 10;
        left_line = findViewById(R.id.left_view_layout);
        for (int i = 1; i <= 12; i++) {
            View view_left = LayoutInflater.from(KCBActivity.this).inflate(R.layout.left_view, null);
            TextView left_num = view_left.findViewById(R.id.class_number_text);
            left_num.setText(i + "");
            left_line.addView(view_left);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width_pm / 12, item_height);
            view_left.setLayoutParams(params);
        }
        Cursor cursor = kcbHelper.select();
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.kc_name = cursor.getString(cursor.getColumnIndex("kc_name"));
            course.kc_teacher = cursor.getString(cursor.getColumnIndex("kc_teacher"));
            course.kc_zhou = cursor.getString(cursor.getColumnIndex("kc_zhou"));
            course.kc_start = cursor.getString(cursor.getColumnIndex("kc_start"));
            course.kc_end = cursor.getString(cursor.getColumnIndex("kc_end"));
            course.kc_location = cursor.getString(cursor.getColumnIndex("kc_location"));
            add_Course_ui(course);
        }
        add_course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void initView() {
        monday = (RelativeLayout) findViewById(R.id.monday);
        tuesday = (RelativeLayout) findViewById(R.id.tuesday);
        wednesday = (RelativeLayout) findViewById(R.id.wednesday);
        thursday = (RelativeLayout) findViewById(R.id.thursday);
        friday = (RelativeLayout) findViewById(R.id.friday);
        add_course_btn = (Button) findViewById(R.id.add_course_btn);
        goback=findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    void add_Course_ui(final Course course) {
        RelativeLayout now_rel = null;
        switch (course.kc_zhou) {
            case "一":
                now_rel = monday;
                break;
            case "二":
                now_rel = tuesday;
                break;
            case "三":
                now_rel = wednesday;
                break;
            case "四":
                now_rel = thursday;
                break;
            case "五":
                now_rel = friday;
                break;
        }
        View view_course = LayoutInflater.from(KCBActivity.this).inflate(R.layout.course_view, null);
        int c_height = item_height * (Integer.valueOf(course.kc_end) - Integer.valueOf(course.kc_start)+1);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, c_height);
        view_course.setLayoutParams(params);
        view_course.setY((Integer.valueOf(course.kc_start) -1)* item_height);
        final TextView c_name=view_course.findViewById(R.id.course_name);
        TextView course_location=view_course.findViewById(R.id.course_location);
        CardView cardView=view_course.findViewById(R.id.cardview);
        Random random = new Random();
        int r=random.nextInt(9)+1;
        if(r==1) cardView.setCardBackgroundColor(0xFF3F51B5);
        if(r==2) cardView.setCardBackgroundColor(0xFFFF5722);
        if(r==3) cardView.setCardBackgroundColor(0xFFE91E63);
        if(r==4) cardView.setCardBackgroundColor(0xFFFF9800);
        if(r==5) cardView.setCardBackgroundColor(0xFF009688);
        if(r==6) cardView.setCardBackgroundColor(0xFF795548);
        if(r==7) cardView.setCardBackgroundColor(0xFFE91E63);
        if(r==8) cardView.setCardBackgroundColor(0xFF795548);
        if(r==9) cardView.setCardBackgroundColor(0xFF607D8B);

        c_name.setText(course.kc_name);
        course_location.setText(course.kc_location);

        now_rel.addView(view_course);
        view_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KCBActivity.this,KcbMoreActivity.class);
                intent.putExtra("kc_name", course.kc_name);
                intent.putExtra("kc_location", course.kc_location);
                intent.putExtra("kc_start", course.kc_start);
                intent.putExtra("kc_end", course.kc_end);
                intent.putExtra("kc_zhou", course.kc_zhou);
                intent.putExtra("kc_teacher", course.kc_teacher);
                startActivity(intent);
            }
        });
        view_course.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog_delete(course);
                return true;
            }
        });
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(KCBActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_add_course);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button btn_add=window.findViewById(R.id.insert_btn);
        final EditText course_name=window.findViewById(R.id.course_name);
        final EditText teacher_name=window.findViewById(R.id.teacher_name);
        final EditText class_room=window.findViewById(R.id.class_room);
        final Spinner zhou,start,end;
        zhou=window.findViewById(R.id.week);
        start=window.findViewById(R.id.classes_begin);
        end=window.findViewById(R.id.classes_ends);

        String[] zhous = {"一","二","三","四","五"};
        ArrayAdapter<String> spinnerAdapter_zhou = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, zhous);
        zhou.setAdapter(spinnerAdapter_zhou);

        final String[] starts = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> spinnerAdapter_start = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, starts);
        start.setAdapter(spinnerAdapter_start);

        String[] ends = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> spinnerAdapter_end = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, ends);
        end.setAdapter(spinnerAdapter_end);

        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course course=new Course();
                course.kc_name = course_name.getText().toString();
                course.kc_teacher = teacher_name.getText().toString();
                course.kc_zhou = (String) zhou.getSelectedItem();
                course.kc_start = (String) start.getSelectedItem();
                course.kc_end = (String) end.getSelectedItem();
                course.kc_location = class_room.getText().toString();
                if(course.kc_start.compareTo(course.kc_end)>0){
                    Toast.makeText(KCBActivity.this,"结束不能小于开始",Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.cancel();
                    add_Course_ui(course);
                    kcbHelper.insert(course);
                }

            }
        });
    }
    public void showDialog_delete(final Course course) {
        final Dialog dialog = new Dialog(KCBActivity.this);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_delete_kc);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button delete_btn = window.findViewById(R.id.delete);
        Button cancel = window.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kcbHelper.delete(course.kc_name, course.kc_zhou);
                removeall();
                Cursor cursor = kcbHelper.select();
                while (cursor.moveToNext()) {
                    Course course = new Course();
                    course.kc_name = cursor.getString(cursor.getColumnIndex("kc_name"));
                    course.kc_teacher = cursor.getString(cursor.getColumnIndex("kc_teacher"));
                    course.kc_zhou = cursor.getString(cursor.getColumnIndex("kc_zhou"));
                    course.kc_start = cursor.getString(cursor.getColumnIndex("kc_start"));
                    course.kc_end = cursor.getString(cursor.getColumnIndex("kc_end"));
                    course.kc_location = cursor.getString(cursor.getColumnIndex("kc_location"));
                    add_Course_ui(course);
                }
                Toast.makeText(KCBActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
    void removeall(){
        monday.removeAllViews();
        tuesday.removeAllViews();
        wednesday.removeAllViews();
        thursday.removeAllViews();
        friday.removeAllViews();
    }
    @Override
    protected void onResume() {
        super.onResume();
        removeall();
        Cursor cursor = kcbHelper.select();
        while (cursor.moveToNext()) {
            Course course = new Course();
            course.kc_name = cursor.getString(cursor.getColumnIndex("kc_name"));
            course.kc_teacher = cursor.getString(cursor.getColumnIndex("kc_teacher"));
            course.kc_zhou = cursor.getString(cursor.getColumnIndex("kc_zhou"));
            course.kc_start = cursor.getString(cursor.getColumnIndex("kc_start"));
            course.kc_end = cursor.getString(cursor.getColumnIndex("kc_end"));
            course.kc_location = cursor.getString(cursor.getColumnIndex("kc_location"));
            add_Course_ui(course);
        }
    }
}