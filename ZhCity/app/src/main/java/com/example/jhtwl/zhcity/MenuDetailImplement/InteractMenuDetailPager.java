package com.example.jhtwl.zhcity.MenuDetailImplement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;

/**
 * 描述：互动页面
 * 创建人: jhtwl
 * 时间: 16/9/14  上午11:09
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {
    public InteractMenuDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-互动");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
