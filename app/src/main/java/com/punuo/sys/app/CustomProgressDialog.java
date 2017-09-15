package com.punuo.sys.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Author chenhan
 * Date 2017/9/15
 */

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomProgressDialog(Context context) {
        this(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customprogressdialog);
    }
}
