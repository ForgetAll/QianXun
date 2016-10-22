package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/10/20.
 * desc: 聊天好友列表
 *
 */

public class Friend implements Serializable {

    /**
     * status : success
     * message :
     * content : [{"id":"204e0d1213734c308e0f06239ccadf35","userId":"f535188fed1f42cbba12e39960bb62ed","friendId":"2579f5a10a3b47ca9c0e2289e7096786","nickname":"小孟","icon":"http://odxpoei6h.bkt.clouddn.com/qianxun5809bc219ad4d.jpg","remark":null,"description":null}]
     */

    private String status;
    private String message;
    /**
     * id : 204e0d1213734c308e0f06239ccadf35
     * userId : f535188fed1f42cbba12e39960bb62ed
     * friendId : 2579f5a10a3b47ca9c0e2289e7096786
     * nickname : 小孟
     * icon : http://odxpoei6h.bkt.clouddn.com/qianxun5809bc219ad4d.jpg
     * remark : null
     * description : null
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
        private String id;
        private String userId;
        private String friendId;
        private String nickname;
        private String icon;
        private Object remark;
        private Object description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFriendId() {
            return friendId;
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }
    }
}