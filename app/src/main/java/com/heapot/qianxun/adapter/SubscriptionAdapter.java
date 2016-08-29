package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.SubscriptionBean;

import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class SubscriptionAdapter extends BaseAdapter {
    private List<SubscriptionBean> mList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public SubscriptionAdapter(Context context, List<SubscriptionBean> mList) {
        this.mList = mList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
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
        ViewHold holder;
        if (convertView == null){
            holder = new ViewHold();
            convertView = layoutInflater.inflate(R.layout.subscription_item,null);
            holder.textView = (TextView) convertView.findViewById(R.id.txt_subscription_item);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_subscription_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHold) convertView.getTag();
        }
        String currentStatus = mList.get(position).getStatus();
        String currentName = mList.get(position).getName();
        holder.textView.setText(currentName+","+currentStatus);
        if (currentStatus.equals("1")){
            //已订阅
            holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.sub_item_clicked));
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.color_first));
            holder.textView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            holder.linearLayout.setLayoutParams(
//                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }else {
            //未订阅
            holder.textView.setBackgroundColor(mContext.getResources().getColor(R.color.sub_item));
            holder.textView.setTextColor(mContext.getResources().getColor(R.color.sub_item_clicked));
//            holder.linearLayout.setLayoutParams(
//                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            holder.textView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        }

        return convertView;
    }

    private class ViewHold{
        TextView textView;
        LinearLayout linearLayout;
    }
}
