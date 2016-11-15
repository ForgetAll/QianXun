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
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/11.
 * desc: 主页文章列表的适配器
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder>{

    private Context context;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private OnArticleListItemClickListener mListener;

    public ArticleListAdapter(Context context, List<MainListBean.ContentBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setData(List<MainListBean.ContentBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public void setOnArticleListClickListener(OnArticleListItemClickListener listClickListener){
        this.mListener = listClickListener;
    }

    @Override
    public ArticleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_article_list_item,parent,false);

        ArticleListViewHolder holder = new ArticleListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleListViewHolder holder, int position) {
        if (holder instanceof  ArticleListViewHolder){
            String title = mList.get(position).getTitle();
            String time = mList.get(position).getCreate_time();
            String image = mList.get(position).getImages();
            holder.mTitle.setText(title);
            holder.mTime.setText("发布时间：" + CommonUtil.getDateTime(time));
            Glide.with(context).load(image).error(R.drawable.ic_default_item_background).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() ;
    }

    public class ArticleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle,mTime;
        private ImageView imageView;

        public ArticleListViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_item_article_title);
            mTime = (TextView) itemView.findViewById(R.id.txt_item_article_subtitle);
            imageView = (ImageView) itemView.findViewById(R.id.txt_item_article_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                mListener.onItemClick(v,this.getPosition());
            }
        }
    }

    public interface OnArticleListItemClickListener{

        void onItemClick(View view, int position);
    }
}
