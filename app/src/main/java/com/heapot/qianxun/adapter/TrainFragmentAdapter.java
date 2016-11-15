package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.fragment.ArticleListFragment;
import com.heapot.qianxun.fragment.TrainListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 * desc:
 */

public class TrainFragmentAdapter extends FragmentStatePagerAdapter{

    private Context mContext;

    private List<SubBean> mList = new ArrayList<>();

    public TrainFragmentAdapter(FragmentManager fm, Context mContext, List<SubBean> mList) {
        super(fm);
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setData(List<SubBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return TrainListFragment.getInstance(position+1,mList.get(position).getId());
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
        TrainListFragment trainListFragment = (TrainListFragment) super.instantiateItem(container, position);
        return trainListFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return TrainFragmentAdapter.POSITION_NONE;
    }
}
