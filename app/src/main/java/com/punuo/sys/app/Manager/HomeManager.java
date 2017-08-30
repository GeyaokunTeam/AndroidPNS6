package com.punuo.sys.app.Manager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Author chenhan
 * Date 2017/8/30
 */

public class HomeManager extends BaseManager {
    public HomeManager(Context context, @Nullable Object iLogin) {
        super(context, iLogin);
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.i("HomeManager", "destroy: ");
    }
}
