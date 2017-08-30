package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.punuo.sys.app.Constant;
import com.punuo.sys.app.Manager.NavigationTabManager;
import com.punuo.sys.app.NavigationTabBar;
import com.punuo.sys.app.R;
import com.punuo.sys.app.Type;
import com.punuo.sys.app.Util;
import com.punuo.sys.app.fragment.AudioFragment;
import com.punuo.sys.app.fragment.ContactFragment;
import com.punuo.sys.app.fragment.HomeFragment;
import com.punuo.sys.app.fragment.MessageFragment;
import com.punuo.sys.app.fragment.VideoFragment;
import com.punuo.sys.app.i.IFragmentListener;

/**
 * Author chenhan
 * Date 2017/8/1
 */

public class Main extends BaseActivity {
    private static final String TAG = Main.class.getSimpleName();
    NavigationTabManager navigationTabManager;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment mHomeFragment;
    private AudioFragment mAudioFragment;
    private ContactFragment mContactFragment;
    private MessageFragment mMessageFragment;
    private VideoFragment mVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initManager();
        initFragment();
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mContactFragment = new ContactFragment();
        mAudioFragment = new AudioFragment();
        mVideoFragment = new VideoFragment();
        mMessageFragment = new MessageFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.realtabcontent, mMessageFragment)
                .add(R.id.realtabcontent, mContactFragment)
                .add(R.id.realtabcontent, mHomeFragment)
                .add(R.id.realtabcontent, mVideoFragment)
                .add(R.id.realtabcontent, mAudioFragment)
                .commit();
        hideAll();
        show(Constant.NAVIGATION_TAB_HOME);
    }

    private void initManager() {
        navigationTabManager = new NavigationTabManager(this, (NavigationTabBar) findViewById(R.id.tablayout), Constant.NAVIGATION_TAB_HOME, null, newFragmentListener());
    }

    private void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (navigationTabManager != null) {
            navigationTabManager.destroy();
        }
    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - exitTime >= 2000) {
            Util.showToast(this, "再按一下退出登录");
            exitTime = currentTime;
            return;
        }
        setResult(Login.LOGOUT);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setContentView() {
        setContentView(getLayoutId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main;
    }

    @Override
    protected void setBackground() {
        mView.setBackground(getBackgroundDrawable());
    }

    @Override
    protected Drawable getBackgroundDrawable() {
        return getDrawable(R.drawable.main_bg);
    }

    @Override
    protected String getActivityType() {
        return Type.CH_MAIN;
    }

    IFragmentListener newFragmentListener() {
        return new IFragmentListener() {
            @Override
            public void showFragment(String tag) {
                show(tag);
            }
        };
    }

    private void show(String tag) {
        hideAll();
        ft = fm.beginTransaction();
        switch (tag) {
            case Constant.NAVIGATION_TAB_MESSAGE:
                ft.show(mMessageFragment);
                break;
            case Constant.NAVIGATION_TAB_CONTACT:
                ft.show(mContactFragment);
                break;
            case Constant.NAVIGATION_TAB_HOME:
                ft.show(mHomeFragment);
                break;
            case Constant.NAVIGATION_TAB_AUDIO:
                ft.show(mAudioFragment);
                break;
            case Constant.NAVIGATION_TAB_VIDEO:
                ft.show(mVideoFragment);
                break;
        }
        ft.commit();
    }

    private void hideAll() {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        ft = fm.beginTransaction();
        if (!mHomeFragment.isHidden()) {
            ft.hide(mHomeFragment);
        }
        if (!mMessageFragment.isHidden()) {
            ft.hide(mMessageFragment);
        }
        if (!mVideoFragment.isHidden()) {
            ft.hide(mVideoFragment);
        }
        if (!mAudioFragment.isHidden()) {
            ft.hide(mAudioFragment);
        }
        if (!mContactFragment.isHidden()) {
            ft.hide(mContactFragment);
        }
        ft.commit();
    }
}
