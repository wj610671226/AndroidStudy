package com.example.jhtwl.safty360;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddressLocationActivity extends AppCompatActivity {

    private TextView tv_top;
    private TextView tv_bottom;
    private TextView tv_drag;

    private int startX;
    private int startY;
    private SharedPreferences sharedPreferences;

    private long[] mHits = new long[2];// 数组长度表示要点击的次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_location);

        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        tv_drag = (TextView) findViewById(R.id.tv_drag);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

        // 读取坐标
        int lastX = sharedPreferences.getInt("lastX", 0);
        final int lastY = sharedPreferences.getInt("lastY", 0);

        // 获取屏幕宽高
        final int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        final int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

        if (lastY > (screenHeight - 80) / 2) {// 上边显示,下边隐藏
            tv_top.setVisibility(View.VISIBLE);
            tv_bottom.setVisibility(View.INVISIBLE);
        } else {
            tv_top.setVisibility(View.INVISIBLE);
            tv_bottom.setVisibility(View.VISIBLE);
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_drag.getLayoutParams();
        params.leftMargin = lastX;
        params.topMargin = lastY;

        tv_drag.setLayoutParams(params);

        // 监听点击事件
        tv_drag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();// 开机后开始计算的时间
                if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                    // 把图片居中
                    tv_drag.layout(screenWidth / 2 - tv_drag.getWidth() / 2,
                            tv_drag.getTop(), screenWidth / 2 + tv_drag.getWidth()
                                    / 2, tv_drag.getBottom());
                }
            }
        });


        tv_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Toast.makeText(AddressLocationActivity.this, "down", Toast.LENGTH_SHORT).show();
                        startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        int endX = (int) motionEvent.getRawX();
                        int endY = (int) motionEvent.getRawY();


                        int dx = endX - startX;
                        int dy = endY - startY;

                        // 更新上下左右距离
                        int left = tv_drag.getLeft() + dx;
                        int right = tv_drag.getRight() + dx;
                        int top = tv_drag.getTop() + dy;
                        int bottom = tv_drag.getBottom() + dy;

                        // 判断是否超出边界
                        if (left < 0 || top < 0 || right > screenWidth || bottom > screenHeight - 80) {
                            break;
                        }

                        if (top > (screenHeight - 80)  / 2) {// 上边显示,下边隐藏
                            tv_top.setVisibility(View.VISIBLE);
                            tv_bottom.setVisibility(View.INVISIBLE);
                        } else {
                            tv_top.setVisibility(View.INVISIBLE);
                            tv_bottom.setVisibility(View.VISIBLE);
                        }

                        // 更新界面
                        tv_drag.layout(left, top, right, bottom);

                        // 重新初始化起点坐标
                        startX = endX;
                        startY = endY;

                        break;
                    case MotionEvent.ACTION_UP:
                        // 保存最后一次的坐标
                        sharedPreferences.edit().putInt("lastX", tv_drag.getLeft()).commit();
                        sharedPreferences.edit().putInt("lastY", tv_drag.getTop()).commit();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });
    }
}
