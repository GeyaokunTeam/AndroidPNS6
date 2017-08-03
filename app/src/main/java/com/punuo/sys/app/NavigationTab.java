package com.punuo.sys.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Author chenhan
 * Date 2017/8/3
 */

public class NavigationTab extends RelativeLayout implements GestureDetector.OnDoubleTapListener {
    private final String TAG = NavigationTab.class.getSimpleName();

    private TextView mLabel;
    private ImageView mIcon;
    private ImageView mFlag;
    private TextView mCountFlag;

    private int checkedIconId;
    private int unCheckedIconId;

    private String mTag;
    protected int mFlagCount;

    private final int REFRESH = 3;
    private final int HANDLE_TEXT = 2;
    /**
     * 选中状态 文字颜色
     */
    private String checkedTextColor = "#87CEEB";
    /**
     * 未选中状态 文字颜色
     */
    private String unCheckedTextColor = "#FFFFFF";

    OnNavigationCheckedListener oncLs;

    private boolean isChecked;

    public NavigationTab(Context context) {
        super(context);
        init();
    }

    public NavigationTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationTab(Context context, AttributeSet attrs,
                         int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.navigation_tab, null);
        addView(mView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mLabel = (TextView) findViewById(R.id.label);
        mIcon = (ImageView) findViewById(R.id.img);
        mFlag = (ImageView) findViewById(R.id.flag);
        mCountFlag = (TextView) findViewById(R.id.count);

        mLabel.setTextColor(Color.parseColor(unCheckedTextColor));


    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
                case REFRESH:
                    refresh();
                    break;
                case HANDLE_TEXT:
                    handSetText((String) msg.obj);
                    break;
            }
            return true;
        }
    });

    private void refresh() {
        if (isChecked()) {
            mLabel.setTextColor(Color.parseColor(checkedTextColor));
            if (mIcon.getVisibility() == VISIBLE)
                mIcon.setBackgroundResource(checkedIconId);

        } else {
            mLabel.setTextColor(Color.parseColor(unCheckedTextColor));
            if (mIcon.getVisibility() == VISIBLE)
                mIcon.setBackgroundResource(unCheckedIconId);

        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (oncLs != null) {
            oncLs.OnCheckedListener(NavigationTab.this, NavigationTab.this.getId());
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    public interface OnNavigationCheckedListener {
        void OnCheckedListener(NavigationTab tab, int id);
    }

    public void setOnNavigationCheckedListener(OnNavigationCheckedListener oncLs) {
        this.oncLs = oncLs;
    }

    public String getTagTag() {
        return mTag;
    }

    public void setTabTag(String mTag) {
        this.mTag = mTag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isCheck) {
        this.isChecked = isCheck;
    }

    public int getCheckedIconId() {
        return checkedIconId;
    }

    public void setCheckedIconId(int checkedIconId) {
        this.checkedIconId = checkedIconId;
        if (isChecked)
            mHandler.sendEmptyMessage(REFRESH);
    }

    public int getUnCheckedIconId() {
        return unCheckedIconId;
    }

    public void setUnCheckedIconId(int unCheckedIconId) {
        this.unCheckedIconId = unCheckedIconId;
        if (!isChecked)
            mHandler.sendEmptyMessage(REFRESH);
    }

    public void setText(String text) {
        mHandler.obtainMessage(HANDLE_TEXT, text).sendToTarget();
    }

    public void setText(int id) {
        mHandler.obtainMessage(HANDLE_TEXT, getContext().getString(id)).sendToTarget();
    }
    private void handSetText(String text) {
        mLabel.setText(text);
    }

    public String getCheckedTextColor() {
        return checkedTextColor;
    }

    public void setCheckedTextColor(String checkedTextColor) {
        this.checkedTextColor = checkedTextColor;
        if (isChecked)
            mHandler.sendEmptyMessage(REFRESH);

    }

    public String getUnCheckedTextColor() {
        return unCheckedTextColor;
    }

    public void setUnCheckedTextColor(String unCheckedTextColor) {
        this.unCheckedTextColor = unCheckedTextColor;
        if (!isChecked)
            mHandler.sendEmptyMessage(REFRESH);
    }
}
