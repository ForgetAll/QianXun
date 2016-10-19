package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.adapter.CreateAdapter;
import com.heapot.qianxun.application.CreateActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.OrgInfoBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/9/13.
 * description： 创建项目页面
 *
 */
public class CreateActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView createGridView;
    private GridLayoutManager gridLayoutManager;
    private CreateAdapter adapter;
    private List<String> list = new ArrayList<>();
    private List<UserOrgBean.ContentBean> orgList  = new ArrayList<>();
    private ImageView close;
    private String orgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        CreateActivityCollector.addActivity(this);
        initView();
    }
    private void initView(){
        createGridView = (RecyclerView) findViewById(R.id.rv_create);
        close = (ImageView) findViewById(R.id.iv_btn_close);
        close.setOnClickListener(this);
        //禁用RecyclerView的滑动事件，配合ScrollView
        gridLayoutManager = new GridLayoutManager(this,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        createGridView.setLayoutManager(gridLayoutManager);
        getUserOrg();

    }

    /**
     * 初始化数据
     */
    private void initData(int n){
        if (n > 0) {
            list.add("创建文章");
            list.add("创建招聘");
            list.add("创建课程");
        }else {
            list.add("创建文章");
        }
        adapter = new CreateAdapter(this,list);
        createGridView.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        //关闭当前页面
        this.finish();
        CreateActivityCollector.removeActivity(this);
    }


    private void getUserOrg(){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.QUERY_UER_ORG;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：",response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                UserOrgBean userOrgBean = (UserOrgBean) JsonUtil.fromJson(String.valueOf(response),UserOrgBean.class);
                                SerializableUtils.setSerializable(CreateActivity.this,ConstantsBean.USER_ORG_LIST,userOrgBean);
                                orgList.addAll(userOrgBean.getContent());
                                orgId=userOrgBean.getContent().get(0).getOrgId();
                                getOrgInfo();
                                Logger.d("orgList------->"+orgList.size());
                                initData(orgList.size());
                            }else {
                                Toast.makeText(CreateActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

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
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private void getOrgInfo() {
        String url = ConstantsBean.BASE_PATH+"/orgs/"+orgId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：",response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                OrgInfoBean orgInfoBean = (OrgInfoBean) JsonUtil.fromJson(String.valueOf(response),OrgInfoBean.class);
                                SerializableUtils.setSerializable(CreateActivity.this,ConstantsBean.USER_ORG_INFO,orgInfoBean);
                                String orgName=orgInfoBean.getContent().getName();
                                PreferenceUtil.putString("orgName",orgName);
                                String orgCode=orgInfoBean.getContent().getCode();
                                PreferenceUtil.putString("orgCode",orgCode);
                            }else {
                                Toast.makeText(CreateActivity.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

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
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreateActivityCollector.removeActivity(this);
    }
}
