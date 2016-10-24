package com.heapot.qianxun.activity.create;

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
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CreateActivityCollector;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.FileUploadTask;
import com.heapot.qianxun.widget.PhotoCarmaWindow;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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
    private String images = "",title = "",content="",token = "";
    private static final int GET_ARTICLE_CONTENT = 1;
    private static final int GET_ARTICLE_IMAGES = 2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int n = msg.what;
            switch (n){
                case GET_ARTICLE_CONTENT:
                    String json =  msg.getData().getString("content");
                    Logger.d(json);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        images = jsonObject.getString("articleImg");
                        Logger.d(images);
                        content = jsonObject.getString("content");
                        title = jsonObject.getString("title");
                        String body = "";
                        if (title.equals("") || content.equals("")){
                            Toast.makeText(CreateArticleActivity.this, "标题/内容不能为空", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(CreateArticleActivity.this,SortList.class);
                            body = "{\"title\":\"" + title + "\",\"content\":\"" + content + "\"";
                            intent.putExtra("article",body);
                            if (images.equals("")) {
                                intent.putExtra("status",0);
                            }else {
                                intent.putExtra("status",1);
                                intent.putExtra("images",images);
                            }
                            startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_ARTICLE_IMAGES:
                    token =  msg.getData().getString("token");
                    Logger.d(token);
                    PhotoCarmaWindow bottomPopup = new PhotoCarmaWindow(CreateArticleActivity.this);
                    bottomPopup.showPopupWindow();
                    break;
            }
        }
    };
    Handler handlerImage = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (!TextUtils.isEmpty(result)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    Log.e("jsonObject", String.valueOf(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonObject.optString("return_code").equals("success")) {
                    try {
                        JSONObject content = jsonObject.getJSONObject("content");
                        images = content.getString("url");
                        webView.loadUrl("javascript:imgReady(\""+token+"\",\""+images+"\")");
                        Logger.d("Images:"+images+",Token:"+token);
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
        CreateActivityCollector.addActivity(this);
        initView();

    }
    private void initView(){
        mToolBarTitle = (TextView) findViewById(R.id.txt_title);
        mToolBarSave = (TextView) findViewById(R.id.txt_btn_function);
        // mCreateIon=(ImageView)  findViewById(R.id.iv_create_icon);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        webView = (WebView) findViewById(R.id.wv_content);
        //  mCreateIon.setOnClickListener(this);

        //显示，默认隐藏的保存按钮隐藏
        mToolBarSave.setVisibility(View.VISIBLE);
        //设置ToolBar标题
        mToolBarTitle.setText("创建文章");
        //设置保存按钮的监听事件
        mToolBarSave.setOnClickListener(this);
        mToolBarSave.setText("下一步");
        //选择标签事件监听
        mBack.setOnClickListener(this);
        initSettings();//初始化webView
    }

    private void initSettings(){

        webSettings = webView.getSettings();
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(new WebChromeClient() {});
        webView.loadUrl(ConstantsBean.WEB_CREATE_ARTICLE_EDIT);

    }

    /**
     * 接收编辑区所有数据
     * @param json
     */
    @JavascriptInterface
    public void setHtml(String json){
        Logger.d("json--->"+json);

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("content",json);
        message.setData(bundle);
        message.what = GET_ARTICLE_CONTENT;
        handler.sendMessage(message);

    }

    /**
     * 当需要添加图片的时候JS调用该方法
     */
    @JavascriptInterface
    public void setImages(String token){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("token",token);
        message.setData(bundle);
        message.what = GET_ARTICLE_IMAGES;
        handler.sendMessage(message);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //保存按钮
            case R.id.txt_btn_function:
                webView.loadUrl("javascript:appGetContent()");//通知js返回数据
                break;
            case R.id.iv_btn_back:
                CreateArticleActivity.this.finish();
                break;
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
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        intent.putExtra("scale", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, 102);
    }
    //上传头像
    private void uploadImageFile(File file) {
        FileUploadTask task = new FileUploadTask(this, handlerImage, file);
        task.execute(ConstantsBean.UPLOAD);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        CreateActivityCollector.removeActivity(this);
    }
}
