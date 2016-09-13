package com.example.jhtwl.androidstudysummary;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 得到应用程序的版本名称
    public String getAppVersion() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 调用系统工具安装apk
    private void installAPK(File t) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.INSTALL_PACKAGE" );
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
        startActivity(intent);
    }

//    //声明一个意图，作用是开启设备的超级管理员
//    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//    ComponentName cn = new ComponentName(this, MyAdmin.class);
//    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
//    //劝说用户开启管理员
//    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
//            "开启我把。开启我就可以锁屏了，开启送积分");
//    startActivity(intent);


    // 两种上下文的区别
//    getApplicationContext();生命周期长，只要应用还存活它就存在；
//            this 生命周期短，只要Activity不存在了，系统就会回收；
//    其中：getBaseContext(),getApplication(),getApplicationContext();
//    都不能放在AlertDialog做上下文；
//    getApplicationContext（） 使用场景是比如频繁需要操作的数据库
//    推荐用法:Activity.this

    // shape、selector 、 动画 的使用
    // 手势的使用、广播、读取手机联系人（内容提供者）
    // SmsManager 服务的使用
    // 数据库的使用  Android Studio  项目中的数据库存放位置
    // 两次点击事件的处理
    // PackageManger popupwindow
}
