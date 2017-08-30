package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.i.IUserLogin;
import com.punuo.sys.app.sip.BodyFactory;
import com.punuo.sys.app.sip.SipMessageFactory;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class UserLoginManager extends BaseManager<IUserLogin> {

    private IUserLogin iUserLogin;
    private int port;

    public UserLoginManager(Context context, IUserLogin iUserLogin, int port) {
        super(context, iUserLogin);
        this.iUserLogin = iUserLogin;
        this.port = port;
    }

    public void register(String userAccount) {
        GlobalSetting.userLogined = false;
        SipURL local = new SipURL(GlobalSetting.REGISTER_ID, GlobalSetting.serverIp, port);
        GlobalSetting.user_from = new NameAddress(userAccount, local);
        Message register = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.user_to, GlobalSetting.user_from, port);
        sip.sendMessage(register, port);
    }

    public void register2(String password) {
        Message register2 = SipMessageFactory.createRegisterRequest(sip, GlobalSetting.user_to, GlobalSetting.user_from,
                BodyFactory.createRegisterBody(password), port);
        sip.sendMessage(register2, port);
    }

    public void logout() {
        Message logout = SipMessageFactory.createNotifyRequest(sip, GlobalSetting.user_to, GlobalSetting.user_from,
                BodyFactory.createLogOutBody(), port);
        sip.sendMessage(logout, port);
    }

    public void checkTimeout() {
        Timer timer = new Timer();
        TimerTask checkTimeout = new TimerTask() {
            @Override
            public void run() {
                if (!GlobalSetting.userLogined) {
                    if (iUserLogin != null)
                        iUserLogin.OnUserLoginTimeOut();
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
