package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.heapot.qianxun.fragment.PageFragment;
import com.heapot.qianxun.fragment.PersonalFirstFragment;

import java.util.List;

/**
 * Created by 15859 on 2016/8/29.
 * 个人页面ViewPager适配器
 *
 */
public class PersonalPageAdapter extends FragmentPagerAdapter {
    private List<String> mList;
    private Context mContext;

    public PersonalPageAdapter(FragmentManager fm, Context context, List<String> list) {
        super(fm);
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PersonalFirstFragment();
        } else {
            return PageFragment.newInstance(position + 1);
        }

    }

    @Override
    public int getCount() {
        return mList.size()+1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "个人";
        return mList.get(position-1).toString();
    }
}
