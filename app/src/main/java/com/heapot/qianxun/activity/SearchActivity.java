package com.heapot.qianxun.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blankj.utilcode.utils.NetworkUtils;
import com.heapot.qianxun.R;
import com.heapot.qianxun.adapter.SearchArticleAdapter;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.SearchBean;
import com.heapot.qianxun.helper.RecordSQLiteOpenHelper;
import com.heapot.qianxun.popupwindow.SearchListView;
import com.heapot.qianxun.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15859 on 2016/9/12.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_search;
    private TextView tv_tip, tv_clear;
    private SearchListView listView;
    private ImageView mBack, mClearSearch;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private SearchListView lv_search;
    private List<SearchBean.ContentBean.RowsBean> rowsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //查找控件
        findView();
        //获取数据
        getData();
        //设置数据
        setData();
    }

    private void findView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (com.heapot.qianxun.popupwindow.SearchListView) findViewById(R.id.listView);
        lv_search = (com.heapot.qianxun.popupwindow.SearchListView) findViewById(R.id.lv_search);
        setListViewHeightBasedOnChildren(lv_search);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mClearSearch = (ImageView) findViewById(R.id.iv_clearSearch);
        mClearSearch.setOnClickListener(this);
        mBack.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                    // 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast.makeText(SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lv_search.setVisibility(View.GONE);
                tv_clear.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listView.setVisibility(View.GONE);
                tv_clear.setVisibility(View.GONE);
                lv_search.setVisibility(View.VISIBLE);
                String searchStr = et_search.getText().toString().trim();
                boolean isAvailable = NetworkUtils.isAvailable(SearchActivity.this);
                if (isAvailable) {
                    searchArticle(searchStr);
                } else {
                    Toast.makeText(activity, "请检查网络连接", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    listView.setVisibility(View.VISIBLE);
                    lv_search.setVisibility(View.GONE);
                    tv_tip.setText("搜索历史");
                    tv_clear.setVisibility(View.VISIBLE);
                    mClearSearch.setVisibility(View.GONE);
                } else {
                    tv_tip.setText("搜索结果");
                    tv_clear.setVisibility(View.GONE);
                    mClearSearch.setVisibility(View.VISIBLE);
                    searchArticle(s.toString().trim());
                }
                String temName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(temName);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把item的字传到输入框
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                searchArticle(name);
                //跳转事件，待解决
                listView.setVisibility(View.GONE);
                lv_search.setVisibility(View.VISIBLE);
                tv_clear.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = rowsList.get(position).getId();
            }
        });
    }

    private void getData() {
        // 第一次进入查询所有的历史记录
        queryData("");
    }

    private void setData() {

    }


    //插入数据，便于测试，否则第一次进入没有数据
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    //模糊查询数据，目前只是查询历史，还需要再建立一个查询，查询整个后台数据
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //清空数据
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear:
                deleteData();
                queryData("");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clearSearch:
                et_search.setText("");
                break;

        }

    }

    private void searchArticle(String searchStr) {
        String url = ConstantsBean.BASE_PATH + ConstantsBean.ARTICLES_SEARCH + searchStr;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("success")) {
                        SearchBean searchBean = (SearchBean) JsonUtil.fromJson(String.valueOf(response), SearchBean.class);
                         rowsList =searchBean.getContent().getRows();
                        Log.e("搜索的结果是：", String.valueOf(rowsList.size()));
                        SearchArticleAdapter articleAdapter = new SearchArticleAdapter(activity, rowsList);
                        lv_search.setAdapter(articleAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    //检查数据库中是否有该条数据
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }
    /**
     * scrollview嵌套listview显示不全解决
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
