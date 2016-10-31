package com.jhtwl.googlestore.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "接收到第一条广播", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("MyReceiver", "第一条广播接受者增加的数据");
        setResultExtras(bundle);
        // 终止广播
//        abortBroadcast();
    }
}
