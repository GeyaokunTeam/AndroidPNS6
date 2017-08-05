package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.punuo.sys.app.Constant;
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
        initManager();
    }

    private void initManager() {
        navigationTabManager= new NavigationTabManager(this, (NavigationTabBar)findViewById(R.id.tablayout), Constant.NAVIGATION_TAB_HOME,null);
    }

    private void initView() {
        rootView=findViewById(R.id.rootView);
        setBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (navigationTabManager != null) {
            navigationTabManager.destroy();
        }
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
            rootView.setBackground(getBackground());
    }

    @Override
    public Drawable getBackground() {
        return getResources().getDrawable(R.drawable.main_bg);
    }
}
