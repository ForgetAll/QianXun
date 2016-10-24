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
import android.os.Handler;
import android.os.Message;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.create.CreateActivity;
import com.heapot.qianxun.adapter.MainTabFragmentAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.Friend;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.util.ChatInfoUtils;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.heapot.qianxun.util.SerializableUtils;
import com.heapot.qianxun.util.TagsUtils;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

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
    private TextView mSubscription,mainTitle,subTitle;
    private static final String PAGE_SCIENCE = "PAGE_SCIENCE";
    private static final String PAGE_RECRUIT = "PAGE_RECRUIT";
    private static final String PAGE_TRAIN = "PAGE_TRAIN";
    private String pid = CustomApplication.PAGE_ARTICLES_ID;

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
        subTitle = (TextView) findViewById(R.id.txt_second_title);


        //注册本地广播
        localReceiver();
        //开启融云服务器连接
        getRongToken();
        //获取用户好友信息
//        ChatInfoUtils.getAddFriendsRequestList(CustomApplication.TOKEN);
        getFriend();

        //测试token
        Logger.d("打印本地token-->"+PreferenceUtil.getString("token")+"打印application中的token---》"+ CustomApplication.TOKEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RongIM.setUserInfoProvider(MainActivity.this,true);
//        getLocalFriend();
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

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mPageAdapter = new MainTabFragmentAdapter(getSupportFragmentManager(), this, mList);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mList.size() == 0){
            Toast.makeText(MainActivity.this, "快去订阅标签", Toast.LENGTH_SHORT).show();
        }

        mainTitle.setOnClickListener(this);
        subTitle.setOnClickListener(this);
    }


    /**
     * 初始化数据
     */
    public  void initData(){
        mList.clear();
        list.clear();
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
            Logger.d("当前页面："+CustomApplication.getCurrentPageName()+",当前数据大小"+mList.size());

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
//                    RongIM.getInstance().startPrivateChat(this,"110","大大");

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
            case R.id.txt_second_title:
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
    public void setBanner(String url){
        Logger.d("MainActivity:setBanner------>"+url);
//        Glide.with(this).load(url).into(mBanner);
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
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            moveTaskToBack(true);
//            return false;
//        }
//
//        return super.onKeyDown(keyCode, event);


//        long exitTime=0;
//        // TODO 按两次返回键退出应用程序
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            // 判断间隔时间 大于2秒就退出应用
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                // 应用名
//                String applicationName = getResources().getString(
//                        R.string.app_name);
//                String msg = "再按一次返回键退出" + applicationName;
//                //String msg1 = "再按一次返回键回到桌面";
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                // 计算两次返回键按下的时间差
//                exitTime = System.currentTimeMillis();
//            } else {
//                // 关闭应用程序
//                finish();
//                // 返回桌面操作
//                // Intent home = new Intent(Intent.ACTION_MAIN);
//                // home.addCategory(Intent.CATEGORY_HOME);
//                // startActivity(home);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
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

        getFriend();
        Logger.d("MainActivity:FriendList----->"+"当前id是---->"+friendList.get(0).getFriendId()+"当前名字是----->"+friendList.get(0).getNickname());
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
//                case 6:
//                    String url = intent.getExtras().getString("imageUrl");
//                    setBanner(url);
//                    break;
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
    /**
     *需要的时候进行融云服务器连接
     */
    /**
     * 获取当前用户在融云的token
     * @return 返回获取到的token
     */
    private String getRongToken(){
        final String token = "";
        String url = ConstantsBean.BASE_PATH+ConstantsBean.IM_TOKEN;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                //获取成功，取出来token
                                String im_token = response.getString("content");
                                conn(im_token);
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
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
        return token;
    }

    /**
     * 建立与融云服务器的连接
     * @param token 连接所需token
     */
    private void conn(final String token){
        if (getApplicationInfo().packageName.equals(CustomApplication.getCurProcessName(getApplicationContext()))){
            /**
             * IMKit SDK调用第二步，建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    //Token错误，重新请求token
                    getRongToken();
                    Logger.d("拿到的Token错误");
                }

                @Override
                public void onSuccess(String s) {

                    //连接融云成功
                    Logger.d("IM-Success-------->UserId:"+s);
                    CustomApplication.IM_TOKEN = token;
                    RongIM.getInstance().getRongIMClient().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                        @Override
                        public boolean onReceived(io.rong.imlib.model.Message message, int i) {
                            Toast.makeText(MainActivity.this, "有新消息来了", Toast.LENGTH_SHORT).show();
                            ChatInfoUtils.getAddFriendsRequestList(CustomApplication.TOKEN);//测试拿到用户信息并存储
                            return false;
                        }
                    });

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Logger.d("连接失败，错误码--------->"+errorCode);
                }
            });
        }
    }


    private void getFriend(){
        friendList.clear();
        Object object = SerializableUtils.getSerializable(this,ConstantsBean.IM_FRIEND);
        if (object!= null){
            friendList = (List<Friend.ContentBean>) object;
        }
        if (SerializableUtils.getSerializable(this,ConstantsBean.MY_USER_INFO )!=null){
            Object object2 = SerializableUtils.getSerializable(this,ConstantsBean.MY_USER_INFO);
            MyUserBean.ContentBean myUserBean = (MyUserBean.ContentBean) object2;
            String name = myUserBean.getNickname();
            String id = myUserBean.getId();
            String icon = myUserBean.getIcon();
            Friend.ContentBean friend = new Friend.ContentBean();
            friend.setFriendId(id);
            friend.setNickname(name);
            friend.setIcon(icon);
            friendList.add(friend);
            Logger.d("第二次取数据----->"+friendList.size());
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }
    private void refreshUserInfo(){
        //刷新用户信息
        String id = "";
        String name = "";
        String image = "";
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,name,Uri.parse(image)));
    }
}
