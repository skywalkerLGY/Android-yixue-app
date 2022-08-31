package com.example.curriculum_design;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
    private final static int MIN_MOVE = 200;
    ViewFlipper viewimage;
    Context context;
    public MyGestureListener(Context context, ViewFlipper viewimage) {
        this.viewimage = viewimage;
        this.context=context;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //设置一个最小的移动触发换页距离
        if(e1.getX() - e2.getX() > MIN_MOVE){
            viewimage.stopFlipping();
            viewimage.setInAnimation(context, R.anim.right_in);
            viewimage.setOutAnimation(context, R.anim.right_out);
            //显示下一个drawable
            viewimage.showNext();
            viewimage.startFlipping();
            //可以设置延时,就是一个自动与手动之间的切换协调
        }else if(e2.getX() - e1.getX() > MIN_MOVE){
            viewimage.stopFlipping();
            viewimage.setInAnimation(context, R.anim.left_in);
            viewimage.setOutAnimation(context, R.anim.left_out);
            //显示前一个drawable
            viewimage.showPrevious();
            viewimage.startFlipping();
        }
        //使viewFlipper每次手动控制之后，自动转动的时候仍为向右滑动。
        viewimage.setInAnimation(context, R.anim.right_in);
        viewimage.setOutAnimation(context, R.anim.right_out);
        return true;
    }
}
