package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.sip.BodyFactory;
import com.punuo.sys.app.sip.SipMessageFactory;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author chenhan
 * Date 2017/8/1
 */

public class DevLoginManager extends BaseManager<IDevLogin> {

    private IDevLogin iDevLogin;
    private int port;

    public DevLoginManager(Context context, IDevLogin iDevLogin, int port) {
        super(context, iDevLogin);
        this.iDevLogin = iDevLogin;
        this.port = port;
    }

    public void register() {
        GlobalSetting.devLogined = false;
        SipURL local = new SipURL(GlobalSetting.devId, GlobalSetting.serverIp, port);
        GlobalSetting.dev_from = new NameAddress("PNDT20", local);
        Message register = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.dev_to, GlobalSetting.dev_from, port);
        sip.sendMessage(register, port);
    }

    public void register2() {
        Message register2 = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.dev_to, GlobalSetting.dev_from,
                BodyFactory.createRegisterBody("123456"), port);
        sip.sendMessage(register2, port);
    }

    public void logout() {
        Message logout = SipMessageFactory.createNotifyRequest(sip, GlobalSetting.dev_to, GlobalSetting.dev_from,
                BodyFactory.createLogOutBody(), port);
        sip.sendMessage(logout, port);
    }

    public void checkTimeout() {
        Timer timer = new Timer();
        TimerTask checkTimeout = new TimerTask() {
            @Override
            public void run() {
                if (!GlobalSetting.userLogined) {
                    if (iDevLogin != null)
                        iDevLogin.OnDevLoginTimeOut();
                }
            }
        };
        timer.schedule(checkTimeout, 5000);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
