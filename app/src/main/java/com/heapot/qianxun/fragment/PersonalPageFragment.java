package com.heapot.qianxun.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.ArticleActivity;
import com.heapot.qianxun.adapter.PersonalArticleAdapter;
import com.heapot.qianxun.adapter.PersonalTabAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.MyPersonalArticle;
import com.heapot.qianxun.helper.OnRecyclerViewItemClickListener;
import com.heapot.qianxun.util.JsonUtil;
import com.heapot.qianxun.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李大总管 on 2016/10/2.
 */
public class PersonalPageFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private int mPage;
    private String mId;
    View mView;

    private PersonalTabAdapter personalTabAdapter;
    private List<String> list = new ArrayList<>();
    PersonalArticleAdapter personalArticleAdapter;
    private ListView ll_listView;
    private List<MyPersonalArticle.ContentBean.RowsBean> articleList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        mPage = getArguments().getInt(PAGE);
        personalTabAdapter=new PersonalTabAdapter(getContext(),list);
        //添加点击事件
        personalTabAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_personal_page_list, container, false);
        findList();
        return mView;
    }

    private void findList() {
        ll_listView = (ListView) mView.findViewById(R.id.ll_listView);
        ll_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id1 = articleList.get(position).getId();
            }
        });
    }


    public static PersonalPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        PersonalPageFragment personalPageFragment = new PersonalPageFragment();
        personalPageFragment.setArguments(args);
        return personalPageFragment;
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
                        Log.e("我发表的文章：", String.valueOf(articleList.size()));
                        personalArticleAdapter = new PersonalArticleAdapter(getContext(), articleList);
                        ll_listView.setAdapter(personalArticleAdapter);
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
                headers.put(ConstantsBean.KEY_TOKEN, CustomApplication.TOKEN);
                return headers;
            }
        };
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

}
