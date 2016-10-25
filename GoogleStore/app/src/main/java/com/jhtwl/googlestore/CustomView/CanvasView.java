package com.jhtwl.googlestore.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/19  上午10:37
 */
public class CanvasView extends View {
    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 把整张画布绘制成白色
        canvas.drawColor(Color.WHITE);
        // 创建画笔
        Paint paint = new Paint();
        // 去锯齿
        paint.setAntiAlias(true);
        // 设置画笔颜色
        paint.setColor(Color.BLUE);
        // 设置填充风格
        paint.setStyle(Paint.Style.STROKE);
        // 设置填充宽度
        paint.setStrokeWidth(4);

        int viewWidth = this.getWidth();
        Log.e("viewWidth", "" + viewWidth);
        // 绘制圆形
        canvas.drawCircle(viewWidth / 10 + 10, viewWidth / 10 + 10, viewWidth / 10, paint);
        // 绘制正方形
        canvas.drawRect(10, viewWidth / 5 + 20, viewWidth / 5 + 10, viewWidth * 2 / 5 + 20, paint);
        // 绘制矩形
        canvas.drawRect(10, viewWidth * 2 / 5 + 30, viewWidth / 5 + 10, viewWidth / 2 + 30, paint);
        // 绘制圆角矩形
        RectF rectF1 = new RectF(10, viewWidth / 2 + 40, 10 + viewWidth / 5, viewWidth * 3 / 5 + 40);
        canvas.drawRoundRect(rectF1, 15, 15, paint);

        // 定义path对象，封闭成一个三角形
        Path path1 = new Path();
        path1.moveTo(10, viewWidth * 9 / 10 + 60);
        path1.lineTo(viewWidth / 5 + 10, viewWidth * 9 / 10 + 60);
        path1.lineTo(viewWidth / 10 + 10, viewWidth * 7 / 10 + 60);
        path1.close();
        // 根据path进行绘制，绘制三角形
        canvas.drawPath(path1, paint);

        paint.setStyle(Paint.Style.FILL);
        // 设置渐变器后绘制
        // 为paint设置渐变器
        Shader mShader = new LinearGradient(0 , 0, 40, 60, new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW},
                null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        // 设置阴影
        paint.setShadowLayer(25, 20, 20, Color.GRAY);
        // 绘制圆形
        canvas.drawCircle(viewWidth / 2 + 30, viewWidth / 10 + 10, viewWidth / 10, paint);


//         剪切一个矩形
//        canvas.clipRect( RectF rect)
//        // 绘制字符串
//        canvas.drawText( String text, float x, float y,  Paint paint);
//        // 画圆角矩形
//        canvas.drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint);
//        // 画矩形
//        canvas.drawRect(float left, float top, float right, float bottom,  Paint paint) ;
//        // 画一个点
//        canvas.drawPoint(float x, float y,  Paint paint)
//        // 根据path画图
//        canvas.drawPath( Path path, Paint paint);
//        // 画椭圆
//        canvas.drawOval( RectF oval,  Paint paint) ;
//        // 画一条直线
//        canvas.drawLine(float startX, float startY, float stopX, float stopY, Paint paint);
//        // 画圆
//        canvas.drawCircle(float cx, float cy, float radius, Paint paint);
//        // 从一张bitmap上的指定区域扣一块出来
//        canvas.drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint);
//        // 画圆弧
//        canvas.drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint);



//        // 设置绘制文本字体大小
//        paint.setTextSize(float textSize);
//        // 设置绘制文本时的文字对齐方式
//        paint.setTextAlign(Align align);
//        // 设置Paint的填充风格
//        paint.setStyle(Style style);
//        // 设置画笔转弯处的连接风格
//        paint.setStrokeJoin(Join join);
//        // 设置画笔的宽度
//        paint.setStrokeWidth(float width);
//        // 设置阴影
//        paint.setShadowLayer(float radius, float dx, float dy, int shadowColor);
//        // 设置画笔额填充效果
//        paint.setShader(Shader shader)
//        // 设置颜色
//        paint.setColor(int color);
//        // 设置是否抗锯齿
//        paint.setAntiAlias(boolean aa);
//        // 设置颜色
//        paint.setARGB(int a, int r, int g, int b);
//        // 设置透明度
//        paint.setAlpha(int a);
    }
}
