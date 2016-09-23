package com.example.jhtwl.zhcity.BitMapCacheUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.jhtwl.zhcity.Utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/9/23  上午10:43
 */
public class LocalCacheUtils {

    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache_52";
    /**
     * 从本地读取图片
     * @param url 读取图片的key
     * @return 一张图片
     */
    public Bitmap getBitmapFromLocal(String url) {
        try {
            String md5Url = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, md5Url);

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将图片存储到本地
     * @param bitmap 图片
     * @param url url
     */
    public void setBitmapToLocal(Bitmap bitmap, String url) {
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            // 将图片保存在本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
