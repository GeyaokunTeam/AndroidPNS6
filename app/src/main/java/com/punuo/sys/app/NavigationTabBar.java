package com.punuo.sys.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.punuo.sys.app.i.OnNavigationChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class NavigationTabBar extends RelativeLayout implements NavigationTab.OnNavigationCheckedListener {

    private final String TAG = NavigationTabBar.class.getSimpleName();
    private View topView;
    private List<NavigationTab> mTabList;

    private OnNavigationChangeListener mNCLs;

    public NavigationTabBar(Context context) {
        super(context);
        init();
    }

    public NavigationTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.navigation_tab_layout, null);
        addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        topView = findViewById(R.id.top_view);
        NavigationTab tab1 = (NavigationTab) mView.findViewById(R.id.tab1);
        NavigationTab tab2 = (NavigationTab) mView.findViewById(R.id.tab2);
        NavigationTab tab3 = (NavigationTab) mView.findViewById(R.id.tab3);
        NavigationTab tab4 = (NavigationTab) mView.findViewById(R.id.tab4);
        NavigationTab tab5 = (NavigationTab) mView.findViewById(R.id.tab5);

        tab1.setTabTag(Constant.NAVIGATION_TAB_MESSAGE);
        tab2.setTabTag(Constant.NAVIGATION_TAB_CONTACT);
        tab3.setTabTag(Constant.NAVIGATION_TAB_HOME);
        tab4.setTabTag(Constant.NAVIGATION_TAB_AUDIO);
        tab5.setTabTag(Constant.NAVIGATION_TAB_VIDEO);

        mTabList = new ArrayList<NavigationTab>();
        mTabList.add(tab1);
        mTabList.add(tab2);
        mTabList.add(tab3);
        mTabList.add(tab4);
        mTabList.add(tab5);

        tab3.setChecked(true);
        for (NavigationTab nt : mTabList)
            nt.setOnNavigationCheckedListener(this);
    }

    @Override
    public void OnCheckedListener(NavigationTab tab, int id) {
        Log.d(TAG, "OnCheckedListener: id = " + id);
        int count = ((LinearLayout) getChildAt(0)).getChildCount();
        Log.d(TAG, "getChildCount = " + count);

        //重置所有 tab check的状态
        for (int i = 0; i < count; i++) {
            View linear = ((LinearLayout) getChildAt(0)).getChildAt(i);
            if (linear != null && linear instanceof NavigationTab) {
                NavigationTab child = (NavigationTab) linear;
                //如果是被check的tab
                if (id == child.getId())
                    if (mNCLs != null) {
                        mNCLs.OnChangedListener(i);
                    }
                child.setChecked(false);
            }
        }
        tab.setChecked(true);
    }

    public void setIconId(String tag, int checkedId, int unCheckedId) {
        NavigationTab nt = getNavigationTab(tag);
        if (nt != null) {
            nt.setCheckedIconId(checkedId);
            nt.setUnCheckedIconId(unCheckedId);
        }
    }

    public NavigationTab getNavigationTab(String tag) {
        int index = getTabIndex(tag);
        if (index != -1 && mTabList != null) {
            return mTabList.get(index);
        }
        return null;
    }

    public int getTabIndex(String tag) {
        if (mTabList == null)
            return -1;
        for (int i = 0; i < mTabList.size(); i++) {
            if (mTabList.get(i).getTagTag().equals(tag)) {
                return i;
            }
        }
        return -1;
    }

    public void switchTabUI(String tag) {
        int count = ((LinearLayout) getChildAt(0)).getChildCount();
        for (int i = 0; i < count; i++) {
            View linear = ((LinearLayout) getChildAt(0)).getChildAt(i);
            if (linear != null && linear instanceof NavigationTab) {
                NavigationTab child = (NavigationTab) linear;
                if (child.getTagTag().equals(tag)) {
                    child.setChecked(true);
                } else {
                    child.setChecked(false);
                }
            }
        }
    }

    public void setText(String[] text) {
        if (mTabList == null || text == null)
            return;
        for (int i = 0; i < text.length; i++) {
            if (mTabList.get(i) != null)
                mTabList.get(i).setText(text[i]);
        }
    }

    public void setText(int[] text) {
        if (mTabList == null || text == null)
            return;
        for (int i = 0; i < text.length; i++) {
            mTabList.get(i).setText(text[i]);
        }
    }

    public void setTextColor(String checkedId, String unCheckedId) {
        if (mTabList == null)
            return;
        for (NavigationTab nt : mTabList) {
            nt.setCheckedTextColor(checkedId);
            nt.setUnCheckedTextColor(unCheckedId);
        }
    }
}
