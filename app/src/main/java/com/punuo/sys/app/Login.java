package com.punuo.sys.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.punuo.sys.app.Manager.UserLoginManager;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class Login extends Activity implements View.OnClickListener, IUserLogin {


    private static final String TAG = Login.class.getSimpleName();
    UserLoginManager mUserLoginManager;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        initMagager();
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    private void initMagager() {
        mUserLoginManager = new UserLoginManager(this);

        mUserLoginManager.setIUserLogin(this);
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
    public void OnLogin1(String salt, String seed) {
        String password = "1234567";
        SHA1 sha1 = SHA1.getInstance();
        password = sha1.hashData(salt + password);
        password = sha1.hashData(seed + password);
        mUserLoginManager.register2(password);
    }

    @Override
    public void OnLogin2() {
        //TODO 跳转，开启心跳保活线程
        Log.i(TAG, "用户注册成功");
        GlobalSetting.Timeout = false;
    }

    @Override
    public void OnLogin1Failed(int Error) {
        Log.e(TAG, "OnLogin1Failed: " + Error);
        GlobalSetting.Timeout = false;
    }

    @Override
    public void OnLogin2Failed(int Error) {
        Log.e(TAG, "OnLogin2Failed: " + Error);
        GlobalSetting.Timeout = false;
    }

    @Override
    public void OnLoginTimeOut() {
        Log.e(TAG, "OnLogin2Failed: 服务器连接超时");
        GlobalSetting.Timeout = true;
    }
}
