package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/10/8.
 */
public class UserOrgBean  implements Serializable{

    /**
     * status : success
     * message :
     * content : [{"userId":"f535188fed1f42cbba12e39960bb62ed","orgId":"7606d05252ba4269b41aa7191ab71c96","id":"675cf7ca72884fcf948db003c1b5c998","createTime":1475916460276,"status":{"index":1,"name":"正常"}}]
     */

    private String status;
    private String message;
    /**
     * userId : f535188fed1f42cbba12e39960bb62ed
     * orgId : 7606d05252ba4269b41aa7191ab71c96
     * id : 675cf7ca72884fcf948db003c1b5c998
     * createTime : 1475916460276
     * status : {"index":1,"name":"正常"}
     */

    private List<ContentBean> content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable{
        private String userId;
        private String orgId;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public StatusBean getStatus() {
            return status;
        }

        public void setStatus(StatusBean status) {
            this.status = status;
        }

        public static class StatusBean implements Serializable{
            private int index;
            private String name;

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
