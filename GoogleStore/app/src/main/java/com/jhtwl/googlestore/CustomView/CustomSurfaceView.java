package com.jhtwl.googlestore.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jhtwl.googlestore.R;

import java.util.Random;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/19  下午4:59
 */
public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder holder;
    private UpdateViewThread updateViewThread;
    private boolean hasSurface;
    private Bitmap back;
    private Bitmap fishs[];
    // 定义变量记录绘制第几张鱼的图片
    private int finshIndex = 0;
    // 下面定义两个变量。记录鱼的初始位置
    private float fishX = 788;
    private float fishY = 500;
    // 鱼游动的速度
    private float finshSpeed = 6;
    // 定义鱼游动的角度
    private int finshAngle = new Random().nextInt(60);
    Matrix matrix = new Matrix();

    public CustomSurfaceView(Context context) {
        super(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取该SurfaceView对应的SurfaceHolder，并将该类的实例作为其Callback
        holder = getHolder();
        holder.addCallback(this);
        hasSurface = false;
        back = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        fishs = new Bitmap[10];
        // 初始化鱼游动动画的10张图片
        for (int i = 0; i < 10; i ++) {
        }
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class UpdateViewThread {

    }
}
