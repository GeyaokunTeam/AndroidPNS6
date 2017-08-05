package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.punuo.sys.app.i.IActivity;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public abstract class BaseActivity extends FragmentActivity implements IActivity {
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public abstract void setBackground();

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
