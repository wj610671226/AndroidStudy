package com.example.jhtwl.safty360;

import android.content.SharedPreferences;
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
        int lastY = sharedPreferences.getInt("lastY", 0);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_drag.getLayoutParams();
        params.leftMargin = lastX;
        params.topMargin = lastY;

        tv_drag.setLayoutParams(params);

        tv_drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(AddressLocationActivity.this, "down", Toast.LENGTH_SHORT).show();
                        startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        int endX = (int) motionEvent.getRawX();
                        int endY = (int) motionEvent.getRawY();

                        if (endX < 0) {
                            endX = 0;
                        }


                        int dx = endX - startX;
                        int dy = endY - startY;

                        // 更新上下左右距离
                        int left = tv_drag.getLeft() + dx;
                        int right = tv_drag.getRight() + dx;
                        int top = tv_drag.getTop() + dy;
                        int bottom = tv_drag.getBottom() + dy;

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
                return true;
            }
        });
    }
}
