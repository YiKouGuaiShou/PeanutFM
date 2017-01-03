package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snowflake on 2016/12/29.
 */
public class SortBean implements Serializable{

    /**
     * con : [{"id":10,"name":"历史志异","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515101.png"},{"id":3,"name":"武侠","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng201611151569.png"},{"id":5,"name":"广播剧","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F14%2Fpng2016111514532.png"},{"id":4,"name":"影视音乐","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F14%2Fpng201611151451.png"},{"id":6,"name":"脱口秀","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515834.png"},{"id":12,"name":"情感","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515564.png"},{"id":7,"name":"财经","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515825.png"},{"id":9,"name":"惊悚故事","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515609.png"},{"id":8,"name":"花生主播","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515770.png"},{"id":11,"name":"亲子","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515726.png"},{"id":13,"name":"生活资讯","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng201611151555.png"},{"id":14,"name":"综艺","logo":"http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515157.png"},{"id":15,"name":"粤语歌单","logo":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F16%2F11%2Fpng2016111611281.png"}]
     * curcount : null
     * total : 0
     * rt : 1
     * des : 2
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

    public static class ConBean implements Serializable{
        /**
         * id : 10
         * name : 历史志异
         * logo : http://cdn.linker.cc/FSYT%2F201611%2F15%2F15%2Fpng2016111515101.png
         */

        private int id;
        private String name;
        private String logo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
