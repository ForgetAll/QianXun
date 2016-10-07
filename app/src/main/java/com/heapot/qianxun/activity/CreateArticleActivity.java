package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.create.SortList;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2016/9/22.
 * 创建文章内容
 *
 */
public class CreateArticleActivity extends BaseActivity implements View.OnClickListener {
    private TextView mToolBarTitle,mToolBarSave,mChooseSub;
    private ImageView mBack,mIcon;
    private EditText mTitle,mContent;
    private String images = "",catalogId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_articles);
        initView();

    }
    private void initView(){
        mToolBarTitle = (TextView) findViewById(R.id.txt_title);
        mToolBarSave = (TextView) findViewById(R.id.txt_btn_function);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mIcon = (ImageView) findViewById(R.id.iv_create_icon);
        mTitle = (EditText) findViewById(R.id.edt_create_title);
        mChooseSub = (TextView) findViewById(R.id.txt_choose_sub);
        mContent = (EditText) findViewById(R.id.edt_create_content);
        //显示，默认隐藏的保存按钮隐藏
        mToolBarSave.setVisibility(View.VISIBLE);
        //设置ToolBar标题
        mToolBarTitle.setText("创建文章");
        //设置保存按钮的监听事件
        mToolBarSave.setOnClickListener(this);
        mToolBarSave.setText("提交");
        //选择标签事件监听
        mChooseSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_btn_function:
                postArticle();
                break;
            case R.id.iv_create_icon:
                images = "";//返回链接
                break;
            case R.id.txt_choose_sub:
                //这里传递一个数据，告诉选择标签的页面，从文章招聘课程中选择相应的数据进行加载
                Intent article = new Intent(CreateArticleActivity.this, SortList.class);
                article.putExtra("create_status",0);
                startActivityForResult(article,0);
                break;
        }
    }
    private void postArticle(){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.CREATE_ARTICLES;
        JSONObject request = getBody();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //提交成功要进行如下操作
                                //1、关闭当前页面

                            }else {
                                Toast.makeText(CreateArticleActivity.this, "错误原因："+response.getString("message"), Toast.LENGTH_SHORT).show();
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
                return super.getHeaders();
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    /**
     *抽取要提交的数据
     * @return 返回body数据
     */
    private JSONObject getBody(){
        //title和content还有catalogId是必须的，所以提交之前一定要进行判断
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        String data = "";
        if (title.equals("") || content.equals("") || catalogId.equals("")){
            Toast.makeText(CreateArticleActivity.this, "标题/内容/文章分类不能为空", Toast.LENGTH_SHORT).show();
        }else {
            if (images.equals("") || images == null){
                data = "{\"title\":\""+title+"\",\"content\":\""+content+"\",\"catalogId\":\""+catalogId+"\"}";
            }else {
                data = "{\"images\":\""+images+"\",\"title\":\""+title+"\",\"content\":\""+content+"\",\"catalogId\":\""+catalogId+"\"}";
            }
        }

        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


}
