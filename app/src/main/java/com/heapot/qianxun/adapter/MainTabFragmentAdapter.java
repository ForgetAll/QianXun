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

    public MainTabFragmentAdapter(FragmentManager fm, Context context, List<SubscribedBean.ContentBean.RowsBean> list) {
        super(fm);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position+1,mList.get(position).getId());
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }
}
