package com.example.curriculum_design.Find;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import com.example.curriculum_design.R;

public class LeftSlideRemoveListView extends ListView implements View.OnTouchListener, AbsListView.OnScrollListener{
    private final static int SNAP_VELOCITY = 600;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private boolean mIsSlide = false;
    private int mDelta = 0;
    private int mDownX;
    private int mDownY;
    private int mMaxDistence;
    private int mSlidePosition = INVALID_POSITION;
    private View mCurrentContentView, mCurrentRemoveView;
    private int listViewTouchAction;
    private static final int MAXIMUM_LIST_ITEMS_VIEWABLE = 99;
    public LeftSlideRemoveListView(Context context) {
        this(context, null);
    }

    /*
    *
    * 这是左划删除功能
    *
    * */
    public LeftSlideRemoveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        listViewTouchAction = -1;
        setOnScrollListener(this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mMaxDistence = (int) (75 * metrics.density);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                addVelocityTracker(ev);
                if (!mScroller.isFinished()) {
                    return super.dispatchTouchEvent(ev);
                }
                // 起始位置，当前position
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                int position = pointToPosition(mDownX, mDownY);
                if (position == mSlidePosition) break;
                mSlidePosition = position;
                if (mSlidePosition == INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }
                // 恢复状态
                clear();
                // 获取当前界面
                View childView = getChildAt(mSlidePosition - getFirstVisiblePosition());
                mCurrentContentView = (View) childView.findViewById(R.id.view_content).getParent();
                mCurrentRemoveView = childView.findViewById(R.id.tv_remove);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentContentView == null) break;
                if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
                        || (Math.abs(ev.getX() - mDownX) > mTouchSlop
                        && Math.abs(ev.getY() - mDownY) < mTouchSlop)) {
                    // 开始滑动
                    mIsSlide = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentContentView == null/* || mIsSlide*/) break;
                // 如果左滑小于4/5，按钮不显示
                if (mDelta < mMaxDistence * 4 / 5) {
                    //mCurrentRemoveView.setVisibility(View.GONE);
                    scrollRight();
                } else if (mDelta < mMaxDistence) {
                    scrollLeft();
                }
                recycleVelocityTracker();
                mIsSlide = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsSlide && mSlidePosition != INVALID_POSITION) {
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    addVelocityTracker(ev);
                    int deltaX = mDownX - x;
                    mDownX = x;
                    mDelta += deltaX;
                    Log.i("onTouchEvent", "mDelta = " + mDelta);
                    if (mDelta < 0) {
                        mCurrentContentView.scrollTo(0, 0);
                        mDelta = 0;
                    } else if (mDelta >= mMaxDistence) {
                        mDelta = mMaxDistence;
                        mCurrentContentView.scrollTo(mMaxDistence, 0);
                    } else {
                        mCurrentContentView.scrollBy(deltaX, 0);
                    }
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    // 右滑
    private void scrollRight() {
        final int delta = mDelta;
        Log.i("scrollRight", "mDelta = " + mDelta);
        mScroller.startScroll(delta, 0, -delta, 0, Math.abs(delta));
        mDelta = 0;
        postInvalidate();
    }

    // 左滑
    private void scrollLeft() {
        final int delta = mMaxDistence - mDelta;
        Log.i("scrollLeft", "mDelta = " + mDelta + ", delta = " + delta);
        mScroller.startScroll(mDelta, 0, delta, 0, Math.abs(delta));
        mDelta = mMaxDistence;
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mCurrentContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished()) {
                mCurrentContentView.scrollTo(mDelta, 0);
            }
        }
    }

    private void addVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return velocity;
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void clear() {
        if (mCurrentContentView != null) {
            mDelta = 0;
            mCurrentContentView.scrollTo(0, 0);
            mCurrentContentView = null;
            mCurrentRemoveView = null;
        }
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec
//                ,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST));
//    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE) {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE) {
                scrollBy(0, -1);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newHeight = 0;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            ListAdapter listAdapter = getAdapter();
            if (listAdapter != null && !listAdapter.isEmpty()) {
                int listPosition = 0;
                for (listPosition = 0; listPosition < listAdapter.getCount()
                        && listPosition < MAXIMUM_LIST_ITEMS_VIEWABLE; listPosition++) {
                    View listItem = listAdapter.getView(listPosition, null, this);
                    //now it will not throw a NPE if listItem is a ViewGroup instance
                    if (listItem instanceof ViewGroup) {
                        listItem.setLayoutParams(new LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    }
                    listItem.measure(widthMeasureSpec, heightMeasureSpec);
                    newHeight += listItem.getMeasuredHeight();
                }
                newHeight += getDividerHeight() * listPosition;
            }
            if ((heightMode == MeasureSpec.AT_MOST) && (newHeight > heightSize)) {
                if (newHeight > heightSize) {
                    newHeight = heightSize;
                }
            }
        } else {
            newHeight = getMeasuredHeight();
        }
        setMeasuredDimension(getMeasuredWidth(), newHeight);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE) {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE) {
                scrollBy(0, 1);
            }
        }
        return false;
    }
}