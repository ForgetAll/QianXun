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
 * 订阅列表
 *
 */
public class Subscription extends BaseActivity implements View.OnClickListener {
    //全部数据相关
    private RecyclerView tags;
    private List<TagsBean.ContentBean> tagsList = new ArrayList<>();//全部数据
    private TagsAdapter tagsAdapter;
    private LinearLayoutManager linearLayoutManager;
    //已订阅相关
    private RecyclerView sub;
    private List<SubscribedBean.ContentBean.RowsBean> subscribedList = new ArrayList<>();
    private SubAdapter subAdapter;
    private GridLayoutManager gridLayoutManager;
    ItemTouchHelper helper;

    //加入跳转按钮
    private TextView btnToMain;
    Intent toMain;
    public static boolean isEmpty = true;
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
        toMain = getIntent();
    }
    private void initEvent(){
        //初始化数据
        initData();
        boolean is = toMain.getBooleanExtra("toSub", false);//传递数据成功
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
     * 初始化数据
     * 1、网络正常情况下，从网络请求
     * 2、网络不正常的情况下从本地取
     */
    private void initData(){
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected){
            getTags();
            getSub();
        }else {
            tagsList = (List<TagsBean.ContentBean>) getLocalData(ConstantsBean.TAG_FILE_NAME);
            subscribedList = (List<SubscribedBean.ContentBean.RowsBean>) getLocalData(ConstantsBean.SUB_FILE_NAME);
            initRecycler();
        }
    }

    /**
     * 获取所有标签，包括用户订阅状态
     */
    private void getTags(){
        tagsList.clear();//先清空数据
        String url = ConstantsBean.BASE_PATH + ConstantsBean.ORG_CODE+ConstantsBean.CATALOGS;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                TagsBean jsonBean = (TagsBean) JsonUtil.fromJson(String.valueOf(response),TagsBean.class);
                                tagsList.addAll(jsonBean.getContent());
                                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.TAG_FILE_NAME, tagsList);
                            }else {
                                Object object = getLocalData(ConstantsBean.TAG_FILE_NAME);
                                tagsList.add((TagsBean.ContentBean) object);
                                Toast.makeText(Subscription.this, "刷新数据失败", Toast.LENGTH_SHORT).show();
                            }
                            initRecycler();
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
     * 查看已订阅列表
     */
    private void getSub(){
        //先清空一下数据
        subscribedList.clear();
        String url = ConstantsBean.BASE_PATH+ConstantsBean.GET_SUBSCRIBED;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //获取列表成功，加载列表
                                SubscribedBean subBean = (SubscribedBean) JsonUtil.fromJson(String.valueOf(response),SubscribedBean.class);
                                subscribedList.addAll(subBean.getContent().getRows());
                                SerializableUtils.setSerializable(Subscription.this,ConstantsBean.SUB_FILE_NAME,subscribedList);
                            }else {
                                Object object = getLocalData(ConstantsBean.SUB_FILE_NAME);
                                subscribedList.addAll((Collection<? extends SubscribedBean.ContentBean.RowsBean>) object);
                                Toast.makeText(Subscription.this, "刷新数据失败", Toast.LENGTH_SHORT).show();
                            }
                            initRecycler();
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
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     * 提交订阅
     * @param id 订阅标签的id
     */
    private void postSub(final String id){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.POST_SUBSCRIPTION+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                Logger.d("订阅成功："+id);
                                getTags();
                                getSub();
                            }else {
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
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }
//    //4、提交排序
//    private void postSort(){
//
//    }
    /**
     * 取消订阅
     * @param id 所需要取消订阅标签的id
     */
    private void deleteSub(String id){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.CANCEL_SUBSCRIPTION+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //删除成功以后重新加载数据
                                getSub();
                                getTags();
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
                        Toast.makeText(Subscription.this, "未知错误", Toast.LENGTH_SHORT).show();
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
     * 初始化列表
     */
    private void initRecycler(){
        tagsAdapter = new TagsAdapter(Subscription.this, tagsList);
        tags.setAdapter(tagsAdapter);
        tagsAdapter.notifyDataSetChanged();

        subAdapter = new SubAdapter(Subscription.this,subscribedList);
        sub.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();

        tagsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = tagsList.get(position).getId();
                String name = tagsList.get(position).getName();
                Toast.makeText(Subscription.this, "订阅名单"+name, Toast.LENGTH_SHORT).show();
                Logger.d("点击了："+name+",id是："+id);
                int status = tagsList.get(position).getSubscribeStatus();
                if (status == 0){
                    postSub(id);
                }else if (status == 1){
                    deleteSub(id);
                }
                tagsAdapter.notifyDataSetChanged();

            }
        });
        subAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d(subscribedList.get(position).getId());
                String id = subscribedList.get(position).getId();
                Logger.d("点击了"+position+",id:"+id);
                deleteSub(id);
            }
        });

        if (subscribedList.size() == 0){
            isEmpty = true;

        }else {
            isEmpty = false;
        }
    }

    /**
     * 从本地指定文件下取数据
     * @param fileName 文件名
     * @return 返回类型
     */
    private Object getLocalData(String fileName){
        Object object = SerializableUtils.getSerializable(this,fileName);
        return object;
    }

    @Override
    public void onClick(View v) {
        Logger.d("订阅标签是否为空"+isEmpty);
        if (isEmpty) {
            Toast.makeText(Subscription.this, "至少订阅一项", Toast.LENGTH_SHORT).show();
        } else {
            if (CustomApplication.isReturnMain) {
                //返回的话使用return
//                boolean is = toMain.getBooleanExtra("toSub", false);//传递数据成功
                toMain.putExtra("toMain", true);
                setResult(1, toMain);
            } else {
                //关闭当前页面，直接跳转到主页
                Intent intent = new Intent(Subscription.this, MainActivity.class);
                startActivity(intent);
                CustomApplication.isReturnMain = true;
            }
            Subscription.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
        Logger.d("按下返回键了");
        if (isEmpty){
            Toast.makeText(Subscription.this, "至少订阅一项", Toast.LENGTH_SHORT).show();
            Logger.d("至少订阅一项");
        }else{
            if (CustomApplication.isReturnMain) {
                //返回的话使用return
//                boolean is = toMain.getBooleanExtra("toSub", false);//传递数据成功
                toMain.putExtra("toMain", true);
                setResult(1, toMain);
            } else {
                //关闭当前页面，直接跳转到主页
                Intent intent = new Intent(Subscription.this, MainActivity.class);
                startActivity(intent);
                CustomApplication.isReturnMain = true;
            }
            super.onBackPressed();
        }
    }
}
