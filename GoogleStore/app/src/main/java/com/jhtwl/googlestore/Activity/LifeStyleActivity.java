package com.jhtwl.googlestore.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jhtwl.googlestore.R;

public class LifeStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);

        Log.e("onCreate", "-----创建Activity---");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "-----启动Activity---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "-----恢复Activity，onStart之后调用---");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onStart", "-----暂停Activity---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStart", "----停止Activity---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onStart", "-----销毁Activity---");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart", "-----重新启动Activity---");
    }
}
