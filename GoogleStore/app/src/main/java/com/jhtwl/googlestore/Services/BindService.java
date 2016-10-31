package com.jhtwl.googlestore.Services;

import android.app.AlarmManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;

public class BindService extends Service {
    public BindService() {

       //
        //
        // AlarmManager : 开发手机闹钟，指定时间、周期内启动 Activity、Services、BroadcastReceiver
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
