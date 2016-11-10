package com.heapot.qianxun.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.heapot.qianxun.R;
import com.heapot.qianxun.fragment.ArticleFragment;
import com.heapot.qianxun.fragment.JobFragment;
import com.heapot.qianxun.fragment.TrainFragment;
import com.heapot.qianxun.util.LoadUserInfo;

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
        implements RongIM.UserInfoProvider, LoadUserInfo.OnUserInfoListener {



    DrawerLayout mDrawer;


    ArticleFragment articleFragment;
    JobFragment jobFragment;
    TrainFragment trainFragment;

    static final int PAGE_ARTICLE = 1;
    static final int PAGE_JOB = 2;
    static final int PAGE_TRAIN = 3;


    //主页界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTransparentBar();
        initView();
        if (savedInstanceState == null){
            setDefaultFragment();
        }
    }

    //初始化控件
    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
    }

    private void  setDefaultFragment(){
        articleFragment = new ArticleFragment();
        jobFragment = new JobFragment();
        trainFragment = new TrainFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction
                .add(R.id.fl_main_content,articleFragment,"千家论道")
                .add(R.id.fl_main_content,jobFragment,"职男职女")
                .add(R.id.fl_main_content,trainFragment,"赋能成长")
                .hide(jobFragment)
                .hide(trainFragment)
                .commit();
    }

    public void showFragmentPage(int i){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (i){
            case PAGE_ARTICLE:
                if (articleFragment == null){
                    articleFragment = new ArticleFragment();
                }
                transaction
                        .show(articleFragment)
                        .hide(jobFragment)
                        .hide(trainFragment);
                break;
            case PAGE_JOB:
                transaction
                        .show(jobFragment)
                        .hide(articleFragment)
                        .hide(trainFragment);
                break;
            case PAGE_TRAIN:
                transaction
                        .show(trainFragment)
                        .hide(articleFragment)
                        .hide(jobFragment);
                break;
            default:
                break;
        }

        transaction.commit();
    }

    public void setDrawerLayout(boolean isOpen){
        if (isOpen){
            mDrawer.openDrawer(Gravity.LEFT);
        }else {
            mDrawer.closeDrawers();
        }
    }

    public void startChatList(){
        if (RongIM.getInstance() != null){
            //设置融云的用户信息
            RongIM.getInstance().startConversationList(this);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        RongIM.setUserInfoProvider(MainActivity.this,true);

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
    public void onResponseSuccess(String id, String nickname, String icon) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,nickname, Uri.parse(icon)));
    }

    @Override
    public void onResponseError(String id) {
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id,"仟询用户", null));
    }
}
