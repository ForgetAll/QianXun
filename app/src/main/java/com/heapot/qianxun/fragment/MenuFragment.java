package com.heapot.qianxun.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.heapot.qianxun.bean.DataBean;

/**
 * Created by Karl on 2016/8/20.
 * 自定义侧滑菜单布局：
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private ImageView mIcon;
    private TextView mName,mQuote,mScience,mRecruit,mTrain,mSetting,mHelp;
    private LinearLayout mHeader;
    private View mMenuView;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuView = inflater.inflate(R.layout.fragment_menu,container,false);
        initView();
        initEvent();
        return mMenuView;
    }
    private void initView(){
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
    private void initEvent(){
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
        switch (v.getId()){
            //science学术、recruit招聘、train培训三个menu的点击事件，点击切换fragment
            case R.id.txt_menu_science:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_SCIENCE);
                break;
            case R.id.txt_menu_recruit:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_RECRUIT);
                break;
            case R.id.txt_menu_train:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_TRAIN);
                break;
            //设置、帮助的点击事件
            case R.id.txt_menu_settings:
                Intent setting=new Intent(getContext(),SystemSettingActivity.class);
                startActivity(setting);
                break;
            case R.id.txt_menu_help:
                Intent help=new Intent(getContext(),SystemHelpActivity.class);
                startActivity(help);
                break;
            //点击切换侧滑菜单头布局的背景
            case R.id.ll_menu_header:
                Intent intent = new Intent(getContext(),PersonalActivity.class);
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
        mActivity=null;
    }
}
