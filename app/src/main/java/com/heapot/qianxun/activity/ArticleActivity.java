package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

/**
 * Created by Karl on 2016/9/24.
 * 文章详情页面，
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private WebSettings webSettings;
    private boolean isConnected;//判断网络状态
    private EditText input_comment;

    private static final int MSG_SHOW_INPUT = 0;//显示输入框
    private static final int MSG_HIDE_INPUT = 1;//隐藏输入框
    private static final int MSG_UPDATE = 3;//更新引用
    private static final int MSG_COMMENT = 4;
    private TextView quoteId;
    private ImageView sendMess;
    private LinearLayout input;
    //测试输入法的显示与隐藏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_INPUT:
                    input.setVisibility(View.VISIBLE);
                    break;
                case MSG_HIDE_INPUT:
                    input.setVisibility(View.GONE);
                    break;
                case MSG_UPDATE:

                    if (input_comment.getVisibility() == View.GONE) {
                        input_comment.setVisibility(View.VISIBLE);
                    }
                    String quoteStr = msg.getData().getString("quote");
                    if (quoteStr != null) {
                        if (quoteId.getVisibility() == View.GONE) {
                            quoteId.setVisibility(View.VISIBLE);
                        }
                        quoteId.setText(quoteStr);
                    }
                    input_comment.setFocusable(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        initEvent();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wv_articles);
        input_comment = (EditText) findViewById(R.id.edt_article_input);
        quoteId = (TextView) findViewById(R.id.txt_articles_quote);
        sendMess = (ImageView) findViewById(R.id.iv_send_message);
        input = (LinearLayout) findViewById(R.id.ll_input);
        input.setVisibility(View.GONE);

    }

    private void initEvent() {
        //发送信息注册点击事件
        sendMess.setOnClickListener(this);
        //获取当前页面id
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        String url = "http://sijiache.heapot.com/Tabs/userPage/article/" + "?articleId=" + id + "&device=android";
        final String url2 = "http://sijiache.heapot.com/Tabs/editer/test/111.html";
        //初始化webView
        initSettings();
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
        });
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view,url);
//                view.loadUrl("javascript:screenLog(\"加载加载\")");
//            }
//        });

    }

    private void initSettings() {
        webSettings = webView.getSettings();
        isConnected = NetworkUtils.isAvailable(this);
        if (isConnected) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ONLY);

        }
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "android");
    }

    /**
     * 输入框显示与隐藏
     *
     * @param str 传入参数
     */
    @JavascriptInterface
    public void showInput(String str) {
        handler.sendEmptyMessage(MSG_SHOW_INPUT);
    }

    @JavascriptInterface
    public void hideInput() {
        handler.sendEmptyMessage(MSG_HIDE_INPUT);
    }

    /**
     * 更新引用
     *
     * @param id 引用评论id
     */
    @JavascriptInterface
    public void updateQuote(String id) {
        Message message = new Message();
        message.what = MSG_UPDATE;
        Bundle bundle = new Bundle();
        bundle.putString("quote", id);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * 清空输入框
     */
    private void clearInput(boolean isClear) {
        if (isClear) {
            handler.sendEmptyMessage(MSG_COMMENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        webView.destroy();
    }

    @Override
    public void onClick(View v) {
        //postComment(data)
        switch (v.getId()) {
            case R.id.iv_send_message:

                String getComment = input_comment.getText().toString();
                String quote = quoteId.getText().toString();
                if (getComment != null && !getComment.equals("")) {
                    Toast.makeText(ArticleActivity.this, "评论发送中", Toast.LENGTH_SHORT).show();
                    String data;
                    if (quote != null && !quote.equals("")) {
                        data = "\"{" + "\'token\':\'" + CustomApplication.TOKEN + "\',\'refId\':" + quote + "\',\'commentContent\':\'" + getComment + "\'}\"";
                    } else {
                        data = "\"{" + "\'token\':\'" + CustomApplication.TOKEN + "\',\'commentContent\':\'" + getComment + "\'}\"";

                    }
                    webView.loadUrl("javascript:postComment(" + data + ")");//调用Js方法发表评论
                } else {
                    Toast.makeText(ArticleActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
