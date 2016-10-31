package com.jhtwl.googlestore.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("绑定");
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("创建服务");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("启动服务");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("服务销毁");
        super.onDestroy();
    }
}
