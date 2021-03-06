package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.fragment.PageFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/8/25.
 * 主页tab创建Fragment适配器
 *
 */
public class MainTabFragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<SubBean> mList = new ArrayList<>();
    private String currentPage;


    public MainTabFragmentAdapter(FragmentManager fm, Context context, List<SubBean> list,String currentPage) {
        super(fm);
        this.mContext = context;
        this.mList = list;
        this.currentPage = currentPage;
        Logger.d("adapter里面"+currentPage);
    }

    public void setData(List<SubBean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position+1, mList.get(position).getId(),currentPage);
    }

    @Override
    public int getCount() {
        return mList==null? 0 :mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PageFragment pageFragment = (PageFragment) super.instantiateItem(container, position);
        return pageFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
