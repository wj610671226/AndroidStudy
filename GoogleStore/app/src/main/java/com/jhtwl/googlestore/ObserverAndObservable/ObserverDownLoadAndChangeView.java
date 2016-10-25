package com.jhtwl.googlestore.ObserverAndObservable;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * 描述：观察者 --》 下载完成改变界面
 * 创建人: jhtwl
 * 时间: 16/10/21  下午2:40
 */
public class ObserverDownLoadAndChangeView implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Log.e("update", "监测到下载操作完成，观察者 修改界面");
    }
}
