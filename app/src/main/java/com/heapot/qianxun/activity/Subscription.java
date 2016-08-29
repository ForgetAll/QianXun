package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubscriptionAdapter;
import com.heapot.qianxun.bean.SubscriptionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class Subscription extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private TextView mCount;
    private ListView mListView;
    private ImageView mClose;
    private SubscriptionAdapter mAdapter;
    private List<SubscriptionBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        initView();
        initEvent();


    }
    private void initView(){
        mCount = (TextView) findViewById(R.id.txt_subscription);
        mListView = (ListView) findViewById(R.id.lv_subscription);
        mClose = (ImageView) findViewById(R.id.iv_subscription_close);
        mData = new ArrayList<>();
    }

    private void initEvent(){
        mListView.setOnItemClickListener(this);
        mClose.setOnClickListener(this);
        getData();
        mListView.setAdapter(mAdapter);
    }

    private void getData(){
        SubscriptionBean item;
        for (int i = 0; i < 20; i++) {
            item = new SubscriptionBean();
            item.name = "Name is" +i;
            item.status = "0";
            mData.add(item);
        }
        mAdapter = new SubscriptionAdapter(this,mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String clickItemStatus = mData.get(position).getStatus();
        if (clickItemStatus.equals("0")){
            mData.get(position).setStatus("1");
        }else {
            mData.get(position).setStatus("0");
        }

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_subscription_close:
                Subscription.this.finish();
                break;
        }
    }
}
