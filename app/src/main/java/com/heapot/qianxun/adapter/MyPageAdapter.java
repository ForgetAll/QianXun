package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.heapot.qianxun.fragment.PageFragment;

import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 */
public class MyPageAdapter extends FragmentPagerAdapter {
    private List<String> mList;
    private Context mContext;

    public MyPageAdapter(FragmentManager fm, Context context, List<String> list) {
        super(fm);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).toString();
    }
}
