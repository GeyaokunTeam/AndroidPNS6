package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.GlobalSetting;
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

    public DevLoginManager(Context context, IDevLogin iDevLogin) {
        super(context, iDevLogin);
        this.iDevLogin=iDevLogin;
    }
    public void register() {
        GlobalSetting.devLogined = false;
        SipURL local = new SipURL(GlobalSetting.devId, GlobalSetting.serverIp, GlobalSetting.SERVER_PORT_DEV);
        GlobalSetting.dev_from = new NameAddress("PNDT20", local);
        Message register = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.dev_to, GlobalSetting.dev_from, GlobalSetting.SERVER_PORT_DEV);
        sip.sendMessage(register, GlobalSetting.SERVER_PORT_DEV);
    }
    public void register2() {
        Message register2 = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.dev_to, GlobalSetting.dev_from,
                BodyFactory.createRegisterBody("123456"), GlobalSetting.SERVER_PORT_DEV);
        sip.sendMessage(register2, GlobalSetting.SERVER_PORT_DEV);
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
}
