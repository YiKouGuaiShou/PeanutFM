package com.yikouguaishou.peanutfm.bean;

import java.io.Serializable;

/**
 * Created by snowflake on 2017/1/11.
 */
public class CollectBean implements Serializable {

    /**
     * rt : 1
     * des : succeed
     */

    private int rt;
    private String des;

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
}
