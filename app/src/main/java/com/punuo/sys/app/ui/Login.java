package com.punuo.sys.app.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.punuo.sys.app.BaseApp;
import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.Manager.DevLoginManager;
import com.punuo.sys.app.Manager.UserLoginManager;
import com.punuo.sys.app.R;
import com.punuo.sys.app.SHA1;
import com.punuo.sys.app.Type;
import com.punuo.sys.app.Util;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.i.IUserLogin;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class Login extends BaseActivity implements View.OnClickListener, IUserLogin, IDevLogin {

    private static final String TAG = Login.class.getSimpleName();
    UserLoginManager mUserLoginManager;
    DevLoginManager mDevLoginManager;
    Button login;
    public static int LOGOUT = 0x0000;
    public static int LOGIN = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initManager();
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    private void initManager() {
        mUserLoginManager = new UserLoginManager(this, this, GlobalSetting.SERVER_PORT_USER);
        mDevLoginManager = new DevLoginManager(this, this, GlobalSetting.SERVER_PORT_DEV);
    }

    private void init() {
        if (!BaseApp.getInstance().isInited()) {
            BaseApp.getInstance().init();
        }
        GlobalSetting.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void setContentView() {
        setContentView(getLayoutId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void setBackground() {
        mView.setBackground(getBackgroundDrawable());
    }

    @Override
    protected Drawable getBackgroundDrawable() {
        return getDrawable(R.drawable.login_bg);
    }

    @Override
    protected String getActivityType() {
        return Type.CH_LOGIN;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mUserLoginManager.register("deqing10");
                mUserLoginManager.checkTimeout();
                break;
        }
    }

    @Override
    public void OnUserLogin1(String salt, String seed) {
        String password = "123456";
        SHA1 sha1 = SHA1.getInstance();
        password = sha1.hashData(salt + password);
        password = sha1.hashData(seed + password);
        mUserLoginManager.register2(password);
    }

    @Override
    public void OnUserLogin2() {
        //TODO 设备注册 开启用户心跳保活线程
        Log.v(TAG, "用户注册成功");
        GlobalSetting.userLogined = true;
        mDevLoginManager.register();
        mDevLoginManager.checkTimeout();
    }

    @Override
    public void OnUserLogin1Failed(int Error) {
        Log.e(TAG, "OnLogin1Failed: " + Error);
        Util.showToast(this,"ErrorCode："+Error);
        GlobalSetting.userLogined = false;
    }

    @Override
    public void OnUserLogin2Failed(int Error) {
        Log.e(TAG, "OnLogin2Failed: " + Error);
        Util.showToast(this,"账号密码错误"+Error);
        GlobalSetting.userLogined = false;
    }

    @Override
    public void OnUserLoginTimeOut() {
        Log.e(TAG, "OnUserLoginTimeOut: 服务器连接超时");
        Util.showToast(this,"服务器连接超时");
        GlobalSetting.userLogined = false;
    }

    @Override
    public void OnDevLogin1() {
        mDevLoginManager.register2();
    }

    @Override
    public void OnDevLogin2() {
        Log.v(TAG, "设备注册成功");
        GlobalSetting.devLogined = true;
        Util.showToast(this,"登录成功");
        //TODO 跳转
        startActivityForResult(new Intent(Login.this, Main.class), LOGIN);
    }

    @Override
    public void OnDevLoginFailed() {
        Log.e(TAG, "设备注册失败");
        Util.showToast(this,"设备注册失败");
        GlobalSetting.devLogined = false;
    }

    @Override
    public void OnDevLoginTimeOut() {
        Log.e(TAG, "OnDevLoginTimeOut：服务器连接超时");
        Util.showToast(this,"服务器连接超时");
        GlobalSetting.devLogined = false;

    }

    @Override
    public void OnServerError() {
        Log.e(TAG, "OnServerError 服务器的锅");
        Util.showToast(this,"服务器异常");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            if (resultCode == LOGOUT) {
                mUserLoginManager.logout();
                mDevLoginManager.logout();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
