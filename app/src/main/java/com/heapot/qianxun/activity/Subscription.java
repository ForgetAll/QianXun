package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubAdapter;
import com.heapot.qianxun.adapter.TagsAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.SerializableUtils;
import com.heapot.qianxun.util.TagsUtils;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表，该页面最理想的状态应该是不加载数据，从本地获取
 *
 */
public class Subscription extends BaseActivity implements View.OnClickListener,TagsUtils.onPostResponseListener {
    //全部数据相关
    private RecyclerView tags;
    private List<TagsBean.ContentBean> tagsList = new ArrayList<>();//全部二级数据
    private TagsAdapter tagsAdapter;
    private LinearLayoutManager linearLayoutManager;
    //已订阅相关
    private RecyclerView sub;
    private List<SubBean> subList = new ArrayList<>();
    private SubAdapter subAdapter;
    private GridLayoutManager gridLayoutManager;
    ItemTouchHelper helper;
    //全部数据集合
    List<TagsBean.ContentBean> list = new ArrayList<>();
    private String pid = "";
//    List<String> postIdList = new ArrayList<>();//实时记录要提交的数据
//    List<String> deleteIdList = new ArrayList<>();//实时记录要删除的数据
    //加入关闭按钮
    private ImageView btnToMain;

    private String PAGE_ARTICLE = "PAGE_ARTICLE";
    private String PAGE_RECRUIT = "PAGE_RECRUIT";
    private String PAGE_TRAIN = "PAGE_TRAIN";

    public String PAGE_ARTICLES_ID = "f3b8d91b8f9c4a03a4a06a5678e79872";
    public String PAGE_ACTIVITIES_ID = "9025053c65e04a6992374c5d43f31acf";
    public String PAGE_JOBS_ID = "af3a09e8a4414c97a038a2d735064ebc";


    Intent intent;

    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
        initEvent();

    }
    private void initView(){
        sub = (RecyclerView) findViewById(R.id.rv_drag);
        tags = (RecyclerView) findViewById(R.id.rv_content);
        btnToMain = (ImageView) findViewById(R.id.btn_close_subscription);


    }
    private void initEvent(){
        //初始化数据
        initData();
        // 已订阅列表，含拖拽功能
        gridLayoutManager = new GridLayoutManager(this,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        sub.setLayoutManager(gridLayoutManager);
        subAdapter = new SubAdapter(Subscription.this,subList);
        sub.setAdapter(subAdapter);

        // 全部标签列表，不可拖拽
        linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        tags.setLayoutManager(linearLayoutManager);
        tagsAdapter = new TagsAdapter(Subscription.this,tagsList);
        tags.setAdapter(tagsAdapter);
        //添加点击监听事件
        btnToMain.setOnClickListener(this);
        tagsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = tagsList.get(position).getId();
                int status = tagsList.get(position).getSubscribeStatus();
                if (status == 0){
                    new TagsUtils(Subscription.this,Subscription.this).postSub(id,position,0);
                }else if (status == 1){

                    new TagsUtils(Subscription.this,Subscription.this).deleteSub(id,position,0);

                }


            }
        });

        subAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //实现联动
                String id = subList.get(position).getId();
                new TagsUtils(Subscription.this,Subscription.this).deleteSub(id,position,1);

            }
        });
    }

    /**
     * 初始化数据:从本地获取
     */
    private void initData(){
        //判断当前页面是什么
        intent = getIntent();
        String page = intent.getExtras().getString("page");

        if (page.equals(PAGE_ARTICLE)){

            pid = PAGE_ARTICLES_ID;
        }else if (page.equals(PAGE_RECRUIT)){
            pid = PAGE_JOBS_ID;
        }else if (page.equals(PAGE_TRAIN)){
            pid =PAGE_ACTIVITIES_ID;
        }
        Object objTags =  SerializableUtils.getSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME);
        list.addAll((Collection<? extends TagsBean.ContentBean>) objTags);
        List<Integer> posList = new ArrayList<>();
        //获取当前页面的二级标题
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPid() != null && list.get(i).getPid().equals(pid)){
                posList.add(i);
            }
        }
        //将获取到的二级标题赋给TagsList
        for (int i = 0; i < posList.size(); i++) {
            tagsList.add(list.get(posList.get(i)));
        }
        //再从当期页面标签获取到已订阅的赋给SubList
        SubBean subBean;
        for (int i = 0; i < tagsList.size(); i++) {
            subBean = new SubBean();
            if (tagsList.get(i).getSubscribeStatus() == 1){
                subBean.setId(tagsList.get(i).getId());
                subBean.setPid(tagsList.get(i).getPid().toString());
                subBean.setName(tagsList.get(i).getName());
                subList.add(subBean);
            }
        }
        Logger.d(subList.size());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_subscription:
                intent.putExtra("result", isRefresh);
                setResult(101, intent);
                this.finish();
                break;
        }

    }



    @Override
    public void onSubResponse(String id,int position,int flag) {

            tagsList.get(position).setSubscribeStatus(1);
            //SubList,添加
            SubBean subBean = new SubBean();
            subBean.setId(tagsList.get(position).getId());
            subBean.setPid(tagsList.get(position).getPid().toString());
            subBean.setName(tagsList.get(position).getName());
            subList.add(subBean);
            //还需要更新list，找出相同id的数据在list的下标
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(id)) {
                    count = i;
                }
            }
            list.get(count).setSubscribeStatus(1);//修改指定地方的状态

            //加载数据源并更新数据
            sub.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
            tags.setAdapter(tagsAdapter);
            tagsAdapter.notifyDataSetChanged();
            //重新进行本地数据存储
//            SerializableUtils.setSerializable(Subscription.this, ConstantsBean.TAG_FILE_NAME, list);


        isRefresh = true;

    }

    @Override
    public void onCancelResponse(String id,int position,int flag) {

        if (flag == 0) {
            Toast.makeText(this, "取消成功", Toast.LENGTH_SHORT).show();
            tagsList.get(position).setSubscribeStatus(0);
            int count = 0;
            for (int i = 0; i < subList.size(); i++) {
                if (subList.get(i).getId().equals(id)) {
                    count = i;
                }
            }
            subList.remove(count);
            //加载数据源并更新数据
            sub.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
            tags.setAdapter(tagsAdapter);
            tagsAdapter.notifyDataSetChanged();
            //重新进行本地数据存储
            SerializableUtils.setSerializable(Subscription.this, ConstantsBean.TAG_FILE_NAME, list);
        }else if (flag ==1){
            int pos = 0;
            for (int i = 0; i < tagsList.size(); i++) {
                if (tagsList.get(i).getId().equals(id)){
                    pos = i;
                }
            }
            tagsList.get(pos).setSubscribeStatus(0);
            tags.setAdapter(tagsAdapter);
            tagsAdapter.notifyDataSetChanged();

            //还需要更新list
            int pos2 = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(id)){
                    pos2 = i;
                }
            }
            list.get(pos2).setSubscribeStatus(0);
//            SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME,list);//重新存储
            //自身也要删除
            subList.remove(position);
            sub.setAdapter(subAdapter);
            subAdapter.notifyDataSetChanged();
        }
        isRefresh = true;
    }

    @Override
    public void onPostError() {
        Toast.makeText(this, "取消失败", Toast.LENGTH_SHORT).show();
    }
}
