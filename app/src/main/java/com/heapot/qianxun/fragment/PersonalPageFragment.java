package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.detail.ArticleActivity;
import com.heapot.qianxun.adapter.PersonalArticleAdapter;
import com.heapot.qianxun.adapter.PersonalArticleRecyclerViewAdapter;
import com.heapot.qianxun.adapter.PersonalTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyPersonalArticle;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15998 on 2016/10/2.
 */
public class PersonalPageFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private int mPage;
    private String mId;
    View mView;

    private PersonalTabAdapter personalTabAdapter;
    private List<MyPersonalArticle.ContentBean.RowsBean> list = new ArrayList<>();
    PersonalArticleAdapter personalArticleAdapter;
    private List<MyPersonalArticle.ContentBean.RowsBean> articleList = new ArrayList<>();
    private RecyclerView recyclerView;
    View view;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private PersonalArticleRecyclerViewAdapter personalArticleRecyclerViewAdapter;


    public static PersonalPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        PersonalPageFragment personalPageFragment = new PersonalPageFragment();
        personalPageFragment.setArguments(args);
        return personalPageFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(PAGE);
       /* personalTabAdapter = new PersonalTabAdapter(getContext(), list);
        personalTabAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
            }
        });*/

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_personal_page_list, container, false);

        findList();
        return mView;
    }

    private void findList() {
        recycler_view = (RecyclerView) mView.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(linearLayoutManager);
        loadData();

    }


    /**
     * 数据
     */
    private void loadData() {
        String url = ConstantsBean.BASE_PATH + "/articles?userId=" + PreferenceUtil.getString(ConstantsBean.USER_ID);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("个人发表的内容", response.toString());
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {
                        MyPersonalArticle myPersonalArticle = (MyPersonalArticle) JsonUtil.fromJson(String.valueOf(response), MyPersonalArticle.class);

                        articleList = myPersonalArticle.getContent().getRows();
                        list.addAll(myPersonalArticle.getContent().getRows());
                        personalArticleRecyclerViewAdapter = new PersonalArticleRecyclerViewAdapter(getContext(), articleList);
                        recycler_view.setAdapter(personalArticleRecyclerViewAdapter);
                        personalArticleRecyclerViewAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                                intent.putExtra("id",articleList.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                Glide.with(getContext()).resumeRequests();
                            }

                            @Override
                            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                                    Glide.with(getContext()).pauseRequests();
                                }
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = PreferenceUtil.getString("token");
                headers.put(ConstantsBean.KEY_TOKEN, token);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }


}
