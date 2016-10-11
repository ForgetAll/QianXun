package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.MyPersonalArticle;

import java.util.List;

/**
 * Created by 15859 on 2016/10/11.
 */
public class PersonalArticleAdapter extends BaseAdapter{
    Context context;
    List<MyPersonalArticle.ContentBean.RowsBean> articleList;
    private LayoutInflater mInflater;
    public PersonalArticleAdapter(Context context, List<MyPersonalArticle.ContentBean.RowsBean> articleList) {
        this.context=context;
        this.articleList=articleList;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_list_item, null);
            viewHolder.txt_main_list_title = (TextView) convertView.findViewById(R.id.txt_main_list_title);
            viewHolder.iv_main_list_image = (ImageView) convertView.findViewById(R.id.iv_main_list_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyPersonalArticle.ContentBean.RowsBean bean = articleList.get(position);
        bean.getId();
        viewHolder.txt_main_list_title.setText(bean.getTitle());
        String path=bean.getImages();
        Glide.with(CustomApplication.getContext()).load(path).into(viewHolder.iv_main_list_image);
        return convertView;
    }

    private class ViewHolder {
        TextView txt_main_list_title;
        ImageView iv_main_list_image;
    }
}
