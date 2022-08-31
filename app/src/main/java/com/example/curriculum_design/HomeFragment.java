package com.example.curriculum_design;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.bumptech.glide.Glide;
import com.example.curriculum_design.ChatRoom.ChatRoom;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.Eat_CanTing.CanTing_Activity;
import com.example.curriculum_design.IP.IP;
import com.example.curriculum_design.Internet.Data_TQ;
import com.example.curriculum_design.Internet.Data_XW;
import com.example.curriculum_design.Internet.STATIC_XW;
import com.example.curriculum_design.Library.BookActivity;
import com.example.curriculum_design.PlayGame.PlayActivity;
import com.example.curriculum_design.Word_recite.Word_Recite;
import com.example.curriculum_design.message.MyMessageActivity;
import com.example.curriculum_design.mykcb.KCBActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.mengpeng.mphelper.ToastUtils;
//import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class HomeFragment extends Fragment {
    int ccc = 0;
    private float startX;
    private ViewFlipper viewimage;
    //把所有图片资源的id放在一个int数组中
    private int[] resId = {R.drawable.show1, R.drawable.show2, R.drawable.show3, R.drawable.show4, R.drawable.show5, R.drawable.show6};
    private MyGestureListener myGestureListener;
    private GestureDetector gestureDetector;
    private TextView tv_city;
    private ViewPager viewPager, viewPager2;
    private List<View> views, views2;
    private MyPagerAdapter pagerAdapter, pagerAdapter2;
    private ImageView dot1, dot2;
    private ImageView gaokao,kaoyan,bookshop,wenju,youji,book,opengaokao;
    private ScrollView scrollView;
    private EditText searchView;
    private FrameLayout testLayout;
    private ViewFlipper test;
    private TelescopicAnimator2 telescopicAnimator;
    View view;
    ImageView study;
    private ListView listView;
    OkHttpClient client;
    String city = null, temp, win, wea, win_speed, air_level, date;
    private static final String TAG = "123";
    String result = "";
    String result0 = "";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x00 && city != null)
                tv_city.setText("   今日资讯:" + "江苏:" + "   " + wea + "   " + temp + "   " + win + "   " + win_speed + "   空气质量:" + air_level + "    " + date);
            if (msg.what == 0x01) {
                listView.setAdapter(new MyListAdapter(getActivity()));
            }
        }
    };
    OkHttpClient client_xw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_page, container, false);
        initPermission();
        TQ_INIT();
        NEW_List_Init();
        MyInit();
        study=(ImageView)view.findViewById(R.id.study_main);
        study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),StudyActivity.class);
                startActivity(intent);
            }
        });
        opengaokao=(ImageView)view.findViewById(R.id.opengaokao);
        opengaokao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),opengaokaowebActivity.class);
                startActivity(intent);
            }
        });
        gaokao=(ImageView)view.findViewById(R.id.img_gakao);
        gaokao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),gaokaoActivity.class);
                startActivity(intent);
            }
        });
        kaoyan=(ImageView)view.findViewById(R.id.img_kaoyan);
        kaoyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),kaoyanActivity.class);
                startActivity(intent);
            }
        });
        book=(ImageView)view.findViewById(R.id.img_book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),bookshopActivity.class);
                startActivity(intent);
            }
        });
        wenju=(ImageView)view.findViewById(R.id.img_wenju);
        wenju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CanTing_Activity.class);
                startActivity(intent);
            }
        });
        youji=(ImageView)view.findViewById(R.id.img_jishu);
        youji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),My_KdActivity.class);
                startActivity(intent);
            }
        });
        bookshop=(ImageView)view.findViewById(R.id.img_bookshop);
        bookshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),bookshopActivity.class);
                startActivity(intent);
            }
        });
        searchView = view.findViewById(R.id.tv_search);
        testLayout = view.findViewById(R.id.fl_bg);
        scrollView = view.findViewById(R.id.test_scrollView);
        test = view.findViewById(R.id.image_scroll);

        testLayout.getBackground().mutate().setAlpha(0);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            changeToolbarAlpha();
            if (scrollView.getScrollY() >= (test.getHeight() - testLayout.getHeight())) {
                animator().expand();
                searchView.setVisibility(view.VISIBLE);
            }
            //滚动距离<=0时 即滚动到顶部时  且当前伸展状态 进行收缩操作
            else if (scrollView.getScrollY() <= 0) {

                animator().reduce();

                new Handler().postDelayed(new Runnable(){
                    public void run(){
                        searchView.setVisibility(view.INVISIBLE);
                    }
                },6000);             }
        });

        return view;
    }

    void TQ_INIT() {
        tv_city = view.findViewById(R.id.tv_city);
        client = new OkHttpClient();
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String url = "https://www.tianqiapi.com/api?version=v61&appid=32725686&appsecret=E4Ceaayq";
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    String json = response.body().string();
                    Data_TQ data = new Gson().fromJson(json, Data_TQ.class);
                    city = data.getCity();
                    temp = "平均"+data.getTem1() + "~" + data.getTem2() + "℃";
                    wea = data.getWea();
                    win = data.getWin();
                    win_speed = data.getWin_speed();
                    air_level = data.getAir_level();
                    date = data.getDate();
                    handler.sendEmptyMessageDelayed(0x00, 1000);
                } catch (Exception e) {
                }
            }
        }).start();

    }

    void NEW_List_Init() {
        listView = view.findViewById(R.id.home_listview);
        if (STATIC_XW.data_xw_x.getResult() == null) {
            STATIC_XW.init();
            listView.setAdapter(new MyListAdapter(getActivity()));
            client_xw = new OkHttpClient();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    String url = "https://way.jd.com/jisuapi/get?channel=%E6%95%99%E8%82%B2&num=20&start=0&appkey=185468829787d6a2807032b503727e89";
                    Request request = new Request.Builder().url(url).build();
                    try (Response response = client.newCall(request).execute()) {
                        String json = response.body().string();
                        Data_XW data_xw = new Gson().fromJson(json, Data_XW.class);
                        STATIC_XW.data_xw_x = data_xw.getResult();
                        handler.sendEmptyMessageDelayed(0x01, 3000);
                    } catch (Exception e) {
                    }
                }
            }).start();

        } else {
            listView.setAdapter(new MyListAdapter(getActivity()));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Web_Activity.class);
                intent.putExtra("web_url", STATIC_XW.data_xw_x.getResult().getList().get(position).getWeburl());
                startActivity(intent);
            }
        });
    }

    void Refresh_XW() {
        client_xw = new OkHttpClient();
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                String url = "https://way.jd.com/jisuapi/get?channel=%E6%95%99%E8%82%B2&num=20&start=0&appkey=185468829787d6a2807032b503727e89";
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    String json = response.body().string();
                    Data_XW data_xw = new Gson().fromJson(json, Data_XW.class);
                    STATIC_XW.data_xw_x = data_xw.getResult();
                    handler.sendEmptyMessageDelayed(0x01, 3000);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    void MyInit() {
        final ScrollViewUpRefreshLayout scrollViewUpRefreshLayout = view.findViewById(R.id.shop_format);
        scrollViewUpRefreshLayout.setOnRefreshListener(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                vibrator.vibrate(20);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TQ_INIT();
                        Refresh_XW();
                    }
                }).start();

                scrollViewUpRefreshLayout.finishRefreshing();
                try {
                    sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        dot1 = view.findViewById(R.id.dot_1);
        dot2 = view.findViewById(R.id.dot_2);
        viewimage = view.findViewById(R.id.image_scroll);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager2 = view.findViewById(R.id.home_viewPager2);
        views = new ArrayList<>();
        views2 = new ArrayList<>();
        setDot(true, false);
        Image_Scroll();
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout1_1, null));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout1_2, null));

        views2.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout2_1, null));
        views2.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout2_2, null));
        views2.add(LayoutInflater.from(getActivity()).inflate(R.layout.layout2_3, null));
        pagerAdapter = new MyPagerAdapter(views, getActivity());
        pagerAdapter2 = new MyPagerAdapter(views2, getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager2.setAdapter(pagerAdapter2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ccc = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Button saoyisao = views.get(0).findViewById(R.id.saoyisao);
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
//                    intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
//                    getActivity().startActivity(intent);
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), "请检查微信是否正常", Toast.LENGTH_SHORT).show();
//                }
                //=======设置扫描活动  可根据需求设置以下内容
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                //启动自定义的扫描活动，不设置则启动默认的活动
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                //启动扫描
                intentIntegrator.initiateScan();
            }
        });
        Button kcb_btn = views.get(0).findViewById(R.id.my_project);
        kcb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KCBActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
            }
        });
        Button english_btn = views.get(0).findViewById(R.id.english_btn);
        english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Word_Recite.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);

            }
        });
        Button my_card = views.get(0).findViewById(R.id.my_card);
        my_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolCardAcitvity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);

            }
        });
        Button in_eat = views.get(1).findViewById(R.id.in_eat);
        in_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), CanTing_Activity.class);
                startActivity(it);
                getActivity().overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
            }
        });
        Button library = views.get(1).findViewById(R.id.library);
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), BookActivity.class);
                startActivity(it);
                getActivity().overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
            }
        });
        Button btn_kefu = views.get(1).findViewById(R.id.kefu);
        btn_kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1469199086";//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Toast.makeText(getActivity(), "正在跳转至与客服的聊天", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "请检查是否安装QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_tonzhi = views.get(1).findViewById(R.id.tonzhi);
        btn_tonzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Web_Activity_about.class);
                intent.putExtra("web_url", "https://support.qq.com/product/401853");
                startActivity(intent);
            }
        });
        Button kuaidi = views2.get(0).findViewById(R.id.kuaidi);
        kuaidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), KD_Activity.class);
                startActivity(it);
                getActivity().overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
            }
        });
        Button Smart_Voice = views2.get(0).findViewById(R.id.yuyin);
        Smart_Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.onSuccessShowToast("请说，易学教育正在倾听你的声音");
                initSpeech(getActivity());
            }
        });//-------------------------------------------------------------------------------语音识别
        Button play_btn = views2.get(0).findViewById(R.id.play_btn);
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 侧边菜单
         */
        final DrawerLayout drawerLayout;
        final NavigationView navigationView;
        Button menu;
        drawerLayout = view.findViewById(R.id.activity_na);
        navigationView = view.findViewById(R.id.nav);

        menu = view.findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });
        View header = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        TextView head_qianming = header.findViewById(R.id.head_qianming);
        TextView head_nicheng = header.findViewById(R.id.head_nicheng);
        ImageView person_photo = header.findViewById(R.id.person_photo);

        head_qianming.setText(Now_User.qianming);
        head_nicheng.setText(Now_User.nicheng);
