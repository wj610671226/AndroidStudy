package com.jhtwl.googlestore.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhtwl.googlestore.Broadcast.MyReceiver;
import com.jhtwl.googlestore.R;

public class BroadcastActivity extends AppCompatActivity {

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    // 发送
    public void sendBroadcast(View v) {
        // 代码注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(20);
        intentFilter.addAction("com.jhtwl.googlestore.mybroadsast");
        myReceiver = new MyReceiver();
        registerReceiver(new MyReceiver(), intentFilter);

        Intent intent = new Intent();
        intent.setAction("com.jhtwl.googlestore.mybroadsast");
        intent.putExtra("message", "传递的数据");

        // 发送无序广播
        // sendBroadcast(intent);
        // 发送有序广播
        sendOrderedBroadcast(intent, null);
    }

    public void unregisterBroadcast(View v) {
        unregisterReceiver(myReceiver);
    }
}
