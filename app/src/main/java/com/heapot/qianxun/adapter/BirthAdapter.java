package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/10/31.
 */
public class BirthAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private TextView tv_jobTypeItem;
    private int[] birth;

    public BirthAdapter(Context context, int[] birth) {
        this.context=context;
        this.birth=birth;
        inflater=  LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return birth.length;
    }

    @Override
    public Object getItem(int position) {
        return birth[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=  inflater.inflate(R.layout.create_job_type_list_item,null);
        tv_jobTypeItem=(TextView)  view.findViewById(R.id.tv_jobTypeItem);
        tv_jobTypeItem.setText( String.valueOf(birth[position]));
        return view;
    }
}
