package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by Karl on 2016/9/8.
 */
public class SubBean implements Serializable{
    public String name;
    public int status;
    public int pos;//记录位置

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
