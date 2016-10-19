package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.JobTypeBean;

import java.util.List;

/**
 * Created by 15859 on 2016/10/19.
 */
public class CreateJobTypeAdapter extends BaseAdapter {
    Context context;
    List<JobTypeBean.ContentBean> jobList;
    private LayoutInflater mInflater;
    public CreateJobTypeAdapter(Context context, List<JobTypeBean.ContentBean> jobList) {
        this.context = context;
        this.jobList = jobList;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public Object getItem(int position) {
        return jobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=   mInflater.inflate(R.layout.create_job_type_list_item,null);
            viewHolder.tv_jobTypeItem= (TextView) convertView.findViewById(R.id.tv_jobTypeItem);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        JobTypeBean.ContentBean bean= jobList.get(position);
        viewHolder.tv_jobTypeItem.setText(bean.getName());
        return convertView;
    }
    class ViewHolder{
        TextView tv_jobTypeItem;
    }
}
