package com.heapot.qianxun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SubscribedAdapter;
import com.heapot.qianxun.adapter.DragAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.helper.ItemTouchHelperCallback;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/8/29.
 * 订阅列表
 */
public class Subscription extends Activity  {
    private RecyclerView drag, content;
    private List<SubBean> dragList = new ArrayList<>();
    private List<SubBean> contentList = new ArrayList<>();
    private DragAdapter dragAdapter;
    private SubscribedAdapter contentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    ItemTouchHelper helper;

    RequestQueue queue;

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
        queue = Volley.newRequestQueue(this);
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
        SubBean subBean;
        for (int i = 0; i < 30; i++) {
            subBean = new SubBean();
            subBean.name = "标签 #"+i;
            subBean.status = 0;
            subBean.pos = i;
            contentList.add(subBean);

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
    /**
     * 获取分类
     * 1、获取用户权限、获取token
     * 2、判断网络状况
     * 3、缓存数据
     */
    private void getCatalogs(){
        boolean isAdmin = CustomApplication.isAdmin;
        String token = CustomApplication.TOKEN;

        boolean isAvailable = NetworkUtils.isAvailable(this);
        if (isAvailable) {//网络可以用的时候请求数据
            //获取token
            if (token.equals("")) {
                token = PreferenceUtil.getString("token");
            }
            //判断是否是管理员
            if (isAdmin) {
                //是管理员所以使用管理员地址请求数据
                String url = ConstantsBean.BASE_PATH + "admin/platform/catalogs";
                postAdminCatalogs(url, token);

            } else {
                //普通用户可以直接获取数据，不需要请求头也不需要token
                String url = ConstantsBean.BASE_PATH + "qianxun/catalogs";
                postClientCatalogs(url);
            }
        }else {
            //网络有问题的时候从本地缓存获取
            Toast.makeText(Subscription.this, "网络有问题", Toast.LENGTH_SHORT).show();
            //取出本地缓存数据，如果缓存无数据再提示用户检查网络
            SerializableUtils.getSerializable(this,ConstantsBean.SUBSCRIPTION);
        }
    }

    /**
     * 请求管理员分类
     * @param url
     */
    private void postAdminCatalogs(String url, final String token){
        JsonObjectRequest jsonObjectRequest_admin = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            String status = jsonObject.getString("status");
                            if (status.equals("success")){
                                //请求成功
                                JSONArray jsonArray = jsonObject.getJSONArray("content");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonContent = jsonArray.getJSONObject(1);
                                    SubBean subBean = new SubBean();
                                    subBean.name = jsonContent.getString("name");

                                }
                            }else {
                                //提示失败信息
                                Toast.makeText(Subscription.this, "数据请求失败"+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //管理员账号请求需要带参数
                Map<String, String> headers = new HashMap<>();
                headers.put("x-auth-token",token);
                headers.put("X-Requested-With","XMLHttpRequest");
                return headers;
            }
        };
        queue.add(jsonObjectRequest_admin);
    }

    /**
     * 普通用户获取分类
     * @param url
     */
    private void postClientCatalogs(String url){
        JsonObjectRequest jsonObjectRequest_client = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(jsonObjectRequest_client);

    }

}
