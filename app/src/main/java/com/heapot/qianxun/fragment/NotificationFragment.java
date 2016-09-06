package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.heapot.qianxun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class NotificationFragment extends Fragment {
    public static final String NOTIFICATION_PAGE = "NOTIFICATION_PAGE";
    private int mPage;

    private ListView listView;
    private NotificationListAdapter adapter;
    private List<String> list;


    public static NotificationFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(NOTIFICATION_PAGE,page);
        NotificationFragment notificationFragment = new NotificationFragment();
        notificationFragment.setArguments(args);
        return notificationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(NOTIFICATION_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View nView = inflater.inflate(R.layout.notification_list,container,false);
        listView = (ListView) nView.findViewById(R.id.lv_notification);
        list = new ArrayList<>();
        getData();
        listView.setAdapter(adapter);
        return nView;
    }
    private void getData(){
        for (int i = 0; i < 20; i++) {
            list.add("Tab #"+mPage+" Item #"+i);
        }
        adapter = new NotificationListAdapter(list);

    }

    private class NotificationListAdapter<String> extends BaseAdapter{
        private List<String> mList;

        public NotificationListAdapter(List<String> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_item_01,null);
                holder.textView = (TextView) convertView.findViewById(R.id.txt_item_content_notification);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mList.get(position).toString());
            return convertView;
        }
        private class ViewHolder{
            TextView textView;
        }
    }
}
