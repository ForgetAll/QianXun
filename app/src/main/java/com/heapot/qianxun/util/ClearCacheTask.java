package com.heapot.qianxun.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heapot.qianxun.application.CustomApplication;

/**
 * Created by 15859 on 2016/9/22.
 * 清除缓存
 */
public class ClearCacheTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private TextView textView;

    public ClearCacheTask(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //清除数据
        Glide.get(CustomApplication.getContext()).clearDiskCache();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        textView.setText("");
    }
}
