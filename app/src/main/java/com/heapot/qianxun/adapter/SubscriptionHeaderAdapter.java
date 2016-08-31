package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.SubscriptionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/30.
 */
public class SubscriptionHeaderAdapter extends BaseAdapter {
    List<SubscriptionBean> mList = new ArrayList<>();
    LayoutInflater inflater;
    Context mContext;

    public SubscriptionHeaderAdapter(Context context, List<SubscriptionBean> mList) {
//        for (int i = 0; i < mList.size(); i++) {
//            if (mList.get(i).getStatus().equals("1")){
//                this.mList.add(mList.get(i));
//            }
//        }
        this.mList = mList;
        this.mContext = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mList.size()+1;
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
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;//头布局
        }else {
            return 0;//正常布局
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;//一个头布局一个正常布局
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        TitleViewHolder titleViewHolder;
        switch (getItemViewType(position)){
            case 0:
                if (convertView == null){
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.subscription_header_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.txt_subscription_header_item);
                    convertView.setTag(holder);
                }else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.textView.setText(mList.get(position-1).getName());
                holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.sub_item_clicked));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.color_first));
                break;
            case 1:
                if (convertView == null){
                    titleViewHolder = new TitleViewHolder();
                    convertView = inflater.inflate(R.layout.subscription_header_item, null);
                    titleViewHolder.textView = (TextView) convertView.findViewById(R.id.txt_subscription_header_item);
                    convertView.setTag(titleViewHolder);
                }else {
                    titleViewHolder = (TitleViewHolder) convertView.getTag();
                }
                int count = mList.size();
                if (count == 0){
                    titleViewHolder.textView.setText("暂无订阅");
                }else {
                    titleViewHolder.textView.setText("已订阅:");
                }
                break;
        }
        return convertView;
    }

    private class TitleViewHolder{
        TextView textView;
    }
    private class ViewHolder{
        TextView textView;
    }
}
