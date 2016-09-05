package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.RecommendAdapter;
import com.heapot.qianxun.adapter.SubscribedAdapter;
import com.heapot.qianxun.bean.RecommendBean;
import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.tools.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 */
public class Subscription extends Activity {
    private ListView recommend;
    private MyGridView subscribed;
    private List<SubscribedBean> subscribedBeanList = new ArrayList<>();
    private List<RecommendBean> recommendBeanList = new ArrayList<>();
    private RecommendAdapter recommendAdapter;
    private SubscribedAdapter subscribedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
    }
    private void initView(){
        recommend = (ListView) findViewById(R.id.lv_recommend);
        subscribed = (MyGridView) findViewById(R.id.gv_subscribed);
        recommend.setDividerHeight(10);
        getData();
        recommend.setAdapter(recommendAdapter);
        subscribed.setAdapter(subscribedAdapter);
        setListViewHeight(recommend);
        subscribed.setExpanded(true);
//        subscribed.setFocusable(false);

    }

    private void getData(){
        RecommendBean recommendBean;
        for (int i = 0; i < 30; i++) {
            recommendBean = new RecommendBean();
            recommendBean.name = "推荐标签 #"+i;
            recommendBean.status = 0;
            recommendBeanList.add(recommendBean);
        }
        SubscribedBean subscribedBean;
        for (int i = 0; i < 8; i++) {
            subscribedBean = new SubscribedBean();
            subscribedBean.setName("已订阅标签 #"+i);
            subscribedBeanList.add(subscribedBean);
        }
        subscribedAdapter = new SubscribedAdapter(this,subscribedBeanList);
        subscribedAdapter.notifyDataSetChanged();

        recommendAdapter = new RecommendAdapter(this,recommendBeanList);
        recommendAdapter.notifyDataSetChanged();
    }

    /**
     * 手动测量ListView高度
     * @param listView
     */
    private static void setListViewHeight(ListView listView){
        if (listView == null){
            return;
        }
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null){
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i,null,listView);
            item.measure(0,0);
            totalHeight += item.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params =listView.getLayoutParams();
        params.height = totalHeight+(listView.getDividerHeight()*(adapter.getCount()-1));
        listView.setLayoutParams(params);
    }

}
