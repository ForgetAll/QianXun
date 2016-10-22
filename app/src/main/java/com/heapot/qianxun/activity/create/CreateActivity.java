package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CreateActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.OrgInfoBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.heapot.qianxun.util.ToastUtil;
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
 */
public class CreateActivity extends BaseActivity implements View.OnClickListener {
    private List<UserOrgBean.ContentBean> orgList = new ArrayList<>();
    private ImageView close;
    private LinearLayout create_article, create_job, create_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        CreateActivityCollector.addActivity(this);
        initView();
        getUserOrg();
        //  initData();

    }

    private void initView() {
        close = (ImageView) findViewById(R.id.iv_btn_close);
        create_article = (LinearLayout) findViewById(R.id.create_article);
        create_job = (LinearLayout) findViewById(R.id.create_job);
        create_course = (LinearLayout) findViewById(R.id.create_course);
        close.setOnClickListener(this);
        create_article.setOnClickListener(this);
       /* create_job.setOnClickListener(this);
        create_course.setOnClickListener(this);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_btn_close:
                finish();
                break;
            case R.id.create_article:
                Intent intent = new Intent(CreateActivity.this, CreateArticleActivity.class);
                startActivity(intent);
                break;
        }

        CreateActivityCollector.removeActivity(this);
    }


    private void getUserOrg() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.QUERY_UER_ORG;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                UserOrgBean userOrgBean = (UserOrgBean) JsonUtil.fromJson(String.valueOf(response), UserOrgBean.class);
                                SerializableUtils.setSerializable(CreateActivity.this, ConstantsBean.USER_ORG_LIST, userOrgBean);
                                orgList.addAll(userOrgBean.getContent());
                                Log.e("列表数目11111111", String.valueOf(orgList.size()));
                                PreferenceUtil.putString("mycompany", String.valueOf(orgList.size()));
                                setClick(orgList.size());
                                if (orgList.size()>0){
                                    String orgId = userOrgBean.getContent().get(0).getOrgId();
                                    getOrgInfo(orgId);
                                }else {
                                }
                                Logger.d("orgList------->" + orgList.size());
                            } else {
                                Toast.makeText(CreateActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private void setClick(final int comNum) {
            create_job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (comNum>0){
                    Intent job = new Intent(CreateActivity.this, CreateJobActivity.class);
                    startActivity(job);
                    }else {
                        ToastUtil.show("请注册公司账号");
                    }
                }
            });
            create_course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (comNum>0){
                    Intent course = new Intent(CreateActivity.this, CreateCourseActivity.class);
                    startActivity(course);
                    }else {
                        ToastUtil.show("请注册公司账号");
                    }
                }
            });

    }


    private void getOrgInfo(String orgId) {
        String url = ConstantsBean.BASE_PATH + "/orgs/" + orgId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        Log.e("所有的Json数据：", response.toString());
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                OrgInfoBean orgInfoBean = (OrgInfoBean) JsonUtil.fromJson(String.valueOf(response), OrgInfoBean.class);
                                SerializableUtils.setSerializable(CreateActivity.this, ConstantsBean.USER_ORG_INFO, orgInfoBean);
                                String orgName = orgInfoBean.getContent().getName();
                                PreferenceUtil.putString("orgName", orgName);
                                String orgCode = orgInfoBean.getContent().getCode();
                                PreferenceUtil.putString("orgCode", orgCode);
                                String orgPhone = orgInfoBean.getContent().getPhone();
                                PreferenceUtil.putString("orgPhone", orgPhone);
                            } else {
                                Toast.makeText(CreateActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
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
