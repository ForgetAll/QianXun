package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 * desc
 */

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.TrainListViewHolder> {

    private Context mContext;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private OnTrainListItemClickListener mListener;

    public TrainListAdapter(Context mContext, List<MainListBean.ContentBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setData(List<MainListBean.ContentBean> mList){
        this.mList = mList;
    }

    public void setOnTrainListItemClickListener(OnTrainListItemClickListener listener){

        this.mListener = listener;
    }

    @Override
    public TrainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView =
                LayoutInflater.from(mContext).inflate(R.layout.fragment_train_list_item,parent,false);
        TrainListViewHolder holder = new TrainListViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrainListViewHolder holder, int position) {
        if (holder instanceof TrainListViewHolder){
            String title = mList.get(position).getTitle();
            String time = mList.get(position).getCreate_time();
            String icon = mList.get(position).getImages();
            holder.mTitle.setText(title);
            holder.mTime.setText("发布时间："+ CommonUtil.getDateTime(time));
            Glide.with(mContext).load(icon).error(R.drawable.ic_default_item_background).into(holder.mIcon);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null? 0 : mList.size();
    }

    public class TrainListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;

        TextView mTime;

        ImageView mIcon;

        public TrainListViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.txt_item_train_title);

            mTime = (TextView) itemView.findViewById(R.id.txt_item_train_subtitle);

            mIcon = (ImageView) itemView.findViewById(R.id.txt_item_train_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onItemClick(v,this.getPosition());
            }
        }
    }

    public interface OnTrainListItemClickListener{

        void onItemClick(View view, int position);

    }

}
