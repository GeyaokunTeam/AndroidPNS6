package com.punuo.sys.app.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.punuo.sys.app.BaseApp;
import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.Manager.DevLoginManager;
import com.punuo.sys.app.R;
import com.punuo.sys.app.SHA1;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.i.IUserLogin;
import com.punuo.sys.app.Manager.UserLoginManager;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class Login extends BaseActivity implements View.OnClickListener, IUserLogin, IDevLogin {


    private static final String TAG = Login.class.getSimpleName();
    UserLoginManager mUserLoginManager;
    DevLoginManager mDevLoginManager;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        initManager();
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    private void initManager() {
        mUserLoginManager = new UserLoginManager(this, this);
        mDevLoginManager = new DevLoginManager(this, this);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mUserLoginManager.register("3245073");
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
        GlobalSetting.userLogined = false;
    }

    @Override
    public void OnUserLogin2Failed(int Error) {
        Log.e(TAG, "OnLogin2Failed: " + Error);
        GlobalSetting.userLogined = false;
    }

    @Override
    public void OnUserLoginTimeOut() {
        Log.e(TAG, "OnUserLoginTimeOut: 服务器连接超时");
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
    }

    @Override
    public void OnDevLoginFailed() {
        Log.e(TAG, "设备注册失败");
        GlobalSetting.devLogined = false;
    }

    @Override
    public void OnDevLoginTimeOut() {
        Log.e(TAG, "OnDevLoginTimeOut：与服务器连接超时");
        GlobalSetting.devLogined = false;

    }

    @Override
    public void OnServerError() {
        Log.e(TAG, "OnServerError 服务器的锅");
    }
}
