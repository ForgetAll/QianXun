package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
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
//    private List<SubscribedBean.ContentBean.RowsBean> subscribedList = new ArrayList<>();
    private List<SubBean> subList = new ArrayList<>();
    private SubAdapter subAdapter;
    private GridLayoutManager gridLayoutManager;
    ItemTouchHelper helper;
    //全部数据集合
    List<TagsBean.ContentBean> list = new ArrayList<>();
    int page = 0;
    //加入跳转按钮
    private TextView btnToMain;
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
        switch (CustomApplication.getCurrentPageName()){
            case "PAGE_SCIENCE":
                page =0;
                break;
            case "PAGE_RECRUIT":
                page = 1;
                break;
            case "PAGE_TRAIN":
                page = 2;
                break;
        }
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
        //添加点击监听事件
        btnToMain.setOnClickListener(this);
    }

    /**
     * 初始化数据:从本地获取
     */
    private void initData(){
        Object objTags =  SerializableUtils.getSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME);
        list.addAll((Collection<? extends TagsBean.ContentBean>) objTags);
        List<Integer> posList = new ArrayList<>();
        //获取指定二级标题
        for (int i = 0; i < list.size(); i++) {
            switch (page){
                case 0:
                    if (list.get(i).getPid() != null || list.get(i).getCode().equals("articles")){
                        posList.add(i);
                    }
                    break;
                case 1:
                    if (list.get(i).getPid() != null || list.get(i).getCode().equals("jobs")){
                        posList.add(i);
                    }
                    break;
                case 2:
                    if (list.get(i).getPid() != null || list.get(i).getCode().equals("activities")){
                        posList.add(i);
                    }
                    break;
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
        initRecycler();
    }
    /**
     * 初始化列表
     */
    private void initRecycler(){
        tagsAdapter = new TagsAdapter(Subscription.this, tagsList);
        tags.setAdapter(tagsAdapter);
        tagsAdapter.notifyDataSetChanged();

//        subAdapter = new SubAdapter(Subscription.this,subscribedList);
        subAdapter = new SubAdapter(Subscription.this,subList);
        sub.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();

        tagsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = tagsList.get(position).getId();
                int status = tagsList.get(position).getSubscribeStatus();
                if (status == 0){
                    tagsList.get(position).setSubscribeStatus(1);
                    //同步SubList
                    SubBean subBean = new SubBean();
                    subBean.setId(tagsList.get(position).getId());
                    subBean.setPid(tagsList.get(position).getPid().toString());
                    subBean.setName(tagsList.get(position).getName());
                    subBean.setStatus(tagsList.get(position).getSubscribeStatus());
                    subList.add(subBean);
                    //还需要更新list
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(id)){
                            count = i;
                        }
                    }
                    list.get(count).setSubscribeStatus(1);
                }else if (status == 1){
                    tagsList.get(position).setSubscribeStatus(0);
                    //删除数据联动
                    int count = 0;
                    for (int i = 0; i < subList.size(); i++) {
                        if (subList.get(i).getId().equals(id)){
                            count = i;
                        }
                    }
                    subList.remove(count);
                    //还需要更新list
                    int count2 = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(id)){
                            count2 = i;
                        }
                    }
                    list.get(count2).setSubscribeStatus(1);
                }
                subAdapter = new SubAdapter(Subscription.this,subList);
                subAdapter.notifyDataSetChanged();
                tagsAdapter = new TagsAdapter(Subscription.this,tagsList);
                tagsAdapter.notifyDataSetChanged();
                //刷新以后改变本地数据存储
                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME,list);

            }
        });
        subAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //实现联动
                String id = subList.get(position).getId();
                int pos = 0;
                int count = tagsList.size();
                for (int i = 0; i < count; i++) {
                    if (tagsList.get(i).getId().equals(id)){
                        pos = i;
                    }
                }
                tagsList.get(pos).setSubscribeStatus(0);
                tagsAdapter = new TagsAdapter(Subscription.this,tagsList);
                tagsAdapter.notifyDataSetChanged();

                //还需要更新list
                int pos2 = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(id)){
                        pos2 = i;
                    }
                }
                list.get(pos2).setSubscribeStatus(0);
                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME,list);
                //自身也要删除
                subList.remove(position);
                subAdapter = new SubAdapter(Subscription.this,subList);
                subAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
       //点击click的时候将页面改动进行存储，同时发送广播进行刷新

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
