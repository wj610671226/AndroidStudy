package com.jhtwl.googlestore.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jhtwl.googlestore.R;

public class ImplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);


        Intent intent = getIntent();
        String action = intent.getAction();
        Log.e("action = ", action);


        // 获取资源
//        getResources().getText()

        /**
         *  Drawable
         *  1、StateListDrawable   <selector ></selector>
         *  2、LayerDrawable <layer-list></layer-list>
         *  3、ShapeDrawable <shape></shape>
         *  rectangle 长方形
         *  line 线
         *  ring 圆环
         *  oval 椭圆
         *  <corners>圆角</corners>
         *  <gradient>渐变</gradient>
         *  <padding>间隔</padding>
         *  <size>大小</size>
         *  <solid>填充</solid>
         *  <stroke>瞄边</stroke>
         *  4、ClipDrawable <clip></clip>
         *  5、AnimationDrawable
         *
         */
    }
}
