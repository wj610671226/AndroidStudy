package com.jhtwl.googlestore.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/19  上午11:32
 */
public class DrawBoardView extends View {
    // 记录前一个拖动事件发生点的坐标
    private float preX;
    private float preY;
    private Path path;
    public Paint paint = null;
    // 定义内存中的图片，作为缓冲区
    Bitmap cachaeBitmap = null;
    Canvas cacheCanvas = null;

    public DrawBoardView(Context context, int width, int height) {
        super(context);
        initView(width, height);
    }

    private void initView(int width, int height) {
        // 创建一个与View具有相同大小额缓冲区
        cachaeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();
        // 设置cacheCanvas将会绘制到内存中的cachaBitmap上
        cacheCanvas.setBitmap(cachaeBitmap);
        // 设置画笔的颜色
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        // 设置画笔风格
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        // 反锯齿
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取拖动事件的发生位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 把当前点设置为起始点
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(preX, preY, x, y);
                preY = y;
                preX = x;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint bmpPaint = new Paint();
        // 将cacheBitmap绘制到该View组件上
        canvas.drawBitmap(cachaeBitmap, 0, 0 ,bmpPaint);
        canvas.drawPath(path, paint);
    }
}
