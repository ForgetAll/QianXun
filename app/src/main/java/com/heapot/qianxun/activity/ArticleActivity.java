package com.heapot.qianxun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private String mId;
    private TextView quote_txt,sendMess;
    private LinearLayout input_layout,quote_layout;
    private ImageView clearQuote;
    //测试输入法的显示与隐藏
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_INPUT:
                    input_layout.setVisibility(View.VISIBLE);
                    break;
                case MSG_HIDE_INPUT:
                    if (quote_txt.getText().toString().equals("") && input_comment.getText().toString().equals("")) {
                        input_layout.setVisibility(View.GONE);
                        quote_layout.setVisibility(View.GONE);
                    }
                    break;
                case MSG_UPDATE:
                    if (input_layout.getVisibility() == View.GONE){
                        input_layout.setVisibility(View.VISIBLE);

                    }
                    String quoteStr = msg.getData().getString("quote");
                    if (quoteStr != null) {
                        if (quote_layout.getVisibility() == View.GONE) {
                            quote_layout.setVisibility(View.VISIBLE);
                        }
                        quote_txt.setText(quoteStr);
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
        quote_txt = (TextView) findViewById(R.id.txt_articles_quote);
        input_layout = (LinearLayout) findViewById(R.id.ll_input);
        sendMess = (TextView) findViewById(R.id.txt_send_mess);
        input_layout.setVisibility(View.GONE);
        input_comment.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        quote_layout = (LinearLayout) findViewById(R.id.ll_quote);
        quote_layout.setVisibility(View.GONE);
        clearQuote = (ImageView) findViewById(R.id.iv_clear_quote);

    }

    private void initEvent() {
        //发送信息注册点击事件
        sendMess.setOnClickListener(this);
        clearQuote.setOnClickListener(this);
        //获取当前页面id
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        mId = id;
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
            case R.id.txt_send_mess:

                String content = input_comment.getText().toString();
                String quote = quote_txt.getText().toString();
                if (content != null && !content.equals("")) {
                    Toast.makeText(ArticleActivity.this, "评论发送中", Toast.LENGTH_SHORT).show();
                    postComment(content,mId,quote);
                } else {
                    Toast.makeText(ArticleActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_clear_quote:
                quote_txt.setText("");
                quote_layout.setVisibility(View.GONE);
                break;
        }
    }

    private void postComment(String content, String article, final String quoteId){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.ADD_COMMENT;
        String body;
        if (quoteId == null || quoteId.equals("")){
            body = "{\"articleId\":\""+article+"\",\"content\":\""+content+"\"}";
        }else {
            body = "{\"refId\":"+quoteId+"\"articleId\":\""+article+"\",\"content\":\""+content+"\"}";
        }
        JSONObject json = null;
        try {
            json = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.json(String.valueOf(json));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                //发送成功
                                input_comment.setText("");//清空数据
//                                input_comment.setFocusable(false);
                                quote_txt.setText("");
                                quote_layout.setVisibility(View.GONE);
                                //刷新评论
                                webView.loadUrl("javascript:fillComment()");
                            }else {
                                Toast.makeText(ArticleActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
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
                Map<String, String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);

    }


}
