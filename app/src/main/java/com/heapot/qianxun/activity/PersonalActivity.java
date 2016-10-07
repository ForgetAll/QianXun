package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.PersonalPageAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.CommonUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class PersonalActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private List<String> mList;
    private PersonalPageAdapter mPersonalPageAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private ImageView mBanner, mHeadUrl, mClose;
    private TextView mFans, mName, mSign;
    private int pt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setTransparentBar();
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.content);
        mToolBar = (Toolbar) findViewById(R.id.personal_tool_bar);
        mTabLayout = (TabLayout) findViewById(R.id.personal_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.personal_view_pager);
        mBanner = (ImageView) findViewById(R.id.personal_iv_banner);
        mFans = (TextView) findViewById(R.id.tv_fans);
        mHeadUrl = (ImageView) findViewById(R.id.iv_headUrl);
        mName = (TextView) findViewById(R.id.tv_name);
        mSign = (TextView) findViewById(R.id.tv_sign);
        mClose = (ImageView) findViewById(R.id.iv_close);
        mToolBar.setOnClickListener(this);
        mBanner.setOnClickListener(this);
        mHeadUrl.setOnClickListener(this);
        mName.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mList = new ArrayList<>();
    }

    private void initEvent() {
        //状态栏
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
    }

    private void initData() {
        Object object = getLocalInfo(ConstantsBean.MY_USER_INFO);
            MyUserBean myUserBean = (MyUserBean) object;
            if (myUserBean.getContent().getDescription() != null) {
                mSign.setText(myUserBean.getContent().getDescription());
            }  else {
                mSign.setText("请设置签名");
            }
            String nickName = myUserBean.getContent().getNickname();
            if (nickName != null) {
                mName.setText(nickName);
            } else {
                mName.setText("请设置昵称");
            }
            if (myUserBean.getContent().getIcon() != null) {
                CommonUtil.loadImage(mHeadUrl, myUserBean.getContent().getIcon(), R.drawable.imagetest);
            } else {
                mHeadUrl.setImageResource(R.drawable.imagetest);
            }

        //Glide.with(activity).load("http://odxpoei6h.bkt.clouddn.com/qianxun57f1fb7f9a56e.jpeg").into(mHeadUrl);
        Logger.d(mSign);
        Logger.d(mHeadUrl);

        for (int i = 0; i < 1; i++) {
            mList.add("Tab-" + i);
        }
        initTab();

    }
    private Object getLocalInfo(String fileName) {
        return SerializableUtils.getSerializable(activity, fileName);
    }

    private void initTab() {
        mPersonalPageAdapter = new PersonalPageAdapter(getSupportFragmentManager(), this, mList);
        mViewPager.setAdapter(mPersonalPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager.setOnPageChangeListener(this);
    }

    //状态栏
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //toolBar
            case R.id.personal_tool_bar:
                break;
            //大图片
            case R.id.personal_iv_banner:

                break;
            //头像
            case R.id.iv_headUrl:
                Intent headUrl = new Intent(PersonalActivity.this, PersonalInforActivity.class);
                startActivity(headUrl);
                break;
            //名字
            case R.id.tv_name:
                Intent mName = new Intent(PersonalActivity.this, PersonalInforActivity.class);
                startActivity(mName);
                break;
            //签名
            case R.id.tv_sign:
                Intent mSign = new Intent(PersonalActivity.this, PersonalInforActivity.class);
                startActivity(mSign);
                break;

            case R.id.iv_close:
                finish();
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pt = position;
        Log.e("当前的position", pt + "");
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
