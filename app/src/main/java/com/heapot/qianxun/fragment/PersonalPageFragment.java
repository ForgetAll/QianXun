package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.adapter.PersonalTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李大总管 on 2016/10/2.
 */
public class PersonalPageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String PAGE = "PAGE";
    private int mPage;
    private String mId;
    View mView;

    private RecyclerView recyclerView;
    private PersonalTabAdapter personalTabAdapter;
    private List<String> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh = false;


    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE,page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_list,container,false);
        Logger.d("当前页面是 #"+mPage+"，Id是："+mId);
        initView();
        initEvent();
        return mView;
    }

    private void initView(){
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.srl_main_fragment);

    }
    private void initEvent(){
        //adapter = new MainTabAdapter(getContext(),list);
        personalTabAdapter=new PersonalTabAdapter(getContext(),list);
        loadData();
        recyclerView.setAdapter(personalTabAdapter);
        //添加点击事件
        personalTabAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple,
                android.R.color.holo_orange_light
        );
    }
    /**
     * 模拟数据
     */
    private void loadData(){
        for (int i = 0; i < 20; i++) {
            list.add("Tab #"+mPage+" Item #"+i);
        }
        String url= ConstantsBean.BASE_PATH+ConstantsBean.PERSONAL_ARTICLE;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("个人发表的内容",response.toString());
                try {
                    String status=response.getString("status");
                    String content=response.getString("content");
                    if (status.equals("success")){
                        if (content!=null){

                        }else {
                            Toast.makeText(getContext(),"您还没有发表任何内容",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public void onRefresh() {
        if (!isRefresh){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
