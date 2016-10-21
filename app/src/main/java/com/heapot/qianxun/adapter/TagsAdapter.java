package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/8.
 * 标签订阅不可拖拽页面
 *
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.SubscribedViewHolder> implements View.OnClickListener {
    private Context context;
    private List<TagsBean.ContentBean> mList = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public TagsAdapter(Context context, List<TagsBean.ContentBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public SubscribedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subscription_item,parent,false);
        SubscribedViewHolder holder = new SubscribedViewHolder(view);

        //创建View的点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(SubscribedViewHolder holder, int position) {
        int status = mList.get(position).getSubscribeStatus();
        String name = mList.get(position).getName();
        String desc = mList.get(position).getDescription().toString();

        //状态为0是未订阅
        if (status == 0){
            holder.textView.setText(desc);
            holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_white));
            holder.textView.setTextColor(context.getResources().getColor(R.color.background_blue));
            holder.textView.setGravity(Gravity.CENTER);
        }else if (status ==1){//状态为1，是已订阅
            holder.textView.setText(name);
            holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_blue));
            holder.textView.setTextColor(context.getResources().getColor(R.color.background_white));
            holder.textView.setGravity(Gravity.CENTER);
        }

        //将数据保存在itemView的tag中，以便点击时获取
        holder.itemView.setTag(position);

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public static class SubscribedViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SubscribedViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_subscribed_item);
        }
    }
}
