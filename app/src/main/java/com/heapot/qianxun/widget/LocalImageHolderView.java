package com.heapot.qianxun.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.heapot.qianxun.R;

/**
 * Created by 15859 on 2016/10/28.
 *
 * 本地加载viewpager图片
 */
public class LocalImageHolderView implements Holder<Integer> {
    ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_viewpager_image, null);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }
}
