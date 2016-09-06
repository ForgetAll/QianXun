package com.heapot.qianxun.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.OrderListAdapter;
import com.heapot.qianxun.adapter.PersonalPageAdapter;
import com.heapot.qianxun.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/29.
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolBar;
    private List<String> mList;
    private PersonalPageAdapter mPersonalPageAdapter;
    private String currentId;
    private CoordinatorLayout mCoordinatorLayout;
    private ImageView mBanner, mHeadUrl, mClose, mSet, mAdd;
    private TextView mFans, mName, mSign;
    private AlertDialog alertDialog, alertDialogAdd;
    private int pt;
    private TextView mTabTitle, mDeleteTab, mChangeTitle, mOrder, mTabTitleAdd, mChangeTitleAdd;
    private Button mSure, mCancel, mSureAdd, mCancelAdd;
    private ImageView mExtend;
    private EditText mCurrentTab, mCurrentTabAdd;
    private ListView mOrderList;
    public static int order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setTransparentBar();
        initView();
        initEvent();
        initData();
        order = 0;
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
        mAdd = (ImageView) findViewById(R.id.iv_add);
        mSet = (ImageView) findViewById(R.id.iv_set);
        mToolBar.setOnClickListener(this);
        mBanner.setOnClickListener(this);
        mHeadUrl.setOnClickListener(this);
        mName.setOnClickListener(this);
        mSign.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mSet.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mList = new ArrayList<>();
    }

    private void initEvent() {
        //状态栏
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
    }

    private void initData() {

        for (int i = 0; i < 15; i++) {
            mList.add("Tab-" + i);

        }
        currentId = DataBean.PAGE_SCIENCE;
        initTab();
    }

    private void initTab() {
        mPersonalPageAdapter = new PersonalPageAdapter(getSupportFragmentManager(), this, mList);
        mViewPager.setAdapter(mPersonalPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
            //设置栏目对话框
            case R.id.iv_set:
                personalSetDialog();
                break;
            //新建栏目对话框
            case R.id.iv_add:
                personalAddDialog();

                break;
            case R.id.iv_close:
                finish();
                break;
            //设置对话框删除栏目
            case R.id.tv_deleteTab:
                alertDialog.dismiss();
                break;
            //设置对话框扩展图片
            case R.id.iv_extend:
                if (order == 0) {
                    mOrderList.setVisibility(View.VISIBLE);
                    order = 1;
                } else {
                    mOrderList.setVisibility(View.GONE);
                    order = 0;
                }

                break;
            //对话框设置确定
            case R.id.btn_sure:
                alertDialog.dismiss();
                Toast.makeText(PersonalActivity.this, "完成",
                        Toast.LENGTH_SHORT).show();
                break;
            //对话框设置取消
            case R.id.btn_cancel:
                alertDialog.dismiss();
                break;
            //添加栏目对话框输入
            case R.id.et_currentTabAdd:
                break;
            //添加栏目去确定
            case R.id.btn_sureAdd:
                alertDialogAdd.dismiss();
                break;
            //添加栏目取消
            case R.id.btn_cancelAdd:
                alertDialogAdd.dismiss();
                break;
        }
    }

    //增加栏目的对话框
    private void personalAddDialog() {
        AlertDialog.Builder builderAdd = new AlertDialog.Builder(PersonalActivity.this);
        View viewAdd = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.personal_table_add_table, null);
        mTabTitleAdd = (TextView) viewAdd.findViewById(R.id.tv_tabTitleAdd);
        mChangeTitleAdd = (TextView) viewAdd.findViewById(R.id.tv_changeTitleAdd);
        mCurrentTabAdd = (EditText) viewAdd.findViewById(R.id.et_currentTabAdd);
        mSureAdd = (Button) viewAdd.findViewById(R.id.btn_sureAdd);
        mCancelAdd = (Button) viewAdd.findViewById(R.id.btn_cancelAdd);
        mCurrentTabAdd.setOnClickListener(this);
        mSureAdd.setOnClickListener(this);
        mCancelAdd.setOnClickListener(this);
        builderAdd.setView(viewAdd);
        alertDialogAdd = builderAdd.show();
    }

    //修改Tab标签的对话框
    private void personalSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
        View view = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.personal_table_set_dialog, null);
        mTabTitle = (TextView) view.findViewById(R.id.tv_tabTitle);
        mDeleteTab = (TextView) view.findViewById(R.id.tv_deleteTab);
        mChangeTitle = (TextView) view.findViewById(R.id.tv_changeTitle);
        mCurrentTab = (EditText) view.findViewById(R.id.et_currentTab);
        mOrder = (TextView) view.findViewById(R.id.tv_order);
        mExtend = (ImageView) view.findViewById(R.id.iv_extend);
        mOrderList = (ListView) view.findViewById(R.id.lv_orderList);
        mSure = (Button) view.findViewById(R.id.btn_sure);
        mCancel = (Button) view.findViewById(R.id.btn_cancel);
        mDeleteTab.setOnClickListener(this);
        mExtend.setOnClickListener(this);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this, mList);
        mOrderList.setAdapter(orderListAdapter);
        mSure.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        builder.setView(view);
        alertDialog = builder.show();
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
