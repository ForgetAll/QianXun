package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/10/19.
 */
public class OrgInfoBean implements Serializable{
    /**
     * status : success
     * message :
     * content : {"name":"仟徇","code":"qianxun","address":"河南省","phone":"13434343433","description":null,"id":"7606d05252ba4269b41aa7191ab71c96","createTime":1470735993305,"status":{"index":1,"name":"正常"}}
     */

    private String status;
    private String message;
    /**
     * name : 仟徇
     * code : qianxun
     * address : 河南省
     * phone : 13434343433
     * description : null
     * id : 7606d05252ba4269b41aa7191ab71c96
     * createTime : 1470735993305
     * status : {"index":1,"name":"正常"}
     */

    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        private String name;
        private String code;
        private String address;
        private String phone;
        private Object description;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
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
