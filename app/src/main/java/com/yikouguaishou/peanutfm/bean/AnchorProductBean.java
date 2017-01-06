package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowflake on 2017/1/5.
 */
public class AnchorProductBean implements Serializable {


    /**
     * con : [{"columnCover":"http://cdn.linker.cc/FSYT%2F201611%2F01%2F16%2Fpng2016110116677.png","columnId":"1126","columnName":"泛思哲音乐工程","songName":"爱情唯二最后嘅结果，分手or结婚--合久必婚","providerCode":"13010"},{"columnCover":"http://cdn.linker.cc/LTYT%2F201609%2F27%2F13%2Fpng2016092713809.png","columnId":"1155","columnName":"阿詹的异空间","songName":"湘西志异\u2014\u2014赶尸人","providerCode":"13010"},{"columnCover":"http://cdn.linker.cc/FSYT%2F201611%2F11%2F14%2Fjpg2016111114541.jpg","columnId":"14788473845850","columnName":"粤影留声","songName":"粤影留声01--你经常哼唱的这些粤语歌，其实奥秘多多","providerCode":"13010"},{"columnCover":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F16%2F10%2Fpng2016111610358.png","columnId":"14788473845854","columnName":"新事听","songName":"新事听20170105","providerCode":"13010"},{"columnCover":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F16%2F10%2Fjpg2016111610342.jpg","columnId":"14788473845855","columnName":"ACG动漫达人","songName":"动漫达人20170101","providerCode":"13010"}]
     * curcount : null
     * total : 0
     * rt : 1
     * des : 获取主播作品成功
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
         * columnCover : http://cdn.linker.cc/FSYT%2F201611%2F01%2F16%2Fpng2016110116677.png
         * columnId : 1126
         * columnName : 泛思哲音乐工程
         * songName : 爱情唯二最后嘅结果，分手or结婚--合久必婚
         * providerCode : 13010
         */

        private String columnCover;
        private String columnId;
        private String columnName;
        private String songName;
        private String providerCode;

        public String getColumnCover() {
            return columnCover;
        }

        public void setColumnCover(String columnCover) {
            this.columnCover = columnCover;
        }

        public String getColumnId() {
            return columnId;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getProviderCode() {
            return providerCode;
        }

        public void setProviderCode(String providerCode) {
            this.providerCode = providerCode;
        }
    }
}
