package com.nobel.apitest.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * Created by shishuaigang on 2017/6/1.
 * 线程工厂模式生成 threadfactory
 * 实现ThreadFactory interface
 */

public class WorkThreadFactory implements ThreadFactory {

    private int threadPriority = Thread.NORM_PRIORITY;
    private boolean daemon = false;

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(getThreadPriority());
        thread.setDaemon(isDaemon());
        return thread;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public boolean isDaemon() {
        return daemon;
    }
}
