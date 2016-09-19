package com.example.jhtwl.zhcity.Base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhtwl.zhcity.MainActivity;
import com.example.jhtwl.zhcity.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 描述：pager的基类，抽取公共的UI
 * 创建人: jhtwl
 * 时间: 16/9/13  上午10:34
 */
public class BasePager {
    public Activity mactivity;
    public View mRootView;
    public TextView tx_title; // 标题
    public ImageButton imageButton; // 菜单按钮
    public FrameLayout flContent; // 内容

    public BasePager(Activity activity) {
        this.mactivity = activity;
        initView();
    }

    /*
        *  初始化视图
        * */
    private void initView() {
        mRootView = View.inflate(mactivity, R.layout.base_viewpager_layout, null);
        tx_title = (TextView) mRootView.findViewById(R.id.tv_title);
        imageButton = (ImageButton) mRootView.findViewById(R.id.ib_menu);
        flContent = (FrameLayout) mRootView.findViewById(R.id.fl_pager_content);
    }

    public void initData() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSildingMenuShow();
            }
        });
    }

    private void setSildingMenuShow() {
        MainActivity mainActivity = (MainActivity) mactivity;
        SlidingMenu menu = mainActivity.getSlidingMenu();
        menu.toggle();
    }

    public void setSildingMenuEnable(boolean enable) {
        MainActivity mainActivity = (MainActivity) mactivity;
        SlidingMenu menu = mainActivity.getSlidingMenu();
        if (enable) {
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
        // 设置菜单按钮的显示
        setLeftMenuShow(enable);
    }

    private void setLeftMenuShow(boolean enable) {
        if (enable) { // 显示
            imageButton.setVisibility(View.VISIBLE);
        } else {
            imageButton.setVisibility(View.INVISIBLE);
        }
    }
}
