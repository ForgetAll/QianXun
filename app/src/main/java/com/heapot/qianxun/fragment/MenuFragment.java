package com.heapot.qianxun.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.activity.PersonalActivity;
import com.heapot.qianxun.activity.SystemHelpActivity;
import com.heapot.qianxun.activity.SystemSettingActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by Karl on 2016/8/20.
 * 自定义侧滑菜单布局：
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private ImageView mIcon;
    private TextView mName, mQuote, mScience, mRecruit, mTrain, mSetting, mHelp;
    private LinearLayout mHeader;
    private View mMenuView;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuView = inflater.inflate(R.layout.fragment_menu, container, false);
        initView();
        initEvent();
        getData();
        return mMenuView;
    }

    private void getData() {

        mName.setText(PreferenceUtil.getString(ConstantsBean.nickName));
      //  mQuote.setText(PreferenceUtil.getString(ConstantsBean.userAutograph));
    }

    @Override
    public void onResume() {
        super.onResume();
        String url = "https://qinxi1992.xicp.net//user/i";
        Ion.with(getContext()).load(url).as(MyUserBean.class).setCallback(new FutureCallback<MyUserBean>() {
            @Override
            public void onCompleted(Exception e, MyUserBean result) {
                if (result != null && result.getStatus().equals("success")) {
                    MyUserBean.ContentBean useBean = result.getContent();
                    PreferenceUtil.putString(ConstantsBean.userImage, useBean.getIcon());
                    PreferenceUtil.putString(ConstantsBean.USER_ID, useBean.getId());
                    PreferenceUtil.putString(ConstantsBean.email, useBean.getEmail());
                    PreferenceUtil.putString(ConstantsBean.loginTime, useBean.getLoginName());
                    PreferenceUtil.putString(ConstantsBean.name, useBean.getName());
                    PreferenceUtil.putString(ConstantsBean.nickName, useBean.getNickname());
                }
            }
        });
        if (!TextUtils.isEmpty(PreferenceUtil.getString(ConstantsBean.userImage))) {
            CommonUtil.loadImage(mIcon, PreferenceUtil.getString(ConstantsBean.userImage), R.drawable.imagetest);
        } else {
            mIcon.setImageResource(R.drawable.imagetest);
        }
    }

    private void initView() {
        mIcon = (ImageView) mMenuView.findViewById(R.id.iv_menu_image);
        mName = (TextView) mMenuView.findViewById(R.id.txt_menu_name);
        mQuote = (TextView) mMenuView.findViewById(R.id.txt_menu_quote);
        mScience = (TextView) mMenuView.findViewById(R.id.txt_menu_science);
        mRecruit = (TextView) mMenuView.findViewById(R.id.txt_menu_recruit);
        mTrain = (TextView) mMenuView.findViewById(R.id.txt_menu_train);
        mSetting = (TextView) mMenuView.findViewById(R.id.txt_menu_settings);
        mHelp = (TextView) mMenuView.findViewById(R.id.txt_menu_help);
        mHeader = (LinearLayout) mMenuView.findViewById(R.id.ll_menu_header);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //science学术、recruit招聘、train培训三个menu的点击事件，点击切换fragment
            case R.id.txt_menu_science:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_SCIENCE);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_SCIENCE);
                break;
            case R.id.txt_menu_recruit:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_RECRUIT);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_RECRUIT);
                break;
            case R.id.txt_menu_train:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_TRAIN);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_TRAIN);
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
        mActivity = null;
    }
}
