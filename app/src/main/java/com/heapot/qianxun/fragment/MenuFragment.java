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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.activity.PersonalActivity;
import com.heapot.qianxun.activity.system.SystemHelpActivity;
import com.heapot.qianxun.activity.system.SystemSettingActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyUserBean;
import com.heapot.qianxun.helper.SerializableUtils;
import com.heapot.qianxun.util.CommonUtil;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Karl on 2016/8/20.
 * 自定义侧滑菜单布局：
 * <p>
 * <p>
 * desc:
 * 存数据：SerializableUtils.setSerializable(getContext(), ConstantsBean.MY_USER_INFO, myUserBean);
 * 取数据：SerializableUtils.getSerializable(getContext(),ConstantsBean.MY_USER_INFO)
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    private ImageView mIcon;
    private TextView mName, mQuote, mScience, mRecruit, mTrain, mSetting, mHelp;
    private LinearLayout mHeader;
    private View mMenuView;
    private Activity mActivity;
    //本地广播尝试
    private IntentFilter intentFilter;
    private RefreshPersonalReceiver refreshReceiver;
    private LocalBroadcastManager localBroadcastManager;

//    private List<MyUserBean.ContentBean> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMenuView = inflater.inflate(R.layout.fragment_menu, container, false);
        initView();
        initEvent();

        return mMenuView;
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
        Object object = getLocalInfo(ConstantsBean.MY_USER_INFO);
        if (object != null) {
            MyUserBean.ContentBean myUserBean = (MyUserBean.ContentBean) object;
            if (myUserBean.getDescription() != null) {
                mQuote.setText(myUserBean.getDescription());
            } else {
                mQuote.setText("请设置签名");
            }
            String nickName = myUserBean.getNickname();
            if (nickName != null) {
                mName.setText(nickName);
            } else {
                mName.setText("请设置昵称");
            }
            if (myUserBean.getIcon() != null) {
                CommonUtil.loadImage(mIcon, myUserBean.getIcon(), R.drawable.imagetest);
                Log.e("网上获取图片",myUserBean.getIcon());
            } else {
                mIcon.setImageResource(R.drawable.imagetest);
            }
        } else {
            getUserInfo();
        }

    }

    /**
     * 在主页获取用户信息然后进行存储，直接在侧滑菜单进行绘制就可以了
     */
    private void getUserInfo() {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.PERSONAL_INFO;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d(response);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                MyUserBean myUserBean = (MyUserBean) JsonUtil.fromJson(String.valueOf(response), MyUserBean.class);
                                MyUserBean.ContentBean userBean = myUserBean.getContent();
                                SerializableUtils.setSerializable(getContext(), ConstantsBean.MY_USER_INFO, userBean);
                                PreferenceUtil.putString(ConstantsBean.nickName, myUserBean.getContent().getNickname());
                                PreferenceUtil.putString(ConstantsBean.userAutograph, myUserBean.getContent().getDescription());
                                PreferenceUtil.putString(ConstantsBean.userImage, myUserBean.getContent().getIcon());
                                if (userBean.getDescription() != null) {
                                    mQuote.setText(userBean.getDescription());
                                } else {
                                    mQuote.setText("请设置签名");
                                }
                                String nickName = userBean.getNickname();
                                if (nickName != null) {
                                    mName.setText(nickName);
                                    CustomApplication.NICK_NAME = nickName;
                                    Log.e("网上获取图片fgsgbtehgte",nickName);
                                } else {
                                    mName.setText("请设置昵称");
                                }
                                if (userBean.getIcon() != null) {
                                   CommonUtil.loadImage(mIcon, userBean.getIcon(), R.drawable.imagetest);
                                    Log.e("网上获取图片fgsgbtehgte",userBean.getIcon());
                                } else {
                                    mIcon.setImageResource(R.drawable.imagetest);
                                }

                            } else {
                                Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                Logger.d(response.getString("message"));
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private Object getLocalInfo(String fileName) {
        return SerializableUtils.getSerializable(getContext(), fileName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //science学术、recruit招聘、train培训三个menu的点击事件，点击切换fragment
            case R.id.txt_menu_science:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_SCIENCE);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_SCIENCE);
                ((MainActivity) mActivity).refreshData();
                break;
            case R.id.txt_menu_recruit:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_RECRUIT);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_RECRUIT);
                ((MainActivity) mActivity).refreshData();
                break;
            case R.id.txt_menu_train:
                ((MainActivity) mActivity).closeDrawer();
                ((MainActivity) mActivity).setToolBarTitle(ConstantsBean.PAGE_TRAIN);
                CustomApplication.setCurrentPage(ConstantsBean.PAGE_TRAIN);
                ((MainActivity) mActivity).refreshData();
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
                    Object object = SerializableUtils.getSerializable(getContext(), ConstantsBean.MY_USER_INFO);
                    if (object != null) {
                        MyUserBean.ContentBean myUserBean = (MyUserBean.ContentBean) object;
                        CommonUtil.loadImage(mIcon, myUserBean.getIcon(), R.drawable.imagetest);
                        mName.setText(myUserBean.getNickname());
                        mQuote.setText(myUserBean.getDescription());
                    }
                    break;
            }
        }
    }
}
