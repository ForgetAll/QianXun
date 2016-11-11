package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
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
import com.heapot.qianxun.activity.detail.ArticleActivity;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.adapter.SortAdapter;
import com.heapot.qianxun.application.CreateActivityCollector;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.util.SerializableUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Karl on 2016/10/7.
 * desc：创建项目的时候选择分类
 *
 */
public class SortList extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView back;
    private TextView title;
    private ListView listView;
    private List<TagsBean.ContentBean> list = new ArrayList<>();
    private List<TagsBean.ContentBean> tagList = new ArrayList<>();//这里tagList的意思是对应的二级标题
    private SortAdapter adapter;
    private String request = "";
    private String imageUrl = "";
    private int n = 0;
    String pid = "";
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        CreateActivityCollector.addActivity(this);
        initView();
        initEvent();
    }
    private void initView(){
        title = (TextView) findViewById(R.id.txt_title);
        back = (ImageView) findViewById(R.id.iv_btn_back);
        listView = (ListView) findViewById(R.id.lv_sort_list);
        listView.setDividerHeight(1);
        //一些初始化设置
        title.setText("选择分类");
        back.setOnClickListener(this);

        intent = getIntent();
        n = intent.getExtras().getInt("status");
        request = intent.getExtras().getString("article");
        if (n==1){

            imageUrl = intent.getExtras().getString("images");
            request = request+",\"images\":\""+imageUrl+"\"";
        }

        Logger.d(""+request);

    }
    private void initEvent(){
        loadData();
        adapter = new SortAdapter(this,tagList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void loadData(){
        Object object = SerializableUtils.getSerializable(this, ConstantsBean.TAG_FILE_NAME);
        list.addAll((Collection<? extends TagsBean.ContentBean>) object);
        List<Integer> posList = new ArrayList<>();
        //获取当前页面的二级标题
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPid() != null && list.get(i).getPid().equals(pid)){
                posList.add(i);
            }
        }
        for (int i = 0; i < posList.size(); i++) {
            tagList.add(list.get(posList.get(i)));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_btn_back:
                this.finish();//直接返回
                break;

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "提交中请稍等", Toast.LENGTH_SHORT).show();
        request = request +",\"catalogId\":\""+tagList.get(position).getId()+"\"}";
        Logger.d(request);
        postArticle(request);


    }

    private void postArticle(String jsonRequest){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.CREATE_ARTICLES;
        JSONObject json = getBody(jsonRequest);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                JSONObject content = response.getJSONObject("content");
                                String id = content.getString("id");
                                Intent intent = new Intent(SortList.this,ArticleActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                                CreateActivityCollector.finishAll();
                            }else {
                                Toast.makeText(SortList.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SortList.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN,getAppToken());
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private JSONObject getBody(String str){
        //title和content还有catalogId是必须的，所以提交之前一定要进行判断

        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        CreateActivityCollector.removeActivity(this);
    }
}
