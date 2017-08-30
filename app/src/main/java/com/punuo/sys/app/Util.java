package com.punuo.sys.app;

import android.content.Context;
import android.widget.Toast;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class Util {
    public static void showToast(Context mContext, String content) {
        Toast.makeText(mContext.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }
}
