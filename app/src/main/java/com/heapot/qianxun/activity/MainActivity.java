package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.MyPageAdapter;
import com.heapot.qianxun.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSubscription,mSearch,mNotification,mStar;
    private ViewPager mViewPager;
    private MyPageAdapter mPageAdapter;


    private String currentId;
    private List<String> mList;
    //主页界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTransparentBar();
        initView();
        initEvent();
        initData();
    }
    //初始化控件
    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);

        mSearch = (ImageView) findViewById(R.id.iv_search);
        mStar = (ImageView) findViewById(R.id.iv_star);
        mNotification = (ImageView) findViewById(R.id.iv_notification);
        mBanner = (ImageView) findViewById(R.id.iv_banner);
        mSubscription = (ImageView) findViewById(R.id.iv_subscription_choose);

        mList = new ArrayList<>();
    }
    private void initEvent(){
        //状态栏和抽屉效果
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.app_name,R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //添加监听事件
        mSearch.setOnClickListener(this);
        mStar.setOnClickListener(this);
        mNotification.setOnClickListener(this);
        mBanner.setOnClickListener(this);
        mSubscription.setOnClickListener(this);
    }
    /**
     * 模拟数据
     */
    private void initData(){
        for (int i = 0; i < 15; i++) {
            mList.add("Tab-"+i);
        }
        currentId = DataBean.PAGE_SCIENCE;
        initTab();
    }

    /**
     * 实现动态添加Tab
     */
    private void initTab(){
        mPageAdapter = new MyPageAdapter(getSupportFragmentManager(),this,mList);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                break;
            case R.id.iv_star:
                break;
            case R.id.iv_notification:
                break;
            case R.id.iv_banner:
                break;
            case R.id.iv_subscription_choose:
                Intent intent = new Intent(this,Subscription.class);
                startActivity(intent);
                break;


        }
    }

    /**
     * 提供一些对外接口方法
     */
    //关闭抽屉
    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }
    public void setPageId(String id){
        currentId = id;
    }



    /**
     * 配置状态栏
     */
    private void setTransparentBar(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
