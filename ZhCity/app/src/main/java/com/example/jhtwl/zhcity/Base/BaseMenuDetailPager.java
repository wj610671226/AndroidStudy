package com.example.jhtwl.zhcity.Base;

import android.app.Activity;
import android.view.View;

/**
 * 描述：菜单详情页基类
 * 创建人: jhtwl
 * 时间: 16/9/14  上午10:47
 */
public abstract class BaseMenuDetailPager {
    public Activity mActivity;

    public View mRootView; // 根布局对象

    public BaseMenuDetailPager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initViews();
    }

    /*
    *   初始化界面
    * */
    public abstract View initViews();

    /*
    *   初始化数据
    * */
    public void initData() {

    }
}
