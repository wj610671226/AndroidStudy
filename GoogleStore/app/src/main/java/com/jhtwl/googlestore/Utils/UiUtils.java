package com.jhtwl.googlestore.Utils;

import android.content.Context;

import com.jhtwl.googlestore.Base.BaseApplication;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/14  上午10:15
 */
public class UiUtils {
    /**
     * 获取上下文
     * @return 返回一个上下文
     */
    public static Context getContext() {
        return BaseApplication.getBaseApplication();
    }
}
