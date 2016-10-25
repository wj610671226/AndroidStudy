package com.jhtwl.googlestore.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.jhtwl.googlestore.CustomView.DrawBoardView;
import com.jhtwl.googlestore.R;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        // 获取创建的宽度和高度
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        DrawBoardView drawView = new DrawBoardView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        linearLayout.addView(drawView);
        setContentView(linearLayout);
    }
}
