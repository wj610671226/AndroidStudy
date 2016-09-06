package com.example.jhtwl.customview.Utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by jhtwl on 16/9/6.
 */
public class ScrollAnimation extends Animation{

    private View view;
    private int targetScrollX;
    private int startScrollX;
    private int totalValue;

    public ScrollAnimation(View view, int targetScrollX) {
        this.view = view;
        this.targetScrollX = targetScrollX;

        this.startScrollX = view.getScrollX();
        totalValue = this.targetScrollX - startScrollX;

        int time = Math.abs(totalValue);
        setDuration(time);
    }

    // 在指定时间内一直执行该方法，直到动画结束
    // interpolatedTime： 0 - 1 标识动画执行的进度或者百分比
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        int currentScrollX = startScrollX + totalValue;
        view.scrollTo(currentScrollX, 0);
    }
}
