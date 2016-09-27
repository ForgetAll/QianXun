package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
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
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.ItemTouchHelperCallback;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
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
 * 订阅列表
 */
public class Subscription extends BaseActivity  {
    private RecyclerView sub, content;
    private List<TagsBean.ContentBean> tagsList = new ArrayList<>();//全部数据
    private List<SubBean> subList = new ArrayList<>();//已订阅数据
    private SubAdapter subAdapter;
    private TagsAdapter tagsAdapter;

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
        sub = (RecyclerView) findViewById(R.id.rv_drag);
        content = (RecyclerView) findViewById(R.id.rv_content);
    }
    private void initEvent(){
        getCatalogs();
        /**
         * 拖拽效果
         */
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
            //取出本地缓存数据，如果缓存无数据再提示用户检查网络
            Logger.d(SerializableUtils.getSerializable(this,ConstantsBean.TAG_FILE_NAME));
            Object object = SerializableUtils.getSerializable(this,ConstantsBean.TAG_FILE_NAME);
            if (object == null){
                //本地无数据，提示用户检查网络后重新加载
                Toast.makeText(Subscription.this, "暂无数据，请检查网络", Toast.LENGTH_SHORT).show();
            }else {
                //本地有数据，直接加载
                tagsList.addAll((Collection<? extends TagsBean.ContentBean>) object);
                refreshSubList(tagsList);
            }
            //将加载到的数据添加到适配器
            initList();
        }

    }
    /**
     * 普通用户获取分类
     * @param url 分类API
     */
    private void getCatalogs(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("succcess")){
                                TagsBean jsonBean = (TagsBean) JsonUtil.fromJson(String.valueOf(response),TagsBean.class);
                                tagsList.addAll(jsonBean.getContent());
                                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME, tagsList);
                                //由于后台数据的问题，这里只是临时的解决办法,抽取出干净的数据
                                refreshSubList(tagsList);
                                initList();
                            }else {
                                Object object = SerializableUtils.getSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME);
                                if (object == null){
                                    Toast.makeText(Subscription.this, "数据请求失败，暂无数据", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Subscription.this, "服务器错误！", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }
    /**
     * 需要用到Volley的逻辑必须写到Volley成功响应方法里面，否则会血崩！
     */
    private void initList(){
        tagsAdapter = new TagsAdapter(Subscription.this, tagsList);
        content.setAdapter(tagsAdapter);
        // 添加所有标签列表的点击事件
        tagsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = tagsList.get(position).getId();
                int status = tagsList.get(position).getSubscribeStatus();
                //这里需要进行判断，如果是已订阅那点击就是取消，未订阅点击就是订阅
                if (status == 0){
                    postSubscribeTags(id,position);//提交订阅
                }else if (status == 1){
                    cancelTags(id,position,0);//取消订阅
                }
                tagsAdapter.notifyDataSetChanged();

            }
        });
        //已订阅数据
        subAdapter = new SubAdapter(Subscription.this,subList);
        sub.setAdapter(subAdapter);
        //点击事件
        subAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d(subList.get(position).getId());
                //因为这里全都是已订阅的，所以点击就是取消订阅
                String id = subList.get(position).getId();
                //从网络请求取消订阅
                cancelTags(id,position,0);
            }
        });
        //刷新数据
        subAdapter.notifyDataSetChanged();
        tagsAdapter.notifyDataSetChanged();
    }

    /**
     *  提交订阅
     * @param catalogId 订阅Id
     * @param position 数据下标
     */
    private void postSubscribeTags(String catalogId, final int position){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.SUBSCRIBE_CATALOGS+"?catalogId="+catalogId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //提交成功,刷新本地存储的数据
                                tagsList.get(position).setSubscribeStatus(1);
                                refreshSubList(tagsList);
                                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME, tagsList);
                            }else {
                                //提交订阅失败
                                Toast.makeText(Subscription.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Subscription.this, "服务器的锅！", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }

    /**
     * 取消订阅
     * @param id 所需要取消订阅标签的id
     * @param position 该标签对应的position
     * @param i 通过i确定position是全部标签的还是已订阅标签的坐标
     */
    private void cancelTags(String id, final int position, final int i){
        String url = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                switch (i){
                                    case 0:
                                        tagsList.get(position).setSubscribeStatus(0);
                                        refreshSubList(tagsList);
                                        tagsAdapter.notifyDataSetChanged();
                                        //同时更新本地存储数据
                                        SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME, tagsList);
                                        break;
                                    case 1:
                                        int pos = subList.get(position).getPosition();//定位TagList下标
                                        //从List删除取消订阅数据
                                        subList.remove(position);
                                        //从tag列表改变数据状态
                                        tagsList.get(pos).setSubscribeStatus(0);
                                        //刷新数据源
                                        subAdapter.notifyDataSetChanged();
                                        tagsAdapter.notifyDataSetChanged();
                                        break;
                                }

                            }else {
                                //取消订阅失败
                                Toast.makeText(Subscription.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
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
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * 每当总数据发生变化，这里都会变化
     * @param list 将TagList传进来
     */
    private void refreshSubList(List<TagsBean.ContentBean> list){
        subList.clear();//清空一下数据
        SubBean subBean = new SubBean();
        for (int i = 0; i < list.size(); i++) {
            subBean.setId(list.get(i).getId());
            subBean.setSubscribeStatus(1);
            subBean.setName(list.get(i).getName());
            subBean.setPosition(i);
            subList.add(subBean);
        }
        subAdapter.notifyDataSetChanged();
    }
}
