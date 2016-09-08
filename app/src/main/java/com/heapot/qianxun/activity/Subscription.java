package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.ContentAdapter;
import com.heapot.qianxun.adapter.DragAdapter;
import com.heapot.qianxun.bean.DragBean;
import com.heapot.qianxun.helper.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 */
public class Subscription extends Activity  {
    private RecyclerView drag,content;
    private List<DragBean> mSubList = new ArrayList<>();
    private List<DragBean> mContentList = new ArrayList<>();
    private DragAdapter dragAdapter;
    private ContentAdapter contentAdapter;
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
    }
    private void initData(){
        DragBean dragBean;
        for (int i = 0; i < 30; i++) {
            dragBean = new DragBean();
            dragBean.name = "标签 #"+i;
            dragBean.status = 0;
            mContentList.add(dragBean);
            mSubList.add(dragBean);
        }
        dragAdapter = new DragAdapter(this,mSubList);
        contentAdapter = new ContentAdapter(this, mContentList);
    }


}
