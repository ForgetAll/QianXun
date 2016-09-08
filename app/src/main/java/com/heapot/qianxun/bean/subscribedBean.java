package com.heapot.qianxun.bean;

import com.heaven7.android.dragflowlayout.IDraggable;

/**
 * Created by Karl on 2016/9/1.
 */
public class SubscribedBean implements IDraggable {
    public String text;
    boolean draggable = true;

    public SubscribedBean(String text) {
        this.text = text;
    }

    @Override
    public boolean isDraggable() {
        return draggable;
    }

}
