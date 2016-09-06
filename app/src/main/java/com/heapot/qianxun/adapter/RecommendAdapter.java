package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.RecommendBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/1.
 */
public class RecommendAdapter extends BaseAdapter {
    private Context context;
    private List<RecommendBean> mList = new ArrayList<>();
    private LayoutInflater inflater;

    public RecommendAdapter( Context context,List<RecommendBean> mList) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = mList.size();
        Log.d("TAG","Size = "+count);
        return count;
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
        RecommendViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.subscription_item_recommend,null);
            holder = new RecommendViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.txt_recommend);
            convertView.setTag(holder);
        }else {
            holder = (RecommendViewHolder) convertView.getTag();
        }
        String name = mList.get(position).getName();
        Log.d("TAG",name);
        holder.textView.setText(name);
        int status = mList.get(position).getStatus();
        if (status == 0){
            holder.textView.setTextColor(context.getResources().getColor(R.color.background_blue));
            holder.textView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_white));
        }else {
            holder.textView.setTextColor(convertView.getResources().getColor(R.color.background_white));
            holder.textView.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_blue));
        }
        return convertView;
    }

    private class RecommendViewHolder{
        TextView textView;
    }
}
