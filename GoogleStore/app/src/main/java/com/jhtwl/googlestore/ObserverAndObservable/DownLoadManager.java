package com.jhtwl.googlestore.ObserverAndObservable;

import android.util.Log;

import java.util.Observable;


/**
 * 描述：监测下载进度
 * 创建人: jhtwl
 * 时间: 16/10/21  下午2:40
 */
public class DownLoadManager extends Observable {
    public DownLoadManager() {
//        Log.e("DownLoadManager", "下载完成");
        // 通知状态改变
        setChanged();
    }
}
