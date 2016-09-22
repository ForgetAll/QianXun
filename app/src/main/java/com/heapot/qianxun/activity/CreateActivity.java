package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.CreateAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/13.
 */
public class CreateActivity extends BaseActivity implements OnRecyclerViewItemClickListener {
    private RecyclerView createGridView;
    private GridLayoutManager gridLayoutManager;
    private CreateAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initView();
        initEvent();
    }
    private void initView(){
        createGridView = (RecyclerView) findViewById(R.id.rv_create);
        //禁用RecyclerView的滑动事件，配合ScrollView
        gridLayoutManager = new GridLayoutManager(this,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        createGridView.setLayoutManager(gridLayoutManager);

    }
    private void initEvent(){
        initData();
        createGridView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }
    private void initData(){
        list.add("创建文章");
        list.add("创建招聘");
        list.add("创建课程");
        adapter = new CreateAdapter(this,list);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
