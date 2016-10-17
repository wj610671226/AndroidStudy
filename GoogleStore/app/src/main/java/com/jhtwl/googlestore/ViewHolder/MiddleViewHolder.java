package com.jhtwl.googlestore.ViewHolder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jhtwl.googlestore.Base.BaseViewHolder;
import com.jhtwl.googlestore.Bean.MiddleBean;
import com.jhtwl.googlestore.R;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/14  上午10:55
 */
public class MiddleViewHolder extends BaseViewHolder<MiddleBean> implements View.OnClickListener{

    private Button valueAnimationBtn;
    private LinearLayout middle_content;
    private boolean flag = true;

    public MiddleViewHolder(Context context) {
        super(context);
    }

    @Override
    protected View initHolderView(Context context) {
        View view = View.inflate(context, R.layout.middle_view_layout, null);
        valueAnimationBtn = (Button) view.findViewById(R.id.valueAnimationBtn);
        valueAnimationBtn.setOnClickListener(this);
        middle_content = (LinearLayout) view.findViewById(R.id.middle_content);
        return view;
    }

    @Override
    protected void setSubViewsData(MiddleBean data) {
        // 设置数据
    }

    @Override
    public void onClick(View v) {
        int middle_contentHeight = getMeasuredHeight();
        int startValue = 0;
        int endValue = 0;
        if (flag) { // 展开 -> 关闭
            startValue = middle_contentHeight;
            endValue = 0;
        } else {
            startValue = 0;
            endValue = middle_contentHeight;
        }
        flag = !flag;
        Log.e("onClick", "startValue = " + startValue + "  endValue = " + endValue);

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) middle_content.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);

        // 如果发现运行效果不符合   查看控制台打印两次  请检查测试机的开发者选项动画设置
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                params.height = value;
                middle_content.setLayoutParams(params);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (flag) {
                    valueAnimationBtn.setText("关闭");
                } else {
                    valueAnimationBtn.setText("展开");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    // 测量middle_content的高度
    private int getMeasuredHeight() {
        int width = middle_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        middle_content.measure(widthMeasureSpec, heightMeasureSpec);
        return middle_content.getMeasuredHeight();
    }
}
