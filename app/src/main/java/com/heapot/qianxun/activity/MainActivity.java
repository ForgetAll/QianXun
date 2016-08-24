package com.heapot.qianxun.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.heapot.qianxun.R;
import com.heapot.qianxun.fragment.ScienceFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private FrameLayout frameLayout;
    private Toolbar mToolBar;
    //主页界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载初始页面
        initPage();
        setTransparentBar();
        initView();
        initEvent();
    }
    //初始化默认首页
    private void initPage(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_layout,new ScienceFragment(),"Science").commit();
    }
    //初始化控件
    private void initView(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame_layout);
        mToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
    }


    private void initEvent(){
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);

        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.app_name,R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        frameLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_frame_layout:
                break;
        }
    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }

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
