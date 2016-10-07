package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.heapot.qianxun.bean.TagsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/10/7.
 */
public class SortAdapter extends BaseAdapter{
    private Context context;
    private List<TagsBean.ContentBean> mList = new ArrayList<>();

    public SortAdapter(Context context, List<TagsBean.ContentBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
