package com.example.jhtwl.safty360.Dao;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.jhtwl.safty360.Bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhtwl on 16/8/29.
 */
public class AppInfoParser {
    // 获取手机里面所有的应用程序
    public static List<AppInfo> getAppInfos(Context context) {
        // 得到一个java保证的 包管理器
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> packageInfos = manager.getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        for (PackageInfo packageInfo: packageInfos) {
            AppInfo appInfo = new AppInfo();
            String packname = packageInfo.packageName;
            appInfo.setPackname(packname);
            Drawable icon = packageInfo.applicationInfo.loadIcon(manager);
            appInfo.setIcon(icon);
            String appname = packageInfo.applicationInfo.loadLabel(manager).toString();
            appInfo.setName(appname);
            // 应用程序apk包的路径
            String apkPath = packageInfo.applicationInfo.sourceDir;
            appInfo.setApkpath(apkPath);
            File file = new File(apkPath);
            long appSize = file.length();
            appInfo.setAppSize(appSize);
            // 应用程序安装的位置
            int flags = packageInfo.applicationInfo.flags; // 二进制映射
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags) != 0) {
                // 外部存储
                appInfo.setInRom(false);
            } else {
                appInfo.setInRom(true);
            }

            if ((ApplicationInfo.FLAG_SYSTEM & flags) != 0) {
                // 系统应用
                appInfo.setUserApp(false);
            } else {
                // 用户应用
                appInfo.setUserApp(true);
            }
            appInfos.add(appInfo);
            appInfo = null;
        }
        return appInfos;
    }
}
