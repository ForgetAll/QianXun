package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.MainTabFragmentAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSubscription, mSearch, mNotification, mStar;
    private ViewPager mViewPager;
    private MainTabFragmentAdapter mPageAdapter;

    private List<SubscribedBean.ContentBean.RowsBean> mList;

    private FloatingActionButton mCreate;

    private TextView mainTitle,subTitle;

    private static final String PAGE_SCIENCE = "PAGE_SCIENCE";
    private static final String PAGE_RECRUIT = "PAGE_RECRUIT";
    private static final String PAGE_TRAIN = "PAGE_TRAIN";

    //主页界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTransparentBar();
        initView();
        initEvent();


    }

    //初始化控件
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);

        mSearch = (ImageView) findViewById(R.id.iv_search);
        mStar = (ImageView) findViewById(R.id.iv_star);
        mNotification = (ImageView) findViewById(R.id.iv_notification);
        mBanner = (ImageView) findViewById(R.id.iv_banner);
        mSubscription = (ImageView) findViewById(R.id.iv_subscription_choose);
        mCreate = (FloatingActionButton) findViewById(R.id.fab_create);

        mainTitle = (TextView) findViewById(R.id.txt_first_title);
        subTitle = (TextView) findViewById(R.id.txt_second_title);

        mList = new ArrayList<>();

        //测试token
        Logger.d("打印本地token-->"+PreferenceUtil.getString("token")+"打印application中的token---》"+ CustomApplication.TOKEN);
    }

    private void initEvent() {
        initData();
//        getUserInfo();
        //状态栏和抽屉效果
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //添加监听事件
        mSearch.setOnClickListener(this);
        mStar.setOnClickListener(this);
        mNotification.setOnClickListener(this);
        mBanner.setOnClickListener(this);
        mSubscription.setOnClickListener(this);
        mCreate.setOnClickListener(this);

        //一些基本的初始化数据
        mainTitle.setText("学术");
        subTitle.setText("招聘 培训");


    }

    /**
     * 初始化数据
     */
    private void initData(){
        boolean isConnected = NetworkUtils.isAvailable(this);
        if (isConnected){
            getSubscriptionTags();
        }else {
            Logger.d("网络不正常");
            Object object = SerializableUtils.getSerializable(MainActivity.this, ConstantsBean.SUB_FILE_NAME);
            if (object != null) {
                mList.addAll((Collection<? extends SubscribedBean.ContentBean.RowsBean>) object);
                initTab();
            } else {
                Toast.makeText(MainActivity.this, "没网没数据怎么办", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void getSubscriptionTags(){
        String url = ConstantsBean.BASE_PATH+ConstantsBean.GET_SUBSCRIBED;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                SubscribedBean subscribedBean = (SubscribedBean) JsonUtil.fromJson(String.valueOf(response),SubscribedBean.class);
                                String name = subscribedBean.getContent().getRows().get(0).getName();
                                Logger.d(name);
                                for (int i = 0; i < subscribedBean.getContent().getRows().size(); i++) {
                                    if (subscribedBean.getContent().getRows().get(i) != null){
                                        mList.add(subscribedBean.getContent().getRows().get(i));
                                    }
                                }
//                                mList.addAll(subscribedBean.getContent().getRows());
                                SerializableUtils.setSerializable(MainActivity.this,ConstantsBean.SUB_FILE_NAME,mList);
                                initTab();
                            }else {
                                Toast.makeText(MainActivity.this, "加载数据失败,我也不知道咋办了", Toast.LENGTH_SHORT).show();
                                Logger.d(response.get("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "服务器出错了！", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
                return map;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


    /**
     * 实现动态添加Tab
     */
    private void initTab() {
        mPageAdapter = new MainTabFragmentAdapter(getSupportFragmentManager(), this, mList);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    /**
     * 点击事件
     * @param v 获取id
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case R.id.iv_star:
                break;
            case R.id.iv_notification:
                Intent intentSearch = new Intent(this, NotificationActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.iv_banner:
                break;
            case R.id.iv_subscription_choose:
                Intent intent = new Intent(this, Subscription.class);
                startActivity(intent);
                break;
            case R.id.fab_create:
                Intent createIntent = new Intent(this,CreateActivity.class);
                startActivity(createIntent);
                break;


        }
    }

    /**
     * 提供一些对外接口方法
     * 1、关闭抽屉
     * 3、设置导航图片banner
     */
    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }
    public void setBanner(Bitmap bitmap){
        mBanner.setImageBitmap(bitmap);
    }
    public void setToolBarTitle(String name){
        switch (name){
            case PAGE_SCIENCE:
                mainTitle.setText("学术");
                subTitle.setText("招聘 培训");
                break;
            case PAGE_RECRUIT:
                mainTitle.setText("招聘");
                subTitle.setText("学术 培训");
                break;
            case PAGE_TRAIN:
                mainTitle.setText("培训");
                subTitle.setText("学术 招聘");
                break;
        }
    }


    /**
     * 配置状态栏
     */
    private void setTransparentBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * 返回桌面不退出应用，只放在后台
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
//    private List<MyUserBean.ContentBean> userList = new ArrayList<>();
    /**
     * 在主页获取用户信息然后进行存储，直接在侧滑菜单进行绘制就可以了
     */
//    private void getUserInfo(){
//        String url=ConstantsBean.BASE_PATH + ConstantsBean.PERSONAL_INFO;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String status = response.getString("status");
//                            if (status.equals("success")){
//                                userList = (List<MyUserBean.ContentBean>) JsonUtil.fromJson(String.valueOf(response),MyUserBean.class);
////                                SerializableUtils.setSerializable(MainActivity.this,ConstantsBean.MY_USER_INFO,mList);
//                                CustomApplication.user_nickName = userList.get(0).getName();
//                                CustomApplication.user_quote = userList.get(0).getId();
//                            }else {
//                                Toast.makeText(MainActivity.this, "鬼知道出什么问题了", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> headers = new HashMap<>();
//                headers.put(ConstantsBean.KEY_TOKEN,CustomApplication.TOKEN);
//                return headers;
//            }
//        };
//        CustomApplication.getRequestQueue().add(jsonObjectRequest);
//    }
}
