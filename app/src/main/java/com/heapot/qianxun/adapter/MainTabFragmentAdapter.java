package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.heapot.qianxun.bean.SubscribedBean;
import com.heapot.qianxun.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 * 主页tab创建Fragment适配器
 *
 */
public class MainTabFragmentAdapter extends FragmentPagerAdapter {
    private List<SubscribedBean.ContentBean.RowsBean> mList = new ArrayList<>();
    private Context mContext;
    private int count;

    public MainTabFragmentAdapter(FragmentManager fm, Context context, List<SubscribedBean.ContentBean.RowsBean> list) {
        super(fm);
        this.mContext = context;
        this.mList = list;
        count = list.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (count == 0){
            return PageFragment.newInstance(-1,"暂无数据");
        }else {
            return PageFragment.newInstance(position + 1, mList.get(position).getId());
        }
    }

    @Override
    public int getCount() {
        if (count == 0){
            return 1;
        }else {
            return count;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (count == 0){
            return "暂无数据";
        }else {
            return mList.get(position).getName();
        }
    }
}
