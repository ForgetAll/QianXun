package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.helper.ItemTouchHelperAdapter;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by Karl on 2016/9/8.
 * 拖拽页面适配器
 */
public class DragAdapter extends RecyclerView.Adapter<DragAdapter.DragViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<SubBean> mList;

    public DragAdapter(Context context, List<SubBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public DragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subscription_item,parent,false);
        DragViewHolder holder = new DragViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DragViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
//        Logger.json(JsonUtil.toJson(mList));
        PreferenceUtil.putString("Subscribed",JsonUtil.toJson(mList));
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public static class DragViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public DragViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_subscribed_item);
        }
    }
}
