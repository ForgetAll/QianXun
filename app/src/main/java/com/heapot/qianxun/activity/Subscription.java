package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubscriptionBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 */
public class Subscription extends BaseActivity  {
//    private RecyclerView drag, content;
    private RecyclerView content;
    private List<SubscriptionBean.ContentBean> allList = new ArrayList<>();//全部数据
//    private DragAdapter dragAdapter;
    private SubAdapter allSubAdapter;

    private LinearLayoutManager linearLayoutManager;
//    private GridLayoutManager gridLayoutManager;

    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        initView();
        initEvent();

    }
    private void initView(){
//        drag = (RecyclerView) findViewById(R.id.rv_drag);
        content = (RecyclerView) findViewById(R.id.rv_content);
    }
    private void initEvent(){
        getCatalogs();
        /**
         * 拖拽效果
         */
//        gridLayoutManager = new GridLayoutManager(this,5){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        drag.setLayoutManager(gridLayoutManager);
//        drag.setAdapter(dragAdapter);
//        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(dragAdapter);
//        helper = new ItemTouchHelper(callback);
//        helper.attachToRecyclerView(drag);

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



    }

    /**
     * 需要用到Volley的逻辑必须写到Volley成功响应方法里面，否则会血崩！
     */
    private void initList(){
        Logger.d("initList");
        allSubAdapter = new SubAdapter(Subscription.this,allList);
        content.setAdapter(allSubAdapter);
        // 添加所有标签列表的点击事件
        allSubAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }
    /**
     * 获取分类
     * 1、获取用户权限、获取token
     * 2、判断网络状况
     * 3、缓存数据
     */
    private void getCatalogs(){
        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (isAvailable) {
            //网络可以用的时候请求数据,因为请求分类不需要任何权限，因此可以直接请求
            String url = ConstantsBean.BASE_PATH + ConstantsBean.ORG_CODE+ConstantsBean.CATALOGS;
            getCatalogs(url);
        }else {
            //网络有问题的时候从本地缓存获取
            Toast.makeText(Subscription.this, "网络有问题", Toast.LENGTH_SHORT).show();
            //取出本地缓存数据，如果缓存无数据再提示用户检查网络
            Logger.d(SerializableUtils.getSerializable(this,ConstantsBean.SUBSCRIPTION_FILE_NAME));
            allList.addAll((Collection<SubscriptionBean.ContentBean>) SerializableUtils.getSerializable(this,ConstantsBean.SUBSCRIPTION_FILE_NAME));
            initList();

        }
    }
    /**
     * 普通用户获取分类
     * @param url
     */
    private void getCatalogs(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        SubscriptionBean jsonBean = (SubscriptionBean) JsonUtil.fromJson(String.valueOf(response),SubscriptionBean.class);
                        allList.addAll(jsonBean.getContent());
                        SerializableUtils.setSerializable(Subscription.this,ConstantsBean.SUBSCRIPTION_FILE_NAME,allList);
                        initList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }
}
