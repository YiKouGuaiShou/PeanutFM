package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;

/**
 * Created by snowflake on 2017/1/5.
 */
public class AnchorPersonInfoBean implements Serializable {

    /**
     * entity : {"id":null,"anchorpersonId":"219","anchorpersonName":"Frankie范祺","anchorpersonPic":"http://files.fsdt.com.cn/2016/01/14/899430.jpg","bgUrl":null,"fansNickName":null,"fansNum":6,"isFollow":0,"levelIcon":null,"ifHasLive":1,"broadcastModel":"3","broadcastName":"FM98.5","broadcastId":"41"}
     * rt : 1
     * des : succeed
     */

    private EntityBean entity;
    private int rt;
    private String des;

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
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

    public static class EntityBean implements Serializable {
        /**
         * id : null
         * anchorpersonId : 219
         * anchorpersonName : Frankie范祺
         * anchorpersonPic : http://files.fsdt.com.cn/2016/01/14/899430.jpg
         * bgUrl : null
         * fansNickName : null
         * fansNum : 6
         * isFollow : 0
         * levelIcon : null
         * ifHasLive : 1
         * broadcastModel : 3
         * broadcastName : FM98.5
         * broadcastId : 41
         */

        private Object id;
        private String anchorpersonId;
        private String anchorpersonName;
        private String anchorpersonPic;
        private Object bgUrl;
        private Object fansNickName;
        private int fansNum;
        private int isFollow;
        private Object levelIcon;
        private int ifHasLive;
        private String broadcastModel;
        private String broadcastName;
        private String broadcastId;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getAnchorpersonId() {
            return anchorpersonId;
        }

        public void setAnchorpersonId(String anchorpersonId) {
            this.anchorpersonId = anchorpersonId;
        }

        public String getAnchorpersonName() {
            return anchorpersonName;
        }

        public void setAnchorpersonName(String anchorpersonName) {
            this.anchorpersonName = anchorpersonName;
        }

        public String getAnchorpersonPic() {
            return anchorpersonPic;
        }

        public void setAnchorpersonPic(String anchorpersonPic) {
            this.anchorpersonPic = anchorpersonPic;
        }

        public Object getBgUrl() {
            return bgUrl;
        }

        public void setBgUrl(Object bgUrl) {
            this.bgUrl = bgUrl;
        }

        public Object getFansNickName() {
            return fansNickName;
        }

        public void setFansNickName(Object fansNickName) {
            this.fansNickName = fansNickName;
        }

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public Object getLevelIcon() {
            return levelIcon;
        }

        public void setLevelIcon(Object levelIcon) {
            this.levelIcon = levelIcon;
        }

        public int getIfHasLive() {
            return ifHasLive;
        }

        public void setIfHasLive(int ifHasLive) {
            this.ifHasLive = ifHasLive;
        }

        public String getBroadcastModel() {
            return broadcastModel;
        }

        public void setBroadcastModel(String broadcastModel) {
            this.broadcastModel = broadcastModel;
        }

        public String getBroadcastName() {
            return broadcastName;
        }

        public void setBroadcastName(String broadcastName) {
            this.broadcastName = broadcastName;
        }

        public String getBroadcastId() {
            return broadcastId;
        }

        public void setBroadcastId(String broadcastId) {
            this.broadcastId = broadcastId;
        }
    }
}
