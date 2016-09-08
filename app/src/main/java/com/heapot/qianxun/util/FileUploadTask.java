package com.heapot.qianxun.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.heapot.qianxun.util.HttpUtil;
import java.io.File;

/**
 * @author JackHappiness
 * @date 2016/6/28.
 * @verion 1.0
 * @summary 说明：文件上传异步类
 */

public class FileUploadTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progressDialog;
    private File file;
    Handler handler;

    public FileUploadTask(Context context, Handler handler, File file) {
        this.context = context;
        this.file = file;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在上传文件，请稍候...");
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String[] params) {
        String path = params[0];
        String result = HttpUtil.postFile(path, file);
        return result;
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
        Message msg = Message.obtain();
        msg.obj = o;
        handler.sendMessage(msg);
        Log.e("result:", o + "");
    }
}
