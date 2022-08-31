package com.example.curriculum_design;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Vibrator;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.InitSql;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.DB_Help.SqlLiteHelper;
import com.example.curriculum_design.DB_Help.UserInfo;
import com.example.curriculum_design.DB_Help.UserInfo_List;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.message.Contact;
import com.example.curriculum_design.message.ContactAdapter;
import com.example.curriculum_design.message.ContactIndexView;
import com.example.curriculum_design.message.MyMessageActivity;
import com.example.curriculum_design.message.MyMove;
//import com.example.curriculum_design.message.PinYinUtils;
import com.example.curriculum_design.message.ScrollEvent;
import com.example.curriculum_design.message.TestStackAdapter;
import com.example.curriculum_design.message.Util;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.android.material.navigation.NavigationView;
import com.loopeer.cardstack.CardStackView;
import com.mengpeng.mphelper.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Thread.sleep;

public class MessageFragment extends Fragment {
    int flag_lxr = 1;
    private ListView mListView;
    private ContactIndexView mIndexView;
    private MyGestureListener myGestureListener;
    private GestureDetector gestureDetector;
    private int[] resId = {R.drawable.show1, R.drawable.show2, R.drawable.show3, R.drawable.show4, R.drawable.show5, R.drawable.show6};
    private float startX;
    private ContactAdapter mAdapter;
    private ArrayList<Contact> mContacts = new ArrayList<>();
    private String mLetter;
    private ViewFlipper viewimage;
    private ViewPager msg_viewPager;
    private List<View> views, views2;
    private MyPagerAdapter pagerAdapter, pagerAdapter2;
    private ImageView dot1, dot2;
    String[] NAME;
    String[] QQ;
    //    ArrayList <String> NAME;
    ArrayList<View> viewlist = new ArrayList();
    View view;
    ViewPager viewPager;
    static Button btn_zaixian, btn_txl;
    RelativeLayout relativeLayout;
    RelativeLayout.LayoutParams params;
    ImageView don;
    public static ArrayList<String> PB_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_page, container, false);
        MyInit();
        Init_Card();
        Init();
        return view;

    }

    void MyInit() {

        viewlist.add(LayoutInflater.from(getActivity()).inflate(R.layout.message_view1, null));
        viewlist.add(LayoutInflater.from(getActivity()).inflate(R.layout.message_view2, null));
        btn_zaixian = view.findViewById(R.id.btn_zaixian);
        btn_txl = view.findViewById(R.id.btn_txl);
        relativeLayout = view.findViewById(R.id.rel_test);
        viewPager = view.findViewById(R.id.viewpager_message);
        viewPager.setAdapter(new MyPagerAdapter(viewlist, getActivity()));
        don = view.findViewById(R.id.don);
        params = (RelativeLayout.LayoutParams) don.getLayoutParams();
        params.width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2;
        don.setLayoutParams(params);

        dot1 = view.findViewById(R.id.msg_dot_1);
        dot2 = view.findViewById(R.id.msg_dot_2);
        viewimage = view.findViewById(R.id.message_image_scroll);
        msg_viewPager = view.findViewById(R.id.message_viewPager);
        views = new ArrayList<>();
        views2 = new ArrayList<>();
        setDot(true, false);
        Image_Scroll();
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout1_1, null));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout1_2, null));

        pagerAdapter = new MyPagerAdapter(views, getActivity());
        pagerAdapter2 = new MyPagerAdapter(views2, getActivity());
        msg_viewPager.setAdapter(pagerAdapter);
        msg_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override//当页面滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override//当页面选中时
            public void onPageSelected(int position) {
                setDot(position == 0, position == 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                    params = (RelativeLayout.LayoutParams) don.getLayoutParams();
                    params.setMargins((int) (positionOffset * (relativeLayout.getWidth() - don.getWidth())), 0, 0, 0);// 通过自定义坐标来放置你的控件
                    params.width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2;
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
        btn_zaixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        btn_txl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        /**
         * 侧边菜单
         */
        final DrawerLayout drawerLayout;
        final NavigationView navigationView;
        Button menu;
        drawerLayout = view.findViewById(R.id.message_layout);
        navigationView = view.findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);
        TextView head_qianming = header.findViewById(R.id.head_qianming);
        TextView head_nicheng = header.findViewById(R.id.head_nicheng);
        ImageView person_photo = header.findViewById(R.id.person_photo);

        head_qianming.setText(Now_User.qianming);
        head_nicheng.setText(Now_User.nicheng);
        if (Now_User.photo != null)
            person_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(getActivity(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                if (item.getItemId() == R.id.ip_change) {
                    showDialog_IP();
                }
                if (item.getItemId() == R.id.my_kuaidi) {
                    Intent intent = new Intent(getActivity(), My_KdActivity.class);
                    getActivity().startActivity(intent);
                }
                if (item.getItemId() == R.id.exit_yao) {
                    getActivity().finish();
                }
                if (item.getItemId() == R.id.my_message) {
                    Intent intent = new Intent(getActivity(), MyMessageActivity.class);
                    getActivity().startActivity(intent);
                }
                if (item.getItemId() == R.id.about_us) {
                    showDialog_About();
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
        Button lxr_zhankai = viewlist.get(1).findViewById(R.id.lxr_zhankai);
        Button btn1, btn2, btn3;
        btn1 = viewlist.get(1).findViewById(R.id.lxr_btn1);
        btn2 = viewlist.get(1).findViewById(R.id.lxr_btn2);
        btn3 = viewlist.get(1).findViewById(R.id.lxr_btn3);
        final List<Button> buttonItems = new ArrayList<Button>();
        buttonItems.add(btn1);
        buttonItems.add(btn2);
        buttonItems.add(btn3);
        lxr_zhankai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnimation(buttonItems, 350, flag_lxr);
                flag_lxr = -flag_lxr;
            }
        });
        //屏蔽
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < PB_list.size(); i++)
                    Toast.makeText(getActivity(), PB_list.get(i), Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                buttonAnimation(buttonItems, 350, flag_lxr);
                flag_lxr = -flag_lxr;
            }
        });
        //取消
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.flag_vis = false;
                mAdapter.notifyDataSetChanged();
                PB_list = new ArrayList<>();
                buttonAnimation(buttonItems, 350, flag_lxr);
                flag_lxr = -flag_lxr;
            }
        });
        //解除屏蔽
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PB_list = new ArrayList<>();
                Toast.makeText(getActivity(), "已解除屏蔽", Toast.LENGTH_SHORT).show();
                mAdapter.flag_vis = false;
                mAdapter.notifyDataSetChanged();
                buttonAnimation(buttonItems, 350, flag_lxr);
                flag_lxr = -flag_lxr;
            }
        });
    }


    static List<UserInfo> userlist = new ArrayList<>();

    void Init() {

        for (int i = 0; i < UserInfo_List.userInfo_List.size(); i++) {
            userlist.add(UserInfo_List.userInfo_List.get(i));
        }
        if (UserInfo_List.userInfo_List != null && UserInfo_List.userInfo_List.size() > 0) {
            NAME = new String[UserInfo_List.userInfo_List.size()];
            QQ = new String[UserInfo_List.userInfo_List.size()];
            for (int i = 0; i < UserInfo_List.userInfo_List.size(); i++) {
                NAME[i] = UserInfo_List.userInfo_List.get(i).user_name;
                QQ[i] = UserInfo_List.userInfo_List.get(i).user_qq;
            }
        }
        mListView = viewlist.get(1).findViewById(R.id.list_contact);
        mIndexView = viewlist.get(1).findViewById(R.id.view_contact);
        mIndexView.setTextView((TextView) viewlist.get(1).findViewById(R.id.tv_show_letter));
        mContacts = new ArrayList<>();
        if (NAME != null) {
            for (int i = 0; i < NAME.length; i++) {
                mContacts.add(new Contact(NAME[i], QQ[i]));
            }
        }
        Collections.sort(mContacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
        mAdapter = new ContactAdapter(mContacts, getActivity());
        mListView.setAdapter(mAdapter);
        mIndexView.setOnShowLetter(new ContactIndexView.onShowLetterListener() {
            @Override
            public void showLatter(String letter) {
                mLetter = letter;
                //选择首字母移动到当前首字母字母分类上
                for (int i = 0; i < mContacts.size(); i++) {
                    final String letterName = mContacts.get(i).getPinyin().substring(0, 1);
                    if (letterName.equals(mLetter)) {
                        mListView.setSelection(i);
                        return;
                    }
                }
            }
        });
        if (NAME != null) {
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    String letter = mContacts.get(firstVisibleItem).getPinyin().substring(0, 1);
                    boolean isLast = false;
                    if (firstVisibleItem + visibleItemCount == totalItemCount) {
                        isLast = true;
                    }
                    EventBus.getDefault().post(new ScrollEvent(letter, isLast));
                }
            });
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CheckBox checkBox = view.findViewById(R.id.check_to_delete);
                    TextView textView = view.findViewById(R.id.tv_name);
                    if (checkBox.getVisibility() == View.VISIBLE) {
                        if (checkBox.isChecked() == true) {
                            PB_list.remove(textView.getText().toString());
                            checkBox.setChecked(false);
                        } else {
                            PB_list.add(textView.getText().toString());
                            checkBox.setChecked(true);
                        }

                    } else {
                        String name = textView.getText().toString();
                        String qq = "", phone = "", school_id = "", sex = "";
                        for (int j = 0; j < userlist.size(); j++) {
                            if (name.equals(userlist.get(j).user_name)) {
                                qq = userlist.get(j).user_qq;
                                phone = userlist.get(j).user_phone;
                                school_id = userlist.get(j).user_school_number;
                                sex = userlist.get(j).user_sex;
                            }
                        }
                        Intent intent = new Intent(getActivity(), LXR_More.class);
                        intent.putExtra("name", name);
                        intent.putExtra("qq", qq);
                        intent.putExtra("phone", phone);
                        intent.putExtra("school_id", school_id);
                        intent.putExtra("sex", sex);
                        startActivity(intent);
                    }

                }
            });
            mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    mAdapter.flag_vis = true;
                    mAdapter.notifyDataSetChanged();
                    return true;
                }
            });
        }
        final ScrollViewUpRefreshLayout scrollViewUpRefreshLayout = viewlist.get(1).findViewById(R.id.shuaxing);
        scrollViewUpRefreshLayout.setOnRefreshListener(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                vibrator.vibrate(20);
                scrollViewUpRefreshLayout.finishRefreshing();
                try {
                    InitSql.Init_Sql();
                    sleep(1000);
                    Init();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void Init_Card() {
        final CardStackView mStackView;
        final TestStackAdapter mTestStackAdapter;
        mStackView = viewlist.get(0).findViewById(R.id.stackview_main);

        mStackView.setItemExpendListener(new CardStackView.ItemExpendListener() {
            @Override
            public void onItemExpend(boolean expend) {
                System.out.println(mStackView.getSelectPosition());
            }
        });
        mTestStackAdapter = new TestStackAdapter(getActivity());
        mStackView.setAdapter(mTestStackAdapter);
        mStackView.setAnimatorAdapter(new MyMove(mStackView));
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(new Integer[4]));
                    }
                }
                , 200
        );
    }
    private void setDot(boolean a, boolean b) {
        if (a) dot1.setBackgroundResource(R.drawable.point_on);
        else dot1.setBackgroundResource(R.drawable.point_off);
        if (b) dot2.setBackgroundResource(R.drawable.point_on);
        else dot2.setBackgroundResource(R.drawable.point_off);
    }

    public void showDialog_IP() {
        final Dialog dialog = new Dialog(getActivity());
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_ip);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.chat_Scale);
        window.setGravity(Gravity.CENTER);
        final EditText edt_address = window.findViewById(R.id.edit_address);
        SharedPreferences sp = getActivity().getSharedPreferences("ip_address", Context.MODE_PRIVATE);
        edt_address.setText(sp.getString("ip_add", ""));
        Button cancel_btn = window.findViewById(R.id.cancel_dialog);
        Button join_btn = window.findViewById(R.id.join_dialog);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "已取消", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                Toast.makeText(getActivity(), "已切换此IP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog_About() {
        final Dialog dialog = new Dialog(getActivity());
        //去掉title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_about);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 设置宽高
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.about_dialog);
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 按钮移动动画
     *
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public void buttonAnimation(final List<Button> buttonList, int radius, int flag) {

        for (int i = 0; i < buttonList.size(); i++) {

            ObjectAnimator objAnimatorX;
            ObjectAnimator objAnimatorY;
            ObjectAnimator objAnimatorRotate;

            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);

            // 按钮在X、Y方向的移动距离
            float distanceX = (float) (flag * radius * (Math.cos(Util.getAngle(buttonList.size(), i))));
            float distanceY = -(float) (flag * radius * (Math.sin(Util.getAngle(buttonList.size(), i))));

            // X方向移动
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x", buttonList.get(i).getX(), buttonList.get(i).getX() + distanceX);
            objAnimatorX.setDuration(400);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();

            // Y方向移动
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y", buttonList.get(i).getY(), buttonList.get(i).getY() + distanceY);
            objAnimatorY.setDuration(400);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();

            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i), "rotation", 0, 360);
            objAnimatorRotate.setDuration(400);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();

            if (flag == -1) {
                objAnimatorX.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // TODO Auto-generated method stub
                        // 将按钮设为可见
                        for (int i = 0; i < buttonList.size(); i++) {
                            buttonList.get(i).setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }
    }

    /**
     * string转成bitmap
     *
     * @param str
     */
    public static Bitmap convertStringToIcon(String str) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(str, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    void Image_Scroll() {
        myGestureListener = new MyGestureListener(getActivity(), viewimage);
        gestureDetector = new GestureDetector(getActivity(), myGestureListener);
        for (int i = 0; i < resId.length; i++) {
            //在往viewFlipper中加入imageView
            ImageView img = new ImageView(getActivity());
            img.setImageResource(resId[i]);
            viewimage.addView(img);
        }
        //动态设置切换时间为3000ms
        viewimage.setFlipInterval(3000);
        //开始自动滚动
        viewimage.startFlipping();
        //手势控制进行监听
        viewimage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startX = event.getX();
                        break;
                    }
                    //当手离开时
                    case MotionEvent.ACTION_UP:
                        //向右滑动
                        if (event.getX() - startX > 100) {
                            viewimage.setInAnimation(getActivity(), R.anim.left_in);
                            viewimage.setOutAnimation(getActivity(), R.anim.left_out);
                            viewimage.showNext();
                        }
                        //向左滑动
                        if (startX - event.getX() > 100) {
                            viewimage.setInAnimation(getActivity(), R.anim.right_in);
                            viewimage.setOutAnimation(getActivity(), R.anim.right_out);
                            viewimage.showPrevious();
                        }
                        break;
                }
                return true;
            }
        });
    }
}