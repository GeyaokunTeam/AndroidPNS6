package com.punuo.sys.app.Manager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TabHost;

import com.punuo.sys.app.Constant;
import com.punuo.sys.app.FragmentTabHost;
import com.punuo.sys.app.NavigationTabBar;
import com.punuo.sys.app.R;
import com.punuo.sys.app.i.OnNavigationChangeListener;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class NavigationTabManager extends BaseManager {
    private Context mContext;
    private NavigationTabBar mNavigationTabBar;
    private FragmentTabHost mTabHost;
    /**
     * 选中状态 文字颜色
     */
    private String checkedTextColor = "#87CEEB";
    /**
     * 未选中状态 文字颜色
     */
    private String unCheckedTextColor = "#FFFFFF";
    public NavigationTabManager(Context context, FragmentTabHost tabHost, NavigationTabBar navigationTabBar, String currentTab, Object iLogin) {
        super(context, iLogin);
        mContext = context;
        mTabHost = tabHost;
        mNavigationTabBar = navigationTabBar;
        initTab();
        setCurrentTab(currentTab);
    }

    private void addTab(String tag, CharSequence label, Class<?> cls, Bundle bundle) {
        if (mTabHost == null)
            throw new NullPointerException("TabHost is null,custom error");
        mTabHost.addTab(mTabHost.newTabSpec(tag).setIndicator(label),
                cls, bundle);

    }

    private void initTab() {
        mTabHost.setup(mContext, ((FragmentActivity)mContext).getSupportFragmentManager(), R.id.realtabcontent);

        try {
            addTab(Constant.NAVIGATION_TAB_MESSAGE, mContext.getString(R.string.message), Class.forName("com.punuo.sys.app.fragment.MessageFragment"), null);
            addTab(Constant.NAVIGATION_TAB_CONTACT, mContext.getString(R.string.contact), Class.forName("com.punuo.sys.app.fragment.ContactFragment"), null);
            addTab(Constant.NAVIGATION_TAB_HOME, mContext.getString(R.string.home), Class.forName("com.punuo.sys.app.fragment.HomeFragment"), null);
            addTab(Constant.NAVIGATION_TAB_AUDIO, mContext.getString(R.string.audio), Class.forName("com.punuo.sys.app.fragment.AudioFragment"), null);
            addTab(Constant.NAVIGATION_TAB_VIDEO, mContext.getString(R.string.video), Class.forName("com.punuo.sys.app.fragment.VideoFragment"), null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setTabIcon();

        mNavigationTabBar.setText(new String[]{mContext.getString(R.string.message),
                mContext.getString(R.string.contact),
                mContext.getString(R.string.home),
                mContext.getString(R.string.audio),
                mContext.getString(R.string.video)});
        mNavigationTabBar.setTextColor(checkedTextColor, unCheckedTextColor);

    }

    private void setTabIcon() {
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_MESSAGE, R.drawable.icon_home_pressed, R.drawable.icon_home_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_CONTACT, R.drawable.icon_contact_pressed, R.drawable.icon_contact_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_HOME, R.drawable.icon_home_pressed, R.drawable.icon_home_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_AUDIO, R.drawable.icon_audio_pressed, R.drawable.icon_audio_normal);
        mNavigationTabBar.setIconId(Constant.NAVIGATION_TAB_VIDEO, R.drawable.icon_video_pressed, R.drawable.icon_video_normal);
    }

    private OnNavigationChangeListener onNavigationChangeListener=new OnNavigationChangeListener() {
        @Override
        public void OnChangedListener(int current) {
            if (mTabHost != null) {
                mTabHost.setCurrentTab(current);
            }
        }
    };

    public void setTabChangeListener(TabHost.OnTabChangeListener l) {
        if (l != null)
            mTabHost.setOnTabChangedListener(l);
    }
    /**
     * 根据标签tag获取其index
     */
    public int getTabIndex(String tag) {
        if (mNavigationTabBar != null)
            return mNavigationTabBar.getTabIndex(tag);
        return -1;
    }

    /**
     * 获取当前所在标签的index
     */
    public int getCurrentTab() {
        if (mTabHost != null)
            return mTabHost.getCurrentTab();
        return 0;
    }

    public void setCurrentTab(String tag) {
        if (mTabHost != null) {
            if (getTabIndex(tag) != -1) {
                mTabHost.setCurrentTab(getTabIndex(tag));
                if (mNavigationTabBar != null)
                    mNavigationTabBar.switchTabUI(tag);
            }
        }
    }
}
