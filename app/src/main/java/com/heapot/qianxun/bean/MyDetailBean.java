package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/10/31.
 */
public class MyDetailBean implements Serializable{

    /**
     * status : success
     * message :
     * content : {"userId":"2579f5a10a3b47ca9c0e2289e7096786","sex":null,"birthYear":null,"description":null,"id":"59e6cfd46fda4884ba649929b7a90626","createTime":1477905738276,"status":{"index":1,"name":"正常"}}
     */

    private String status;
    private String message;
    /**
     * userId : 2579f5a10a3b47ca9c0e2289e7096786
     * sex : null
     * birthYear : null
     * description : null
     * id : 59e6cfd46fda4884ba649929b7a90626
     * createTime : 1477905738276
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

    public static class ContentBean implements Serializable{
        private String userId;
        private int sex;
        private int birthYear;
        private String description;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(int birthYear) {
            this.birthYear = birthYear;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
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
