package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.punuo.sys.app.Constant;
import com.punuo.sys.app.FragmentTabHost;
import com.punuo.sys.app.Manager.NavigationTabManager;
import com.punuo.sys.app.NavigationTabBar;
import com.punuo.sys.app.R;

/**
 * Author chenhan
 * Date 2017/8/1
 */

public class Main extends BaseActivity {
    private static final String TAG = Main.class.getSimpleName();
    NavigationTabManager navigationTabManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        setBackground();
        initManager();
    }

    private void initManager() {
        navigationTabManager= new NavigationTabManager(this,(FragmentTabHost) findViewById(android.R.id.tabhost),
                (NavigationTabBar)findViewById(R.id.tablayout), Constant.NAVIGATION_TAB_HOME,null);
        navigationTabManager.setTabChangeListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i(TAG, "onTabChanged: "+ tabId);
            }
        });
    }

    private void initView() {
        rootView=findViewById(R.id.rootView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void setBackground() {

    }

    @Override
    public Drawable getBackground() {
        return null;
    }
}
