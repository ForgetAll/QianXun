package com.heapot.qianxun.activity.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.util.JsonUtil;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15859 on 2016/10/5.
 * 公司大于两家
 */
public class CreateJobMoreList extends BaseActivity {
    private int number;
    Button btn_sure;
    ListView lv;
    private List<UserOrgBean.ContentBean> orgList  = new ArrayList<UserOrgBean.ContentBean>();
    Context mContext;
    MyListAdapter adapter;
    List<Integer> listItemID = new ArrayList<Integer>();
    private TextView tv_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_more_list);
        getData();
        findView();
    }

    private void getData() {
        //获取数据

            String url = ConstantsBean.BASE_PATH+ConstantsBean.QUERY_UER_ORG;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.json(String.valueOf(response));
                            Log.e("所有的Json数据：",response.toString());
                            try {
                                String status = response.getString("status");
                                if (status.equals("success")){
                                    UserOrgBean userOrgBean = (UserOrgBean) JsonUtil.fromJson(String.valueOf(response),UserOrgBean.class);
                                    //SerializableUtils.setSerializable(CreateActivity.this,ConstantsBean.USER_ORG_LIST,userOrgBean);
                                    orgList.addAll(userOrgBean.getContent());
                                    Logger.d("orgst------->"+orgList.size());
                                }else {
                                    Toast.makeText(CreateJobMoreList.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put(ConstantsBean.KEY_TOKEN, getAppToken());
                    return headers;
                }
            };
            CustomApplication.getRequestQueue().add(jsonObjectRequest);
    }

    private void findView() {
        mContext = getApplicationContext();
        tv_complete=(TextView) findViewById(R.id.tv_complete);
        lv = (ListView) findViewById(R.id.lvperson);
        adapter = new MyListAdapter(orgList);
        lv.setAdapter(adapter);
        tv_complete.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        listItemID.clear();
                                        for (int i = 0; i < adapter.mChecked.size(); i++) {
                                            if (adapter.mChecked.get(i)) {
                                                listItemID.add(i);
                                            }
                                        }
                                        number = listItemID.size();
                                        Intent intent = new Intent(CreateJobMoreList.this, CreateJobActivity.class);
                                        intent.putExtra("number", number);
                                        finish();

                                    }
                                }
        );
    }

    //自定义ListView适配器
    class MyListAdapter extends BaseAdapter {
        List<Boolean> mChecked;
        List<UserOrgBean.ContentBean> orgList;
        HashMap<Integer, View> map = new HashMap<Integer, View>();

        public MyListAdapter(List<UserOrgBean.ContentBean> orgList) {
            orgList = new ArrayList<UserOrgBean.ContentBean>();
           this.orgList = orgList;

            mChecked = new ArrayList<Boolean>();
            for (int i = 0; i < orgList.size(); i++) {
                mChecked.add(false);
            }
        }

        @Override
        public int getCount() {
            return orgList.size();
        }

        @Override
        public Object getItem(int position) {
            return orgList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder = null;

            if (map.get(position) == null) {
                Log.e("MainActivity", "position1 = " + position);

                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.create_job_list_item, null);
                holder = new ViewHolder();
                holder.selected = (CheckBox) view.findViewById(R.id.list_select);
                holder.name = (TextView) view.findViewById(R.id.list_name);
                holder.address = (TextView) view.findViewById(R.id.list_address);
                final int p = position;
                map.put(position, view);
                holder.selected.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        mChecked.set(p, cb.isChecked());
                    }
                });
                view.setTag(holder);
            } else {
                Log.e("MainActivity", "position2 = " + position);
                view = map.get(position);
                holder = (ViewHolder) view.getTag();
            }

            holder.selected.setChecked(mChecked.get(position));
            holder.name.setText(orgList.get(position).getOrgId());
            holder.address.setText(orgList.get(position).getUserId());

            return view;
        }

    }

    static class ViewHolder {
        CheckBox selected;
        TextView name;
        TextView address;
    }
}
