package com.heapot.qianxun.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.PersonalActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15859 on 2016/9/2.
 */
public class OrderListAdapter extends BaseAdapter {
    private List<String> mList =new ArrayList<>();
    private LayoutInflater mInflater;
     private PersonalActivity personalActivity;
    private Context context;
    public OrderListAdapter(Context context, List<String> mList) {
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
            Log.e("此处运行了。。。。。。。。。。。。。。。。","");
            viewHolder.mItemText = (TextView) convertView.findViewById(R.id.txt_main_list_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.mItemText.setText(mList.get(position).toString());
        Log.e("此处运行了。。。。。。。。。。。。。。。。",mList.get(position).toString());
        return convertView;
    }

    class ViewHolder {
        TextView mItemText;
    }
}