package com.heapot.qianxun.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.heapot.qianxun.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by 15859 on 2016/9/23.
 * 自动更新弹出框
 */
public class UpdateAppPopup extends BasePopupWindow {
    private View tv_cancel;
    private TextView tv_content, tv_ok;

    public TextView getTv_content() {
        return tv_content;
    }

    public void setTv_content(TextView tv_content) {
        this.tv_content = tv_content;
    }

    public TextView getTv_ok() {
        return tv_ok;
    }

    public void setTv_ok(TextView tv_ok) {
        this.tv_ok = tv_ok;
    }

    public UpdateAppPopup(Activity context) {
        super(context);
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return tv_cancel;
    }

    @Override
    public View getPopupView() {
        View view = View.inflate(mContext, R.layout.app_update_popup, null);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

}
