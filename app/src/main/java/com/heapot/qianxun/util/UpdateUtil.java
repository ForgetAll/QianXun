package com.heapot.qianxun.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.heapot.qianxun.bean.ConstantsBean;
import com.heapot.qianxun.bean.UpdateResultBean;
import com.heapot.qianxun.service.UpdateService;
import com.heapot.qianxun.widget.UpdateAppPopup;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orhanobut.logger.Logger;

/**
 * Created by 15859 on 2016/9/23.
 * 自动更新版本
 */
public class UpdateUtil {
    ProgressDialog dialog = null;
    private UpdateUtil() {
    }

    private static class UpdateHolder {
        private static final UpdateUtil updateUtil = new UpdateUtil();
    }
    public static UpdateUtil getInstance() {
        return UpdateHolder.updateUtil;
    }
    public void checkUpdate(final Activity context, final TextView textView, final boolean isShowTip) {
        if (isShowTip) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("正在检查，请稍候...");
            dialog.show();
        }
        Ion.with(context).load(ConstantsBean.UPDATE_VERSION)
                .as(UpdateResultBean.class).setCallback(new FutureCallback<UpdateResultBean>() {
            @Override
            public void onCompleted(Exception e, UpdateResultBean result) {
                Logger.d(result);
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (result != null) {
                    if (result.getReturn_code().equals("success")) {
                         final UpdateResultBean.ContentBean bean = result.getContent();
                      int Version= bean.getVersion();
                        if (Version == (PackageUtils.getAppVersionCode(context))) {
                            //版本相等，没有更新
                            if (isShowTip) {
                                ToastUtil.show("已是最新版本");
                            }
                        } else {
                            //有新版本
                            final UpdateAppPopup appPopup = new UpdateAppPopup(context);
                            appPopup.showPopupWindow();
                            appPopup.getTv_content().setText(Html.fromHtml(bean.getAppDescribe()));
                            if (textView!=null){
                                textView.setText(bean.getVersioncode());
                                Log.e("给用户看的版本号",bean.getVersioncode());
                            }
                            appPopup.getTv_ok().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    appPopup.dismiss();
                                    //开启服务更新
                                    Intent intent = new Intent(context, UpdateService.class);
                                    intent.putExtra(ConstantsBean.UPDATE_VERSION,  bean);
                                    context.startService(intent);
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}
