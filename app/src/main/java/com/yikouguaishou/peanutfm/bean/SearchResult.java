package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowflake on 2016/12/27.
 */
public class SearchResult implements Serializable{

    /**
     * totalPage : 1
     * count : 162
     * currentPage : 0
     * perPage : 1000
     * con : [{"id":"1120","parentId":"180","name":"盏鬼佛山话","playUrl":"","type":3,"logoUrl":"http://cdn.linker.cc/LTYT%2F201609%2F27%2F13%2Fpng2016092713852.png","providerCode":13010,"providerName":"本台","artist":""}]
     * rt : 1
     * des : succeed
     */

    private int totalPage;
    private int count;
    private int currentPage;
    private int perPage;
    private int rt;
    private String des;
    private List<ConBean> con;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
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

    public static class ConBean implements Serializable{
        /**
         * id : 1120
         * parentId : 180
         * name : 盏鬼佛山话
         * playUrl :
         * type : 3
         * logoUrl : http://cdn.linker.cc/LTYT%2F201609%2F27%2F13%2Fpng2016092713852.png
         * providerCode : 13010
         * providerName : 本台
         * artist :
         */

        private String id;
        private String parentId;
        private String name;
        private String playUrl;
        private int type;
        private String logoUrl;
        private int providerCode;
        private String providerName;
        private String artist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public int getProviderCode() {
            return providerCode;
        }

        public void setProviderCode(int providerCode) {
            this.providerCode = providerCode;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }
    }
}
