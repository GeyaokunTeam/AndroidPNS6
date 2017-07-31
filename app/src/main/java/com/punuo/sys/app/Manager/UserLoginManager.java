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
 * Date 2017/7/31
 */

public class UserLoginManager extends BaseManager {

    IUserLogin iUserLogin;

    public UserLoginManager(Context context, IUserLogin iUserLogin) {
        super(context, iUserLogin);
        this.iUserLogin = iUserLogin;
    }

    public void register(String userAccount) {
        GlobalSetting.userLogined = false;
        SipURL local = new SipURL(GlobalSetting.REGISTER_ID, GlobalSetting.serverIp, GlobalSetting.SERVER_PORT_USER);
        GlobalSetting.user_from = new NameAddress(userAccount, local);
        Message register = SipMessageFactory.createRegisterRequest(sipUser, GlobalSetting.user_to, GlobalSetting.user_from, GlobalSetting.SERVER_PORT_USER);
        sipUser.sendMessage(register);
    }

    public void register2(String password) {
        Message register2 = SipMessageFactory.createRegisterRequest(sipUser, GlobalSetting.user_to, GlobalSetting.user_from,
                BodyFactory.createRegisterBody(password), GlobalSetting.SERVER_PORT_USER);
        sipUser.sendMessage(register2);
    }

    public void checkTimeout() {
        Timer timer = new Timer();
        TimerTask checkTimeout = new TimerTask() {
            @Override
            public void run() {
                if (!GlobalSetting.userLogined) {
                    if (iUserLogin != null)
                        iUserLogin.OnLoginTimeOut();
                }
            }
        };
        timer.schedule(checkTimeout, 5000);
    }
}
