package com.example.curriculum_design.message;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.ChatRoom.ChatRoom;
import com.example.curriculum_design.DB_Help.Chat_List;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.My_KdActivity;
import com.example.curriculum_design.R;
import com.example.curriculum_design.ViewPagerScroller;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.mengpeng.mphelper.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.curriculum_design.Eat_CanTing.DianCanFragment.convertStringToIcon;

public class TestStackAdapter extends StackAdapter<Integer> {

    static ArrayList<View> views;
    static Context context;
    ViewPager viewPager_msg;
    int ccc = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            viewPager_msg.setCurrentItem(ccc % 3);
        }
    };

    public TestStackAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        ItemViewHolder h = (ItemViewHolder) holder;
        h.onBind(data, position);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ItemViewHolder(view);
    }

    class ItemViewHolder extends CardStackView.ViewHolder {
        NoScrollViewPager mLayout;

        public ItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.viewpager_qt);
            mLayout.setScrollable(false);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(Integer data, int position) {
            if (position == 0) {
                Set_One(mLayout);
            }
            if (position == 1) {
                Set_Two(mLayout);
            }
            if (position == 2) {
                Set_Three(mLayout);
            }
            if (position == 3) {
                Set_Four(mLayout);
            }
        }

    }
    void Set_One(NoScrollViewPager mLayout){
        views = new ArrayList<View>();
        views.add(LayoutInflater.from(context).inflate(R.layout.lay1, null));
        mLayout.setAdapter(new MyPagerAdapter(views, context));
        Button zhankai;
        final RelativeLayout rel_java;
        final TextView icon;
        ImageView qun_logo;
        zhankai = views.get(0).findViewById(R.id.zhankai);
        rel_java = views.get(0).findViewById(R.id.rel_java);
        icon = views.get(0).findViewById(R.id.zhankai_icon);
        qun_logo = views.get(0).findViewById(R.id.qun_logo);
        RelativeLayout.LayoutParams params_image = (RelativeLayout.LayoutParams) qun_logo.getLayoutParams();
        params_image.width = params_image.height;
        qun_logo.setLayoutParams(params_image);
        final int height=rel_java.getHeight();
        zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_java.getVisibility() == View.VISIBLE) {
                    HiddenAnimUtils.newInstance(context, rel_java, new View(context), 60).toggle();
                    icon.setBackgroundResource(R.drawable.zhankai_off);
                } else if (rel_java.getVisibility() == View.GONE) {
                    HiddenAnimUtils.newInstance(context, rel_java, new View(context), 60).toggle();
                    icon.setBackgroundResource(R.drawable.zhankai);
                }
            }
        });

        Button qun_btn1=views.get(0).findViewById(R.id.qun_btn1);
        Button qun_btn2=views.get(0).findViewById(R.id.qun_btn2);
        class Mylistener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        }
        qun_btn1.setOnClickListener(new Mylistener());
        qun_btn2.setOnClickListener(new Mylistener());
    }
    void Set_Two(NoScrollViewPager mLayout){
        views = new ArrayList<View>();
        views.add(LayoutInflater.from(context).inflate(R.layout.lay2, null));
        mLayout.setAdapter(new MyPagerAdapter(views, context));
        Button btn_message=views.get(0).findViewById(R.id.my_message_btn);
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyMessageActivity.class);
                context.startActivity(intent);
            }
        });

    }
    void Set_Three(NoScrollViewPager mLayout){
        views = new ArrayList<View>();
        views.add(LayoutInflater.from(context).inflate(R.layout.lay3, null));
        Button btn_my_kd=views.get(0).findViewById(R.id.my_kd_btn);
        final Intent intent = new Intent(context, My_KdActivity.class);
        btn_my_kd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                try {
                                    DbHelper.Query_KD();
                                }catch (Exception e){
                                }
                            }
                        }).start();
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        context.startActivity(intent);
                    }
                }).start();

            }
        });
        mLayout.setAdapter(new MyPagerAdapter(views, context));
    }
    void Set_Four(NoScrollViewPager mLayout){
        views = new ArrayList<View>();
        views.add(LayoutInflater.from(context).inflate(R.layout.lay4, null));
        mLayout.setAdapter(new MyPagerAdapter(views, context));
        viewPager_msg = views.get(0).findViewById(R.id.msg_viewpager);

        int[] images = new int[]{R.drawable.message_image1, R.drawable.message_image2, R.drawable.message_image3};
        final ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
        viewPager_msg.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) imageViews.get(position));
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ccc++;
                    ViewPagerScroller scroller = new ViewPagerScroller(context);
                    scroller.setScrollDuration(1500);
                    scroller.initViewPagerScroll(viewPager_msg); //这个是设置切换过渡时间为2秒
                    handler.sendMessage(new Message());
                }
            }
        }).start();
        final ImageView don = views.get(0).findViewById(R.id.don);
        viewPager_msg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) don.getLayoutParams();
                    float danwei_one = (float) ((viewPager_msg.getWidth() - don.getWidth()) * 0.5);
                    params.setMargins((int) ((positionOffset + position) * (danwei_one)), 0, 0, don.getHeight());// 通过自定义坐标来放置你的控件
                    don.setLayoutParams(params);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void showDialog() {
//        ToastUtils.onErrorShowToast(Chat_List.chat_list.size()+"");
        final Dialog dialog = new Dialog(context);
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_chat);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_scale);
        window.setGravity(Gravity.CENTER);
        final ImageView img =  window.findViewById(R.id.chat_image);
        final EditText name=window.findViewById(R.id.edit_name);
        final EditText school_id=window.findViewById(R.id.edit_school_id);
        school_id.setText(Now_User.user_school_number);
//        if (Now_User.photo != null) {
//            img.setImageBitmap(convertStringToIcon(Now_User.photo));
//        }
        Glide.with(context).load(Now_User.photo).into(img);

        name.setText(Now_User.User_Name);
        Button cancel_btn =  window.findViewById(R.id.cancel_dialog);
        Button join_btn =  window.findViewById(R.id.join_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Intent intent = new Intent(context, ChatRoom.class);
                intent.putExtra("name", name.getText().toString());
                context.startActivity(intent);
            }
        });


    }
    class MyPagerAdapter extends PagerAdapter {
        private List<View> views;

        Context context;

        public MyPagerAdapter(List<View> views, Context context) {
            this.views = views;
            this.context = context;
        }

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
    }
}

