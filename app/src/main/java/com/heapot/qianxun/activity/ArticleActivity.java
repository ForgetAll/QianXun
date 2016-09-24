package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/24.
 */
public class ArticleActivity extends BaseActivity {
    private WebView webView;
    private Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();

    }
    private void initView(){
        String url = "http://192.168.31.236/userPage/article/?57d683ae5c95dc4d92c18522";
        webView = (WebView) findViewById(R.id.wv_articles);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(this,"android");
        webView.loadUrl(url);
        //测试Java调用Js方法
        test = (Button) findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("JavaScript:dataTest('trhrthrhr')");
            }
        });


    }
    //测试Js调用Java方法,API19以上可用
    @JavascriptInterface
    public  void show(String s){
        Toast.makeText(ArticleActivity.this, s, Toast.LENGTH_SHORT).show();
        Logger.d(s);
    }

}
