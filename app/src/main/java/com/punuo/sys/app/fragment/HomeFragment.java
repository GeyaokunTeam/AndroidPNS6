package com.punuo.sys.app.fragment;

import com.punuo.sys.app.R;
import com.punuo.sys.app.Type;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class HomeFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public String getFragmentType() {
        return Type.HOMEFRAGMENT;
    }
}
