package com.punuo.sys.app.Manager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.punuo.sys.app.R;
import com.punuo.sys.app.adapter.AppGridViewAdapter;
import com.punuo.sys.app.adapter.ApplicationAdapter;
import com.punuo.sys.app.i.FragmentListener;
import com.punuo.sys.app.struct.MyApplicationInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author chenhan
 * Date 2017/8/30
 */

public class HomeManager extends BaseManager {
    private String TAG = HomeManager.class.getSimpleName();
    ViewPager mViewPager;
    private Context mContext;
    private ApplicationsIntentReceiver AppReceiver;
    private List<MyApplicationInfo> mApplications;
    private int[] moduleIcons = new int[]{
            R.drawable.home_call_center,
            R.drawable.home_change_psd,
            R.drawable.home_album,
            R.drawable.home_chs_change,
            R.drawable.home_soft_update
    };
    private String[] title = new String[]{
            "呼叫平台",
            "修改密码",
            "我的相册",
            "集群呼叫",
            "软件更新",
            "添加应用"
    };
    int screenCount;
    public final int NUMBER_PER_SCREEN = 16;
    private List<GridView> gridViewList;
    private AppGridViewAdapter mGridViewAdapter;
    private ApplicationAdapter mApplicationAdapter;
    private FragmentListener.HomeFragmentListener mHomeFragmentListener;

    public HomeManager(Context context, View view, FragmentListener.HomeFragmentListener listener) {
        super();
        mContext = context;
        initView(view);
        this.mHomeFragmentListener = listener;
    }

    @Override
    public void destroy() {
        super.destroy();
        Log.i("HomeManager", "destroy");
        unregisterIntentReceivers();
        if (mContext != null) {
            mContext = null;
        }
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        registerIntentReceivers();
        bindApplications();
    }

    private void registerIntentReceivers() {
        IntentFilter filter;
        filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        AppReceiver = new ApplicationsIntentReceiver();
        mContext.registerReceiver(AppReceiver, filter);
    }
    private void unregisterIntentReceivers() {
        mContext.unregisterReceiver(AppReceiver);
    }
    private class ApplicationsIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadApplications(false);
            bindApplications();
        }
    }

    private void loadApplications(boolean isLaunching) {
        if (isLaunching && mApplications != null) {
            return;
        }
        //获取所有app的入口
        PackageManager manager = mContext.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        if (mApplications == null) {
            mApplications = new ArrayList<>();
        }
        mApplications.clear();
        for (int i = 0; i < moduleIcons.length; i++) {
            MyApplicationInfo application = new MyApplicationInfo();
            application.setType(MyApplicationInfo.TYPE_BUTTON);
            application.setTitle(title[i]);
            application.setIcon(mContext.getDrawable(moduleIcons[i]));
            application.setSystemApp(false);
            mApplications.add(application);
        }
        int count = apps.size();
        for (int i = 0; i < count; i++) {
            MyApplicationInfo application = new MyApplicationInfo();
            ResolveInfo info = apps.get(i);

            if (info.loadLabel(manager).equals("设置") || info.loadLabel(manager).equals("文件管理")
                    || (info.activityInfo.applicationInfo.packageName.contains("com.punuo.sys.app")
                    && !info.activityInfo.applicationInfo.packageName.contains("com.punuo.sys.app.xungeng"))) {
                application.setType(MyApplicationInfo.TYPE_APP);
                application.setTitle(info.loadLabel(manager));
                application.packageName = info.activityInfo.applicationInfo.packageName;
                application.setActivity(new ComponentName(
                                info.activityInfo.applicationInfo.packageName,
                                info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                switch (info.loadLabel(manager).toString()) {
                    case "设置":
                        application.setIcon(mContext.getDrawable(R.drawable.home_setting));
                        break;
                    case "文件管理":
                        application.setIcon(mContext.getDrawable(R.drawable.home_filemanager));
                        break;
                    default:
                        application.setIcon(info.activityInfo.loadIcon(manager));
                        break;
                }
                application.setSystemApp((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
                mApplications.add(application);
            }
        }
        MyApplicationInfo applicationInfo = new MyApplicationInfo();
        applicationInfo.setType(MyApplicationInfo.TYPE_BUTTON);
        applicationInfo.setTitle("添加应用");
        applicationInfo.setIcon(mContext.getDrawable(R.drawable.home_add));
        applicationInfo.setSystemApp(false);
        mApplications.add(applicationInfo);
    }

    private void bindApplications() {
        loadApplications(true);
        screenCount = mApplications.size() % NUMBER_PER_SCREEN == 0 ?
                mApplications.size() / NUMBER_PER_SCREEN :
                mApplications.size() / NUMBER_PER_SCREEN + 1;
        mViewPager.removeAllViews();
        gridViewList = new ArrayList<>();
        for (int i = 0; i < screenCount; i++) {
            GridView gv = new GridView(mContext);
            mGridViewAdapter = new AppGridViewAdapter(mContext, mApplications, i);
            gv.setAdapter(mGridViewAdapter);
            gv.setGravity(Gravity.CENTER);
            gv.setNumColumns(4);
            gv.setClickable(true);
            gv.setFocusable(true);
            gv.setColumnWidth(120);
            gv.setVerticalSpacing(60);
            final int finalI = i;
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View vie, int position, long id) {
                    final MyApplicationInfo currentAppInfo = mApplications.get(position + finalI * NUMBER_PER_SCREEN);
                    if (currentAppInfo.getType() == MyApplicationInfo.TYPE_BUTTON) {
                        //按钮功能
                        switch (currentAppInfo.getTitle().toString()) {
                            case "呼叫平台":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnCallCenter();
                                }
                                break;
                            case "修改密码":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnChangePassword();
                                }
                                break;
                            case "我的相册":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnOpenAlbum();
                                }
                                break;
                            case "集群呼叫":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnOpenGroupTalk();
                                }
                                break;
                            case "软件更新":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnCheckUpdate();
                                }
                                break;
                            case "添加应用":
                                if (mHomeFragmentListener != null) {
                                    mHomeFragmentListener.OnAddApplication();
                                }
                                break;
                        }
                    } else {
                        //第三方app或者系统app
                        mContext.startActivity(currentAppInfo.getIntent());
                    }
                }
            });
            gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final MyApplicationInfo currentAppInfo = mApplications.get(position + finalI * NUMBER_PER_SCREEN);
                    System.out.println(currentAppInfo.getTitle() + "" + currentAppInfo.isSystemApp());
                    if (!currentAppInfo.isSystemApp()) {//是否为系统app
                        if (currentAppInfo.getType() == MyApplicationInfo.TYPE_APP) {//是否为第三方app
                            Uri currentAppUri = Uri.parse("package:" + currentAppInfo.packageName);
                            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, currentAppUri);
                            mContext.startActivity(uninstallIntent);
                        }
                    } else {
                        //显示应用信息
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", currentAppInfo.packageName, null);
                        intent.setData(uri);
                        mContext.startActivity(intent);
                    }
                    return true;
                }
            });
            gridViewList.add(gv);
        }
        mApplicationAdapter = new ApplicationAdapter(gridViewList);
        mViewPager.setAdapter(mApplicationAdapter);
    }
}
