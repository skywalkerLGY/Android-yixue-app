package com.example.curriculum_design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.journeyapps.barcodescanner.ViewfinderView;

//  Step 1 :继承  ViewfinderView 并 加控制器
public class ScanWidget extends ViewfinderView {


    //边角线厚度
    public float mLineDepth =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

    //边角线长度/扫描边框长度"的占比 (比例越大，线越长)
    public float mLineRate = 0.1F;


    public ScanWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //  Step 2 : 重写此方法：在此方法删除原来的扫描条等等样式，并加入自己的扫描样式
    @Override
    public void onDraw(Canvas canvas) {

        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        final Rect frame = framingRect;
        final Rect previewFrame = previewFramingRect;

        final int width = canvas.getWidth();
        final int height = canvas.getHeight();


        //====================自己加入的扫描条动画在此处（扫描条其实是View控件放了个背景，view加入动画就实现了扫描条运动）↓
        //ps:若是启用下面代码（带有 PS 的注释那段，请看其作用），请在全局定义boolean b=false，然后例：
        //if(!b){这里写这段自己加入的动画代码; b=true;}  否则出现扫描条不运动，也不会因下面那PS提示的代码让这段代码反复执行。

        //=====加入扫描条
        MyApplication myApplication= (MyApplication) this.getContext().getApplicationContext();

        //设置扫描条的参数
        View view=myApplication.getView();
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width=frame.right-frame.left;//这是计算扫描框的宽度，进而设置扫描条的宽度
        params.setMargins(frame.left,0,0,0);//设置左边距，让扫描条在横方向在扫描框里
        view.setLayoutParams(params);

        //=========第一种：设置扫描条的动画 ，仿微信，从同到尾循环扫，并且块到尾时逐渐变完全透明
        AnimationSet set=new AnimationSet(true);

        //参数 3：运动开始的地方：frame.top是扫描框离屏幕顶部的距离，减70是因为 这个扫描条 的高是 70 px,
        // 参数3的单位也是px，所以运动开始的地方就是 frame.top-70；参数4作用同3
        Animation trans=new TranslateAnimation(0, 0, frame.top-70, frame.bottom-70);
        trans.setDuration(2000);
        trans.setRepeatMode(Animation.RESTART);
        trans.setRepeatCount(Animation.INFINITE);
        set.addAnimation(trans);

        //==这段就是接近尾部后加入的透明，不需要可删除
        Animation alpha=new AlphaAnimation(1,0);
        //注意 1500+500是等于time的 ，因为在移动动画执行3分之2后才执行透明的
        alpha.setStartOffset(1500);
        alpha.setDuration(500);
        alpha.setRepeatMode(Animation.RESTART);
        alpha.setRepeatCount(Animation.INFINITE);
        alpha.setFillAfter(false);
        set.addAnimation(alpha);

        //添加动画
        view.startAnimation(set);

        //=========第二种：设置扫描条的动画，从上扫到下后又从下往上，如此循环的
/*        Animation animation = new TranslateAnimation(0, 0, frame.top-70, frame.bottom-70);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(2000);
        view.startAnimation(animation);*/

        //清除内存
        myApplication.setView(null);

        //=====为矩形扫描区域四个角加上边框  根据情况可以去掉该段代码，像微信扫码了
        paint.setColor(Color.GREEN); // 定义四个角画笔的颜色（本身整个扫描界面都为此颜色，通过设置四个角距离而被覆盖，进而形成四个角）
        //左上角
        canvas.drawRect(frame.left, frame.top, frame.left + frame.width() * mLineRate, frame.top + mLineDepth, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + mLineDepth, frame.top + frame.height() * mLineRate, paint);

        //右上角
        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.top, frame.right, frame.top + mLineDepth, paint);
        canvas.drawRect(frame.right - mLineDepth, frame.top, frame.right, frame.top + frame.height() * mLineRate, paint);

        //左下角
        canvas.drawRect(frame.left, frame.bottom - mLineDepth, frame.left + frame.width() * mLineRate, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - frame.height() * mLineRate, frame.left + mLineDepth, frame.bottom, paint);

        //右下角
        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.bottom - mLineDepth, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - mLineDepth, frame.bottom - frame.height() * mLineRate, frame.right, frame.bottom, paint);

        //============================自己加入动画↑=========================


        // 灰色遮罩层  可以去掉
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

/*

        //===========PS：以下方法，不断执行onDraw方法绘制扫描线等样式进而产生自带的扫描线和闪光点，
        // 若是扫描到了，就会把结果图绘制在矩形框上，根据情况选择是否注释以下代码或部分动画代码

        if (resultBitmap != null) {
            //扫描到后在矩形上绘制不透明的图
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            //自带的红色扫描线
            // Draw a red "laser scanner" line through the middle to show decoding is active
            paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            final int middle = frame.height() / 2 + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);

            final float scaleX = frame.width() / (float) previewFrame.width();
            final float scaleY = frame.height() / (float) previewFrame.height();

            final int frameLeft = frame.left;
            final int frameTop = frame.top;

            // draw the last possible result points
            if (!lastPossibleResultPoints.isEmpty()) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (final ResultPoint point : lastPossibleResultPoints) {
                    canvas.drawCircle(
                            frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint
                    );
                }
                lastPossibleResultPoints.clear();
            }

            // draw current possible result points
            if (!possibleResultPoints.isEmpty()) {
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (final ResultPoint point : possibleResultPoints) {
                    canvas.drawCircle(
                            frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint
                    );
                }

                // swap and clear buffers
                final List<ResultPoint> temp = possibleResultPoints;
                possibleResultPoints = lastPossibleResultPoints;
                lastPossibleResultPoints = temp;
                possibleResultPoints.clear();
            }

            //不断调用执行绘制该活动界面进出现自动的动画
            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }

*/
    }
}

