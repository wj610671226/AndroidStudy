package com.jhtwl.googlestore.ObserverAndObservable;

import android.util.Log;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/21  下午3:27
 */
public class ObserverOther implements OtherDownLoadOpera.MyObserver {
    @Override
    public void update() {
        Log.e("ObserverOther", "更新界面或其他操作");
    }
}
