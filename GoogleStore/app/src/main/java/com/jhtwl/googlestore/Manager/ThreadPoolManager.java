package com.jhtwl.googlestore.Manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 描述：线程池管理
 * 创建人: jhtwl
 * 时间: 16/10/21  上午10:39
 */
public class ThreadPoolManager {

    private ThreadPoolProxy poolProxy;

    private ThreadPoolManager() {}

    private static ThreadPoolManager manager = new ThreadPoolManager();

    public static ThreadPoolManager getInstence() {
        return manager;
    }

    public synchronized ThreadPoolProxy createPool() {
        if (poolProxy == null) {
            poolProxy = new ThreadPoolProxy(3, 3, 5000L);
        }
        return poolProxy;
    }

    public class ThreadPoolProxy {
        private ThreadPoolExecutor pool;
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable) {
            if (pool == null) {
                /**
                 *  corePoolSize 线程个数
                 *  maximumPoolSize
                 *  keepAliveTime 线程池存活的时间
                 *  time 时间类型
                 *  LinkedBlockingQueue
                 */
                pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
            }
            pool.execute(runnable);
        }

        public void cancal(Runnable runnable) {
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                // 取消异步任务
                pool.remove(runnable);
            }
        }
    }
}
