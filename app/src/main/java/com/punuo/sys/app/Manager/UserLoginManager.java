package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.IUserLogin;
import com.punuo.sys.app.ThreadPool;
import com.punuo.sys.app.sip.BodyFactory;
import com.punuo.sys.app.sip.SipMessageFactory;
import com.punuo.sys.app.sip.SipUser;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class UserLoginManager {

    public SipUser sipUser;
    IUserLogin iUserLogin;

    public UserLoginManager(final Context context) {
        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                int hostPort = new Random().nextInt(5000) + 2000;
                GlobalSetting.hostPort = hostPort;
                sipUser = new SipUser(null, hostPort, context);
                sipUser.setIUserLogin(iUserLogin);
            }
        });
    }

    public void setIUserLogin(IUserLogin iUserLogin) {
        this.iUserLogin = iUserLogin;
    }

    public void register(String userAccount) {
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
                if (GlobalSetting.Timeout) {
                    if (iUserLogin != null)
                        iUserLogin.OnLoginTimeOut();
                }
            }
        };
        timer.schedule(checkTimeout, 5000);
    }
}
