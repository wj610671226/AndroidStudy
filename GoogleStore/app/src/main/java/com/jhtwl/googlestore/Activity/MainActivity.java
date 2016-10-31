package com.jhtwl.googlestore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jhtwl.googlestore.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton (View view) {
        switch (view.getId()) {
            case R.id.startActionBarActivity:
                startActivity(new Intent(this, TabActivity.class));
                break;
            case R.id.startNotifictionActivity:
                startActivity(new Intent(this, NotifictionActivity.class));
                break;
            case R.id.startTouchActivity:
                startActivity(new Intent(this, TouchActivity.class));
                break;
            case R.id.startViewHolderActivity:
                startActivity(new Intent(this, ViewHolderActivity.class));
                break;
            case R.id.startAsyncTaskActivity:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            case R.id.startKindOfActivity:
                startActivity(new Intent(this, KindOfActivity.class));
                break;
            case R.id.startLifeStyleActivity:
                startActivity(new Intent(this, LifeStyleActivity.class));
                break;
            case R.id.startImplicitActivity:
                Intent intent = new Intent();
                intent.setAction("com.jhtwl.googlestore.startImplicitActivity");
                intent.addCategory("com.jhtwl.googlestore.addCategory");
                startActivity(intent);
                break;
            case R.id.startCanvasActivity:
                startActivity(new Intent(this, CanvasActivity.class));
                break;
            case R.id.startAnimationActivity:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.startSaveFileActivity:
                startActivity(new Intent(this, SaveFileActivity.class));
                break;
            case R.id.startObservableActivity:
                startActivity(new Intent(this, ObservableActivity.class));
                break;
            case R.id.startGestureActivity:
                startActivity(new Intent(this, GestureActivity.class));
                break;
            case R.id.startServicesActivity:
                startActivity(new Intent(this, ServicesActivity.class));
                break;
            case R.id.startBroadcastActivity:
                startActivity(new Intent(this, BroadcastActivity.class));
                break;
            case R.id.startWebViewActivity:
                startActivity(new Intent(this, WebviewActivity.class));
                break;
        }
    }
}
