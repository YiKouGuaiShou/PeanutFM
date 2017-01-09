package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowflake on 2017/1/9.
 */
public class DynamicCommentListBean implements Serializable {

    /**
     * con : [{"id":1,"commentId":null,"correlateId":null,"type":null,"supplierId":null,"songName":null,"content":"这是啥","praiseCount":0,"status":0,"discussantId":"72539","discussantIcon":"http://cdn.linker.cc/sc_upload%2F201612%2F26%2F20%2Fjpg2016122620791.jpg","discussantName":"snowflake","replyUserId":null,"replyUserName":null,"replyCommentId":-1,"createTime":"2017-01-09 16:13:14","isPraise":"0","isPresenter":"0","contentType":null,"detailsCount":0,"details":null,"title":null,"imgList":null,"voiceUrl":null,"voiceContent":null,"voiceDuration":null,"resourceType":0,"isTop":0}]
     * curcount : null
     * total : 0
     * rt : 1
     * des : 1
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
         * id : 1
         * commentId : null
         * correlateId : null
         * type : null
         * supplierId : null
         * songName : null
         * content : 这是啥
         * praiseCount : 0
         * status : 0
         * discussantId : 72539
         * discussantIcon : http://cdn.linker.cc/sc_upload%2F201612%2F26%2F20%2Fjpg2016122620791.jpg
         * discussantName : snowflake
         * replyUserId : null
         * replyUserName : null
         * replyCommentId : -1
         * createTime : 2017-01-09 16:13:14
         * isPraise : 0
         * isPresenter : 0
         * contentType : null
         * detailsCount : 0
         * details : null
         * title : null
         * imgList : null
         * voiceUrl : null
         * voiceContent : null
         * voiceDuration : null
         * resourceType : 0
         * isTop : 0
         */

        private int id;
        private Object commentId;
        private Object correlateId;
        private Object type;
        private Object supplierId;
        private Object songName;
        private String content;
        private int praiseCount;
        private int status;
        private String discussantId;
        private String discussantIcon;
        private String discussantName;
        private Object replyUserId;
        private Object replyUserName;
        private int replyCommentId;
        private String createTime;
        private String isPraise;
        private String isPresenter;
        private Object contentType;
        private int detailsCount;
        private Object details;
        private Object title;
        private Object imgList;
        private Object voiceUrl;
        private Object voiceContent;
        private Object voiceDuration;
        private int resourceType;
        private int isTop;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCommentId() {
            return commentId;
        }

        public void setCommentId(Object commentId) {
            this.commentId = commentId;
        }

        public Object getCorrelateId() {
            return correlateId;
        }

        public void setCorrelateId(Object correlateId) {
            this.correlateId = correlateId;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Object supplierId) {
            this.supplierId = supplierId;
        }

        public Object getSongName() {
            return songName;
        }

        public void setSongName(Object songName) {
            this.songName = songName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPraiseCount() {
            return praiseCount;
        }

        public void setPraiseCount(int praiseCount) {
            this.praiseCount = praiseCount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDiscussantId() {
            return discussantId;
        }

        public void setDiscussantId(String discussantId) {
            this.discussantId = discussantId;
        }

        public String getDiscussantIcon() {
            return discussantIcon;
        }

        public void setDiscussantIcon(String discussantIcon) {
            this.discussantIcon = discussantIcon;
        }

        public String getDiscussantName() {
            return discussantName;
        }

        public void setDiscussantName(String discussantName) {
            this.discussantName = discussantName;
        }

        public Object getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(Object replyUserId) {
            this.replyUserId = replyUserId;
        }

        public Object getReplyUserName() {
            return replyUserName;
        }

        public void setReplyUserName(Object replyUserName) {
            this.replyUserName = replyUserName;
        }

        public int getReplyCommentId() {
            return replyCommentId;
        }

        public void setReplyCommentId(int replyCommentId) {
            this.replyCommentId = replyCommentId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(String isPraise) {
            this.isPraise = isPraise;
        }

        public String getIsPresenter() {
            return isPresenter;
        }

        public void setIsPresenter(String isPresenter) {
            this.isPresenter = isPresenter;
        }

        public Object getContentType() {
            return contentType;
        }

        public void setContentType(Object contentType) {
            this.contentType = contentType;
        }

        public int getDetailsCount() {
            return detailsCount;
        }

        public void setDetailsCount(int detailsCount) {
            this.detailsCount = detailsCount;
        }

        public Object getDetails() {
            return details;
        }

        public void setDetails(Object details) {
            this.details = details;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getImgList() {
            return imgList;
        }

        public void setImgList(Object imgList) {
            this.imgList = imgList;
        }

        public Object getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(Object voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public Object getVoiceContent() {
            return voiceContent;
        }

        public void setVoiceContent(Object voiceContent) {
            this.voiceContent = voiceContent;
        }

        public Object getVoiceDuration() {
            return voiceDuration;
        }

        public void setVoiceDuration(Object voiceDuration) {
            this.voiceDuration = voiceDuration;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }
    }
}
