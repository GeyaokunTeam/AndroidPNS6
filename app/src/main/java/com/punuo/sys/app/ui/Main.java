package com.punuo.sys.app.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.punuo.sys.app.R;

/**
 * Author chenhan
 * Date 2017/8/1
 */

public class Main extends BaseActivity {
    private static final String TAG = Main.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        setBackground();
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
        Drawable drawable=getBackground();
        rootView.setBackground(drawable);
        drawable=null;
    }

    @Override
    public Drawable getBackground() {
        return getResources().getDrawable(R.drawable.kk_date_vert_fragment_background);
    }
}
