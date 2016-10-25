package com.jhtwl.googlestore.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jhtwl.googlestore.ObserverAndObservable.DownLoadManager;
import com.jhtwl.googlestore.ObserverAndObservable.ObserverDownLoadAndChangeView;
import com.jhtwl.googlestore.ObserverAndObservable.ObserverOther;
import com.jhtwl.googlestore.ObserverAndObservable.OtherDownLoadOpera;
import com.jhtwl.googlestore.R;

public class ObservableActivity extends AppCompatActivity {


    private DownLoadManager manager;
    private OtherDownLoadOpera otherDownLoadOpera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);

        // 系统方式处理
        manager = new DownLoadManager();
        manager.addObserver(new ObserverDownLoadAndChangeView());

        // 自定义Observe
        otherDownLoadOpera = new OtherDownLoadOpera();
        otherDownLoadOpera.addObserver(new ObserverOther());
    }

    public void clickBtn(View v) {
        manager.notifyObservers();
    }


    public void clickObserve(View v) {
        otherDownLoadOpera.notifyObservers();
    }

}
