package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by Karl on 2016/9/24.
 * 主页tab下的列表的适配器
 *
 */
public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.MainTabViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> mList;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mListener =null;

    public MainTabAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MainTabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_list_item,parent,false);
        MainTabViewHolder viewHolder = new MainTabViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainTabViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).toString());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mListener != null){
            mListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    /**
     * 暴露接口
     * @param listener 监听事件
     */
    public  void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mListener =listener;
    }

    public static class MainTabViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MainTabViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
