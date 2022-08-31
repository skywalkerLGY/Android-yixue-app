package com.example.curriculum_design;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ScrollViewUpRefreshLayout extends LinearLayout implements View.OnTouchListener {

    RelativeLayout header;
    TextView description;
    boolean loadOnce;
    int hideHeaderHeight;
    MarginLayoutParams headerLayoutParams;
    ScrollView webView;
    Context context;
    boolean ableToPull;
    float yDown;
    int touchSlop;
    int PULL =0;
    int RELEASA=1;
    int REFRESHING=2;
    int END=3;
    int currentStatus = END;
    int lastStatus = currentStatus;
    Runnable mListener;
    int pullTime = 200;



    public ScrollViewUpRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !loadOnce) {
            loadOnce = true;
            header = (RelativeLayout) getChildAt(0);
            description = (TextView)header.getChildAt(0);
            webView = (ScrollView) getChildAt(1);
            webView.setOnTouchListener(this);
            hideHeaderHeight = -header.getHeight();
            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
            headerLayoutParams.topMargin = hideHeaderHeight;
            header.setLayoutParams(headerLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        setIsAbleToPull(arg1);
        if (ableToPull) {
            switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    yDown = arg1.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    int distance=(int)(arg1.getRawY()-yDown);
                    if (distance<=0 && headerLayoutParams.topMargin <= hideHeaderHeight) {
                        return false;
                    }
                    if (distance<touchSlop) {
                        return false;
                    }
                    if (currentStatus!=REFRESHING) {
                        if (headerLayoutParams.topMargin>0) {
                            currentStatus = RELEASA;
                        }else {
                            currentStatus = PULL;
                        }
                        headerLayoutParams.topMargin = (distance/2)+hideHeaderHeight;
                        header.setLayoutParams(headerLayoutParams);
                        webView.fullScroll(ScrollView.FOCUS_UP);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                default:
                    if (currentStatus==RELEASA) {
                        RefreshingTask();
                    }
                    if (currentStatus==PULL) {
                        HideHeaderTask();
                    }
                    break;

            }

            if (currentStatus==RELEASA|| currentStatus==PULL) {
                updateHeaderView();
                webView.setPressed(false);
                webView.setFocusable(false);
                webView.setFocusableInTouchMode(true);
                lastStatus=currentStatus;
                return true;
            }
        }
        return false;
    }

    private void HideHeaderTask() {
        // TODO Auto-generated method stub
        ValueAnimator valueAnimator=ValueAnimator.ofInt(headerLayoutParams.topMargin, hideHeaderHeight);
        valueAnimator.setDuration(pullTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                // TODO Auto-generated method stub
                int integer=((Integer)arg0.getAnimatedValue()).intValue();
                headerLayoutParams.topMargin = integer;
                header.setLayoutParams(headerLayoutParams);
                webView.fullScroll(ScrollView.FOCUS_UP);
                if (integer==hideHeaderHeight) {
                    currentStatus = END;
                }
                updateHeaderView();
            }
        });
        valueAnimator.start();
    }

    private void RefreshingTask() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(headerLayoutParams.topMargin, 0);
        valueAnimator.setDuration(pullTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int integer=((Integer)arg0.getAnimatedValue()).intValue();
                headerLayoutParams.topMargin = integer;
                header.setLayoutParams(headerLayoutParams);
                webView.fullScroll(ScrollView.FOCUS_UP);
                if (integer == 0) {
                    currentStatus = REFRESHING;
                    if (mListener!=null) {
                        mListener.run();
                    }
                }
                updateHeaderView();
            }
        });
        valueAnimator.start();

    }

    public void setOnRefreshListener(Runnable pullToRefreshListener){
        mListener = pullToRefreshListener;
    }

    private void updateHeaderView() {
        if (lastStatus!=currentStatus) {
            if (currentStatus==RELEASA) {
                description.setText("正在刷新");
            }else if (currentStatus==PULL) {
                description.setText("正在刷新");
            }
//            else if (currentStatus==REFRESHING) {
//                description.setText("正在加载");
//            }
        }
    }

    private void setIsAbleToPull(MotionEvent arg1) {
        if (webView.getScrollY()<=0) {//顶部判断
            if (!ableToPull) {
                yDown = arg1.getRawY();
            }
            ableToPull = true;
        } else {
            if (headerLayoutParams.topMargin != hideHeaderHeight) {
                headerLayoutParams.topMargin = hideHeaderHeight;
                header.setLayoutParams(headerLayoutParams);
            }
            ableToPull = false;
        }
    }

    public void finishRefreshing(){
        currentStatus = END;
        HideHeaderTask();
    }

}
