package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class TurnOneItemBean implements Serializable{

    /**
     * total : 0
     * con : [{"browseVolume":218,"startDate":"2016-11-22 00:00:00","createTime":"2016-12-22 15:39:14","conent":"http://218.104.174.102/votingSystem/index3.html?id=2","endDate":"2017-12-01 23:59:59","providerCode":null,"sponsor":"俊情一点","commentCount":1,"type":1,"id":84,"title":"俊情K歌排行榜","cover":"http://cdn.linker.cc/FSYT%2F201612%2F22%2F15%2Fjpg2016122215827.jpg","isPartake":"1"},{"browseVolume":681,"startDate":"2016-11-01 00:00:00","createTime":"2016-11-15 17:14:43","conent":"http://yydj2016.kuaizhan.com/","endDate":"2016-11-21 23:59:59","providerCode":null,"sponsor":"花生FM","commentCount":0,"type":1,"id":80,"title":"粤语主播大赛专题","cover":"http://cdn.linker.cc/FSYT%2F201611%2F09%2F16%2Fjpg2016110916720.jpg","isPartake":"0"},{"browseVolume":666,"startDate":"2016-11-01 00:00:00","createTime":"2016-11-30 16:53:05","conent":"https://shop3475947.koudaitong.com/v2/showcase/homepage?alias=gocd7omc&sf=wx_sm","endDate":"2016-11-30 23:59:59","providerCode":null,"sponsor":"花生FM","commentCount":0,"type":1,"id":79,"title":"推广","cover":"http://cdn.linker.cc/FSYT%2F201611%2F03%2F13%2Fjpg2016110313301.jpg","isPartake":"1"}]
     * des : succeed
     * rt : 1
     * curcount : null
     */
    private int total;
    private List<ConEntity> con;
    private String des;
    private int rt;
    private String curcount;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCon(List<ConEntity> con) {
        this.con = con;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public void setCurcount(String curcount) {
        this.curcount = curcount;
    }

    public int getTotal() {
        return total;
    }

    public List<ConEntity> getCon() {
        return con;
    }

    public String getDes() {
        return des;
    }

    public int getRt() {
        return rt;
    }

    public String getCurcount() {
        return curcount;
    }

    public class ConEntity implements Serializable{
        /**
         * browseVolume : 218
         * startDate : 2016-11-22 00:00:00
         * createTime : 2016-12-22 15:39:14
         * conent : http://218.104.174.102/votingSystem/index3.html?id=2
         * endDate : 2017-12-01 23:59:59
         * providerCode : null
         * sponsor : 俊情一点
         * commentCount : 1
         * type : 1
         * id : 84
         * title : 俊情K歌排行榜
         * cover : http://cdn.linker.cc/FSYT%2F201612%2F22%2F15%2Fjpg2016122215827.jpg
         * isPartake : 1
         */
        private int browseVolume;
        private String startDate;
        private String createTime;
        private String conent;
        private String endDate;
        private String providerCode;
        private String sponsor;
        private int commentCount;
        private int type;
        private int id;
        private String title;
        private String cover;
        private String isPartake;

        public void setBrowseVolume(int browseVolume) {
            this.browseVolume = browseVolume;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setConent(String conent) {
            this.conent = conent;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public void setProviderCode(String providerCode) {
            this.providerCode = providerCode;
        }

        public void setSponsor(String sponsor) {
            this.sponsor = sponsor;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setIsPartake(String isPartake) {
            this.isPartake = isPartake;
        }

        public int getBrowseVolume() {
            return browseVolume;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getConent() {
            return conent;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getProviderCode() {
            return providerCode;
        }

        public String getSponsor() {
            return sponsor;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getCover() {
            return cover;
        }

        public String getIsPartake() {
            return isPartake;
        }
    }
}
