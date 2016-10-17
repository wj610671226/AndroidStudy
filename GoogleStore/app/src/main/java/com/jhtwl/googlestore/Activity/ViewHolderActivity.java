package com.jhtwl.googlestore.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.jhtwl.googlestore.Bean.BottomBean;
import com.jhtwl.googlestore.Bean.MiddleBean;
import com.jhtwl.googlestore.Bean.TopBean;
import com.jhtwl.googlestore.R;
import com.jhtwl.googlestore.ViewHolder.BottomViewHolder;
import com.jhtwl.googlestore.ViewHolder.MiddleViewHolder;
import com.jhtwl.googlestore.ViewHolder.TopViewHolder;

public class ViewHolderActivity extends AppCompatActivity {

    private TopViewHolder topViewHolder;
    private FrameLayout topView, bottomView, valueAnimationView;
    private BottomViewHolder bottomViewHolder;
    private MiddleViewHolder middleViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_holder);

        initView();
    }

    private void initView() {
        topView = (FrameLayout) findViewById(R.id.topView);
        topViewHolder = new TopViewHolder(this);
        // 设置数据
        topViewHolder.setViewHolderData(new TopBean());
        // 添加到界面
        topView.addView(topViewHolder.getConvertView());

        bottomView = (FrameLayout) findViewById(R.id.bottomView);
        bottomViewHolder = new BottomViewHolder(this);
        bottomViewHolder.setViewHolderData(new BottomBean());
        bottomView.addView(bottomViewHolder.getConvertView());

        valueAnimationView = (FrameLayout) findViewById(R.id.valueAnimationView);
        middleViewHolder = new MiddleViewHolder(this);
        middleViewHolder.setViewHolderData(new MiddleBean());
        valueAnimationView.addView(middleViewHolder.getConvertView());
    }
}
