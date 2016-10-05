package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.CommonUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/24.
 * 主页tab下的列表的适配器
 *
 */
public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.MainTabViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MainListBean.ContentBean> mList = new ArrayList<>();
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mListener =null;

    public MainTabAdapter(Context context, List<MainListBean.ContentBean> mList) {
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
        String title = mList.get(position).getTitle();
        String time = mList.get(position).getCreate_time();
        String image = mList.get(position).getImages();
        holder.mTitle.setText(title);
        holder.mTime.setText("发布时间："+ CommonUtil.getDateTime(time));
        if (position ==1){
            holder.imageView.setVisibility(View.GONE);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
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
        private TextView mTitle,mTime;
        private ImageView imageView;
        public MainTabViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_main_list_title);
            mTime = (TextView) itemView.findViewById(R.id.txt_main_list_time);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main_list_image);

        }
    }
}