//        if (Now_User.photo != null)
//            person_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
        Glide.with(getActivity()).load(Now_User.photo).into(person_photo);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(getActivity(), "TTS暂时不支持这种语音的朗读！",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private TextToSpeech textToSpeech;
    /**
     * 根据用户的语音输入，做不同的事情
     */
    void Ai_Voice(String voice){
        textToSpeech.setPitch(0.8f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        if(voice.contains("打开")&&voice.contains("扫一扫")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开扫一扫",
                    TextToSpeech.QUEUE_FLUSH, null);
            try {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "请检查微信是否正常", Toast.LENGTH_SHORT).show();
            }
        }
        else if(voice.contains("打开快递")&&voice.contains("快递")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开快递功能",
                    TextToSpeech.QUEUE_FLUSH, null);
            Intent it = new Intent(getActivity(), KD_Activity.class);
            startActivity(it);
            getActivity().overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
        }
        else if(voice.contains("打开日程表")||voice.contains("日程表")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开日程表",
                    TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(getActivity(), KCBActivity.class);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
        }else if(voice.contains("打开背单词")||voice.contains("背单词")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开背单词",
                    TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(getActivity(), Word_Recite.class);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.login_in, R.anim.null_out);
        } else if(voice.contains("打开图书馆")||voice.contains("图书馆")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开智慧图书馆",
                    TextToSpeech.QUEUE_FLUSH, null);
            Intent it = new Intent(getActivity(), BookActivity.class);
            startActivity(it);
            getActivity().overridePendingTransition(R.anim.activity_in1, R.anim.null_out);
        }else if(voice.contains("打开客服")||voice.contains("客服")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您跳转客服对话",
                    TextToSpeech.QUEUE_FLUSH, null);
            try {
                //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=1469199086";//uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                Toast.makeText(getActivity(), "正在跳转至与客服的聊天", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "请检查是否安装QQ", Toast.LENGTH_SHORT).show();
            }
        }else if(voice.contains("打开朋友圈")||voice.contains("好友圈")||voice.contains("朋友圈")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开好友圈",
                    TextToSpeech.QUEUE_FLUSH, null);
            MainActivity.btn_find.performClick();
        }else if(voice.contains("打开通讯录")||voice.contains("通讯录")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开好友录",
                    TextToSpeech.QUEUE_FLUSH, null);
            MainActivity.btn_message.performClick();
