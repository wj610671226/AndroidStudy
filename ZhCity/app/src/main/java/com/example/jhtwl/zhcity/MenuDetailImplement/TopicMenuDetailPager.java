package com.example.jhtwl.zhcity.MenuDetailImplement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;

/**
 * 描述：专题
 * 创建人: jhtwl
 * 时间: 16/9/14  上午11:11
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {
    public TopicMenuDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-专题");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
