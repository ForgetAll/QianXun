package com.heapot.qianxun.fragment;
import android.app.Activity;
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
        mMenuView = inflater.inflate(R.layout.layout_menu,container,false);
        initView();
        initEvent();
        return mMenuView;
    }
    private void initView(){
        mIcon = (ImageView) mMenuView.findViewById(R.id.menu_image);
        mName = (TextView) mMenuView.findViewById(R.id.menu_name);
        mQuote = (TextView) mMenuView.findViewById(R.id.menu_quote);
        mScience = (TextView) mMenuView.findViewById(R.id.menu_science);
        mRecruit = (TextView) mMenuView.findViewById(R.id.menu_recruit);
        mTrain = (TextView) mMenuView.findViewById(R.id.menu_train);
        mSetting = (TextView) mMenuView.findViewById(R.id.menu_settings);
        mHelp = (TextView) mMenuView.findViewById(R.id.menu_help);
        mHeader = (LinearLayout) mMenuView.findViewById(R.id.menu_header);
        mActivity = getActivity();
    }
    private void initEvent(){
        mIcon.setOnClickListener(this);
        mName.setOnClickListener(this);
        mQuote.setOnClickListener(this);
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
            //image头像、name昵称、quote一句话签名
            case R.id.menu_image:
                break;
            case R.id.menu_name:
                break;
            case R.id.menu_quote:
                break;
            //science学术、recruit招聘、train培训三个menu的点击事件，点击切换fragment
            case R.id.menu_science:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_SCIENCE);
                break;
            case R.id.menu_recruit:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_RECRUIT);
                break;
            case R.id.menu_train:
                ((MainActivity)mActivity).closeDrawer();
                ((MainActivity)mActivity).setPageId(DataBean.PAGE_TRAIN);
                break;
            //设置、帮助的点击事件
            case R.id.menu_settings:
                break;
            case R.id.menu_help:
                break;
            //点击切换侧滑菜单头布局的背景
            case R.id.menu_header:
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
