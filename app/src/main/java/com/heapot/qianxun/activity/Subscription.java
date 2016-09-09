package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubscribedAdapter;
import com.heapot.qianxun.adapter.DragAdapter;
import com.heapot.qianxun.bean.DragBean;
import com.heapot.qianxun.helper.ItemTouchHelperCallback;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 */
public class Subscription extends Activity  {
    private RecyclerView drag, content;
    private List<DragBean> dragList = new ArrayList<>();
    private List<DragBean> contentList = new ArrayList<>();
    private DragAdapter dragAdapter;
    private SubscribedAdapter contentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
        initEvent();

    }
    private void initView(){
        drag = (RecyclerView) findViewById(R.id.rv_drag);
        content = (RecyclerView) findViewById(R.id.rv_content);
    }
    private void initEvent(){
        initData();
        /**
         * 拖拽效果
         */
        gridLayoutManager = new GridLayoutManager(this,5){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        drag.setLayoutManager(gridLayoutManager);
        drag.setAdapter(dragAdapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(dragAdapter);
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(drag);

        /**
         * 普通列表展示
         */
        linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        content.setLayoutManager(linearLayoutManager);
        content.setAdapter(contentAdapter);
        /**
         * 添加点击事件
         */
        contentAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int status = contentList.get(position).getStatus();
                if (status ==0){
                    contentList.get(position).setStatus(1);//订阅标签
                    dragList.add(contentList.get(position));
                    dragAdapter.notifyDataSetChanged();
                }else {
                    contentList.get(position).setStatus(0);//取消订阅
                    dragList.remove(contentList.get(position));
                    dragAdapter.notifyDataSetChanged();
                }
                contentAdapter.notifyDataSetChanged();
            }
        });
    }
    private void initData(){
        DragBean dragBean;
        for (int i = 0; i < 30; i++) {
            dragBean = new DragBean();
            dragBean.name = "标签 #"+i;
            dragBean.status = 0;
            dragBean.pos = i;
            contentList.add(dragBean);

        }
        for (int i = 0; i < contentList.size(); i++) {
            int status = contentList.get(i).getStatus();
            if (status == 1){
             dragList.add(contentList.get(i));
            }
        }
        dragAdapter = new DragAdapter(this, dragList);
        contentAdapter = new SubscribedAdapter(this, contentList);
    }

}
