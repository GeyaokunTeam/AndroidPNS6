package com.punuo.sys.app;

import android.app.Application;
import android.util.Log;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class BaseApp extends Application {
    private final static String TAG = "BaseApp";
    static BaseApp mInstance;
    private boolean mInited =false;

    public static BaseApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        init();

    }

    public void init() {
        Log.e(TAG, "init: BaseApp 0");
        if (mInited)
            return;
        mInited=true;
        Log.e(TAG, "init: BaseApp 1");
        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                CrashHandler.getInstance().init(getApplicationContext());
            }
        });
    }
    public boolean isInited(){
        return mInited;
    }
}
