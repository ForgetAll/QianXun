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
import com.heapot.qianxun.bean.DragBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/8.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private Context context;
    private List<DragBean> mList = new ArrayList<>();

    public ContentAdapter(Context context, List<DragBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subscription_item,parent,false);
        ContentViewHolder holder = new ContentViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        int status = mList.get(position).getStatus();
        String name = mList.get(position).getName();
        holder.textView.setText(name);
        if (status == 0){
            holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_white));
            holder.textView.setGravity(Gravity.CENTER);
        }else if (status ==1){
            holder.textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            holder.textView.setBackgroundColor(context.getResources().getColor(R.color.background_white));
            holder.textView.setGravity(Gravity.CENTER);

        }

    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ContentViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_subscribed_item);
        }
    }
}
