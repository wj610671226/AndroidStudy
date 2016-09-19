package com.example.jhtwl.zhcity.MenuDetailImplement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.jhtwl.zhcity.Base.BaseMenuDetailPager;
import com.example.jhtwl.zhcity.Bean.NewsData;

import java.util.ArrayList;

/**
 * 描述：新闻页面
 * 创建人: jhtwl
 * 时间: 16/9/14  上午11:10
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-新闻");
        text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);

        return text;
    }
}
