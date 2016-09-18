package com.example.jhtwl.zhcity.Base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 描述：页签详情页
 * 创建人: jhtwl
 * 时间: 16/9/14  上午10:52
 */
public class TabDetailPager extends BaseMenuDetailPager {
    private TextView tvText;

    public TabDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        tvText = new TextView(mActivity);
        tvText.setText("页签详情页");
        tvText.setTextColor(Color.RED);
        tvText.setTextSize(25);
        tvText.setGravity(Gravity.CENTER);
        return tvText;
    }

    @Override
    public void initData() {
        tvText.setText("ssss");
    }
}
