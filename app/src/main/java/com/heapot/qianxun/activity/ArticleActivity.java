package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.orhanobut.logger.Logger;

/**
 * Created by Karl on 2016/9/24.
 * 文章详情页面，
 *
 */
public class ArticleActivity extends BaseActivity {
    private WebView webView;
    private WebSettings webSettings;
    private Button test;
    private boolean isConnected;//判断网络状态
    private EditText input_comment;
    private Handler handler;
    private static final int MSG_SHOW_INPUT = 0;//显示输入框
    private static final int MSG_HIDE_INPUT = 1;//隐藏输入框
    private static final int MSG_UPDATE = 3;//更新引用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initWebView();
        initEvent();

    }
    private void initView(){
        webView = (WebView) findViewById(R.id.wv_articles);
        input_comment = (EditText) findViewById(R.id.edt_article_input);
        input_comment.setVisibility(View.GONE);



    }
    private void initEvent(){
        //测试Java调用Js方法
        test = (Button) findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("JavaScript:dataTest('trhrthrhr')");
            }
        });
        //测试输入法的显示与隐藏
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_SHOW_INPUT:
                        input_comment.setVisibility(View.VISIBLE);
                        break;
                    case MSG_HIDE_INPUT:
                        input_comment.setVisibility(View.GONE);
                        break;
                    case MSG_UPDATE:
                        input_comment.setFocusable(true);
                        break;
                }
            }
        };
    }
    private void initWebView(){
        String url =
                "http://192.168.31.236/userPage/article/?articleId=57d683ae5c95dc4d92c18522&token=qqqqq&device=android";
        //配置webView设置
        webSettings = webView.getSettings();
        isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);
        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"android");
        webView.loadUrl(url);//必须在最后一行
    }
    //测试Js调用Java方法,API19以上可用
    @JavascriptInterface
    public  void show(String s){
        Toast.makeText(ArticleActivity.this, s, Toast.LENGTH_SHORT).show();
        Logger.d(s);

    }

    /**
     * 输入框显示与隐藏
     * @param str 传入参数
     */
    @JavascriptInterface
    public void showInput(String str){
        Toast.makeText(ArticleActivity.this, "参数是："+str, Toast.LENGTH_SHORT).show();
        handler.sendEmptyMessage(MSG_SHOW_INPUT);
    }
    @JavascriptInterface
    public void hideInput(){
        handler.sendEmptyMessage(MSG_HIDE_INPUT);
    }

    /**
     * 清空缓存
     * @param isClear 是否清楚缓存
     */
    @JavascriptInterface
    public void clearCache(boolean isClear){
        webView.clearCache(isClear);
    }

    /**
     * 更新引用
     * @param id 引用评论id
     */
    public void updateQuote(String id){
        Toast.makeText(ArticleActivity.this, "引用"+id, Toast.LENGTH_SHORT).show();
        handler.sendEmptyMessage(MSG_UPDATE);
    }

}
