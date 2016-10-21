package com.heapot.qianxun.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.heapot.qianxun.bean.Friend;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Karl on 2016/9/24.
 * 文章详情页面，
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTitle;
    private ImageView mBack;

    private WebView webView;
    private WebSettings webSettings;
    private boolean isConnected;//判断网络状态
    private EditText input_comment;

    private static final int MSG_SHOW_INPUT = 0;//显示输入框
    private static final int MSG_HIDE_INPUT = 1;//隐藏输入框
    private static final int MSG_UPDATE = 3;//更新引用
    private static final int MSG_COMMENT = 4;//添加评论
    private static final int MSG_TO_CHAT = 5;//跳转聊天
    private String articleId = "";
    private String refId = "";
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
                    String data = msg.getData().getString("quote");

                    if (data != null) {
                        if (quote_layout.getVisibility() == View.GONE) {
                            quote_layout.setVisibility(View.VISIBLE);
                        }
                        String content = "";
                        String id = "";
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject inputData = jsonObject.getJSONObject("inputData");
                            content = inputData.getString("refContent");
                            id = inputData.getString("refId");
                            Logger.d("id--->"+id+",content--->"+content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refId = id;
                        quote_txt.setText(content);
                    }
                    input_comment.setFocusable(true);
                    break;
                case MSG_TO_CHAT:
                    String id = msg.getData().getString("id");
                    String title = msg.getData().getString("title");

                    Logger.d("id------>"+id+",title------>"+title);
                    Object object = SerializableUtils.getSerializable(ArticleActivity.this,ConstantsBean.MY_USER_INFO);
                    if (object != null){
                        MyUserBean.ContentBean myUserBean = (MyUserBean.ContentBean) object;
                        if (!myUserBean.getId().equals(id)){
                            if (RongIM.getInstance() != null){
                                getUserInfoFromNetWork(id);
                                RongIM.getInstance().startPrivateChat(ArticleActivity.this,id,title);

                            }
                        }

                    }
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

    @Override
    protected void onResume() {
        super.onResume();

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
        mTitle = (TextView) findViewById(R.id.txt_title);
        mTitle.setText("文章详情");
        mBack = (ImageView) findViewById(R.id.iv_btn_back);

    }

    private void initEvent() {
        //发送信息注册点击事件
        sendMess.setOnClickListener(this);
        clearQuote.setOnClickListener(this);
        mBack.setOnClickListener(this);
        //获取当前页面id
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        articleId = id;
        String url = "http://sijiache.heapot.com/Tabs/userPage/article/" + "?articleId=" + id + "&device=android";
//        final String url2 = "http://sijiache.heapot.com/Tabs/editer/test/111.html";
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
     * @param data 引用评论id
     */
    @JavascriptInterface
    public void updateQuote(String data) {
        Logger.json(data);
        Message message = new Message();
        message.what = MSG_UPDATE;
        Bundle bundle = new Bundle();
        bundle.putString("quote", data);
        message.setData(bundle);
        handler.sendMessage(message);
    }
    @JavascriptInterface
    public void toChat(String id,String title){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        bundle.putString("title",title);
        message.setData(bundle);
        message.what = MSG_TO_CHAT;
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
                if (content != null && !content.equals("")) {
                    Toast.makeText(ArticleActivity.this, "评论发送中", Toast.LENGTH_SHORT).show();
                    postComment(content, articleId);
                } else {
                    Toast.makeText(ArticleActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_clear_quote:
                quote_txt.setText("");
                refId = "";
                quote_layout.setVisibility(View.GONE);
                break;
            case R.id.iv_btn_back:
                webView.clearCache(true);
                webView.destroy();
                finish();
                break;
        }
    }

    private void postComment(String content, String article){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.ADD_COMMENT;
        String body;
        if (refId.equals("")){
            body = "{\"articleId\":\""+article+"\",\"content\":\""+content+"\"}";
        }else {
            body = "{\"refId\":\""+refId+"\",\"articleId\":\""+article+"\",\"content\":\""+content+"\"}";
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
                                Toast.makeText(ArticleActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                //发送成功
                                input_comment.setText("");//清空数据
                                quote_txt.setText("");
                                refId = "";
                                quote_layout.setVisibility(View.GONE);
                                //刷新评论
                                webView.loadUrl("javascript:fillComment()");
                            }else {
                                Toast.makeText(ArticleActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ArticleActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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


    private void getUserInfoFromNetWork(String id){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_USER_INFO+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(String.valueOf(response));
                        //所以要取出来原来的数据们然后加入新的一起存取
                        try {
                            if (response.getString("status").equals("success")){
                                Toast.makeText(ArticleActivity.this, "存储成功！", Toast.LENGTH_SHORT).show();
                                //请求数据成功
                                MyUserBean userBean = (MyUserBean) JsonUtil.fromJson(String.valueOf(response),MyUserBean.class);
                                List<MyUserBean.ContentBean> list = new ArrayList<>();
                                //拿到当前用户信息
                                list.add(userBean.getContent());
                                //先判断本地存的有没有数据
                                Object object = SerializableUtils.getSerializable(ArticleActivity.this,ConstantsBean.IM_FRIEND);
                                //本地没有数据的话，直接添加
                                if (object == null){
                                    List<Friend> friends = new ArrayList<>();
                                    Friend friend = new Friend(list.get(0).getId(),list.get(0).getNickname(),list.get(0).getIcon());
                                    friends.add(friend);
                                    SerializableUtils.setSerializable(ArticleActivity.this,ConstantsBean.IM_FRIEND,friends);
                                }else {
                                    //本地已经有数据，在后面追加
                                    List<Friend> friends = new ArrayList<>();//本地数据集合
                                    friends.addAll((Collection<? extends Friend>) object);
                                    List<Integer> pos = new ArrayList<>();//本地已有数据记录位置
                                    //循环遍历拿到本地已有数据下标,
                                    for (int i = 0; i < friends.size(); i++) {
                                        if (friends.get(i).getUserId().equals(list.get(0).getId())){
                                            pos.add(i);
                                        }
                                    }
                                    //判断是否是重复数据，为0则没有重复，直接添加当前一条数据，
                                    if (pos.size() != 0){
                                        //数据已存在，进行数据更新
                                        for (int i = 0; i < pos.size(); i++) {
                                            friends.get(pos.get(i)).setUserId(list.get(0).getId());
                                            friends.get(pos.get(i)).setUserName(list.get(0).getNickname());
                                            friends.get(pos.get(i)).setPortraitUri(list.get(0).getIcon());
                                        }
                                    }else {
                                        //里面没有重复的数据,将当前id加上
                                        Friend friend = new Friend(list.get(0).getId(),list.get(0).getNickname(),list.get(0).getIcon());
                                        friends.add(friend);
                                    }
                                    SerializableUtils.setSerializable(ArticleActivity.this,ConstantsBean.IM_FRIEND,friends);
                                }
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

}
