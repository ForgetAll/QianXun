package com.heapot.qianxun.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
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

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.MainTabFragmentAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSubscription, mSearch, mNotification, mStar;
    private ViewPager mViewPager;
    private MainTabFragmentAdapter mPageAdapter;
    private List<SubBean> mList = new ArrayList<>();
    private List<TagsBean.ContentBean> list = new ArrayList<>();
    private FloatingActionButton mCreate;
    private TextView mainTitle,subTitle;
    private static final String PAGE_SCIENCE = "PAGE_SCIENCE";
    private static final String PAGE_RECRUIT = "PAGE_RECRUIT";
    private static final String PAGE_TRAIN = "PAGE_TRAIN";
    private String pid = CustomApplication.PAGE_ARTICLES_ID;

    //本地广播尝试
    private IntentFilter intentFilter;
    private RefreshReceiver refreshReceiver;
    private LocalBroadcastManager localBroadcastManager;

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
        //注册本地广播
        localReceiver();

        //测试token
        Logger.d("打印本地token-->"+PreferenceUtil.getString("token")+"打印application中的token---》"+ CustomApplication.TOKEN);
    }

    private void initEvent() {
        initData();
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
     * 本地广播接收
     */
    private void localReceiver(){
        localBroadcastManager = LocalBroadcastManager.getInstance(this);//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.karl.refresh");
        refreshReceiver = new RefreshReceiver();
        localBroadcastManager.registerReceiver(refreshReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(refreshReceiver);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        switch (CustomApplication.getCurrentPageName()){
            case "PAGE_SCIENCE":
                pid = CustomApplication.PAGE_ARTICLES_ID;
                break;
            case "PAGE_RECRUIT":
                pid = CustomApplication.PAGE_JOBS_ID;
                break;
            case "PAGE_TRAIN":
                pid = CustomApplication.PAGE_ACTIVITIES_ID;
                break;
        }
        Object object = SerializableUtils.getSerializable(MainActivity.this, ConstantsBean.TAG_FILE_NAME);
        if (object != null) {
            list.addAll((Collection<? extends TagsBean.ContentBean>) object);//获取所有数据
            List<Integer> posList = new ArrayList<>();
            //获取指定二级标题的pos
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getPid() != null && list.get(i).getPid().equals(pid) && list.get(i).getSubscribeStatus() == 1){
                    posList.add(i);
                }

            }
            //获取指定页面的二级标题赋值给mList
            SubBean subBean;
            for (int i = 0; i < posList.size(); i++) {
                subBean = new SubBean();
                subBean.setId(list.get(posList.get(i)).getId());
                subBean.setPid(list.get(posList.get(i)).getPid().toString());
                subBean.setName(list.get(posList.get(i)).getName());
                subBean.setStatus(list.get(posList.get(i)).getSubscribeStatus());
                mList.add(subBean);
            }
            Logger.d("本地拿到的所有数据大小list："+list.size()+",当前页面id："+pid+",拿到的数据mList："+mList.size());
            initTab();
        } else {

        }
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

    /**
     * 广播接收器
     */
    class RefreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String post = intent.getExtras().getString("sub");
            String del = intent.getExtras().getString("del");
            Toast.makeText(context, "刷新订阅数据"+post+","+del, Toast.LENGTH_SHORT).show();
        }
    }
}
