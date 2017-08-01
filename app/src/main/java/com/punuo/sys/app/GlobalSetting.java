package com.punuo.sys.app;

import android.content.Context;
import android.content.SharedPreferences;

import org.zoolu.sip.address.NameAddress;
import org.zoolu.sip.address.SipURL;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class GlobalSetting {


    private SharedPreferences sharedPreferences;

    private static final String PREFERENCES_FILE = "setting";
    //服务器名
    private static final String SERVER_NAME = "rvsup";
    //服务器ID
    private static final String SERVER_ID = "330100000010000090";
    //用户端口
    public static final int SERVER_PORT_USER = 6061;
    //设备端口
    public static final int SERVER_PORT_DEV = 6060;
    //用户注册获取用户ID使用
    public static final String REGISTER_ID = "330100000010000190";
    //用户Id
    public static UserProfile user;

    public boolean debug = false;

    public static int hostPort = -1;
    //服务器ip
    public static String serverIp = "101.69.255.132";
    //用户账号
    public static String userAccount;
    //用户密码
    public static String passWord;
    //设备Id
    public static String devId = "310023000100020001";
    //sip消息From地址
    public static NameAddress user_from;
    //sip消息To地址（发给服务器）
    public static NameAddress user_to;
    //sip消息(聊天消息)To好友地址
    public static NameAddress toUser;
    //sip消息(设备)To地址
    public static NameAddress dev_to;
    //sip消息(设备)From地址
    public static NameAddress dev_from;
    //sip消息(用户)请求视频设备地址
    public static NameAddress toDev;

    public static boolean userLogined = false;

    public static boolean devLogined = false;

    private static GlobalSetting mInstance;

    public static GlobalSetting getInstance() {
        if (mInstance == null) {
            throw new MustCallException("init()" + GlobalSetting.class);
        }
        return mInstance;
    }

    public static void init(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        mInstance = new GlobalSetting(context);
    }

    private GlobalSetting(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        read(context);
        initData();
    }


    private void read(Context context) {

    }

    private void initData() {
        SipURL remote = new SipURL(SERVER_ID, serverIp, SERVER_PORT_USER);
        user_to = new NameAddress(SERVER_NAME, remote);

        SipURL remote_dev = new SipURL(SERVER_ID, serverIp, SERVER_PORT_DEV);
        dev_to = new NameAddress(SERVER_NAME, remote_dev);
    }

}
