package com.example.jhtwl.zhcity.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.Formatter;
import android.util.Log;

import org.xutils.cache.LruCache;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/23  上午11:23
 */
public class MemoryCacheUtils {
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils(Context context) {

        long maxMemory = Runtime.getRuntime().maxMemory();
        Log.e("maxMemory", Formatter.formatFileSize(context, maxMemory));

        mMemoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    /**
     * 从内存读取
     * @param url
     * @return
     */
    public Bitmap getBitmapFromMemory(String url) {
        return mMemoryCache.get(url);
    }

    /**
     * 把图片写入内存
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
