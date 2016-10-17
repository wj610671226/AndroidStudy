package com.jhtwl.googlestore.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhtwl.googlestore.R;

public class NotifictionActivity extends AppCompatActivity {

    private NotificationManager manager;
    static  final int NOtification_ID = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    public void sendMyNotifiction(View view) {
        // 创建一个启动其他Actiity的Intent
        Intent intent = new Intent(this, OtherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent , 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = new Notification.Builder(this)
                    // 设置打开该通知，该通知自动消失
                    .setAutoCancel(true)
                    // 设置显示在状态栏的通知提示信息
                    .setTicker("通知测试")
                    // 设置通知图标
                    .setSmallIcon(R.drawable.ic_action_search)
                    // 设置通知内容的标题
                    .setContentTitle("通知标题")
                    // 设置通知内容
                    .setContentText("通知内容")
                    // 设置使用系统默认的声音、默认led灯
                    .setDefaults(Notification.DEFAULT_ALL)
                    // 设置通知将要启动程序的intent
                    .setContentIntent(pendingIntent).build();
            // 发送通知
            manager.notify(NOtification_ID, notification);
        }
    }

    public void cancelMyNotifiction(View view) {
        // 取消通知
        manager.cancel(NOtification_ID);
    }
}
