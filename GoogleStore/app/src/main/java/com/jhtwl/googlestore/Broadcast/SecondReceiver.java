package com.jhtwl.googlestore.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SecondReceiver extends BroadcastReceiver {
    public SecondReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = getResultExtras(true);
        Toast.makeText(context, bundle.getString("MyReceiver"), Toast.LENGTH_SHORT).show();
    }
}
