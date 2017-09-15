package com.punuo.sys.app.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.punuo.sys.app.BaseApp;
import com.punuo.sys.app.CustomProgressDialog;
import com.punuo.sys.app.GlobalSetting;
import com.punuo.sys.app.Manager.DevLoginManager;
import com.punuo.sys.app.Manager.UserLoginManager;
import com.punuo.sys.app.R;
import com.punuo.sys.app.SHA1;
import com.punuo.sys.app.ThreadPool;
import com.punuo.sys.app.Type;
import com.punuo.sys.app.Util;
import com.punuo.sys.app.i.IDevLogin;
import com.punuo.sys.app.i.IUserLogin;
import com.punuo.sys.app.sip.Sip;
import com.punuo.sys.app.struct.MySelf;

import java.util.Random;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class Login extends BaseActivity implements View.OnClickListener {

    private static final String TAG = Login.class.getSimpleName();
    UserLoginManager mUserLoginManager;
    DevLoginManager mDevLoginManager;
    Button login;
    EditText mUserAccountView;
    EditText mPasswordView;
    CustomProgressDialog mProgressDialog;
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
        mUserAccountView = (EditText) findViewById(R.id.user_account);
        mPasswordView = (EditText) findViewById(R.id.password);
        login.setOnClickListener(this);
        Util.addLayoutListener(mView, login);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Util.hideKeyboard(v.getWindowToken());
                return true;
            }
        });
    }

    private void initManager() {
        mUserLoginManager = new UserLoginManager(this, GlobalSetting.SERVER_PORT_USER, initIUserLogin());
        mDevLoginManager = new DevLoginManager(this, GlobalSetting.SERVER_PORT_DEV, initIDevLogin());
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
                login();
                break;
        }
    }

    private void login() {
        ThreadPool.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                int hostPort = new Random().nextInt(5000) + 2000;
                GlobalSetting.hostPort = hostPort;
                Sip.init(null, hostPort, Login.this);
                Sip.getInstance().setIDevLogin(initIDevLogin());
                Sip.getInstance().setIUserLogin(initIUserLogin());
                GlobalSetting.user = new MySelf();
                String userAccount = mUserAccountView.getText().toString().trim();
                GlobalSetting.user.userAccount = userAccount;
                mUserLoginManager.register(userAccount);
                mUserLoginManager.checkTimeout();
            }
        });
        Util.hideKeyboard(mView.getWindowToken());
        showProgressDialog();
    }

    private IUserLogin initIUserLogin() {
        return new IUserLogin() {
            @Override
            public void OnUserLogin1(String salt, String seed) {
                String password = mPasswordView.getText().toString();
                GlobalSetting.user.password = password;
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
                mUserLoginManager.removeTimeout();
                mDevLoginManager.register();
                mDevLoginManager.checkTimeout();
            }

            @Override
            public void OnUserLogin1Failed(int Error) {
                Log.e(TAG, "OnLogin1Failed: " + Error);
                dismissProgressDialog();
                Util.showToast(Login.this, "ErrorCode：" + Error);
                GlobalSetting.userLogined = false;
                mUserLoginManager.removeTimeout();
            }

            @Override
            public void OnUserLogin2Failed(int Error) {
                Log.e(TAG, "OnLogin2Failed: " + Error);
                dismissProgressDialog();
                Util.showToast(Login.this, "账号密码错误" + Error);
                GlobalSetting.userLogined = false;
                mUserLoginManager.removeTimeout();
            }

            @Override
            public void OnUserLoginTimeOut() {
                Log.e(TAG, "OnUserLoginTimeOut: 服务器连接超时");
                dismissProgressDialog();
                Util.showToast(Login.this, "服务器连接超时");
                GlobalSetting.userLogined = false;
            }

            @Override
            public void OnServerError() {
                Log.e(TAG, "OnServerError 服务器的锅");
                dismissProgressDialog();
                Util.showToast(Login.this, "服务器连接异常");
                mUserLoginManager.removeTimeout();
            }
        };
    }

    private IDevLogin initIDevLogin() {
        return new IDevLogin() {
            @Override
            public void OnDevLogin1() {
                mDevLoginManager.register2();
            }

            @Override
            public void OnDevLogin2() {
                dismissProgressDialog();
                Log.v(TAG, "设备注册成功");
                GlobalSetting.devLogined = true;
                mDevLoginManager.removeTimeout();
                Util.showToast(Login.this, "登录成功");
                //TODO 跳转
                startActivityForResult(new Intent(Login.this, Main.class), LOGIN);
            }

            @Override
            public void OnDevLoginFailed() {
                dismissProgressDialog();
                Log.e(TAG, "设备注册失败");
                Util.showToast(Login.this, "设备注册失败");
                GlobalSetting.devLogined = false;
                mDevLoginManager.removeTimeout();
            }

            @Override
            public void OnDevLoginTimeOut() {
                dismissProgressDialog();
                Log.e(TAG, "OnDevLoginTimeOut：服务器连接超时");
                Util.showToast(Login.this, "服务器连接超时");
                GlobalSetting.devLogined = false;
            }

            @Override
            public void OnServerError() {
                dismissProgressDialog();
                Log.e(TAG, "OnServerError 服务器的锅");
                Util.showToast(Login.this, "服务器连接异常");
                mDevLoginManager.removeTimeout();
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            if (resultCode == LOGOUT) {
                mUserLoginManager.logout();
                mDevLoginManager.logout();
                Sip.getInstance().halt();
                Sip.destroy();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) return;
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(Login.this);
        }
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
    }

    private void dismissProgressDialog() {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) return;
        mProgressDialog.dismiss();
    }
}
