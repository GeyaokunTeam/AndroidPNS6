package com.punuo.sys.app.fragment;

import com.punuo.sys.app.R;
import com.punuo.sys.app.Type;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class AudioFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.audio_fragment_layout;
    }

    @Override
    public String getFragmentType() {
        return Type.AUDIOFRAGMENT;
    }
}
