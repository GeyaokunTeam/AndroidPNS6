package com.punuo.sys.app.Manager;

import android.content.Context;

import com.punuo.sys.app.Constant;
import com.punuo.sys.app.NavigationTabBar;
import com.punuo.sys.app.R;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class NavigationTabManager extends BaseManager {
    private Context mContext;
    private NavigationTabBar mNavigationTabBar;
    /**
     * 选中状态 文字颜色
     */
    private String checkedTextColor = "#87CEEB";
    /**
     * 未选中状态 文字颜色
     */
    private String unCheckedTextColor = "#FFFFFF";

    public NavigationTabManager(Context context, NavigationTabBar navigationTabBar, String currentTab, Object i) {
        super(context, i);
        mContext = context;
        mNavigationTabBar = navigationTabBar;
        initTab();
        setCurrentTab(currentTab);
    }

    private void initTab() {
        setTabIcon();

        mNavigationTabBar.setText(new String[]{mContext.getString(R.string.message),
                mContext.getString(R.string.contact),
                mContext.getString(R.string.home),
                mContext.getString(R.string.audio),
                mContext.getString(R.string.video)});
        mNavigationTabBar.setTextColor(checkedTextColor, unCheckedTextColor);
    }

    private void setTabIcon() {
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_MESSAGE, R.drawable.icon_message_pressed, R.drawable.icon_message_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_CONTACT, R.drawable.icon_contact_pressed, R.drawable.icon_contact_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_HOME, R.drawable.icon_home_pressed, R.drawable.icon_home_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_AUDIO, R.drawable.icon_audio_pressed, R.drawable.icon_audio_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_VIDEO, R.drawable.icon_video_pressed, R.drawable.icon_video_normal);
    }

    public void destroy() {
        if (mNavigationTabBar != null) {
            mNavigationTabBar.destroy();
            mNavigationTabBar = null;
        }
        mContext = null;
    }

    /**
     * 根据标签tag获取其index
     */
    public int getTabIndex(String tag) {
        if (mNavigationTabBar != null)
            return mNavigationTabBar.getTabIndex(tag);
        return -1;
    }

    public void setCurrentTab(String tag) {
        if (getTabIndex(tag) != -1) {
            if (mNavigationTabBar != null)
                mNavigationTabBar.switchTabUI(tag);
        }
    }
}
