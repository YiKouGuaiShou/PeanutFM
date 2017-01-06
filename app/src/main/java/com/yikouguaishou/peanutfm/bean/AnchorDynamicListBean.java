package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowflake on 2017/1/5.
 */
public class AnchorDynamicListBean implements Serializable {

    /**
     * con : [{"anchorId":"282","anchorImg":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F17%2F11%2Fjpg2016111711197.jpg","anchorName":"radiofan","commentNum":0,"content":"今天开始","cover":null,"id":"1","imgList":[{"id":0,"url":"http://cdn.linker.cc/anchorDynamic%2F201612%2F26%2F11%2Fjpeg2016122611296.jpeg"}],"linkUrl":"","praiseCount":0,"title":"","type":"0","isPraise":0,"createTime":"2016-12-26 11:47:58"}]
     * curcount : null
     * total : 0
     * rt : 1
     * des : succeed
     */

    private Object curcount;
    private int total;
    private int rt;
    private String des;
    private List<ConBean> con;

    public Object getCurcount() {
        return curcount;
    }

    public void setCurcount(Object curcount) {
        this.curcount = curcount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<ConBean> getCon() {
        return con;
    }

    public void setCon(List<ConBean> con) {
        this.con = con;
    }

    public static class ConBean implements Serializable {
        /**
         * anchorId : 282
         * anchorImg : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F17%2F11%2Fjpg2016111711197.jpg
         * anchorName : radiofan
         * commentNum : 0
         * content : 今天开始
         * cover : null
         * id : 1
         * imgList : [{"id":0,"url":"http://cdn.linker.cc/anchorDynamic%2F201612%2F26%2F11%2Fjpeg2016122611296.jpeg"}]
         * linkUrl :
         * praiseCount : 0
         * title :
         * type : 0
         * isPraise : 0
         * createTime : 2016-12-26 11:47:58
         */

        private String anchorId;
        private String anchorImg;
        private String anchorName;
        private int commentNum;
        private String content;
        private Object cover;
        private String id;
        private String linkUrl;
        private int praiseCount;
        private String title;
        private String type;
        private int isPraise;
        private String createTime;
        private List<ImgListBean> imgList;

        public String getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(String anchorId) {
            this.anchorId = anchorId;
        }

        public String getAnchorImg() {
            return anchorImg;
        }

        public void setAnchorImg(String anchorImg) {
            this.anchorImg = anchorImg;
        }

        public String getAnchorName() {
            return anchorName;
        }

        public void setAnchorName(String anchorName) {
            this.anchorName = anchorName;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCover() {
            return cover;
        }

        public void setCover(Object cover) {
            this.cover = cover;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(int isPraise) {
            this.isPraise = isPraise;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public static class ImgListBean implements Serializable {
            /**
             * id : 0
             * url : http://cdn.linker.cc/anchorDynamic%2F201612%2F26%2F11%2Fjpeg2016122611296.jpeg
             */

            private int id;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
