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
 */
public class SearchArticleAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<SearchBean.ContentBean.RowsBean> rowsBeen;

    public SearchArticleAdapter(Context context, List<SearchBean.ContentBean.RowsBean> rowsBeen) {
        this.context = context;
        this.rowsBeen = rowsBeen;
    }

    @Override
    public int getCount() {
        return rowsBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return rowsBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.search_list_item, null);
         viewHolder.tv_searchItem= (TextView) convertView.findViewById(R.id.tv_searchItem);
            convertView.setTag(viewHolder);
        } else {
    viewHolder= (ViewHolder) convertView.getTag();
        }
        SearchBean.ContentBean.RowsBean bean=rowsBeen.get(position);
        bean.getId();
        viewHolder.tv_searchItem.setText(bean.getHighlightTitle());
        return convertView;
    }

    private class ViewHolder {
        TextView tv_searchItem;
    }
}
