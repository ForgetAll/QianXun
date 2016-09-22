package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/13.
 */
public class CreateAdapter extends RecyclerView.Adapter<CreateAdapter.CreateViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> mList = new ArrayList<>();
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener listener  = null;

    public CreateAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public CreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.create_item,parent,false);
        CreateViewHolder holder = new CreateViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CreateViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));

        if (position == 0){
            holder.txt_des.setText("畅所欲言");
        }else {
            holder.txt_des.setText("(需要公司账号才能使用)");
            holder.txt_des.setTextColor(context.getResources().getColor(R.color.create_des));
        }

        holder.itemView.setTag(position);

    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener == null){
            listener.onItemClick(v, (int) v.getTag());
        }
    }

    public class CreateViewHolder extends RecyclerView.ViewHolder{
        TextView textView,txt_des;
        public CreateViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_create_name);
            txt_des = (TextView) itemView.findViewById(R.id.txt_create_des);
        }
    }

}
