package com.heapot.qianxun.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.create.CreateActivity;
import com.heapot.qianxun.adapter.MainTabFragmentAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Friend;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.util.SerializableUtils;
import com.heapot.qianxun.util.TagsUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 *
 */

public class MainActivity extends BaseActivity implements View.OnClickListener, RongIM.UserInfoProvider {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSearch, mNotification, mStar;
    private ViewPager mViewPager;
    private MainTabFragmentAdapter mPageAdapter;
    private List<SubBean> mList = new ArrayList<>();
    private List<TagsBean.ContentBean> list = new ArrayList<>();
    private FloatingActionButton mCreate;
    private TextView mSubscription,mainTitle;

    private String PAGE_ARTICLE = "PAGE_ARTICLE";
    private String PAGE_RECRUIT = "PAGE_RECRUIT";
    private String PAGE_TRAIN = "PAGE_TRAIN";
    private static final String PAGE_CURRENT = "PAGE_ARTICLE";

    //添加主页页面的ID
    public String PAGE_ARTICLES_ID = "f3b8d91b8f9c4a03a4a06a5678e79872";
    public String PAGE_ACTIVITIES_ID = "9025053c65e04a6992374c5d43f31acf";
    public String PAGE_JOBS_ID = "af3a09e8a4414c97a038a2d735064ebc";

    private String pid = PAGE_ARTICLES_ID;

    //本地广播尝试
    private IntentFilter intentFilter;
    private RefreshReceiver refreshReceiver;
    private LocalBroadcastManager localBroadcastManager;

    //聊天集合
    List<Friend.ContentBean> friendList = new ArrayList<>();


    //主页界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTransparentBar();
        initView();
        initEvent();
        //检查版本更新,待修改完后台数据 就把注销去掉
        //  UpdateUtil.getInstance().checkUpdate(activity, null, false);

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
        mSubscription = (TextView) findViewById(R.id.iv_subscription_choose);
        mCreate = (FloatingActionButton) findViewById(R.id.fab_create);

        mainTitle = (TextView) findViewById(R.id.txt_first_title);


        Glide.with(this).load("http://114.215.252.158/banner.png").into(mBanner);
        //注册本地广播
        localReceiver();


    }

    @Override
    protected void onResume() {
        super.onResume();
        RongIM.setUserInfoProvider(MainActivity.this,true);
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
        mainTitle.setText("仟言仟语");

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mPageAdapter = new MainTabFragmentAdapter(getSupportFragmentManager(), this, mList);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mList.size() == 0){
            Toast.makeText(MainActivity.this, "快去订阅标签", Toast.LENGTH_SHORT).show();
        }

        mainTitle.setOnClickListener(this);
    }


    /**
     * 初始化数据
     */
    public  void initData(){
        mList.clear();
        list.clear();
        switch (PAGE_CURRENT){
            case "PAGE_ARTICLE":
                pid = PAGE_ARTICLES_ID;
                break;
            case "PAGE_RECRUIT":
                pid = PAGE_JOBS_ID;
                break;
            case "PAGE_TRAIN":
                pid = PAGE_ACTIVITIES_ID;
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

        } else {

        }
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
                if (RongIM.getInstance() != null){
                    //设置融云的用户信息
                    RongIM.getInstance().startConversationList(MainActivity.this);
                }
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
            case R.id.txt_first_title:
                mDrawerLayout.openDrawer(Gravity.LEFT);
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
    public void setToolBarTitle(String name){
        if (name.equals(PAGE_ARTICLE)){
            mainTitle.setText("仟言仟语");
        }else if (name.equals(PAGE_RECRUIT)){
            mainTitle.setText("仟职百态");
        } else if (name.equals(PAGE_TRAIN)) {
            mainTitle.setText("仟锤百炼");
        }
    }

    /**
     * 刷新数据
     */
    public void refreshData(){
        initData();
        mPageAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mList.size() == 0){
            Toast.makeText(MainActivity.this, "快去订阅标签", Toast.LENGTH_SHORT).show();
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

        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {


            final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
            alterDialog.setMessage("确定退出应用？");
            alterDialog.setCancelable(true);

            alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().disconnect(true);

                    android.os.Process.killProcess(Process.myPid());
                }
            });
            alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alterDialog.show();
        }

        return false;


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



    @Override
    public UserInfo getUserInfo(String s) {


        //循环添加
        if (friendList.size() != 0) {
            for (Friend.ContentBean i : friendList) {
                if (i.getFriendId().equals(s)) {
                    Logger.d("当前id是---->"+i.getFriendId()+"当前名字是----->"+i.getNickname());
                    return new UserInfo(i.getFriendId(), i.getNickname(), Uri.parse(i.getIcon()));
                }
            }
        }
        return null;
    }

    /**
     * 广播接收器
     */
    class RefreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getExtras().getInt("status");
            switch (status){
                case 0://无更新,不需要操作
                    break;
                case 1:
                    //只提交更新
                    List<String> post = new ArrayList<>();
                    post.addAll((Collection<? extends String>) intent.getExtras().getSerializable("postList"));
                    for (int i = 0; i < post.size(); i++) {
                        TagsUtils.postSub(MainActivity.this,post.get(i));
                    }
                    break;
                case 2:
                    List<String> del = new ArrayList<>();
                    del.addAll((Collection<? extends String>) intent.getExtras().getSerializable("delList"));
                    for (int i = 0; i < del.size(); i++) {
                        TagsUtils.deleteSub(MainActivity.this,del.get(i));
                    }
                    //只提交取消
                    break;
                case 3:
                    //订阅和取消都要提交
                    List<String> postList = new ArrayList<>();
                    postList.addAll((Collection<? extends String>) intent.getExtras().getSerializable("postList"));
                    for (int i = 0; i < postList.size(); i++) {
                        TagsUtils.postSub(MainActivity.this,postList.get(i));
                    }
                    List<String> delList = new ArrayList<>();
                    delList.addAll((Collection<? extends String>) intent.getExtras().getSerializable("delList"));
                    for (int i = 0; i < delList.size(); i++) {
                        TagsUtils.deleteSub(MainActivity.this,delList.get(i));
                    }
                    break;
                case 5:
                    break;
            }

            if (status != 0 || status != 6){
                Logger.d("发生变化了"+status);
                initData();
                mViewPager.setAdapter(mPageAdapter);
                mPageAdapter.notifyDataSetChanged();
                mTabLayout.setupWithViewPager(mViewPager);
                if (mList.size() == 0){
                    Toast.makeText(MainActivity.this, "快去订阅标签", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
