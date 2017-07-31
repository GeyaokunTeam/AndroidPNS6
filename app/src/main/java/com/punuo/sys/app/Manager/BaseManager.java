package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.ThreadPool;
import com.punuo.sys.app.sip.SipUser;

import java.util.Random;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public class BaseManager {
    public SipUser sipUser;

    public BaseManager(final Context context, final IUserLogin iUserLogin) {
        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (sipUser != null)
                    return;
                int hostPort = new Random().nextInt(5000) + 2000;
                GlobalSetting.hostPort = hostPort;
                sipUser = new SipUser(null, hostPort, context);
                sipUser.setIUserLogin(iUserLogin);
            }
        });
    }
}
