package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.adapter.CreateJobTypeAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.JobTypeBean;
import com.heapot.qianxun.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15859 on 2016/10/16.
 * 工作分类
 */
public class CreateJobTypeActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv_typeList;
    private List<JobTypeBean.ContentBean> jobList = new ArrayList<>();
    private ImageView iv_btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_tybe_list);
        inteView();
        getData();
    }
    private void inteView() {
        lv_typeList=(ListView) findViewById(R.id.lv_typeList);
        iv_btn_back=(ImageView)   findViewById(R.id.iv_btn_back);
        iv_btn_back.setOnClickListener(this);

    }
    private void getData() {
        String url = ConstantsBean.CREATE_JOB_TYPE;
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
                        CreateJobTypeAdapter  createJobTypeAdapter = new CreateJobTypeAdapter(CreateJobTypeActivity.this, jobList);
                        lv_typeList.setAdapter(createJobTypeAdapter);
                        lv_typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                JobTypeBean.ContentBean bean=jobList.get(position);
                                String jobName=bean.getName();
                                String catalogId=bean.getId();
                                Intent intent=new Intent();
                                intent.putExtra("catalogId",catalogId);
                                intent.putExtra("jobName",jobName);
                                setResult(RESULT_OK,intent);
                                finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_btn_back:
                finish();
                break;
        }
    }
}
