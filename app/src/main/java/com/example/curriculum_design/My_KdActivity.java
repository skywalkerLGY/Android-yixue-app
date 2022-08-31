package com.example.curriculum_design;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.KD_List;
import com.example.curriculum_design.DB_Help.Kd_information;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.search.PoiCitySearchDemo;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

import static com.example.curriculum_design.MessageFragment.convertStringToIcon;

public class My_KdActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jijian;
    private Button saoma;
    private Button dizhipu;
    private Button fuwuwangdian;
    private ImageView user_photo;
    private TextView user_name;
    private TextView user_qianming;
    private ListView my_kd_list, search_kd_list;
    ArrayList<Kd_information> kd_list = new ArrayList<>();
    ArrayList<Kd_information> find_kd_list = new ArrayList<>();
    private EditText search_edt;
    searchAdapter searchAdapter;
    private Button go_back;
    ImageView none_kd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_my_kd);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.login_color), false);
        initView();
        user_photo=  findViewById(R.id.user_photo);
        user_name=findViewById(R.id.user_name);
        user_name.setText(Now_User.nicheng);
//        if (Now_User.photo != null) {
////            user_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
//
//        }
        Glide.with(My_KdActivity.this).load(Now_User.photo).into(user_photo);
        for (int i = 0; i < KD_List.kd_list.size(); i++) {
            Kd_information kd_information = KD_List.kd_list.get(i);
            if (Now_User.User_Name.equals(kd_information.ji_name) ||
                    Now_User.User_Name.equals(kd_information.shou_name)) {
                kd_list.add(kd_information);
            }
        }
        if(kd_list.size()>0){
            none_kd.setVisibility(View.GONE);
        } else{
            none_kd.setVisibility(View.VISIBLE);
        }
        my_kd_list.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return kd_list.size();
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
                view = LayoutInflater.from(My_KdActivity.this).inflate(R.layout.my_kd_lst_layout, parent, false);
                TextView word_name = view.findViewById(R.id.word_name);
                TextView shou_name = view.findViewById(R.id.shou_name);
                TextView ji_name = view.findViewById(R.id.ji_name);
                TextView shou_address = view.findViewById(R.id.shou_address);
                TextView kd_status = view.findViewById(R.id.kd_status);
                TextView ji_address = view.findViewById(R.id.ji_address);
                TextView yundaohao = view.findViewById(R.id.yundaohao);
                String ji_string = "xx", shou_string = "xx";

                Kd_information kd_information = new Kd_information();
                kd_information = kd_list.get(position);
                String[] sp_ji = kd_information.ji_address.split(" ");
                String[] sp_shou = kd_information.shou_address.split(" ");
//                if (kd_information.shou_address.contains("省") && kd_information.shou_address.contains("市")
//                        && kd_information.ji_address.contains("省") && kd_information.ji_address.contains("市")) {
//                    String[] sp_ji = kd_information.ji_address.split("省");
//                    String[] sp_shou = kd_information.shou_address.split("省");
//                    String[] sp_ji1 = sp_ji[1].split("市");v
//                    String[] sp_shou1 = sp_shou[1].split("市");
//
//                }
                ji_string = sp_ji[1];
                shou_string = sp_shou[1];
                word_name.setText(kd_information.worker_name);
                shou_name.setText(kd_information.shou_name);
                ji_name.setText(kd_information.ji_name);
                shou_address.setText(shou_string);
                kd_status.setText(kd_information.kd_status);
                ji_address.setText(ji_string);
                yundaohao.setText(kd_information.kd_id);
                return view;
            }
        });
    }

    private void initView() {
        jijian = (Button) findViewById(R.id.jijian);
        saoma = (Button) findViewById(R.id.saoma);
        dizhipu = (Button) findViewById(R.id.dizhipu);
        fuwuwangdian = (Button) findViewById(R.id.fuwuwangdian);
        user_photo = (ImageView) findViewById(R.id.user_photo);
        user_name = (TextView) findViewById(R.id.user_name);
        user_qianming = (TextView) findViewById(R.id.user_qianming);
        my_kd_list = findViewById(R.id.my_kd_list);
        search_kd_list = findViewById(R.id.search_kd_list);
        none_kd=findViewById(R.id.none_kd);
        jijian.setOnClickListener(this);
        saoma.setOnClickListener(this);
        dizhipu.setOnClickListener(this);
        fuwuwangdian.setOnClickListener(this);
        search_edt = (EditText) findViewById(R.id.search_edt);
        find_kd_list = new ArrayList<>();

        for (int i = 0; i < KD_List.kd_list.size(); i++) {
            Kd_information kd_information = KD_List.kd_list.get(i);
            if (kd_information.kd_id.contains(search_edt.getText().toString())) {
                find_kd_list.add(kd_information);
            }
        }
        searchAdapter = new searchAdapter();
        search_kd_list.setAdapter(searchAdapter);
        search_edt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                find_kd_list = new ArrayList<>();
                for (int i = 0; i < KD_List.kd_list.size(); i++) {
                    Kd_information kd_information = KD_List.kd_list.get(i);
                    if (kd_information.kd_id.contains(s)) {
                        find_kd_list.add(kd_information);
                    }
                }
                if (find_kd_list.size() > 0) {
                    search_kd_list.setVisibility(View.VISIBLE);
                    searchAdapter.notifyDataSetChanged();
                }
                if (TextUtils.isEmpty(search_edt.getText())) {
                    search_kd_list.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jijian:
                Intent it = new Intent(My_KdActivity.this, KD_Activity.class);
                startActivity(it);
                overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
                break;
            case R.id.saoma:
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(My_KdActivity.this, "请检查微信是否正常", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dizhipu:

                break;
            case R.id.fuwuwangdian:
                Intent it_zhandian = new Intent(My_KdActivity.this, PoiCitySearchDemo.class);
                startActivity(it_zhandian);
                overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
                break;
            case R.id.go_back:
                finish();
                break;
        }
    }

    class searchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return find_kd_list.size();
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
            view = LayoutInflater.from(My_KdActivity.this).inflate(R.layout.search_kd_layout, parent, false);
            TextView word_name = view.findViewById(R.id.word_name);
            TextView shou_name = view.findViewById(R.id.shou_name);
            TextView ji_name = view.findViewById(R.id.ji_name);
            TextView shou_address = view.findViewById(R.id.shou_address);
            TextView kd_status = view.findViewById(R.id.kd_status);
            TextView ji_address = view.findViewById(R.id.ji_address);
            TextView yundaohao = view.findViewById(R.id.yundaohao);
            String ji_string = "xx", shou_string = "xx";

            Kd_information kd_information = new Kd_information();
            kd_information = find_kd_list.get(position);
            if (kd_information.shou_address.contains("省") && kd_information.shou_address.contains("市")
                    && kd_information.ji_address.contains("省") && kd_information.ji_address.contains("市")) {
                String[] sp_ji = kd_information.ji_address.split("省");
                String[] sp_shou = kd_information.shou_address.split("省");
                String[] sp_ji1 = sp_ji[1].split("市");
                String[] sp_shou1 = sp_shou[1].split("市");
                ji_string = sp_ji1[0];
                shou_string = sp_shou1[0];
            }
            word_name.setText(kd_information.worker_name);
            shou_name.setText(kd_information.shou_name);
            ji_name.setText(kd_information.ji_name);
            shou_address.setText(shou_string + "市");
            kd_status.setText(kd_information.kd_status);
            ji_address.setText(ji_string + "市");
            yundaohao.setText(kd_information.kd_id);
            return view;
        }
    }

}