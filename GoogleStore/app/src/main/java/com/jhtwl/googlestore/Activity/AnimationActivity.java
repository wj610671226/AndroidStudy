package com.jhtwl.googlestore.Activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.jhtwl.googlestore.Animation.CustomTweenAnimation;
import com.jhtwl.googlestore.R;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);


        /**
         *  1、逐帧动画 <animationn-list>  <item></item>  </animationn-list>
         *  2、补间动画 AlphaAnimation  ScaleAnimation TranslateAnimation RotateAnimation
         *  3、属性动画
         */
        // 逐帧动画
        ImageView imageView = (ImageView) findViewById(R.id.animation_demo1);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
//        animationDrawable.start();

        // 补间动画tween
//        ImageView imageView = (ImageView) findViewById(R.id.animation_demo2);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_demo2);
        /**
         * AccelerateDecelerateInterpolator：在动画开始和结束的地方速率变化较慢，中间的时候加速

         AccelerateInterpolator:在动画开始的地方速率改变较慢，然后加速

         CycleInterpolator：动画循环播放特定次数，速率改变沿正弦曲线

         DecelerateInterpolator：在动画开始的地方速率改变较慢，然后减速

         LinearInterpolator：以均匀的速率改变
         */
        // 设置动画变化的速率
//        animation.setInterpolator(new DecelerateInterpolator());
//        imageView.startAnimation(animation);


        int width = imageView.getWidth();
        int height = imageView.getHeight();
        imageView.setAnimation(new CustomTweenAnimation(width / 2, height / 2, 3500));
    }
}
