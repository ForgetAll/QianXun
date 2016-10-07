package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.CreateAdapter;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/9/13.
 * description： 创建项目页面
 *
 */
public class CreateActivity extends BaseActivity {
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

        //设置点击事件
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d("点击了"+position);
                switch (position){
                    case 0:
                        Intent intent = new Intent(CreateActivity.this,CreateArticleActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent job= new Intent(CreateActivity.this,CreateJobActivity.class);
                        startActivity(job);
                        break;
                    case 2:
                        Intent course = new Intent(CreateActivity.this,CreateCourseActivity.class);
                        startActivity(course);
                        break;
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        list.add("创建文章");
        list.add("创建招聘");
        list.add("创建课程");
        adapter = new CreateAdapter(this,list);
        createGridView.setAdapter(adapter);
    }

}
