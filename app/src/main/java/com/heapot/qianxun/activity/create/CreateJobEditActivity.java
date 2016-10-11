package com.heapot.qianxun.activity.create;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.bean.ConstantsBean;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/10/8.
 */
public class CreateJobEditActivity extends BaseActivity implements View.OnClickListener {
    private TextView mToolBarTitle,mSave;
    private ImageView mBack;
    private WebView webView;
    private WebSettings settings;
    private Intent intent;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what ==1){
                //这里解析数据
                String content= msg.getData().getString("content");
                Logger.d(content);
                //获取到数据
                intent.putExtra("","");
                setResult(1,intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_edit);
        initView();
    }
    private void initView(){
        mToolBarTitle = (TextView) findViewById(R.id.txt_title);
        mSave = (TextView) findViewById(R.id.txt_btn_function);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        mSave.setVisibility(View.VISIBLE);
        webView = (WebView) findViewById(R.id.wv_edit_job);
        mToolBarTitle.setText("职位详情");
        mSave.setText("保存");
        mSave.setOnClickListener(this);
        mBack.setOnClickListener(this);
        initEvent();
        intent = getIntent();
    }

    private void initEvent(){
        settings = webView.getSettings();
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            settings.setCacheMode(settings.LOAD_CACHE_ONLY);
        }
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        webView.addJavascriptInterface(this,"android");
        webView.setWebChromeClient(new WebChromeClient() {});
        webView.loadUrl(ConstantsBean.WEB_CREATE_JOB_EDIT);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_btn_back:
                CreateJobEditActivity.this.finish();
                break;
            case R.id.txt_btn_function:
                webView.loadUrl("javascript:appGetContent()");//通知js返回数据

                break;
        }
    }

    /**
     * 接收编辑区所有数据
     * @param json
     */
    @JavascriptInterface
    public void setHtml(String json){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("content",json);
        message.setData(bundle);
        message.what = 1;
        handler.sendMessage(message);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);

    }
}
