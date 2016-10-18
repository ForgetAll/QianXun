package com.heapot.qianxun.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by Karl on 2016/10/18.
 * desc: 重写最外层的ScrollView，解决滑动粘性问题
 *
 */

public class TagScrollView extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public TagScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public TagScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public TagScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (Math.abs(moveY-downY)>mTouchSlop){
                    return true;
                }
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }
}
