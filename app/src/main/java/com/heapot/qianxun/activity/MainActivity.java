package com.heapot.qianxun.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.heapot.qianxun.R;
import com.heapot.qianxun.fragment.ScienceFragment;
//主页界面
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载初始页面
        initPage();

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
        mToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
        frameLayout = (FrameLayout) findViewById(R.id.main_frame_layout);

    }

    private void initEvent(){
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
//                mDrawerLayout.closeDrawers();
                break;
        }
    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawers();
    }
}
