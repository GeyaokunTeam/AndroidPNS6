package com.punuo.sys.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.punuo.sys.app.R;
import com.punuo.sys.app.struct.MyApplicationInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Author chzjy
 * Date 2016/12/19.
 */

public class AppGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<MyApplicationInfo> myApplicationInfoList;
    private List<MyApplicationInfo> currentAppList;
    private int PAGE_SIZE = 16;
    private int currentPage;

    public AppGridViewAdapter(Context context, List<MyApplicationInfo> myApplicationInfoList, int page) {
        this.context = context;
        this.myApplicationInfoList = myApplicationInfoList;
        this.currentPage = page;
        getData();
    }

    private void getData() {
        if (currentAppList == null) {
            currentAppList = new ArrayList<>();
        } else {
            currentAppList.clear();
        }
        int start = currentPage * PAGE_SIZE;
        int end = start + PAGE_SIZE;
        for (int i = start; i < end && i < myApplicationInfoList.size(); i++) {
            currentAppList.add(myApplicationInfoList.get(i));
        }
    }

    @Override
    public int getCount() {
        return currentAppList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.labelicon, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageview.setImageDrawable(currentAppList.get(position).getIcon());
        viewHolder.textview.setText(currentAppList.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        ImageView imageview;
        TextView textview;

        ViewHolder(View view) {
            imageview = (ImageView) view.findViewById(R.id.imageview);
            textview = (TextView) view.findViewById(R.id.textview);
        }
    }
}
