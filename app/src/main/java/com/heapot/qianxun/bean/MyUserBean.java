package com.heapot.qianxun.bean;

/**
 * Created by 15859 on 2016/9/27.
 * 个人实体类
 */
public class MyUserBean {
    /**
     * status : success
     * message :
     * content : {"loginName":null,"password":"799e90487f85a89708e6e80154c265306f926d02","salt":"5a063c4ffaca6c01","phone":"18236969075","email":null,"id":"2579f5a10a3b47ca9c0e2289e7096786","createTime":1474945475208,"status":{"index":1,"name":"正常"},"name":null,"nickname":"limeng","type":{"index":3,"name":"普通用户"},"icon":null}
     */

    private String status;
    private String message;
    /**
     * loginName : null
     * password : 799e90487f85a89708e6e80154c265306f926d02
     * salt : 5a063c4ffaca6c01
     * phone : 18236969075
     * email : null
     * id : 2579f5a10a3b47ca9c0e2289e7096786
     * createTime : 1474945475208
     * status : {"index":1,"name":"正常"}
     * name : null
     * nickname : limeng
     * type : {"index":3,"name":"普通用户"}
     * icon : null
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
        private String password;
        private String salt;
        private String phone;
        private Object email;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;
        private Object name;
        private String nickname;
        /**
         * index : 3
         * name : 普通用户
         */

        private TypeBean type;
        private Object icon;

        public Object getLoginName() {
            return loginName;
        }

        public void setLoginName(Object loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
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

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
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

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
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
   /* {
        "status": "success",
            "message": "",
            "content": {
        "loginName": null,
                "password": "799e90487f85a89708e6e80154c265306f926d02",
                "salt": "5a063c4ffaca6c01",
                "phone": "18236969075",
                "email": null,
                "id": "2579f5a10a3b47ca9c0e2289e7096786",
                "createTime": 1474945475208,
                "status": {
            "index": 1,
                    "name": "正常"
        },
        "name": null,
                "nickname": "limeng",
                "type": {
            "index": 3,
                    "name": "普通用户"
        },
        "icon": null
    }
    }*/

}
