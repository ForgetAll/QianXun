package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.SubscribedBean;

import java.util.List;

/**
 * Created by Karl on 2016/9/1.
 */
public class SubscribedAdapter extends BaseAdapter {
    List<SubscribedBean> mList;
    Context context;
    LayoutInflater inflater;

    public SubscribedAdapter(Context context,List<SubscribedBean> mList) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        SubscribedViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.subscription_item_subscribed,null);
            holder = new SubscribedViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.txt_subscribed);
            convertView.setTag(holder);
        }else {
            holder = (SubscribedViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getName());


        return convertView;
    }


    private class SubscribedViewHolder{
        TextView textView;
    }
}
