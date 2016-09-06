package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.DataBean;

import java.util.List;

/**
 * Created by Karl on 2016/9/1.
 */
public class SubscribedAdapter extends BaseAdapter {
    List<DataBean.SubscribedBean> mList;
    Context context;
    LayoutInflater inflater;
    private int hidePosition = AdapterView.INVALID_POSITION;

    public SubscribedAdapter(Context context,List<DataBean.SubscribedBean> mList) {
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
        if (position != hidePosition){
            holder.textView.setText(mList.get(position).getName());
        }else {
            holder.textView.setText("");
        }

        return convertView;
    }


    private class SubscribedViewHolder{
        TextView textView;
    }
    public void hideView(int Position){
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }
    public void removeView(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }
    public void showHideView(){
        hidePosition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }
    //更新拖动时的gridView
    public void swapView(int draggedPosition,int destPos){
        //从前往后移动，其他item依次前移
        if (draggedPosition < destPos){
            mList.add(destPos+1, (DataBean.SubscribedBean) getItem(draggedPosition));
            mList.remove(draggedPosition);
        }else if(draggedPosition >destPos){
            mList.add(destPos, (DataBean.SubscribedBean) getItem(draggedPosition));
            mList.remove(draggedPosition+1);
        }
        hidePosition = destPos;
        notifyDataSetChanged();
    }
}
