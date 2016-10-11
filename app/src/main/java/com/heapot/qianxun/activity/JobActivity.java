package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;

/**
 * Created by Karl on 2016/10/11.
 */

public class JobActivity extends BaseActivity {
    private TextView mToolBarTitle,mSave;
    private ImageView mBack;
    private WebView webView;
    private WebSettings webSettings;
    private String articleId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        initView();
    }
    private void initView(){
        mToolBarTitle = (TextView) findViewById(R.id.txt_title);
        mToolBarTitle.setText("招聘详情");
        mSave = (TextView) findViewById(R.id.txt_btn_function);
        mSave.setVisibility(View.GONE);
        mBack = (ImageView) findViewById(R.id.iv_btn_back);
        webView  = (WebView) findViewById(R.id.wv_job);
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        articleId = id;
        initWeb();
        String url = "http://sijiache.heapot.com/Tabs/userPage/?type=job&id="+id;
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
        });
    }
    private void initWeb(){

        webSettings = webView.getSettings();
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);

        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "android");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);

    }
}
