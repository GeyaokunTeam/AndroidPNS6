package com.punuo.sys.app;

import android.app.Activity;
import android.os.Bundle;

import com.punuo.sys.app.sip.SipUser;

import java.util.Random;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class BaseActivity extends Activity {

    SipUser sipUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {

        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (sipUser != null)
                    return;
                int hostPort = new Random(5000).nextInt() + 2000;
                GlobalSetting.hostPort = hostPort;
                sipUser = new SipUser(null, hostPort, BaseActivity.this);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sipUser.halt();
        sipUser.shutDown();
        sipUser = null;
    }
}
