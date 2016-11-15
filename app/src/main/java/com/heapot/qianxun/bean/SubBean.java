package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by Karl on 2016/10/2.
 *
 */
public class SubBean implements Serializable{
    public String id;
    public String pid;
    public String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
