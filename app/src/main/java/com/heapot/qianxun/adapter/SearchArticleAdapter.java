package com.heapot.qianxun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.SearchBean;

import java.util.List;

/**
 * Created by 15859 on 2016/10/6.
 * 搜索之后的List适配器
 */
public class SearchArticleAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<SearchBean.ContentBean.RowsBean> rowsList;

    public SearchArticleAdapter(Context context, List<SearchBean.ContentBean.RowsBean> rowsList) {
        this.context = context;
        this.rowsList = rowsList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rowsList.size();
    }

    @Override
    public Object getItem(int position) {
        return rowsList.get(position);
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
            convertView = mInflater.inflate(R.layout.search_list_item, null);
            viewHolder.tv_searchItem = (TextView) convertView.findViewById(R.id.tv_searchItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchBean.ContentBean.RowsBean bean = rowsList.get(position);
        bean.getId();
        String title=bean.getHighlightTitle();
       String result1=title.replace("<em>","");
        String result2=result1.replace("</em>","");

        viewHolder.tv_searchItem.setText(result2 );
        return convertView;
    }

    private class ViewHolder {
        TextView tv_searchItem;
    }
}
