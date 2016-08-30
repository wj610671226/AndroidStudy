package com.example.jhtwl.safty360.Bean;

import android.graphics.drawable.Drawable;

/**
 * Created by jhtwl on 16/8/29.
 * 应用程序信息的业务bean
 */
public class AppInfo {
    public String getApkpath() {
        return apkpath;
    }

    public void setApkpath(String apkpath) {
        this.apkpath = apkpath;
    }

    private String apkpath;

    // 应用图标
    private Drawable icon;
    // 应用程序名称
    private String name;
    // 应用安装位置 true手机内存， false 外部存储
    private boolean inRom;
    // 应用程序的大小
    private long appSize;
    // 是否是用户程序 true 用户程序 false 系统程序
    private boolean userApp;
    // 应用程序的包名
    private String packname;

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
