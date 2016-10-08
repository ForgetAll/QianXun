package com.heapot.qianxun.activity.create;

import android.app.AlertDialog;
import android.content.Context;
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

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.BaseActivity;
import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.UserOrgBean;
import com.heapot.qianxun.helper.SerializableUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 15859 on 2016/10/5.
 * 公司大于两家
 */
public class CreateJobMoreList extends BaseActivity {
    private int number;
    Button show;
    ListView lv;
    List<UserOrgBean.ContentBean> persons = new ArrayList<UserOrgBean.ContentBean>();
    Context mContext;
    MyListAdapter adapter;
    List<Integer> listItemID = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job_more_list);
        getData();
        findView();
    }
    private void getData() {
        Object company = SerializableUtils.getSerializable(activity, ConstantsBean.USER_ORG_LIST);
        persons.addAll((Collection<? extends UserOrgBean.ContentBean>) company);
    }
    private void findView() {
        mContext = getApplicationContext();
        show = (Button)findViewById(R.id.show);
        lv = (ListView)findViewById(R.id.lvperson);
        adapter = new MyListAdapter(persons);
        lv.setAdapter(adapter);
        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listItemID.clear();
                for(int i=0;i<adapter.mChecked.size();i++){
                    if(adapter.mChecked.get(i)){
                        listItemID.add(i);
                    }
                }
                number=   listItemID.size();

                if(listItemID.size()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateJobMoreList.this);
                    builder1.setMessage("没有选中任何记录");
                    builder1.show();
                }else{
                    StringBuilder sb = new StringBuilder();

                    for(int i=0;i<listItemID.size();i++){
                        sb.append("ItemID="+listItemID.get(i)+" . ");
                    }
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(CreateJobMoreList.this);
                    builder2.setMessage(sb.toString());
                    builder2.show();
                }
            }
        });
    }
    //自定义ListView适配器
    class MyListAdapter extends BaseAdapter {
        List<Boolean> mChecked;
        List<UserOrgBean.ContentBean> listPerson;
        HashMap<Integer,View> map = new HashMap<Integer,View>();

        public MyListAdapter(List<UserOrgBean.ContentBean> list){
            listPerson = new ArrayList<UserOrgBean.ContentBean>();
            listPerson = list;

            mChecked = new ArrayList<Boolean>();
            for(int i=0;i<list.size();i++){
                mChecked.add(false);
            }
        }

        @Override
        public int getCount() {
            return listPerson.size();
        }

        @Override
        public Object getItem(int position) {
            return listPerson.get(position);
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
                Log.e("MainActivity","position1 = "+position);

                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.create_job_list_item, null);
                holder = new ViewHolder();
                holder.selected = (CheckBox)view.findViewById(R.id.list_select);
                holder.name = (TextView)view.findViewById(R.id.list_name);
                holder.address = (TextView)view.findViewById(R.id.list_address);
                final int p = position;
                map.put(position, view);
                holder.selected.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox)v;
                        mChecked.set(p, cb.isChecked());
                    }
                });
                view.setTag(holder);
            }else{
                Log.e("MainActivity","position2 = "+position);
                view = map.get(position);
                holder = (ViewHolder)view.getTag();
            }

            holder.selected.setChecked(mChecked.get(position));
            holder.name.setText(listPerson.get(position).getOrgId());
            holder.address.setText(listPerson.get(position).getUserId());

            return view;
        }

    }

    static class ViewHolder{
        CheckBox selected;
        TextView name;
        TextView address;
    }
}
