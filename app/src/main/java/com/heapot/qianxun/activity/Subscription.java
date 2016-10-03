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
//        if (isUpdate()) {
//            //点击click的时候将页面改动进行存储，同时发送广播进行刷新
//            Intent intent = new Intent("com.karl.refresh");
//            sendBroadcast(intent);
//        }
//        Subscription.this.finish();

    }

    @Override
    public void onBackPressed() {

        int status = isUpdate();
        switch (status){
            case 0://不需要刷新
                super.onBackPressed();
                break;
            case 1:
                updateData(1);
                break;
            case 2:
                updateData(2);
                break;
            case 3:
                updateData(3);
                break;
        }
    }

    /**
     * 判断是否刷新
     * @return 0-不刷新，1-只提交更新，2-只提交删除，3-更新和删除都要提交
     */
    private int isUpdate() {
        int status;
        //考虑到位置不同的因素，所以进行下列筛选
        int newCount = subList.size();//新数据大小
        int oldCount = oldSubList.size();//旧数据大小
        int count = 0;
        if (newCount == 0 && oldCount == 0) {
            //初始值和结束值都为0，不需要刷新
            status = 0;
        } else if (newCount == 0 && oldCount != 0) {
            //初始值为0，结束不为零，说明需要提交订阅
            status = 1;
        } else if (newCount != 0 && oldCount == 0) {
            //初始值不为零，结束为零，说明需要删除
            status = 2;
        } else {
            //初始不为零，结束也不为零，需要另外判断
            //求出来交集数量,与新旧总数各自对比
            for (int i = 0; i < newCount; i++) {
                for (int j = 0; j < oldCount; j++) {
                    if (subList.get(i).getId().equals(oldSubList.get(j).getId())) {
                        count++;
                    }
                }
            }
            if (count == 0){
                //新旧数据完全不一样，那肯定是完全变化了，返回3
                status = 3 ;
            }else {//有交集，交集不为零
                if (newCount == oldCount){
                    //新旧数据一样多，
                    if (count == oldCount){
                        //这种情况说明没有变化
                        status = 0;
                    }else {
                        //总数相同，交集比总数少，那肯定多出来的就是需要提交和取消的，而且数量相同
                        status = 3;
                    }
                }else if (newCount > oldCount){
                    if (count == oldCount){
                        //最小值是oldCount，等于交集，说明没有取消的都是增加的
                        status =1;
                    }else if (count < oldCount){
                        //交集比最小的小，那肯定有取消的有增加的，而且增加的比取消的多
                        status = 3;
                    }else {
                        status=0;//这个实际是没用的，因为交集只能小于等于集合
                    }
                }else{
                    if (count == newCount){
                        status = 2;//全都是取消的
                    }else if (count < newCount){
                        status = 3;//有取消的有新增的，取消的比新增的多
                    }else {
                        status=0;//这个实际是没用的，因为交集只能小于等于集合
                    }
                }
            }

        }
        return status;
    }
    //拿出需要提交订阅的数据
    private void updateData(int n) {
        List<SubBean> listForPost = new ArrayList<>();
        List<SubBean> listForDel = new ArrayList<>();
        listForPost.addAll(subList);
        listForDel.addAll(oldSubList);
        int newCount = listForPost.size();//新数据大小
        int oldCount = listForDel.size();//旧数据大小
        switch (n){
            case 1:
                //只有更新没有取消，那就说明交集就是old数据，
                if (oldCount != 0){//这时候求出来交集最重要
                    List<Integer> postList = new ArrayList<>();
                    for (int i = 0; i < newCount; i++) {
                        for (int j = 0; j < oldCount; j++) {
                            if (listForPost.get(i).getId().equals(listForDel.get(i).getId())){
                                postList.add(i);
                            }
                        }
                    }
                    //取出来交集的下标，删除就好
                    for (int i = 0; i < postList.size(); i++) {
                        listForPost.remove(postList.get(i));
                    }

                }else {
                    //旧数据为零，就不用改动新数据了，全都是新的
                }
                break;
            case 2:
                //只有取消没有更新，那肯定交集就是new数据
                if (newCount != 0){
                    List<Integer> delList = new ArrayList<>();
                    for (int i = 0; i < oldCount; i++) {
                        for (int j = 0; j < newCount; j++) {
                            if (listForDel.get(i).getId().equals(listForPost.get(i).getId())){
                                delList.add(i);
                            }
                        }
                    }
                    for (int i = 0; i < delList.size(); i++) {
                        listForDel.remove(delList.get(i));
                    }
                }
                break;
            case 3:
                //有取消有更新，两者都不为0的时候有取消有更新
                List<Integer> pList = new ArrayList<>();
                List<Integer> dList = new ArrayList<>();
                for (int i = 0; i < oldCount; i++) {
                    for (int j = 0; j < newCount; j++) {
                        if (listForDel.get(i).getId().equals(listForPost.get(i).getId())){
                            pList.add(j);
                            dList.add(i);
                        }
                    }
                }
                for (int i = 0; i < pList.size(); i++) {
                    listForPost.remove(pList.get(i));
                }
                for (int i = 0; i < dList.size(); i++) {
                    listForDel.remove(dList.get(i));
                }
                break;
        }

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
