package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.network.LoadTagsList;

import java.util.List;

/**
 * Created by Karl on 2016/11/11.
 * desc:主页文章列表
 */

public class ArticleListFragment extends Fragment implements LoadTagsList.onLoadTagsListListener {
    private static final String CURRENT_PAGE = "CURRENT_PAGE";
    private static final String CURRENT_PAGE_ID = "CURRENT_PAGE_ID";
    private static final int PAGE_SIZE = 8;
    private int mPage;
    private String mPageId = "";
    private int mCurrentIndex = 1;
    private int mMaxIndex =0;


    private View mView;

    private LoadTagsList loadTagsList;


    private ViewPager mViewPager;


    /**
     * 单例，返回当前Fragment类型
     * @param page 当前第几个tab
     * @param id 当前Fragment
     * @return ArticleListFragment
     */
    public static ArticleListFragment getInstance(int page,String id){
        Bundle args = new Bundle();
        args.putInt(CURRENT_PAGE,page);
        args.putString(CURRENT_PAGE_ID,id);
        ArticleListFragment articleListFragment = new ArticleListFragment();
        articleListFragment.setArguments(args);

        return articleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里用于取数据
        mPage = getArguments().getInt(CURRENT_PAGE);
        mPageId = getArguments().getString(CURRENT_PAGE_ID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_list,container,false);

        return mView;
    }

    private void initView(){

    }

    private void initEvent(){
        loadTagsList = new LoadTagsList(getContext());
        loadTagsList.setOnLoadTagsListListener(this);
        initData(0);

    }

    private void initData(int flag){
        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +mPageId+"&page="+ mCurrentIndex +"&pagesize="+PAGE_SIZE;
        loadTagsList.getTagsList(url,0);
    }


    @Override
    public void onSuccessResponse(List<MainListBean.ContentBean> list, int totalIndex, int flag) {
        mMaxIndex = totalIndex;
        if (list!= null){

        }
    }

    @Override
    public void onErrorResponse() {

    }

    @Override
    public void onError() {

    }
}
