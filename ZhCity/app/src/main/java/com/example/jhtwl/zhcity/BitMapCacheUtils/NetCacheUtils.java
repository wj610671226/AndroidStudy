package com.example.jhtwl.zhcity.BitMapCacheUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.util.Log;
import android.widget.ImageView;

import com.example.jhtwl.zhcity.Utils.MemoryCacheUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/23  上午9:38
 */
public class NetCacheUtils {


    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView imageView, String url) {
        // 启动AsyncTask，参数会在doInBackground中接收
        new BitmapTask().execute(imageView, url);
    }

    public class BitmapTask extends AsyncTask<Object, Void, Bitmap> {
        private ImageView imageView;
        private String url;
        // 耗时操作，在子线程中执行
        @Override
        protected Bitmap doInBackground(Object... objects) {
            // 下载图片
            imageView = (ImageView) objects[0];
            url = (String) objects[1];
            return downLoadBitmap(url);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        // doInBackground 结束之后执行的方法
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                mLocalCacheUtils.setBitmapToLocal(bitmap, url);
                mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
                Log.e("NetCacheUtils", "从网络缓存读取图片啦...");
            }
        }
    }

    private Bitmap downLoadBitmap(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
