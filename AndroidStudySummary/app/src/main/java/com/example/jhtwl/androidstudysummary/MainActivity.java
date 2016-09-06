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

    // 两种上下文的区别
//    getApplicationContext();生命周期长，只要应用还存活它就存在；
//            this 生命周期短，只要Activity不存在了，系统就会回收；
//    其中：getBaseContext(),getApplication(),getApplicationContext();
//    都不能放在AlertDialog做上下文；
//    getApplicationContext（） 使用场景是比如频繁需要操作的数据库
//    推荐用法:Activity.this

}
