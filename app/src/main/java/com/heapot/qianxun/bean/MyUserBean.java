package com.heapot.qianxun.bean;

import com.heapot.qianxun.helper.SerializableUtils;

import java.io.Serializable;

import java.io.Serializable;

/**
 * Created by 15859 on 2016/9/27.
 * 个人实体类
 */
public class MyUserBean implements Serializable{
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

    public static class ContentBean implements Serializable{
        private String loginName;
        private String password;
        private String salt;
        private String phone;
        private String email;
        private String description;
        private String id;
        private long createTime;
        /**
         * index : 1
         * name : 正常
         */

        private StatusBean status;
        private String  name;
        private String nickname;
        /**
         * index : 3
         * name : 普通用户
         */

        private TypeBean type;
        private String icon;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
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

        public static class StatusBean implements Serializable {
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

        public static class TypeBean implements Serializable{
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
                "description": null,
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
