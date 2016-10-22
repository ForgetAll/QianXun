package com.heapot.qianxun.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karl on 2016/10/22.
 * desc: 好友请求列表
 *
 */

public class FriendRequestBean implements Serializable{

    /**
     * status : success
     * message :
     * content : [{"id":"5047f89277e94d519300cb867706ceb8","userId":"2579f5a10a3b47ca9c0e2289e7096786","friendId":"f535188fed1f42cbba12e39960bb62ed","nickname":"coderfan","icon":"http://odxpoei6h.bkt.clouddn.com/qianxun58085b58bb963.jpg","remark":null,"requestDescription":null,"processDescription":null,"status":1}]
     */

    private String status;
    private String message;
    /**
     * id : 5047f89277e94d519300cb867706ceb8
     * userId : 2579f5a10a3b47ca9c0e2289e7096786
     * friendId : f535188fed1f42cbba12e39960bb62ed
     * nickname : coderfan
     * icon : http://odxpoei6h.bkt.clouddn.com/qianxun58085b58bb963.jpg
     * remark : null
     * requestDescription : null
     * processDescription : null
     * status : 1
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
        private Object requestDescription;
        private Object processDescription;
        private int status;

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

        public Object getRequestDescription() {
            return requestDescription;
        }

        public void setRequestDescription(Object requestDescription) {
            this.requestDescription = requestDescription;
        }

        public Object getProcessDescription() {
            return processDescription;
        }

        public void setProcessDescription(Object processDescription) {
            this.processDescription = processDescription;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
