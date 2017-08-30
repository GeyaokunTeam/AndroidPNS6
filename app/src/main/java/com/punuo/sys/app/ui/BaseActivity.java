package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.punuo.sys.app.Manager.DevLoginManager;
import com.punuo.sys.app.Manager.ManagerFather;
import com.punuo.sys.app.Manager.UserLoginManager;
import com.punuo.sys.app.R;
import com.punuo.sys.app.i.IActivity;

/**
 * Author chenhan
 * Date 2017/7/31
 */

public abstract class BaseActivity extends AppCompatActivity implements IActivity {
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        mView = findViewById(R.id.rootView);
        setBackground();
        init();
    }

    private void init() {
        ManagerFather.getInstance().setCurrentGroup(getActivityType());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ManagerFather.getInstance().destroy(getActivityType());
        Log.i("chenhan", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //set content view
    protected abstract void setContentView();

    //get Layout id
    protected abstract int getLayoutId();

    //set background
    protected abstract void setBackground();

    //get background drawable
    protected abstract Drawable getBackgroundDrawable();

    //get Activity type
    protected abstract String getActivityType();
}
