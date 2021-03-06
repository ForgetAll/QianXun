package com.heapot.qianxun.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.MainActivity;
import com.heapot.qianxun.activity.SearchActivity;
import com.heapot.qianxun.activity.Subscription;
import com.heapot.qianxun.activity.create.CreateActivity;
import com.heapot.qianxun.adapter.JobFragmentAdapter;
import com.heapot.qianxun.bean.MyTagBean;
import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.bean.TagsBean;
import com.heapot.qianxun.util.LoadTagsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/10.
 * desc:
 */

public class JobFragment extends Fragment implements View.OnClickListener, LoadTagsUtils.OnLoadTagListener {
    private Toolbar mToolBar;
    private ImageView mBanner;
    private TabLayout mTabLayout;
    private ImageView mSearch, mNotification, mStar;
    private ViewPager mViewPager;
    private JobFragmentAdapter mPageAdapter;
    private List<SubBean> mList = new ArrayList<>();
    private FloatingActionButton mCreate;
    private TextView mSubscription,mainTitle;

    private String PAGE_RECRUIT = "PAGE_RECRUIT";
    public String PAGE_JOBS_ID = "af3a09e8a4414c97a038a2d735064ebc";
    private View mView;

    private Activity mActivity;

    LoadTagsUtils loadTagsUtils;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_job,container,false);
        mActivity = getActivity();
        initView();
        initEvent();

        return mView;
    }
    private void initView(){
        mToolBar = (Toolbar) mView.findViewById(R.id.main_tool_bar);
        mTabLayout = (TabLayout) mView.findViewById(R.id.main_tab_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.main_view_pager);
        mSearch = (ImageView) mView.findViewById(R.id.iv_search);
        mStar = (ImageView) mView.findViewById(R.id.iv_star);
        mNotification = (ImageView) mView.findViewById(R.id.iv_notification);
        mBanner = (ImageView) mView.findViewById(R.id.iv_banner);
        mSubscription = (TextView) mView.findViewById(R.id.iv_subscription_choose);
        mCreate = (FloatingActionButton) mView.findViewById(R.id.fab_create);
        mainTitle = (TextView) mView.findViewById(R.id.txt_first_title);
    }

    private void initEvent(){
        mToolBar.setTitle("");
//        ((MainActivity)mActivity).setSupportActionBar(mToolBar);
        //添加监听事件
        mSearch.setOnClickListener(this);
        mStar.setOnClickListener(this);
        mNotification.setOnClickListener(this);
        mBanner.setOnClickListener(this);
        mSubscription.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mainTitle.setOnClickListener(this);

        //一些基本的初始化数据
        mainTitle.setText("仟职百态");
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Glide.with(this).load("http://114.215.252.158/banner.png").into(mBanner);

        loadTagsUtils = new LoadTagsUtils(getContext());
        loadTagsUtils.setOnLoadTagListener(this);
        loadTagsUtils.getTags(0);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                Intent search = new Intent(getContext(), SearchActivity.class);
                startActivity(search);
                break;
            case R.id.iv_star:
                break;
            case R.id.iv_notification:
                ((MainActivity)mActivity).startChatList();
                break;
            case R.id.iv_banner:
                break;
            case R.id.iv_subscription_choose:
                Intent intent = new Intent(getContext(), Subscription.class);
                intent.putExtra("page",PAGE_RECRUIT);
                startActivityForResult(intent,102);
                break;
            case R.id.fab_create:
                Intent createIntent = new Intent(getContext(),CreateActivity.class);
                startActivity(createIntent);
                break;
            case R.id.txt_first_title:
                ((MainActivity)mActivity).openDrawer();
                break;
        }
    }


    @Override
    public void onLoadAllSuccess(List<TagsBean.ContentBean> list, int flag) {
        if (list!=null){
            loadData(list,flag);
        }
    }

    @Override
    public void onLoadSuccess(List<MyTagBean.ContentBean.RowsBean> list, int flag) {
        //该接口暂时不使用
    }

    @Override
    public void onLoadFailed(String message) {
        Toast.makeText(mActivity, "数据加载失败", Toast.LENGTH_SHORT).show();
    }

    private void loadData(List<TagsBean.ContentBean> list,int flag){
        mList.clear();
        List<Integer> pos = new ArrayList<>();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            if (list.get(i).getPid() != null && list.get(i).getPid().equals(PAGE_JOBS_ID) && list.get(i).getSubscribeStatus() == 1){
                pos.add(i);
            }
        }
        if (pos.size()>0){
            SubBean subBean;
            for (int i = 0; i < pos.size(); i++) {
                subBean = new SubBean();
                subBean.setPid((String) list.get(pos.get(i)).getPid());
                subBean.setName(list.get(pos.get(i)).getName());
                subBean.setId(list.get(pos.get(i)).getId());
                mList.add(subBean);
            }
        }

        if (flag == 0) {
            mPageAdapter = new JobFragmentAdapter(getChildFragmentManager(), getContext(), mList);
            mViewPager.setAdapter(mPageAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }else {
            //刷新数据源
            mPageAdapter.setData(mList);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == 102){
           boolean isRefresh = data.getBooleanExtra("result",false);
           if (isRefresh) {
               loadTagsUtils.getTags(1);
           }
       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

}
