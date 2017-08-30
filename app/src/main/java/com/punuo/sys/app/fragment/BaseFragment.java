package com.punuo.sys.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.punuo.sys.app.Manager.HomeManager;
import com.punuo.sys.app.Manager.ManagerFather;

/**
 * Author chenhan
 * Date 2017/8/30
 */

public abstract class BaseFragment extends Fragment {
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ((ViewGroup) (mView.getParent())).removeView(mView);
            return mView;
        }
        mView = inflater.inflate(getLayoutId(), container, false);
        return mView;
    }

    public abstract int getLayoutId();

    public abstract String getFragmentType();

    private HomeManager mHomeManager;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ManagerFather.getInstance().setCurrentGroup(getFragmentType());
        init();
    }

    private void init() {
        initManager();
    }

    private void initManager() {
        mHomeManager=new HomeManager(getActivity(),null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ManagerFather.getInstance().destroy(getFragmentType());
    }
}
