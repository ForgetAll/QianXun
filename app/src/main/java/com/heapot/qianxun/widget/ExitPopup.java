package com.heapot.qianxun.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.activity.LoginActivity;
import com.heapot.qianxun.application.CustomApplication;
import com.heapot.qianxun.util.PreferenceUtil;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by 15859 on 2016/9/22.
 * 退出窗口
 */
public class ExitPopup extends BasePopupWindow implements View.OnClickListener {
    private TextView mCancel,mOk;

    public ExitPopup(Activity context) {
        super(context);
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return mCancel;
    }

    @Override
    public View getPopupView() {
        View view=   View.inflate(mContext, R.layout.exit_app_dialog,null);
        mCancel=(TextView) findViewById(R.id.tv_cancel);
        mOk=(TextView) findViewById(R.id.tv_ok);
        mOk.setOnClickListener(this);
        return view;

    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        PreferenceUtil.clearPreference();
        Intent intent=new Intent(CustomApplication.getContext(), LoginActivity.class);
        CustomApplication.getContext().startActivity(intent);
        //BroadcastUtil.sendDataChangeBroadcase(mContext,new Intent(ConstantsBean.UPDATE));
        mContext.finish();

    }
}
