package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 15859 on 2016/11/1.
 */
public class MyEducationBean implements Serializable{

    /**
     * status : success
     * message :
     * content : [{"school":"郑州轻工业学院","faculty":"软件学院","profession":"软件测试","startYear":2005,"endYear":2009,"userId":"2579f5a10a3b47ca9c0e2289e7096786","id":"bc66124cc2634b8da0e72681f4be1777","createTime":1477980551982,"status":{"index":1,"name":"正常"}}]
     */

    private String status;
    private String message;
    /**
     * school : 郑州轻工业学院
     * faculty : 软件学院
     * profession : 软件测试
     * startYear : 2005
     * endYear : 2009
     * userId : 2579f5a10a3b47ca9c0e2289e7096786
     * id : bc66124cc2634b8da0e72681f4be1777
     * createTime : 1477980551982
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
        private String school;
        private String faculty;
        private String profession;
        private int startYear;
        private int endYear;
        private String userId;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getFaculty() {
            return faculty;
        }

        public void setFaculty(String faculty) {
            this.faculty = faculty;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public int getStartYear() {
            return startYear;
        }

        public void setStartYear(int startYear) {
            this.startYear = startYear;
        }

        public int getEndYear() {
            return endYear;
        }

        public void setEndYear(int endYear) {
            this.endYear = endYear;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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
