package com.jhtwl.googlestore.Base;

import android.app.Application;

/**
 * 描述：这个类的作用是为了放一些全局的和一些上下文都要用到的变量和方法
 * 创建人: jhtwl
 * 时间: 16/10/14  下午3:39
 */
public class BaseApplication extends Application {

    // 需要在AndroidManifest.xml文件中 application节点 添加 android:name=".Base.BaseApplication"
    private static BaseApplication baseApplication;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }
}
