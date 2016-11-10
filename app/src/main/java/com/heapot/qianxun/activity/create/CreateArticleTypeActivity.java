package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.adapter.CreateJobTypeAdapter;
import com.heapot.qianxun.application.CreateActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.JobTypeBean;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15859 on 2016/11/9.
 * <p>
 * 文章分类
 */
public class CreateArticleTypeActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_typeList;
    private List<JobTypeBean.ContentBean> jobList = new ArrayList<>();
    private ImageView iv_btn_back;
    private Intent intent;
    private int n;
    private String request;
    private String imageUrl;
    private TextView txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_tybe_list);
        inteView();
        intentData();
        getData();
    }

    private void intentData() {
        intent = getIntent();
        n = intent.getExtras().getInt("status");
        request = intent.getExtras().getString("article");
        if (n == 1) {

            imageUrl = intent.getExtras().getString("images");
            request = request + ",\"images\":\"" + imageUrl + "\"";
        }

        Logger.d("" + request);
    }

    private void inteView() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("请选择文章类型");
        lv_typeList = (ListView) findViewById(R.id.lv_typeList);
        iv_btn_back = (ImageView) findViewById(R.id.iv_btn_back);
        iv_btn_back.setOnClickListener(this);

    }

    private void getData() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.CREATE_ARTICLE_TYPE;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("个人发表的内容", response.toString());
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        JobTypeBean jobTypeBean = (JobTypeBean) JsonUtil.fromJson(String.valueOf(response), JobTypeBean.class);
                        jobList = jobTypeBean.getContent();
                        Log.e("工作的类型：", String.valueOf(jobList.size()));
                        CreateJobTypeAdapter createJobTypeAdapter = new CreateJobTypeAdapter(CreateArticleTypeActivity.this, jobList);
                        lv_typeList.setAdapter(createJobTypeAdapter);
                        lv_typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                JobTypeBean.ContentBean bean = jobList.get(position);
                                String jobName = bean.getName();
                                String catalogId = bean.getId();
                                Toast.makeText(CreateArticleTypeActivity.this, "请稍等", Toast.LENGTH_SHORT).show();
                                request = request + ",\"catalogId\":\"" + catalogId + "\"}";
                                Logger.d(request);
                                postArticle(request);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }

    private void postArticle(String request) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.CREATE_ARTICLES;
        JSONObject json = null;
        try {
            json = new JSONObject(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                JSONObject content = response.getJSONObject("content");
                                String id = content.getString("id");
                                Intent intent = new Intent(CreateArticleTypeActivity.this, ArticleActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                                CreateActivityCollector.finishAll();
                                finish();
                            } else {
                                Toast.makeText(CreateArticleTypeActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateArticleTypeActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_btn_back:
                finish();
                break;
        }
    }
}
