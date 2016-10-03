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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubAdapter;
import com.heapot.qianxun.adapter.TagsAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.ItemTouchHelperCallback;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表，该页面最理想的状态应该是不加载数据，从本地获取
 *
 */
public class Subscription extends BaseActivity implements View.OnClickListener {
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
    private String pid = CustomApplication.PAGE_ARTICLES_ID;
    //加入跳转按钮
    private TextView btnToMain;
    List<SubBean> oldSubList = new ArrayList<>();
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
        btnToMain = (TextView) findViewById(R.id.btn_close_subscription);

    }
    private void initEvent(){
        //初始化数据
        initData();
        // 已订阅列表，含拖拽功能
        gridLayoutManager = new GridLayoutManager(this,5){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        sub.setLayoutManager(gridLayoutManager);
        subAdapter = new SubAdapter(Subscription.this,subList);
        sub.setAdapter(subAdapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(subAdapter);
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(sub);


        // 全部标签列表，不可拖拽
        linearLayoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
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
                Toast.makeText(Subscription.this, "点击了", Toast.LENGTH_SHORT).show();
                String id = tagsList.get(position).getId();
                int status = tagsList.get(position).getSubscribeStatus();
                if (status == 0){
                    tagsList.get(position).setSubscribeStatus(1);
                    //SubList,添加
                    SubBean subBean = new SubBean();
                    subBean.setId(tagsList.get(position).getId());
                    subBean.setPid(tagsList.get(position).getPid().toString());
                    subBean.setName(tagsList.get(position).getName());
                    subBean.setStatus(tagsList.get(position).getSubscribeStatus());
                    subList.add(subBean);
                    //还需要更新list，找出相同id的数据在list的下标
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(id)){
                            count = i;
                        }
                    }
                    list.get(count).setSubscribeStatus(1);//修改指定地方的状态
                    Logger.d(list.get(count).getName()+list.get(count).getSubscribeStatus());
                }else if (status == 1){
                    tagsList.get(position).setSubscribeStatus(0);
                    //删除数据联动,找到SubList中相同id的数据下标
                    int count = 0;
                    for (int i = 0; i < subList.size(); i++) {
                        if (subList.get(i).getId().equals(id)){
                            count = i;
                        }
                    }
                    subList.remove(count);//删除指定下标的数据
                    //还需要更新list，原理同上
                    int count2 = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(id)){
                            count2 = i;
                        }
                    }
                    list.get(count2).setSubscribeStatus(0);
                }
                //加载数据源并更新数据
                sub.setAdapter(subAdapter);
                subAdapter.notifyDataSetChanged();
                tags.setAdapter(tagsAdapter);
                tagsAdapter.notifyDataSetChanged();
                //重新进行本地数据存储
                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME,list);

            }
        });

        subAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //实现联动
                String id = subList.get(position).getId();
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
                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME,list);//重新存储
                //自身也要删除
                subList.remove(position);
                sub.setAdapter(subAdapter);
                subAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 初始化数据:从本地获取
     */
    private void initData(){
        //判断当前页面是什么
        switch (CustomApplication.getCurrentPageName()){
            case "PAGE_SCIENCE":
                pid = CustomApplication.PAGE_ARTICLES_ID;
                break;
            case "PAGE_RECRUIT":
                pid = CustomApplication.PAGE_JOBS_ID;
                break;
            case "PAGE_TRAIN":
                pid = CustomApplication.PAGE_ACTIVITIES_ID;
                break;
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
//        tagsList.addAll((Collection<? extends TagsBean.ContentBean>) objTags);
        //再从当期页面标签获取到已订阅的赋给SubList
        SubBean subBean;
        for (int i = 0; i < tagsList.size(); i++) {
            subBean = new SubBean();
            if (tagsList.get(i).getSubscribeStatus() == 1){
                subBean.setId(tagsList.get(i).getId());
                subBean.setPid(tagsList.get(i).getPid().toString());
                subBean.setName(tagsList.get(i).getName());
                subBean.setStatus(tagsList.get(i).getSubscribeStatus());
                subList.add(subBean);
            }
        }
        //存储已订阅标签
        oldSubList.addAll(subList);
        Logger.d("所有数据List："+list.size()+",当前二级标题:"+tagsList.size()+"，已订阅二级标题"+subList.size());
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {

    }


    /**
     * 发送广播
     */
    private void sendBroadcast(int n){
        //发送广播并关闭页面
        Intent intent = new Intent("com.karl.refresh");
        intent.putExtra("status",0);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);//发送本地广播
        Subscription.this.finish();
    }


}
