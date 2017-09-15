package com.punuo.sys.app.Manager;

import android.os.Handler;
import android.os.Looper;

import com.punuo.sys.app.i.IManager;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public class BaseManager implements IManager {
    protected Handler mHandler;
    public BaseManager() {
        mHandler = new Handler(Looper.getMainLooper());
        addParent();
    }

    @Override
    public void addParent() {
        ManagerFather.getInstance().addManager(this);
    }

    @Override
    public void destroy() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
