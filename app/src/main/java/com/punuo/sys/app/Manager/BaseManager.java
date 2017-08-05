package com.punuo.sys.app.Manager;

import android.content.Context;
import android.support.annotation.Nullable;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.ThreadPool;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.i.IUserLogin;
import com.punuo.sys.app.sip.Sip;

import java.util.Random;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public class BaseManager<T> {
    public Sip sip;

    public BaseManager(final Context context, @Nullable final T iLogin) {
        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (sip != null)
                    return;
                int hostPort = new Random().nextInt(5000) + 2000;
                GlobalSetting.hostPort = hostPort;
                sip = Sip.getInstance(null, hostPort, context);
                if (iLogin instanceof IUserLogin) {
                    sip.setIUserLogin((IUserLogin) iLogin);
                }
                if (iLogin instanceof IDevLogin) {
                    sip.setiDevLogin((IDevLogin) iLogin);
                }
            }
        });

    }
}
