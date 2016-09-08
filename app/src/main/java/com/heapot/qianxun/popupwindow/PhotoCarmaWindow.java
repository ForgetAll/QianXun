package com.heapot.qianxun.popupwindow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.heapot.qianxun.R;
import com.heapot.qianxun.bean.ConstantsBean;

import java.io.File;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author JackHappiness
 * @date 2016/6/28.
 * @verion 1.0
 * @summary 说明：拍照和相册的Window
 */

public class PhotoCarmaWindow extends BasePopupWindow implements View.OnClickListener {
    private View view;

    public PhotoCarmaWindow(Activity context) {
        super(context);
    }

    @Override
    protected Animation getShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    protected View getClickToDismissView() {
        return view.findViewById(R.id.tv_dismiss);
    }

    @Override
    public View getPopupView() {
        view = View.inflate(mContext, R.layout.pw_sex_layout, null);
        TextView photoView = (TextView) view.findViewById(R.id.tv_girl);
        photoView.setOnClickListener(this);
        photoView.setText("相册");
        TextView carmaView = (TextView) view.findViewById(R.id.tv_boy);
        carmaView.setOnClickListener(this);
        carmaView.setText("相机");
        return view;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选取相册
            case R.id.tv_girl:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mContext.startActivityForResult(intent, 103);
                dismiss();
                break;
            //相机
            case R.id.tv_boy:
                dismiss();
                // smallImage();
                bigImage();
                break;
        }
    }

    //显示大图
    private void bigImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //指定输入拍照保存的sd卡的文件
      File file = new File(ConstantsBean.HEAD_IMAGE_PATH);
       // Log.e("路径：",ConstantsBean.HEAD_IMAGE_PATH);
        //根据文件，解析出文件对应的Uri
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //判断是否有Activity能处理意图
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivityForResult(intent, ConstantsBean.CARMA_RESULT_CODE);
        }
    }

    private void smallImage() {
        /**
         * 通过意图Intent去捕获图片
         * action: MediaStore.ACTION_IMAGE_CAPTURE
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断是否有Activity能处理意图
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivityForResult(intent, ConstantsBean.CARMA_RESULT_CODE);
        }
    }
}
