package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.create.SortList;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.widget.PhotoCarmaWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject.optString("return_code").equals("success")) {
                    try {
                        JSONObject content = jsonObject.getJSONObject("content");
                        images = content.getString("url");
                        Log.e("这是上传头像返回的路径", images);
                        CommonUtil.loadImage(mIcon, images + "", R.mipmap.ic_default_image);
                    } catch (JSONException e) {
                    }
                }
            }
            return false;
        }
    });
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
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_btn_function:
                postArticle();
                break;
            case R.id.iv_create_icon:
                //弹窗选择图片
                PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateArticleActivity.this);
                bottomPopup.showPopupWindow();
                break;
            case R.id.txt_choose_sub:
                //这里传递一个数据，告诉选择标签的页面，从文章招聘课程中选择相应的数据进行加载
                Intent article = new Intent(CreateArticleActivity.this, SortList.class);
                article.putExtra("create_status",0);
                startActivityForResult(article,0);
                break;
            case R.id.iv_btn_back:
                CreateArticleActivity.this.finish();
                break;
        }
    }
    private void postArticle(){
        Toast.makeText(CreateArticleActivity.this, ""+CustomApplication.TOKEN, Toast.LENGTH_SHORT).show();
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
                                CreateArticleActivity.this.finish();
                                Toast.makeText(CreateArticleActivity.this, "创建成功", Toast.LENGTH_SHORT).show();

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

                return headers;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //先判断返回结果码
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //相机请求码
                case ConstantsBean.CARMA_RESULT_CODE:
                    Uri uri = Uri.fromFile(new File(ConstantsBean.HEAD_IMAGE_PATH));
                    cropImage(uri);
                    //yasuo(uri);
                    break;
                //裁剪完成
                case 102:
                    //上传图片
                    File file = new File(ConstantsBean.HEAD_IMAGE_PATH);
                    if (file.exists()) {
                        Log.e("file", file.getAbsolutePath());
                    }
                    uploadImageFile(file);
                    break;
                //相册返回
                case 103:
                    Uri uri1 = intent.getData();
                    cropImage(uri1);
                    break;

            }
        }

        if (requestCode == 0 && resultCode == 1){
            String name = intent.getExtras().getString("TagName");
            String id = intent.getExtras().getString("TagId");
            catalogId = id;
            mChooseSub.setText(name);
        }else {
            mChooseSub.setText("选择分类失败");
        }
    }
    //裁剪图片
    private void cropImage(Uri uri) {
        Uri outuri = Uri.fromFile(new File(ConstantsBean.HEAD_IMAGE_PATH));
        if (uri == null) {
            Log.e("uri:", uri + "");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 90);
        intent.putExtra("outputY", 90);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 102);
    }
    //上传头像
    private void uploadImageFile(File file) {
        FileUploadTask task = new FileUploadTask(this, handler, file);
        task.execute(ConstantsBean.UPLOAD);
    }

}
