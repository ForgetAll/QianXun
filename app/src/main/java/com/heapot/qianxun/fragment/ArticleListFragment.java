package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.ArticleListAdapter;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MainListBean;
import com.heapot.qianxun.util.network.LoadTagsList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 2016/11/11.
 * desc:主页文章列表
 */

public class ArticleListFragment extends Fragment
        implements LoadTagsList.onLoadTagsListListener, ArticleListAdapter.OnArticleListItemClickListener {
    private static final String CURRENT_PAGE = "CURRENT_PAGE";
    private static final String CURRENT_PAGE_ID = "CURRENT_PAGE_ID";
    private static final int PAGE_SIZE = 8;
    private int mPage;
    private String mPageId = "";
    private int mCurrentIndex = 1;
    private int mMaxIndex =0;


    private View mView;

    private LoadTagsList loadTagsList;


//    private ViewPager mViewPager;

    private RecyclerView mRecyclerView;

    private ArticleListAdapter mAdapter;

    private SwipeRefreshLayout mRefresh;

    private List<MainListBean.ContentBean> mList = new ArrayList<>();

    private boolean isRefresh = false;

    private Integer[] refreshColor = {android.R.color.holo_green_light,android.R.color.holo_blue_light,android.R.color.holo_purple,android.R.color.holo_orange_light};



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
        mView = inflater.inflate(R.layout.fragment_layout_list,container,false);
        initView();
        initEvent();

        return mView;
    }

    private void initView(){
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_refresh);

    }

    private void initEvent(){
        loadTagsList = new LoadTagsList(getContext());
        loadTagsList.setOnLoadTagsListListener(this);
        mRefresh.setColorSchemeColors(refreshColor[0],refreshColor[1],refreshColor[2],refreshColor[3]);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(0);
            }
        });


        if (mPageId != null) {
            initData(0);
        }

    }

    private void initData(int flag){
        if (flag == 0 || mCurrentIndex > mMaxIndex){
            mCurrentIndex = 1;
        }

        if (flag == 1){
            mCurrentIndex ++;
        }

        String url = ConstantsBean.GET_LIST_WITH_TAG+"catalogId=" +mPageId+"&page="+ mCurrentIndex +"&pagesize="+PAGE_SIZE;
        loadTagsList.getTagsList(url,flag);
    }


    @Override
    public void onSuccessResponse(List<MainListBean.ContentBean> list, int totalIndex, int flag) {
        mMaxIndex = totalIndex;
        if (list!= null){
            loadData(flag,list);
        }
    }

    @Override
    public void onErrorResponse() {

    }

    @Override
    public void onError() {

    }



    private void loadData(int flag,List<MainListBean.ContentBean> list){
        if (flag == 0){
            mList.clear();
        }
        if (mCurrentIndex > mMaxIndex){
            if (flag == 0 ){
                Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
//                mAdapter = new ArticleListAdapter(getActivity(),mList);
//                mRecyclerView.setAdapter(mAdapter);
            }
            if (flag == 1) {
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
        }else {
            mList.addAll(list);
            if (flag == 0){
                mAdapter = new ArticleListAdapter(getActivity(),mList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnArticleListClickListener(this);
            }
            if (flag == 1){
                mAdapter.setData(mList);
            }
        }

    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
    }
}
