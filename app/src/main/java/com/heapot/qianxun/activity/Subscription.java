package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubscriptionAdapter;
import com.heapot.qianxun.adapter.SubscriptionHeaderAdapter;
import com.heapot.qianxun.bean.SubscriptionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 *
 */
public class Subscription extends Activity {
    private ListView mListView;
    private List<SubscriptionBean> mData = new ArrayList<>();
    private List<SubscriptionBean> mHeaderData = new ArrayList<>();
    private GridView gridView;
    private SubscriptionAdapter mAdapter;
    private SubscriptionHeaderAdapter headerAdapter;
//    private static final int


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        initView();
        initEvent();


    }
    private void initView(){
        mListView = (ListView) findViewById(R.id.lv_subscription);
        gridView = (GridView) findViewById(R.id.gv_subscription_header);
        getData();
        gridView.setAdapter(headerAdapter);
        mListView.setAdapter(mAdapter);
    }

    private void initEvent(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clickItemStatus = mData.get(position).getStatus();
                if (clickItemStatus == 0){
                    mData.get(position).setStatus(1);
                }else {
                    mData.get(position).setStatus(0);
                }
                mHeaderData.clear();
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getStatus() == 1){
                        mHeaderData.add(mData.get(i));
                    }
                }
                headerAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clickItemStatus = mData.get(position-1).getStatus();
                if (clickItemStatus == 0){
                    mData.get(position-1).setStatus(1);
                }else {
                    mData.get(position-1).setStatus(0);
                }
                mHeaderData.clear();
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getStatus() == 1){
                        mHeaderData.add(mData.get(i));
                    }
                }
                headerAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getData(){
        SubscriptionBean item;
        for (int i = 0; i < 20; i++) {
            item = new SubscriptionBean();
            item.name = "Name is" +i;
            item.status = 0;
            mData.add(item);
        }
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getStatus() == 1){
                mHeaderData.add(mData.get(i));
            }
        }
        headerAdapter = new SubscriptionHeaderAdapter(this,mHeaderData);
        headerAdapter.notifyDataSetChanged();

        mAdapter = new SubscriptionAdapter(this,mData);
        mAdapter.notifyDataSetChanged();
    }

}
