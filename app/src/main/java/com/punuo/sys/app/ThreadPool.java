package com.punuo.sys.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author chenhan
 * Date 2017/7/31
 * 如果要自己New Thread()都交给这个线程来处理
 */

public class ThreadPool {
    private ExecutorService pool;

    private static ThreadPool instance;

    private static class Builder {
        static ThreadPool instance = new ThreadPool();
    }

    public static ThreadPool getInstance() {
        if (instance == null)
            instance = Builder.instance;
        return instance;
    }

    private ThreadPool() {
        pool = Executors.newFixedThreadPool(5);
    }

    public void addTask(Runnable task) {
        pool.execute(task);
    }
}
