package com.punuo.sys.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class Util {
    public static void showToast(final Context mContext, final String content) {
        new Handler(mContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param view 最外层布局
     * @param scroll 依赖控件
     */
    public static void addLayoutListener(final View view, final View scroll) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                view.getWindowVisibleDisplayFrame(rect);//rect为输出参数,因此rect不允许为null
                int mainInvisibleHeight = view.getRootView().getHeight() - rect.bottom;
                System.out.println(scroll.getBottom());
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationOnScreen(location);//输入参数必须是一个长度为2的int数组
                    int scrollHeight = (location[1] + scroll.getHeight() - rect.bottom)+dip2px(10);
                    view.scrollTo(0, scrollHeight);
                } else {
                    view.scrollTo(0, 0);
                }

            }
        });
    }

    public static int dip2px(float dipValue) {
        final float scale = BaseApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) BaseApp.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
