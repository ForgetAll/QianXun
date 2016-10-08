package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.UserOrgBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15859 on 2016/9/2.
 */
public class OrderListAdapter extends BaseAdapter {
    private List<UserOrgBean.ContentBean> mList =new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    public OrderListAdapter(Context context, List<UserOrgBean.ContentBean> mList) {
       this.mList= mList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
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
         ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_list_item, null);
            viewHolder.mItemText = (TextView) convertView.findViewById(R.id.txt_main_list_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        String id=mList.get(position).getOrgId();
        viewHolder.mItemText.setText(id);
        return convertView;
    }

    class ViewHolder {
        TextView mItemText;
    }
}