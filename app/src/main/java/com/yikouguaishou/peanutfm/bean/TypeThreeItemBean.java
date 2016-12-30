package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TypeThreeItemBean implements Serializable{

    /**
     * con : [{"id":1599,"placeBottom":null,"logo":"http://cdn.linker.cc/LTYT%2F201609%2F27%2F14%2Fjpg2016092714507.jpg","albumName":"东家唔打打东家","albumId":1091,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1693,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F01%2F16%2Fpng2016110116970.png","albumName":"一周脱口秀","albumId":1089,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1694,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F01%2F16%2Fpng2016110116329.png","albumName":"胶谈","albumId":1093,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1698,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F14%2Fpng2016110214593.png","albumName":"优之文品","albumId":1094,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1712,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F14%2Fpng2016110214943.png","albumName":"超锋粤语体育故事","albumId":1092,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1826,"placeBottom":null,"logo":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F16%2F10%2Fpng2016111610358.png","albumName":"新事听","albumId":14788473845854,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1850,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F15%2FJPG2016110215257.JPG","albumName":"讲东讲西讲东西","albumId":1095,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null},{"id":1866,"placeBottom":null,"logo":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F14%2Fpng2016110214243.png","albumName":"马力全开","albumId":1090,"providerName":"本台","atypeId":108,"providerId":13010,"placeTop":null,"sortNum":null}]
     * des : succeed
     * count : 8
     * rt : 1
     * perPage : 30
     * currentPage : 0
     * totalPage : 1
     */
    private List<ConEntity> con;
    private String des;
    private int count;
    private int rt;
    private int perPage;
    private int currentPage;
    private int totalPage;

    public void setCon(List<ConEntity> con) {
        this.con = con;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ConEntity> getCon() {
        return con;
    }

    public String getDes() {
        return des;
    }

    public int getCount() {
        return count;
    }

    public int getRt() {
        return rt;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public class ConEntity implements Serializable{
        /**
         * id : 1599
         * placeBottom : null
         * logo : http://cdn.linker.cc/LTYT%2F201609%2F27%2F14%2Fjpg2016092714507.jpg
         * albumName : 东家唔打打东家
         * albumId : 1091
         * providerName : 本台
         * atypeId : 108
         * providerId : 13010
         * placeTop : null
         * sortNum : null
         */
        private int id;
        private String placeBottom;
        private String logo;
        private String albumName;
        private String albumId;
        private String providerName;
        private int atypeId;
        private int providerId;
        private String placeTop;
        private String sortNum;

        public void setId(int id) {
            this.id = id;
        }

        public void setPlaceBottom(String placeBottom) {
            this.placeBottom = placeBottom;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setAlbumName(String albumName) {
            this.albumName = albumName;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public void setAtypeId(int atypeId) {
            this.atypeId = atypeId;
        }

        public void setProviderId(int providerId) {
            this.providerId = providerId;
        }

        public void setPlaceTop(String placeTop) {
            this.placeTop = placeTop;
        }

        public void setSortNum(String sortNum) {
            this.sortNum = sortNum;
        }

        public int getId() {
            return id;
        }

        public String getPlaceBottom() {
            return placeBottom;
        }

        public String getLogo() {
            return logo;
        }

        public String getAlbumName() {
            return albumName;
        }

        public String getAlbumId() {
            return albumId;
        }

        public String getProviderName() {
            return providerName;
        }

        public int getAtypeId() {
            return atypeId;
        }

        public int getProviderId() {
            return providerId;
        }

        public String getPlaceTop() {
            return placeTop;
        }

        public String getSortNum() {
            return sortNum;
        }
    }
}
