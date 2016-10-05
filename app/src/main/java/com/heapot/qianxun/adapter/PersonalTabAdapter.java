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
 * Created by 15859 on 2016/10/5.
 *个人中心 tab下的列表的适配器
 */
public class PersonalTabAdapter extends RecyclerView.Adapter<PersonalTabAdapter.PersonalViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> mList;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mListener =null;
    public PersonalTabAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PersonalTabAdapter.PersonalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_list_item,parent,false);
        PersonalViewHolder viewHolder = new PersonalViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonalTabAdapter.PersonalViewHolder holder, int position) {
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
    public static class PersonalViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public PersonalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_main_list_title);
        }
    }

}
