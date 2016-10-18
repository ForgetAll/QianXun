package com.heapot.qianxun.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Karl on 2016/10/18.
 * desc: 重写recyclerView
 *
 */

public class TagRecyclerView extends RecyclerView {

    public TagRecyclerView(Context context) {
        super(context);
    }

    public TagRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TagRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
