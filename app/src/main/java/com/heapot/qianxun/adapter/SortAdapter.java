package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
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
        return mList==null?0:mList.size();
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
        SortViewHolder holder;
        if (convertView == null){
            holder = new SortViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_sort_item,parent,false);
            holder.textView = (TextView) convertView.findViewById(R.id.txt_tags_item);
            convertView.setTag(holder);
        }else {
            holder = (SortViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getName());
        return convertView;
    }
    public class SortViewHolder{
        TextView textView;
    }
}
