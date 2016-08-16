package com.example.jhtwl.safty360.PhoneTheft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public abstract class BaseSettingGuidActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            // 监听手势滑动情况
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                // 判断纵向滑动幅度是否过大, 过大的话不允许切换界面
                if (Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
                    Toast.makeText(BaseSettingGuidActivity.this, "不能这样划哦!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }

                // 判断滑动是否过慢
                if (Math.abs(velocityX) < 100) {
                    Toast.makeText(BaseSettingGuidActivity.this, "滑动的太慢了!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }

                // 向右滑 上一页
                if (e2.getRawX() - e1.getRawX() > 200) {
                    showLastPage();
                    return true;
                }

                // 向左滑 下一页
                if (e1.getRawX() - e2.getRawX() > 200) {
                    showNextPage();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public abstract void showLastPage();

    public abstract void showNextPage();


    public void clickNextButton(View v) {
        showNextPage();
    }

    public void clickLastButton(View v) {
        showLastPage();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event); // 委托手势识别器处理事件
        return super.onTouchEvent(event);
    }
}
