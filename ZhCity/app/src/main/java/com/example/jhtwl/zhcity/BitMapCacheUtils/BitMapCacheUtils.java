package com.example.jhtwl.zhcity.BitMapCacheUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.jhtwl.zhcity.R;
import com.example.jhtwl.zhcity.Utils.MemoryCacheUtils;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/23  上午9:35
 */
public class BitMapCacheUtils {

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private MemoryCacheUtils memoryCacheUtils;

    public BitMapCacheUtils(Context context) {

        localCacheUtils = new LocalCacheUtils();
        memoryCacheUtils = new MemoryCacheUtils(context);
        netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);
    }

    public void display(ImageView imageView, String url) {

        // 设置默认图片
        imageView.setImageResource(R.drawable.news_pic_default);

        Bitmap bitmap = null;
        // 内存缓存
        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.e("display", "从内存读取");
            return;
        }

        // 本地缓存
        bitmap = localCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Log.e("display", "从本地读取");
            // 存储到内存
            memoryCacheUtils.setBitmapToMemory(url, bitmap);
            return;
        }

        // 网络缓存
        netCacheUtils.getBitmapFromNet(imageView, url);
    }
}
