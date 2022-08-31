package com.example.curriculum_design.Eat_CanTing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.curriculum_design.DB_Help.Now_User;
import com.example.curriculum_design.Library.BookActivity;
import com.example.curriculum_design.R;
import com.example.curriculum_design.ViewPagerScroller;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DianCanFragment extends Fragment {
    View view;
    private RelativeLayout rel0;
    ArrayList<ImageView> imageViews = new ArrayList<>();
    private ImageView don;
    private LinearLayout select;
    private ViewPager viewpager_eat;
    private TextView tejia_txt;
    private Button ct_tejia1;
    private Button ct_tejia2;
    private Button ct_tejia3;
    private Button ct_tejia4;
    private Button ct_tejia5;
    private Button zaocan;
    private Button wucan;
    private Button wancan;
    private LinearLayout line_eat;
    private ImageView user_photo;
    private TextView user_name;
    private ImageView logo;
    private RelativeLayout title;
    ArrayList<View> views;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dian_can, container, false);
        MyInit();
        initView();
        ZaoCan();
        WuCan();
        WanCan();
        return view;
    }
    void MyInit() {

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
    private void initView() {
        rel0 = (RelativeLayout) view.findViewById(R.id.rel0);

        select = (LinearLayout) view.findViewById(R.id.select);
        viewpager_eat = (ViewPager) view.findViewById(R.id.viewpager_eat);

        ct_tejia1 = (Button) view.findViewById(R.id.ct_tejia1);
        ct_tejia2 = (Button) view.findViewById(R.id.ct_tejia2);
        ct_tejia3 = (Button) view.findViewById(R.id.ct_tejia3);
        ct_tejia4 = (Button) view.findViewById(R.id.ct_tejia4);
        ct_tejia5 = (Button) view.findViewById(R.id.ct_tejia5);
        user_photo=  view.findViewById(R.id.user_photo);
        user_name=view.findViewById(R.id.user_name);
        user_name.setText(Now_User.nicheng);
//        if (Now_User.photo != null) {
//            user_photo.setImageBitmap(convertStringToIcon(Now_User.photo));
//        }
        Glide.with(getActivity()).load("https://q1.qlogo.cn/g?b=qq&nk="+Now_User.user_qq+"&s=140").into(user_photo);
        views = new ArrayList<>();
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.pager_zaocan, null));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.pager_wucan, null));
        views.add(LayoutInflater.from(getActivity()).inflate(R.layout.pager_wancan, null));
        viewpager_eat.setAdapter(new PagerAdapter() {
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
        ViewPagerScroller scroller = new ViewPagerScroller(getActivity());
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(viewpager_eat); //这个是设置切换过渡时间为2秒
        zaocan = (Button) view.findViewById(R.id.zaocan);
        wucan = (Button) view.findViewById(R.id.wucan);
        wancan = (Button) view.findViewById(R.id.wancan);
        line_eat = (LinearLayout) view.findViewById(R.id.line_eat);
        zaocan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_eat.setCurrentItem(0);
            }
        });
        wucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_eat.setCurrentItem(1);
            }
        });
        wancan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager_eat.setCurrentItem(2);
            }
        });

        ct_tejia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.ct_tejia1;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "蛋炒饭");
                startActivity(it);
            }
        });
        ct_tejia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.ct_tejia2;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "孜然味牛肉鸡");
                startActivity(it);
            }
        });
        ct_tejia3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.ct_tejia3;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "超大番茄味鸡");
                startActivity(it);
            }
        });
        ct_tejia4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.ct_tejia4;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "日式猪排");
                startActivity(it);
            }
        });
        ct_tejia5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.ct_tejia5;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "吾味炒饭");
                startActivity(it);
            }
        });
    }
    void ZaoCan(){
        final Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11;
        btn1=views.get(0).findViewById(R.id.btn1);
        btn2=views.get(0).findViewById(R.id.btn2);
        btn3=views.get(0).findViewById(R.id.btn3);
        btn4=views.get(0).findViewById(R.id.btn4);
        btn5=views.get(0).findViewById(R.id.btn5);
        btn6=views.get(0).findViewById(R.id.btn6);
        btn7=views.get(0).findViewById(R.id.btn7);
        btn8=views.get(0).findViewById(R.id.btn8);
        btn9=views.get(0).findViewById(R.id.btn9);
        btn10=views.get(0).findViewById(R.id.btn10);
        btn11=views.get(0).findViewById(R.id.btn11);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.xianrudoujiang;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "三年二班笔记本");
                startActivity(it);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.haerbingshuijiao;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "小米中性笔聚能写");
                startActivity(it);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zhonguolanzhoulamian;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力柔彩荧光笔");
                startActivity(it);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.haixianhundun;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "简约a4精致笔记本");
                startActivity(it);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.shaxianxiaochi;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "a4牛皮纸笔记本子");
                startActivity(it);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.mianxiucai;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力便签黏贴性强");
                startActivity(it);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.babimantou;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "讲义夹 黑色 办公用品");
                startActivity(it);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.jidanguanbing;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "抽屉式办公文具整理盒");
                startActivity(it);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.donxiaojiemixian;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "简繁A4/B5笔记本");
                startActivity(it);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.wujiahundun;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力圆规尺子套装");
                startActivity(it);
            }
        });
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zaliangbing;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "学生专用尺子套装");
                startActivity(it);
            }
        });
    }
    void WuCan(){
        Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
        btn1=views.get(1).findViewById(R.id.btn1);
        btn2=views.get(1).findViewById(R.id.btn2);
        btn3=views.get(1).findViewById(R.id.btn3);
        btn4=views.get(1).findViewById(R.id.btn4);
        btn5=views.get(1).findViewById(R.id.btn5);
        btn6=views.get(1).findViewById(R.id.btn6);
        btn7=views.get(1).findViewById(R.id.btn7);
        btn8=views.get(1).findViewById(R.id.btn8);
        btn9=views.get(1).findViewById(R.id.btn9);
        btn10=views.get(1).findViewById(R.id.btn10);
        btn11=views.get(1).findViewById(R.id.btn11);
        btn12=views.get(1).findViewById(R.id.btn12);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.niuyoubanfan;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "国风笔套三支装");
                startActivity(it);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.hanguozhaji;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "巨能写中性笔");
                startActivity(it);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.hanshiliaoli;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "高级简约笔记本");
                startActivity(it);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.jianroufan;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "晨光k35中性笔");
                startActivity(it);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.dawanlurou;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "a7笔记本子");
                startActivity(it);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zhonguolanzhoulamian;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力柔彩荧光笔");
                startActivity(it);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.hanbahanbao;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "小米巨能写签字笔芯");
                startActivity(it);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.luzhibanfan;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "初品西斯莱写字板");
                startActivity(it);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.hualaishi;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "故宫文化中性笔");
                startActivity(it);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.niuroufan;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力考研套尺");
                startActivity(it);
            }
        });
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.babimantou;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "讲义夹，办公用品");
                startActivity(it);
            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.huoguoji;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "日本zebra中性笔");
                startActivity(it);
            }
        });
    }
    void WanCan(){
        Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
        btn1=views.get(2).findViewById(R.id.btn1);
        btn2=views.get(2).findViewById(R.id.btn2);
        btn3=views.get(2).findViewById(R.id.btn3);
        btn4=views.get(2).findViewById(R.id.btn4);
        btn5=views.get(2).findViewById(R.id.btn5);
        btn6=views.get(2).findViewById(R.id.btn6);
        btn7=views.get(2).findViewById(R.id.btn7);
        btn8=views.get(2).findViewById(R.id.btn8);
        btn9=views.get(2).findViewById(R.id.btn9);
        btn10=views.get(2).findViewById(R.id.btn10);
        btn11=views.get(2).findViewById(R.id.btn11);
        btn12=views.get(2).findViewById(R.id.btn12);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.lonxia;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "Nasa工作笔记");
                startActivity(it);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.shaokaoyanjiusuo;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "晨光大容量圆珠笔");
                startActivity(it);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.shishangshaokao;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "小米按压式签字笔");
                startActivity(it);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zuoguoyouzha;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "网红便签贴");
                startActivity(it);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zhengzondonbeicai;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "刷题笔st针管");
                startActivity(it);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.tianmimihanguozhaji;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "简约笔记本套装");
                startActivity(it);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.jingyekaoroujing;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "晨光白优品自动笔");
                startActivity(it);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.suhexiaochao;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "加厚笔记本");
                startActivity(it);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.jueweiyabo;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "学生a5 16k课堂本");
                startActivity(it);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.yangjipaidang;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力学习办公书写板");
                startActivity(it);
            }
        });
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.chengduyizhan;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "得力竖版硬壳画板");
                startActivity(it);
            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int image_source=R.drawable.zhougonyihap;
                Intent it=new Intent(getActivity(),SubMit_ZD_Activity.class);
                it.putExtra("image_source", image_source);
                it.putExtra("eat_name", "便携笔记本口袋形");
                startActivity(it);
            }
        });
    }
}