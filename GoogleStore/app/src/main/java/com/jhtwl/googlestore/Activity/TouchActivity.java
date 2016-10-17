package com.jhtwl.googlestore.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.jhtwl.googlestore.CustomView.MyButton;
import com.jhtwl.googlestore.R;

public class TouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        MyButton button = (MyButton) findViewById(R.id.myButton);
        button.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.e("onKey", "onKey");
                }
                return false;
            }
        });

        /**
         *  更新UI方法
         *  1、handler
         *  2、Activity.runOnUiThread(new Runnable()
         *
         *
         *
         *
         *
         *
         */

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Log.e("activity - onKeyDown", "onKeyDown");
        return false;
    }
}
