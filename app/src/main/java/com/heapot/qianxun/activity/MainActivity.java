package com.heapot.qianxun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
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
import com.heapot.qianxun.bean.MyTagBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.util.LoadTagsUtils;
import com.heapot.qianxun.util.LoadUserInfo;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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

public class MainActivity extends BaseActivity
        implements View.OnClickListener, RongIM.UserInfoProvider, LoadTagsUtils.OnLoadTagListener, LoadUserInfo.OnUserInfoListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSearch, mNotification, mStar;
    private ViewPager mViewPager;
    private MainTabFragmentAdapter mPageAdapter;
    private List<SubBean> mList = new ArrayList<>();
    private FloatingActionButton mCreate;
    private TextView mSubscription,mainTitle;

    private String PAGE_ARTICLE = "PAGE_ARTICLE";
    private String PAGE_RECRUIT = "PAGE_RECRUIT";
    private String PAGE_TRAIN = "PAGE_TRAIN";
    public  String PAGE_CURRENT = "PAGE_ARTICLE";

    //添加主页页面的ID
    public String PAGE_ARTICLES_ID = "f3b8d91b8f9c4a03a4a06a5678e79872";
    public String PAGE_ACTIVITIES_ID = "9025053c65e04a6992374c5d43f31acf";
    public String PAGE_JOBS_ID = "af3a09e8a4414c97a038a2d735064ebc";

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        RongIM.setUserInfoProvider(MainActivity.this,true);

    }

    private void initEvent() {
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
        PAGE_CURRENT = PAGE_ARTICLE;
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        new LoadTagsUtils(this,this).getTags(getAppToken(),0);

        mainTitle.setOnClickListener(this);
        Glide.with(this).load("http://114.215.252.158/banner.png").into(mBanner);
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
                intent.putExtra("page",PAGE_CURRENT);
                startActivityForResult(intent,101);
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
    public void refreshData(String page){
        new LoadTagsUtils(this,this).getTags(getAppToken(),0);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public UserInfo getUserInfo(String s) {

        new LoadUserInfo(MainActivity.this,MainActivity.this).getChatUserInfo(s,getAppToken());

        return null;
    }

    @Override
    public void onLoadAllSuccess(List<TagsBean.ContentBean> list,int flag) {
        if (list != null) {
            loadData(list,flag);
        } else {
            Toast.makeText(this, "尚未订阅数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoadSuccess(List<MyTagBean.ContentBean.RowsBean> list , int flag) {
//        if (list != null) {
//            loadData(list,flag);
//        } else {
//            Toast.makeText(this, "尚未订阅数据", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(this, "请求数据失败", Toast.LENGTH_SHORT).show();
    }


    private void loadData(List<TagsBean.ContentBean> list,int flag){
        mList.clear();
        if (PAGE_CURRENT.equals(PAGE_ARTICLE)){

            List<Integer> pos = new ArrayList<>();
            int n = list.size();
            for (int i = 0; i < n; i++) {
                if (list.get(i).getPid() != null && list.get(i).getPid().equals(PAGE_ARTICLES_ID) && list.get(i).getSubscribeStatus() == 1){
                    pos.add(i);
                }
            }
            if (pos.size()>0){
                SubBean subBean;
                for (int i = 0; i < pos.size(); i++) {
                    subBean = new SubBean();
                    subBean.setPid((String) list.get(pos.get(i)).getPid());
                    subBean.setName(list.get(pos.get(i)).getName());
                    subBean.setId(list.get(pos.get(i)).getId());
                    mList.add(subBean);
                }
            }
            Logger.d("文章"+mList.size());

        }else if (PAGE_CURRENT.equals(PAGE_RECRUIT)){

            List<Integer> pos = new ArrayList<>();
            int n = list.size();
            for (int i = 0; i < n; i++) {
                if (list.get(i).getPid() != null && list.get(i).getPid().equals(PAGE_JOBS_ID)&& list.get(i).getSubscribeStatus() == 1){
                    pos.add(i);
                }
            }
            if (pos.size()>0){
                SubBean subBean;
                for (int i = 0; i < pos.size(); i++) {
                    subBean = new SubBean();
                    subBean.setPid((String) list.get(pos.get(i)).getPid());
                    subBean.setName(list.get(pos.get(i)).getName());
                    subBean.setId(list.get(pos.get(i)).getId());
                    mList.add(subBean);
                }
            }
            Logger.d("招聘"+mList.size());

        }else if (PAGE_CURRENT.equals(PAGE_TRAIN)){
            List<Integer> pos = new ArrayList<>();
            int n = list.size();
            for (int i = 0; i < n; i++) {
                if (list.get(i).getPid() != null && list.get(i).getPid().equals(PAGE_ACTIVITIES_ID)&& list.get(i).getSubscribeStatus() == 1){
                    pos.add(i);
                }
            }
            if (pos.size()>0){
                SubBean subBean;
                for (int i = 0; i < pos.size(); i++) {
                    subBean = new SubBean();
                    subBean.setPid((String) list.get(pos.get(i)).getPid());
                    subBean.setName(list.get(pos.get(i)).getName());
                    subBean.setId(list.get(pos.get(i)).getId());
                    mList.add(subBean);
                }
                Logger.d("招聘"+mList.size());
            }
        }
        Logger.d("Main subList"+mList.size());
        if (flag == 0) {
            mPageAdapter = new MainTabFragmentAdapter(getSupportFragmentManager(), this, mList);
            mViewPager.setAdapter(mPageAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }else {
            //刷新数据源
            mPageAdapter.setData(mList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 101){
            boolean result = data.getBooleanExtra("result",true);
            if (result){
                new LoadTagsUtils(this,this).getTags(getAppToken(),0);
            }
        }

    }

    @Override
    public void onResponseSuccess(String id, String nickname, String icon) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,nickname, Uri.parse(icon)));
    }

    @Override
    public void onResponseError(String id) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,"仟询用户", null));
    }
}