//            MessageFragment.btn_txl.performClick();
        }else if(voice.contains("由谁开发")||voice.contains("由谁制作")||voice.contains("由谁完成")){
            textToSpeech.speak("欢迎使用易学教育，由李光亚开发",
                    TextToSpeech.QUEUE_FLUSH, null);
        }else if((voice.contains("打开聊天室")||voice.contains("在线交流")&&voice.contains("聊天"))||voice.contains("学习聊天室")||voice.contains("群聊")){
            textToSpeech.speak(Now_User.User_Name+"您好，正在为您打开聊天室",
                    TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(getActivity(), ChatRoom.class);
            startActivity(intent);
        }else if(voice.contains("打开教育平台")||voice.contains("我要学习")){
            textToSpeech.speak("请说明您想要学习的内容分区，有高考，考研等",
                    TextToSpeech.QUEUE_FLUSH, null); }

        else{
            textToSpeech.speak("您好，感谢使用智慧语音",
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    /**
     * 初始化语音识别
     */
    public void initSpeech(final Context context) {

        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                //解析语音
                result = parseVoice(recognizerResult.getResultString());
                result0 = result;
                //正则表达式去除标点符号
                result = result.replaceAll("[‘；：”“’。，、？-]", "");
 //               ToastUtils.onDefaultShowToast("原来的：" + result0 + "\n现在的：" + result);
                if(!result.equals(""))
                    Toast.makeText(context,"\n"+result+"\n",Toast.LENGTH_SHORT).show();
                Ai_Voice(result);
                ToastUtils.onSuccessShowToast(result);
                ToastUtils.onDefaultShowToast(result);
            }
            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
        mDialog.hide();
        TextView txt = (TextView)mDialog.getWindow().getDecorView().findViewWithTag("textlink");
        txt.setText("");
    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    class Voice {
        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }
    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getActivity(), perm)) {
                toApplyList.add(perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), toApplyList.toArray(tmpList), 123);
        }

    }

    /**
     * 权限申请回调，可以作进一步处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        if (permissions[0].equals(Manifest.permission.RECORD_AUDIO) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initSpeech(getActivity());
        } else {//没有获得到权限

        }
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

    private void setDot(boolean a, boolean b) {
        if (a) dot1.setBackgroundResource(R.drawable.point_on);
        else dot1.setBackgroundResource(R.drawable.point_off);
        if (b) dot2.setBackgroundResource(R.drawable.point_on);
        else dot2.setBackgroundResource(R.drawable.point_off);
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

    class MyListAdapter extends BaseAdapter {
        Context context;
        List<Data_XW.ResultBeanX.ResultBean.ListBean> list = new ArrayList<>();

        public MyListAdapter(Context context) {
            this.context = context;
            list = STATIC_XW.data_xw_x.getResult().getList();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_lst_layout, parent, false);
            TextView textView1 = convertView.findViewById(R.id.title);
            TextView textView2 = convertView.findViewById(R.id.zuozhe);
            ImageView imageView = convertView.findViewById(R.id.image);
            textView1.setText(list.get(position).getTitle());
            textView2.setText(list.get(position).getTime());
            Glide.with(context).load(list.get(position).getPic()).into(imageView);
            return convertView;
        }
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
                IP.file_ip=edt_address.getText().toString();
                Toast.makeText(getActivity(), "已切换此IP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeToolbarAlpha() {
        int scrollY = scrollView.getScrollY();
        if (scrollY < 0) {
            testLayout.getBackground().mutate().setAlpha(0);
            return;
        }
        float radio = Math.min(1, scrollY / (test.getHeight() - testLayout.getHeight() * 1f));
        testLayout.getBackground().mutate().setAlpha((int) (radio * 0xFF));
    }

    private TelescopicAnimator2 animator(){
        if (null == telescopicAnimator){
            telescopicAnimator = new TelescopicAnimator2(searchView,testLayout.getWidth(),searchView.getWidth());
        }
        return telescopicAnimator;
    }
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
