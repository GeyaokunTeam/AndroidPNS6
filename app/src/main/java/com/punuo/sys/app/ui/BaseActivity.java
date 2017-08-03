package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.punuo.sys.app.R;
import com.punuo.sys.app.i.IActivity;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public class BaseActivity extends FragmentActivity implements IActivity {
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.base_layout);
        rootView = findViewById(R.id.rootView);
    }

    public void setBackground(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public Drawable getBackground() {
        return null;
    }
}
