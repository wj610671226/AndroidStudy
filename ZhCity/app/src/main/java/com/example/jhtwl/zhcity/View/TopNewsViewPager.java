package com.example.jhtwl.zhcity.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/20  下午4:15
 */
public class TopNewsViewPager extends ViewPager {

    private int startX;
    private int startY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发, 请求父控件及祖宗控件是否拦截事件
     * 1. 右划, 而且是第一个页面, 需要父控件拦截
     * 2. 左划, 而且是最后一个页面, 需要父控件拦截
     * 3. 上下滑动, 需要父控件拦截
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 不要拦截，这样是为了保证ACTION_MOVE调用
                getParent().requestDisallowInterceptTouchEvent(true);

                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                if (Math.abs(endX - startX) > Math.abs(endY - startY)) { // 左右移动
                    if (endX > startX) { // 右滑
                        if (getCurrentItem() == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        if (getCurrentItem() == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else { // 上下滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
