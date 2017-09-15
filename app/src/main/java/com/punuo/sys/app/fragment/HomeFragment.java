package com.punuo.sys.app.fragment;

import com.punuo.sys.app.Manager.HomeManager;
import com.punuo.sys.app.R;
import com.punuo.sys.app.Type;
import com.punuo.sys.app.i.FragmentListener;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class HomeFragment extends BaseFragment {
    private String TAG = HomeFragment.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public String getFragmentType() {
        return Type.HOMEFRAGMENT;
    }

    @Override
    protected void initManager() {
        mHomeManager = new HomeManager(getActivity(), mView, newHomeFragmentListener());
    }

    FragmentListener.HomeFragmentListener newHomeFragmentListener() {
        return new FragmentListener.HomeFragmentListener() {
            @Override
            public void OnCallCenter() {

            }

            @Override
            public void OnChangePassword() {

            }

            @Override
            public void OnOpenAlbum() {

            }

            @Override
            public void OnOpenGroupTalk() {

            }

            @Override
            public void OnCheckUpdate() {

            }

            @Override
            public void OnAddApplication() {

            }
        };
    }
}
