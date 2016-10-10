package com.heapot.qianxun.activity.create;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;

/**
 * Created by Karl on 2016/10/8.
 *
 */
public class CreateJobEditActivity extends BaseActivity{
    private TextView mToolBarTitle,mSave;
    private ImageView mBack;
    private WebView webView;
    private WebSettings settings;

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
        initEvent();
    }

    private void initEvent(){
        String url = "";
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
        webView.loadUrl(url);

    }

}
