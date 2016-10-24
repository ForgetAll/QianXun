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
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/24.
 * 主页tab下的列表的适配器
 *
 */
public class MainTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<MainListBean.ContentBean> mList = new ArrayList<>();
    private LayoutInflater inflater;

    private static OnRecyclerViewItemClickListener mListener =null;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean isShowFooter = true;

    public MainTabAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void setList(List<MainListBean.ContentBean> mList){
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!isShowFooter){
            return TYPE_ITEM;
        }
        if (position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }
    @Override
    public int getItemCount() {
        int begin = isShowFooter?1:0;
        if (mList == null){
            return begin;
        }else {
            return  mList.size()+begin;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.layout_list_item, parent, false);
            MainTabViewHolder viewHolder = new MainTabViewHolder(view);
            return viewHolder;
        }else {
            View view = inflater.inflate(R.layout.layout_footer,parent,false);
            FooterViewHolder fv = new FooterViewHolder(view);
            return fv;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainTabViewHolder) {
            String title = mList.get(position).getTitle();
            String time = mList.get(position).getCreate_time();
            String image = mList.get(position).getImages();
            ((MainTabViewHolder) holder).mTitle.setText(title);
            ((MainTabViewHolder) holder).mTime.setText("发布时间：" + CommonUtil.getDateTime(time));
          /*  if (image.equals("")||image == null) {
                ((MainTabViewHolder) holder).imageView.setVisibility(View.GONE);
            }else {
                ((MainTabViewHolder) holder).imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(image).into(((MainTabViewHolder) holder).imageView);
            }*/
            Glide.with(context).load(image).error(R.mipmap.ic_zhanweitu).into(((MainTabViewHolder) holder).imageView);
        }
    }
    public void isShowFooter(boolean showFooter){
        this.isShowFooter = showFooter;
    }

    public boolean isShowFooter(){
        return this.isShowFooter;
    }




    /**
     * 暴露接口
     * @param listener 监听事件
     */
    public  void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mListener =listener;
    }


    public static class MainTabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle,mTime;
        private ImageView imageView;
        public MainTabViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_main_list_title);
            mTime = (TextView) itemView.findViewById(R.id.txt_main_list_time);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main_list_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onItemClick(v, this.getPosition());
            }
        }
    }
    public static class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


}
