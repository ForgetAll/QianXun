package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.MyPersonalArticle;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.List;

/**
 * Created by 15859 on 2016/10/20.
 */
public class PersonalArticleRecyclerViewAdapter extends RecyclerView.Adapter<PersonalArticleRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<MyPersonalArticle.ContentBean.RowsBean> articleList;
    private LayoutInflater inflater;

    private OnRecyclerViewItemClickListener mListener = null;

    public PersonalArticleRecyclerViewAdapter(Context context, List<MyPersonalArticle.ContentBean.RowsBean> articleList) {
        this.context = context;
        this.articleList = articleList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_list_item, parent, false);
        MyViewHolder vH = new MyViewHolder(view);
        return vH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String title = articleList.get(position).getTitle();
        String image = articleList.get(position).getImages();
        holder.mTitle.setText(title);
        holder.articleType.setText("文章类型：" + articleList.get(position).getCatalog().getName());
        Log.e("适配器的数据", String.valueOf(articleList.size()));
       /* if (!TextUtils.isEmpty(image)) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(image).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }*/
        Glide.with(context).load(image).error(R.drawable.ic_default_item_background).into( holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articleList.size();

    }


    /**
     * 暴露接口
     *
     * @param listener 监听事件
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, articleType;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.txt_main_list_title);
            imageView = (ImageView) itemView.findViewById(R.id.iv_main_list_image);
            articleType = (TextView) itemView.findViewById(R.id.txt_main_list_time);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, this.getPosition());
            }
        }
    }
}
