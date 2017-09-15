package com.punuo.sys.app.Manager;

import android.content.Context;
import android.util.Log;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.sip.BodyFactory;
import com.punuo.sys.app.sip.Sip;
import com.punuo.sys.app.sip.SipMessageFactory;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;

/**
 * Author chenhan
 * Date 2017/8/1
 */

public class DevLoginManager extends BaseManager {
    private String TAG = DevLoginManager.class.getSimpleName();
    private IDevLogin iDevLogin;
    private int port;

    public DevLoginManager(Context context, int port, IDevLogin iDevLogin) {
        super();
        this.iDevLogin = iDevLogin;
        this.port = port;
    }

    public void register() {
        GlobalSetting.devLogined = false;
        SipURL local = new SipURL(GlobalSetting.devId, GlobalSetting.serverIp, port);
        GlobalSetting.dev_from = new NameAddress("PNDT20", local);
        Message register = SipMessageFactory.createRegisterRequest(Sip.getInstance(), GlobalSetting.dev_to, GlobalSetting.dev_from, port);
        Sip.getInstance().sendMessage(register, port);
    }

    public void register2() {
        Message register2 = SipMessageFactory.createRegisterRequest(Sip.getInstance(), GlobalSetting.dev_to, GlobalSetting.dev_from,
                BodyFactory.createRegisterBody("123456"), port);
        Sip.getInstance().sendMessage(register2, port);
    }

    public void logout() {
        Message logout = SipMessageFactory.createNotifyRequest(Sip.getInstance(), GlobalSetting.dev_to, GlobalSetting.dev_from,
                BodyFactory.createLogOutBody(), port);
        Sip.getInstance().sendMessage(logout, port);
    }

    private Runnable mTimeout = new Runnable() {
        @Override
        public void run() {
            if (!GlobalSetting.devLogined) {
                if (iDevLogin != null)
                    iDevLogin.OnDevLoginTimeOut();
            }
        }
    };

    public void removeTimeout() {
        mHandler.removeCallbacks(mTimeout);
    }

    public void checkTimeout() {
        mHandler.postDelayed(mTimeout, 5000);
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.d(TAG, "DevLoginManager destroy");
    }
}
