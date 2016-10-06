package com.heapot.qianxun.bean;

import java.util.List;

/**
 * Created by 15859 on 2016/10/6.
 */
public class SearchBean {

    /**
     * status : success
     * message :
     * content : {"total":1,"hasNext":false,"rows":[{"id":"57d21a4113512b1a30547360","highlightTitle":"<em>守望<\/em>先锋泛亚太国际锦标赛","highlightSubtitle":null,"highlightSummary":null,"highlightContent":"<em>守望<\/em>先锋泛亚太国际锦标赛(OVERWATCH APAC PERMIRE) 是由香蕉游戏传媒主办,暴雪官方授权的国际型<em>守望<\/em>先锋比赛. 比赛将邀请 <em>中国<\/em>,韩国,美国 三个国家的战队参与. <em>中国<\/em>A组比赛昨天已经完成,今天晚上将迎来B组比赛."}],"hasPre":false}
     */

    private String status;
    private String message;
    /**
     * total : 1
     * hasNext : false
     * rows : [{"id":"57d21a4113512b1a30547360","highlightTitle":"<em>守望<\/em>先锋泛亚太国际锦标赛","highlightSubtitle":null,"highlightSummary":null,"highlightContent":"<em>守望<\/em>先锋泛亚太国际锦标赛(OVERWATCH APAC PERMIRE) 是由香蕉游戏传媒主办,暴雪官方授权的国际型<em>守望<\/em>先锋比赛. 比赛将邀请 <em>中国<\/em>,韩国,美国 三个国家的战队参与. <em>中国<\/em>A组比赛昨天已经完成,今天晚上将迎来B组比赛."}]
     * hasPre : false
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
        private int total;
        private boolean hasNext;
        private boolean hasPre;
        /**
         * id : 57d21a4113512b1a30547360
         * highlightTitle : <em>守望</em>先锋泛亚太国际锦标赛
         * highlightSubtitle : null
         * highlightSummary : null
         * highlightContent : <em>守望</em>先锋泛亚太国际锦标赛(OVERWATCH APAC PERMIRE) 是由香蕉游戏传媒主办,暴雪官方授权的国际型<em>守望</em>先锋比赛. 比赛将邀请 <em>中国</em>,韩国,美国 三个国家的战队参与. <em>中国</em>A组比赛昨天已经完成,今天晚上将迎来B组比赛.
         */

        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPre() {
            return hasPre;
        }

        public void setHasPre(boolean hasPre) {
            this.hasPre = hasPre;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private String id;
            private String highlightTitle;
            private Object highlightSubtitle;
            private Object highlightSummary;
            private String highlightContent;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getHighlightTitle() {
                return highlightTitle;
            }

            public void setHighlightTitle(String highlightTitle) {
                this.highlightTitle = highlightTitle;
            }

            public Object getHighlightSubtitle() {
                return highlightSubtitle;
            }

            public void setHighlightSubtitle(Object highlightSubtitle) {
                this.highlightSubtitle = highlightSubtitle;
            }

            public Object getHighlightSummary() {
                return highlightSummary;
            }

            public void setHighlightSummary(Object highlightSummary) {
                this.highlightSummary = highlightSummary;
            }

            public String getHighlightContent() {
                return highlightContent;
            }

            public void setHighlightContent(String highlightContent) {
                this.highlightContent = highlightContent;
            }
        }
    }
}
