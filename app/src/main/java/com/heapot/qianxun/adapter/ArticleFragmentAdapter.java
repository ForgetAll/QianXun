package com.heapot.qianxun.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.heapot.qianxun.bean.SubBean;
import com.heapot.qianxun.fragment.ArticleFragment;
import com.heapot.qianxun.fragment.ArticleListFragment;
import com.heapot.qianxun.fragment.PageFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/14.
 *
 */

public class ArticleFragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private List<SubBean> mList = new ArrayList<>();

    public ArticleFragmentAdapter(FragmentManager fm, Context context, List<SubBean> list) {
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
        return ArticleListFragment.getInstance(position+1,mList.get(position).getId());
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
        ArticleListFragment pageFragment = (ArticleListFragment) super.instantiateItem(container, position);
        return pageFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
