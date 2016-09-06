package com.example.jhtwl.customview.MyCustomView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.jhtwl.customview.Utils.ScrollAnimation;

/**
 * Created by jhtwl on 16/9/6.
 */
public class SlideMenu extends FrameLayout{

    private View menuView, mainView;
    private int menuWidth = 0;
    private Scroller scroller;
    private int downX;

    public SlideMenu(Context context) {
        super(context);
        initView();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        scroller = new Scroller(getContext());
    }

    // 当1级的子view全部加载完调用，可以用初始化子view的引用
    // 注意，这里无法获取子view的高度
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
    }

    // widthMeasureSpec 和 heightMeasureSpec 是系统测量SlideMenu时传入的参数
    // 这两个参数测量出的宽度让SlideMenu充满窗体，其实正好等于屏幕宽高
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int measureSpec = MeasureSpec.makeMeasureSpec(menuWidth, MeasureSpec.EXACTLY);
//        // 测量所有子view的宽高
//        // 通过getLayoutParams方法可以获取到布局文件中指定的宽高
//        menuView.measure(measureSpec, heightMeasureSpec);
//        // 直接使用SlideMenu的测量参数，因为它的宽高都是充满父窗体
//        mainView.measure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX() - downX);
                if (Math.abs(deltaX) > 8) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        menuView.layout(-menuWidth, 0, 0, menuView.getMeasuredHeight());
        mainView.layout(0, 0, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = (moveX - downX);

                int newScrollX = getScrollX() - deltaX;
                if (newScrollX < -menuWidth) newScrollX = -menuWidth;
                if (newScrollX > 0) newScrollX = 0;

                scrollTo(newScrollX, 0);
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                // 1.使用自定义动画
//                ScrollAnimation scrollAnimation;
//                if (getScrollX() > -menuWidth / 2) {
//                    // 关闭菜单
//                    scrollAnimation = new ScrollAnimation(this, 0);
//                } else {
//                    // 打开菜单
//                    scrollAnimation = new ScrollAnimation(this, -menuWidth);
//                }
//                startAnimation(scrollAnimation);
                // 2、使用Scroller
                if (getScrollX() > - menuWidth / 2) {
                    // guanbi
                    closeMenu();
                } else {
                    opMenu();
                }
                break;
        }
        return true;
    }

    // scroller不主动去调用这个方法 而 invalidate() 可以掉这个方法
    // invalidate -> draw -> computeScroll
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            // 表示动画没有结束
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    // 切换菜单的开和关
    public void switchMenu() {
        if (getScrollX() == 0) {
            // 需要打开
            Toast.makeText(getContext(), "需要打开", Toast.LENGTH_SHORT).show();
            opMenu();
        } else  {
            // 关闭
            Toast.makeText(getContext(), "需要关闭", Toast.LENGTH_SHORT).show();
            closeMenu();
        }
    }

    private void closeMenu() {
        scroller.startScroll(getScrollX(), 0 , 0 - getScrollX(), 0, 400);
        invalidate();
    }

    private void opMenu() {
        scroller.startScroll(getScrollX(), 0, - menuWidth - getScrollX(), 0 , 400);
        invalidate();
    }
}
