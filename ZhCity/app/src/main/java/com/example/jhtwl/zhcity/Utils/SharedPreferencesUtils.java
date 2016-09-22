package com.example.jhtwl.zhcity.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 描述：SharedPreferences 工具
 * 创建人: jhtwl
 * 时间: 16/9/22  上午10:28
 */
public class SharedPreferencesUtils {

    public static void setString(Context content, String key, String value) {
        SharedPreferences sharedPreferences =  content.getSharedPreferences("config", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context content, String key) {
        SharedPreferences sharedPreferences =  content.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
