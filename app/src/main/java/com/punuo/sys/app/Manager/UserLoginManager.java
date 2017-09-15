package com.punuo.sys.app.Manager;

import android.content.Context;
import android.util.Log;

import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.i.IUserLogin;
import com.punuo.sys.app.sip.BodyFactory;
import com.punuo.sys.app.sip.Sip;
import com.punuo.sys.app.sip.SipMessageFactory;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;
import org.zoolu.sip.message.Message;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class UserLoginManager extends BaseManager {
    private String TAG = UserLoginManager.class.getSimpleName();
    private IUserLogin iUserLogin;
    private int port;

    public UserLoginManager(Context context, int port, IUserLogin iUserLogin) {
        super();
        this.iUserLogin = iUserLogin;
        this.port = port;
    }

    public void register(String userAccount) {
        GlobalSetting.userLogined = false;
        SipURL local = new SipURL(GlobalSetting.REGISTER_ID, GlobalSetting.serverIp, port);
        GlobalSetting.user_from = new NameAddress(userAccount, local);
        Message register = SipMessageFactory.createRegisterRequest(Sip.getInstance(), GlobalSetting.user_to, GlobalSetting.user_from, port);
        Sip.getInstance().sendMessage(register, port);
    }

    public void register2(String password) {
        Message register2 = SipMessageFactory.createRegisterRequest(Sip.getInstance(), GlobalSetting.user_to, GlobalSetting.user_from,
                BodyFactory.createRegisterBody(password), port);
        Sip.getInstance().sendMessage(register2, port);
    }

    public void logout() {
        Message logout = SipMessageFactory.createNotifyRequest(Sip.getInstance(), GlobalSetting.user_to, GlobalSetting.user_from,
                BodyFactory.createLogOutBody(), port);
        Sip.getInstance().sendMessage(logout, port);
    }

    private Runnable mTimeout = new Runnable() {
        @Override
        public void run() {
            if (!GlobalSetting.userLogined) {
                if (iUserLogin != null)
                    iUserLogin.OnUserLoginTimeOut();
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
        Log.d(TAG, "UserLoginManager destroy");
    }
}
