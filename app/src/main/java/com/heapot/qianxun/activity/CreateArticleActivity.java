package com.heapot.qianxun.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.create.SortList;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.widget.PhotoCarmaWindow;
import com.orhanobut.logger.Logger;

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
    private TextView mToolBarTitle,mToolBarSave;
    private ImageView mBack;
    private WebView webView;
    private WebSettings webSettings;
    private String images = "",catalogId = "";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1){
                String url =  msg.getData().getString("value");
                Logger.d(url);
            }
        }
    };
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
        webView = (WebView) findViewById(R.id.wv_content);

        //显示，默认隐藏的保存按钮隐藏
        mToolBarSave.setVisibility(View.VISIBLE);
        //设置ToolBar标题
        mToolBarTitle.setText("创建文章");
        //设置保存按钮的监听事件
        mToolBarSave.setOnClickListener(this);
        mToolBarSave.setText("提交");
        //选择标签事件监听
        mBack.setOnClickListener(this);
        initSettings();//初始化webView
    }

    private void initSettings(){
        String url = "http://192.168.31.236/Tabs/editer/artical/?d?device=android";
        webSettings = webView.getSettings();
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);

        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.addJavascriptInterface(new InJavaScriptLocalObj(),"android");
        webView.setWebChromeClient(new WebChromeClient() {});
        webView.loadUrl(url);

    }

    final class InJavaScriptLocalObj{
        @JavascriptInterface
        public void showSource(String html){
            Logger.d("html------>"+html);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_btn_function:
                evaluateJavaScript(webView);
//                postArticle();
                break;
            case R.id.iv_create_icon:
                //弹窗选择图片
                PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateArticleActivity.this);
                bottomPopup.showPopupWindow();
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
//        String content = mContent.getText().toString();
        String data = "";
//        if (title.equals("") || content.equals("") || catalogId.equals("")){
//            Toast.makeText(CreateArticleActivity.this, "标题/内容/文章分类不能为空", Toast.LENGTH_SHORT).show();
//        }else {
//            if (images.equals("") || images == null){
//                data = "{\"title\":\""+title+"\",\"content\":\""+content+"\",\"catalogId\":\""+catalogId+"\"}";
//            }else {
//                data = "{\"images\":\""+images+"\",\"title\":\""+title+"\",\"content\":\""+content+"\",\"catalogId\":\""+catalogId+"\"}";
//            }
//        }

        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     *  webView调用JS方法，返回参数
     *
     * @param webView
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void evaluateJavaScript(WebView webView){
        for (int i = 1; i < 6; i++) {
            webView.evaluateJavascript("getContent("+i+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("value",value);
                    message.setData(bundle);
                    message.what = 1;
                    handler.handleMessage(message);

                }
            });
        }



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
