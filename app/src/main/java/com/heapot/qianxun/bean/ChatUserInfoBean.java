package com.heapot.qianxun.bean;

/**
 * Created by Karl on 2016/11/10.
 * desc: 聊天用户信息
 */

public class ChatUserInfoBean {


    /**
     * status : success
     * message :
     * content : {"loginName":null,"phone":null,"email":null,"description":"程序猿是我","id":"f535188fed1f42cbba12e39960bb62ed","createTime":1475205085030,"status":{"index":1,"name":"正常"},"name":"null","nickname":"coderfan","type":{"index":3,"name":"普通用户"},"icon":"http://odxpoei6h.bkt.clouddn.com/qianxun58204445b0340.jpg"}
     */

    private String status;
    private String message;
    /**
     * loginName : null
     * phone : null
     * email : null
     * description : 程序猿是我
     * id : f535188fed1f42cbba12e39960bb62ed
     * createTime : 1475205085030
     * status : {"index":1,"name":"正常"}
     * name : null
     * nickname : coderfan
     * type : {"index":3,"name":"普通用户"}
     * icon : http://odxpoei6h.bkt.clouddn.com/qianxun58204445b0340.jpg
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

    public static class ContentBean {
        private Object loginName;
        private Object phone;
        private Object email;
        private String description;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;
        private String name;
        private String nickname;
        /**
         * index : 3
         * name : 普通用户
         */

        private TypeBean type;
        private String icon;

        public Object getLoginName() {
            return loginName;
        }

        public void setLoginName(Object loginName) {
            this.loginName = loginName;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public static class StatusBean {
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

        public static class TypeBean {
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
