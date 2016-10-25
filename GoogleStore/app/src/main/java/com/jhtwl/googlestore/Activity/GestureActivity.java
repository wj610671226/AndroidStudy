package com.jhtwl.googlestore.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.jhtwl.googlestore.R;

public class GestureActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private GestureDetector detector;
    private ImageView imageView;
    private int width, height;
    private float currentScale = 1;
    // 图片缩放对象
    private Matrix matrix;
    // 初始的图片资源
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        detector = new GestureDetector(this, this);
        imageView = (ImageView) findViewById(R.id.imageView);
        matrix = new Matrix();

        // 获取被缩放的原图片
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
        // 获取宽 高
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        // 设置图片
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }


    // 按下
    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this, "onDown", Toast.LENGTH_SHORT).show();
        return false;
    }

    // 手指按下，但没开始移动
    @Override
    public void onShowPress(MotionEvent e) {
        Toast.makeText(this, "onShowPress", Toast.LENGTH_SHORT).show();
    }

    // 单击频幕
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        return false;
    }


    // 在屏幕上滑动  滚动
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Toast.makeText(this, "onScroll", Toast.LENGTH_SHORT).show();
        return false;
    }

    // 手指在屏幕上面长按触发
    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(this, "onLongPress", Toast.LENGTH_SHORT).show();
    }

    // 手指在屏幕上滑完时调用
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("onFling", "onFling");
        velocityX = velocityX > 4000 ? 4000 : velocityX;
        velocityY = velocityY < -4000 ? -4000 : velocityY;
        // 根据手势的速度来计算缩放比，如果velocityX > 0, 则放大图片，否则缩小图片
        currentScale += currentScale * velocityX / 4000.0f;
        // 保证currentScale不会等于0
        currentScale = currentScale > 0.01 ? currentScale : 0.01f;
        // 重置matrix
        matrix.reset();
        matrix.setScale(currentScale, currentScale, 160, 200);
        BitmapDrawable tmp = (BitmapDrawable) imageView.getDrawable();
        // 如果图片还未回收，先强直回收该图片
//        if (!tmp.getBitmap().isRecycled()) {
//            tmp.getBitmap().recycle();
//        }
        // 根据原始位图和matrix创建图片
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        imageView.setImageBitmap(bitmap1);
        return true;
    }
}
