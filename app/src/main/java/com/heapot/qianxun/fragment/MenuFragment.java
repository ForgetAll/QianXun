package com.heapot.qianxun.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.activity.personal.PersonalActivity;
import com.heapot.qianxun.activity.system.SystemHelpActivity;
import com.heapot.qianxun.activity.system.SystemSettingActivity;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.PreferenceUtil;

/**
 * Created by Karl on 2016/8/20.
 * desc: 侧滑菜单布局
 *
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private ImageView mHeadUrl;
    private TextView mName, mSign, mScience, mRecruit, mTrain, mSetting, mHelp;
    private LinearLayout mHeader;
    private View mMenuView;
    private Activity mActivity;
    private LinearLayout mMenu;
    //本地广播尝试
    private IntentFilter intentFilter;
    private RefreshPersonalReceiver refreshReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuView = inflater.inflate(R.layout.fragment_menu, container, false);
        initView();
        initEvent();

        return mMenuView;
    }


    private void initView() {
        mHeadUrl = (ImageView) mMenuView.findViewById(R.id.iv_menu_image);
        mName = (TextView) mMenuView.findViewById(R.id.txt_menu_name);
        mSign = (TextView) mMenuView.findViewById(R.id.txt_menu_quote);
        mScience = (TextView) mMenuView.findViewById(R.id.txt_menu_science);
        mRecruit = (TextView) mMenuView.findViewById(R.id.txt_menu_recruit);
        mTrain = (TextView) mMenuView.findViewById(R.id.txt_menu_train);
        mSetting = (TextView) mMenuView.findViewById(R.id.txt_menu_settings);
        mHelp = (TextView) mMenuView.findViewById(R.id.txt_menu_help);
        mHeader = (LinearLayout) mMenuView.findViewById(R.id.ll_menu_header);
        mMenu = (LinearLayout) mMenuView.findViewById(R.id.ll_main_menu);
        mActivity = getActivity();
    }

    private void initEvent() {
        mHeader.setOnClickListener(this);
        mScience.setOnClickListener(this);
        mRecruit.setOnClickListener(this);
        mTrain.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mHeader.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mMenu.setOnClickListener(this);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //注册本地广播
        localReceiver();
    }

    /**
     * 本地广播接收
     */
    private void localReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());//获取实例
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.personal.change");
        refreshReceiver = new RefreshPersonalReceiver();
        localBroadcastManager.registerReceiver(refreshReceiver, intentFilter);
    }

    private void initData() {
        String nickName= PreferenceUtil.getString(ConstantsBean.nickName);
        String autoGraph=PreferenceUtil.getString(ConstantsBean.userAutograph);
        String headUrl=PreferenceUtil.getString(ConstantsBean.userImage);
        if (nickName != null) {
            mName.setText(nickName);
        } else {
            mName.setText("请设置昵称");
        }
        if (autoGraph != null) {
            mSign.setText(autoGraph);
        }  else {
            mSign.setText("请设置签名");
        }
        if (headUrl != null) {
//            CommonUtil.loadImage(mHeadUrl,headUrl, R.drawable.imagetest);
            Glide.with(getActivity()).load(headUrl).error(R.drawable.ic_default_icon).into(mHeadUrl);
        } else {
            Glide.with(getActivity()).load(R.drawable.ic_default_icon).into(mHeadUrl);
        }

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //science学术、recruit招聘、train培训三个menu的点击事件，点击切换fragment
            case R.id.txt_menu_science:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).showFragmentPage(1);
                break;
            case R.id.txt_menu_recruit:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity)mActivity).showFragmentPage(2);
                break;
            case R.id.txt_menu_train:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity)mActivity).showFragmentPage(3);
                break;
            //设置、帮助的点击事件
            case R.id.txt_menu_settings:
                Intent set = new Intent(getContext(), SystemSettingActivity.class);
                startActivity(set);
                break;
            case R.id.txt_menu_help:
                Intent help = new Intent(getContext(), SystemHelpActivity.class);
                startActivity(help);
                break;
            //点击切换侧滑菜单头布局的背景
            case R.id.ll_menu_header:
                Intent intent = new Intent(getContext(), PersonalActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_main_menu:

                break;
            default:
                break;

        }
    }

    /**
     * 回收Activity
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(refreshReceiver);
        mActivity = null;
    }

    /**
     * 广播接收器
     */
    class RefreshPersonalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int personalStatus = intent.getExtras().getInt("personalStatus");
            switch (personalStatus) {
                case 0://无更新,不需要操作
                    break;
                case 1:
                    String nickName= PreferenceUtil.getString(ConstantsBean.nickName);
                    String autoGraph=PreferenceUtil.getString(ConstantsBean.userAutograph);
                    String headUrl=PreferenceUtil.getString(ConstantsBean.userImage);
                    if (nickName != null) {
                        mName.setText(nickName);
                    } else {
                        mName.setText("请设置昵称");
                    }
                    if (autoGraph != null) {
                        mSign.setText(autoGraph);
                    }  else {
                        mSign.setText("请设置签名");
                    }
                    if (headUrl != null) {
                        CommonUtil.loadImage(mHeadUrl,headUrl, R.drawable.imagetest);
                    } else {
                        mHeadUrl.setImageResource(R.drawable.imagetest);
                    }
                    break;
            }
        }
    }
}
