package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.fragment.JobListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 * desc:
 */

public class JobFragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private List<SubBean> mList = new ArrayList<>();

    public JobFragmentAdapter(FragmentManager fm, Context context, List<SubBean> list) {
        super(fm);
        this.mContext = context;
        this.mList = list;
    }

    public void setData(List<SubBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return JobListFragment.getInstance(position+1,mList.get(position).getId());
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        JobListFragment jobListFragment = (JobListFragment) super.instantiateItem(container, position);

        return jobListFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return JobFragmentAdapter.POSITION_NONE;
    }
}
