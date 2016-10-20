package com.heapot.qianxun.bean;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/10/19.
 */
public class CreateJobResultBean implements Serializable{

    /**
     * status : success
     * message :
     * content : {"name":"仟询","code":"QianXun","minSalary":3000,"maxSalary":5000,"num":1,"description":"zhiweenn","orgId":"7606d05252ba4269b41aa7191ab71c96","creatorId":"2579f5a10a3b47ca9c0e2289e7096786","phone":"","email":"","hit":0,"catalogId":"1a0b018d84ba4f4e967f936650d3972d","catalog":null,"img":"","id":"ec36f459fe0345dabc0386e00873ad59","createTime":1476857336665,"status":{"index":1,"name":"正常"}}
     */

    private String status;
    private String message;
    /**
     * name : 仟询
     * code : QianXun
     * minSalary : 3000
     * maxSalary : 5000
     * num : 1
     * description : zhiweenn
     * orgId : 7606d05252ba4269b41aa7191ab71c96
     * creatorId : 2579f5a10a3b47ca9c0e2289e7096786
     * phone :
     * email :
     * hit : 0
     * catalogId : 1a0b018d84ba4f4e967f936650d3972d
     * catalog : null
     * img :
     * id : ec36f459fe0345dabc0386e00873ad59
     * createTime : 1476857336665
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
        private String name;
        private String code;
        private int minSalary;
        private int maxSalary;
        private int num;
        private String description;
        private String orgId;
        private String creatorId;
        private String phone;
        private String email;
        private int hit;
        private String catalogId;
        private Object catalog;
        private String img;
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

        public int getMinSalary() {
            return minSalary;
        }

        public void setMinSalary(int minSalary) {
            this.minSalary = minSalary;
        }

        public int getMaxSalary() {
            return maxSalary;
        }

        public void setMaxSalary(int maxSalary) {
            this.maxSalary = maxSalary;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getHit() {
            return hit;
        }

        public void setHit(int hit) {
            this.hit = hit;
        }

        public String getCatalogId() {
            return catalogId;
        }

        public void setCatalogId(String catalogId) {
            this.catalogId = catalogId;
        }

        public Object getCatalog() {
            return catalog;
        }

        public void setCatalog(Object catalog) {
            this.catalog = catalog;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
