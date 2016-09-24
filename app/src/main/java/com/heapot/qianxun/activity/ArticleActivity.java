package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/24.
 * 文章详情页面
 *
 */
public class ArticleActivity extends BaseActivity {
    private WebView webView;
    private WebSettings webSettings;
    private Button test;
    private boolean isConnected;//判断网络状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initEvent();

    }
    private void initView(){
        webView = (WebView) findViewById(R.id.wv_articles);


        //测试Java调用Js方法
        test = (Button) findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("JavaScript:dataTest('trhrthrhr')");
            }
        });
    }
    private void initEvent(){
        String url = "http://192.168.31.236/userPage/article/?57d683ae5c95dc4d92c18522";
        //配置webView设置
        webSettings = webView.getSettings();
        //缓存模式：根据网络状况进行判断
        isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            //网络正常状况下只从网络获取
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }else {
            //网络不正常状况下从本地获取
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);
        }
        //开启JS权限
        webSettings.setJavaScriptEnabled(true);
        //JS调用Java方法设置
        webView.addJavascriptInterface(this,"android");
        webView.loadUrl(url);//必须在最后一行
    }
    //测试Js调用Java方法,API19以上可用
    @JavascriptInterface
    public  void show(String s){
        Toast.makeText(ArticleActivity.this, s, Toast.LENGTH_SHORT).show();
        Logger.d(s);
    }

}
