package com.heapot.qianxun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.heapot.qianxun.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15859 on 2016/8/31.
 */
public class PersonalFirstFragment extends Fragment {
    public static final String PAGE = "PAGE";
    private int mPage;

    private RecyclerView recyclerView;
    private MyListAdapter adapter;
    private List<String> list;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.layout_list,container,false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        adapter = new MyListAdapter(list);
        loadData();
        recyclerView.setAdapter(adapter);


        return mView;
    }
    private void loadData(){
        for (int i = 0; i < 20; i++) {
            list.add("这是个人中心Fragment"+" 与其他的不一样"+i);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    public class MyListAdapter<String> extends RecyclerView.Adapter<MyViewHolder>{

        public List<String> mList;
        public MyListAdapter(List<String> mList){
            this.mList = mList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item,null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(mList.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
