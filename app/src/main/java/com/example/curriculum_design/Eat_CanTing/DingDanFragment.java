package com.example.curriculum_design.Eat_CanTing;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.curriculum_design.LXR_More;
import com.example.curriculum_design.R;
import com.example.curriculum_design.ViewPagerScroller;

import java.util.ArrayList;

public class DingDanFragment extends Fragment {

    View view;
    ListView dingdan_ing_list, dingdan_over_list;
    ViewPager viewPager;
    private RelativeLayout title;
    private Button ing_btn;
    private Button over_btn;
    private LinearLayout top;
    private ImageView don;
    private ViewPager viewpager_dingdan;
    Save_DingDan ing_list=new Save_DingDan();
    Save_DingDan over_list=new Save_DingDan();
    BaseAdapter ing_Adapter,over_Adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ding_dan, container, false);
        MyInit();
        return view;
    }

    void MyInit() {
        ing_btn=view.findViewById(R.id.ing_btn);
        over_btn=view.findViewById(R.id.over_btn);
        final ArrayList<View> views = new ArrayList();
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.ct_ing_dingdan, null));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.ct_over_dingdan, null));

        viewPager = view.findViewById(R.id.viewpager_dingdan);
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(viewPager); //这个是设置切换过渡时间为2秒
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(views.get(position));
            }
        });
        ing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        over_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        final ImageView don = view.findViewById(R.id.don);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//当页面滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) don.getLayoutParams();
                    float danwei_one = (float) ((viewPager.getWidth() - don.getWidth()));
                    params.setMargins((int) ((positionOffset + position) * (danwei_one)), 0, 0, 0);// 通过自定义坐标来放置你的控件
                    don.setLayoutParams(params);
                }
            }

            @Override//当页面选中时
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        for(int i=0;i<Static_DingDan.time.size();i++){
            if (Static_DingDan.status.get(i) == true) {
                over_list.time.add(Static_DingDan.time.get(i));
                over_list.status.add(Static_DingDan.status.get(i));
                over_list.eat_sum.add(Static_DingDan.eat_sum.get(i));
                over_list.eat_money.add(Static_DingDan.eat_money.get(i));
                over_list.eat_name.add(Static_DingDan.eat_name.get(i));
                over_list.eat_image.add(Static_DingDan.eat_image.get(i));
            }else{
                ing_list.time.add(Static_DingDan.time.get(i));
                ing_list.status.add(Static_DingDan.status.get(i));
                ing_list.eat_sum.add(Static_DingDan.eat_sum.get(i));
                ing_list.eat_money.add(Static_DingDan.eat_money.get(i));
                ing_list.eat_name.add(Static_DingDan.eat_name.get(i));
                ing_list.eat_image.add(Static_DingDan.eat_image.get(i));
            }
        }
        dingdan_over_list = views.get(1).findViewById(R.id.list_over_dingdan);
        over_Adapter=new BaseAdapter() {

            @Override
            public int getCount() {
                return over_list.time.size();
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.ct_dingdan_lst_layout, parent, false);
                View btn_haoping=convertView.findViewById(R.id.btn_haoping);
                btn_haoping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Static_DingDan.time.size();i++){
                            if(Static_DingDan.eat_name.get(i).equals(over_list.eat_name.get(position))&&
                                    Static_DingDan.time.get(i).equals(over_list.time.get(position))){
                                Static_DingDan.hp.set(i,true);
                            }
                        }
                        Toast.makeText(getActivity(), "已好评", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView dd_time=convertView.findViewById(R.id.dd_time);
                TextView money=convertView.findViewById(R.id.dd_money_sum);
                TextView dd_name=convertView.findViewById(R.id.dd_name);
                ImageView dd_image=convertView.findViewById(R.id.dd_image);
                dd_time.setText(over_list.time.get(position));
                dd_name.setText(over_list.eat_name.get(position));
                dd_image.setImageResource(over_list.eat_image.get(position));
                money.setText("￥"+over_list.eat_money.get(position)+"\n共"+over_list.eat_sum.get(position)+"件");
                return convertView;
            }
        };
        dingdan_over_list.setAdapter(over_Adapter);
        dingdan_ing_list = views.get(0).findViewById(R.id.list_ing_dingdan);
        ing_Adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return ing_list.time.size();
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.ct_dingdan_ing_lst_layout, parent, false);
                View btn_quereng=convertView.findViewById(R.id.btn_quereng);
                btn_quereng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Static_DingDan.time.size();i++){
                            if(Static_DingDan.eat_name.get(i).equals(ing_list.eat_name.get(position))&&
                                    Static_DingDan.time.get(i).equals(ing_list.time.get(position))){
                                Static_DingDan.status.set(i,true);
                            }
                        }
                        over_list.time.add(ing_list.time.get(position));
                        over_list.status.add(ing_list.status.get(position));
                        over_list.eat_sum.add(ing_list.eat_sum.get(position));
                        over_list.eat_money.add(ing_list.eat_money.get(position));
                        over_list.eat_name.add(ing_list.eat_name.get(position));
                        over_list.eat_image.add(ing_list.eat_image.get(position));
                        ing_list.time.remove(position);
                        ing_list.status.remove(position);
                        ing_list.eat_sum.remove(position);
                        ing_list.eat_money.remove(position);
                        ing_list.eat_name.remove(position);
                        ing_list.eat_image.remove(position);
                        notifyDataSetChanged();
                        over_Adapter.notifyDataSetChanged();
                    }
                });
                View btn_haoping=convertView.findViewById(R.id.btn_haoping);
                btn_haoping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0;i<Static_DingDan.time.size();i++){
                            if(Static_DingDan.eat_name.get(i).equals(ing_list.eat_name.get(position))&&
                                    Static_DingDan.time.get(i).equals(ing_list.time.get(position))){
                                Static_DingDan.hp.set(i,true);
                            }
                        }
                        Toast.makeText(getActivity(), "已好评", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView dd_time=convertView.findViewById(R.id.dd_time);
                TextView money=convertView.findViewById(R.id.dd_money_sum);
                TextView dd_name=convertView.findViewById(R.id.dd_name);
                ImageView dd_image=convertView.findViewById(R.id.dd_image);
                dd_time.setText(ing_list.time.get(position));
                dd_name.setText(ing_list.eat_name.get(position));
                dd_image.setImageResource(ing_list.eat_image.get(position));
                money.setText("￥"+ing_list.eat_money.get(position)+"\n共"+ing_list.eat_sum.get(position)+"件");
                return convertView;
            }
        };
        dingdan_ing_list.setAdapter(ing_Adapter);
        dingdan_ing_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dd_time=view.findViewById(R.id.dd_time);
                TextView dd_name=view.findViewById(R.id.dd_name);
                int pos=0;
                int pos_ing=0;
                for(int i=0;i<Static_DingDan.time.size();i++){
                    if(Static_DingDan.eat_name.get(i).equals(dd_name.getText().toString())&&
                            Static_DingDan.time.get(i).equals(dd_time.getText().toString())){
                        pos=i;
                    }
                }
                for(int i=0;i<ing_list.time.size();i++){
                    if(ing_list.eat_name.get(i).equals(dd_name.getText().toString())&&
                            ing_list.time.get(i).equals(dd_time.getText().toString())){
                        pos_ing=i;
                    }
                }
                showDialog_ING(pos, pos_ing);
                return true;
            }
        });
        dingdan_over_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dd_time=view.findViewById(R.id.dd_time);
                TextView dd_name=view.findViewById(R.id.dd_name);
                int pos=0;
                int pos_over=0;
                for(int i=0;i<Static_DingDan.time.size();i++){
                    if(Static_DingDan.eat_name.get(i).equals(dd_name.getText().toString())&&
                            Static_DingDan.time.get(i).equals(dd_time.getText().toString())){
                        pos=i;
                    }
                }
                for(int i=0;i<over_list.time.size();i++){
                    if(over_list.eat_name.get(i).equals(dd_name.getText().toString())&&
                            over_list.time.get(i).equals(dd_time.getText().toString())){
                        pos_over=i;
                    }
                }
                showDialog_Over(pos, pos_over);
                return true;
            }
        });
        dingdan_ing_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dd_time=view.findViewById(R.id.dd_time);
                TextView dd_name=view.findViewById(R.id.dd_name);
                int pos=0;
                for(int i=0;i<Static_DingDan.time.size();i++){
                    if(Static_DingDan.eat_name.get(i).equals(dd_name.getText().toString())&&
                            Static_DingDan.time.get(i).equals(dd_time.getText().toString())){
                        pos=i;
                    }
                }
                Intent intent=new Intent(getActivity(),More_Info_Activity.class);
                intent.putExtra("position", pos);
                startActivity(intent);
            }
        });
        dingdan_over_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dd_time=view.findViewById(R.id.dd_time);
                TextView dd_name=view.findViewById(R.id.dd_name);
                int pos=0;
                for(int i=0;i<Static_DingDan.time.size();i++){
                    if(Static_DingDan.eat_name.get(i).equals(dd_name.getText().toString())&&
                            Static_DingDan.time.get(i).equals(dd_time.getText().toString())){
                        pos=i;
                    }
                }
                Intent intent=new Intent(getActivity(),More_Info_Activity.class);
                intent.putExtra("position", pos);
                startActivity(intent);
            }
        });
    }
    public void showDialog_ING(final int pos_all, final int pos_ing) {
        final Dialog dialog = new Dialog(getActivity());
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button delete_btn =  window.findViewById(R.id.delete);
        Button cancel_btn =  window.findViewById(R.id.cancel);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static_DingDan.time.remove(pos_all);
                Static_DingDan.eat_image.remove(pos_all);
                Static_DingDan.eat_sum.remove(pos_all);
                Static_DingDan.eat_name.remove(pos_all);
                Static_DingDan.status.remove(pos_all);
                Static_DingDan.eat_money.remove(pos_all);

                ing_list.time.remove(pos_ing);
                ing_list.eat_image.remove(pos_ing);
                ing_list.eat_sum.remove(pos_ing);
                ing_list.eat_name.remove(pos_ing);
                ing_list.status.remove(pos_ing);
                ing_list.eat_money.remove(pos_ing);
                ing_Adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "已删除", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "已取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
    public void showDialog_Over(final int pos_all, final int pos_over) {
        final Dialog dialog = new Dialog(getActivity());
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_delete);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.lxr_dialog);
        window.setGravity(Gravity.BOTTOM);
        Button delete_btn =  window.findViewById(R.id.delete);
        Button cancel_btn =  window.findViewById(R.id.cancel);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Static_DingDan.time.remove(pos_all);
                Static_DingDan.eat_image.remove(pos_all);
                Static_DingDan.eat_sum.remove(pos_all);
                Static_DingDan.eat_name.remove(pos_all);
                Static_DingDan.status.remove(pos_all);
                Static_DingDan.eat_money.remove(pos_all);

                over_list.time.remove(pos_over);
                over_list.eat_image.remove(pos_over);
                over_list.eat_sum.remove(pos_over);
                over_list.eat_name.remove(pos_over);
                over_list.status.remove(pos_over);
                over_list.eat_money.remove(pos_over);
                over_Adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "已删除", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "已取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
}