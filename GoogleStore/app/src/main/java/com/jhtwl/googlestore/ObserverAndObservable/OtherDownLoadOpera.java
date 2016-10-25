package com.jhtwl.googlestore.ObserverAndObservable;

import java.util.ArrayList;

/**
 * 描述：
 * 创建人: jhtwl
 * 时间: 16/10/21  下午2:56
 */
public class OtherDownLoadOpera {

    public interface MyObserver {
        public void update();
    }

    private ArrayList<MyObserver> observers;

    public OtherDownLoadOpera() {
        this.observers = new ArrayList<MyObserver>();
    }

    public synchronized void addObserver(MyObserver o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void notifyObservers() {
        for (MyObserver observer: observers) {
            observer.update();
        }
    }
}
