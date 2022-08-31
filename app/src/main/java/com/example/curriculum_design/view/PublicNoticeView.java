package com.example.curriculum_design.view;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.curriculum_design.MainNewsActivity;
import com.example.curriculum_design.R;
import com.example.curriculum_design.public_notice_page1;
import com.example.curriculum_design.public_notice_page2;

public class PublicNoticeView extends LinearLayout {

    private static final String TAG = "PUBLICNOTICEVIEW";
    private Context mContext;
    private ViewFlipper mViewFlipper;
    private View mScrollTitleView;

    public PublicNoticeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PublicNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        bindLinearLayout();
        bindNotices();
    }

    /**
     * 初始化自定义的布局
     */
    private void bindLinearLayout() {
        mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.scrollnoticebar, null);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(mScrollTitleView, params);
        mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.id_scrollNoticeTitle);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        mViewFlipper.startFlipping();
    }

    /**
     * 网络请求内容后进行适配
     */
    protected void bindNotices() {
        mViewFlipper.removeAllViews();
        int i = 0;
        while (i < 5) {
            String text1 = "聚焦“逆行”求学的大龄研究生";
            TextView textView1 = new TextView(mContext);
            textView1.setTextSize(14);
            textView1.setText(text1);
            LayoutParams layoutParams1 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            mViewFlipper.addView(textView1, layoutParams1);
            textView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(getContext(), MainNewsActivity.class));
                }
            });
            i++;
            String text2 = "研究生延毕会与导师发生矛盾吗？";
            TextView textView2 = new TextView(mContext);
            textView2.setTextSize(14);
            textView2.setText(text2);
            LayoutParams layoutParams2 = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            mViewFlipper.addView(textView2, layoutParams2);
            textView2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(getContext(), MainNewsActivity.class));
                }
            });
            i++;
        }

    }

}
