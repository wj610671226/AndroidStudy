package com.example.jhtwl.zhcity.ImplementPager;

import android.app.Activity;

import com.example.jhtwl.zhcity.Base.BasePager;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/13  上午10:56
 */
public class GovaPager extends BasePager {

    public GovaPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        super.initData();
        tx_title.setText("政务");
        setSildingMenuEnable(false);

    }
}
