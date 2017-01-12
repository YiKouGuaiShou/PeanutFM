package com.yikouguaishou.peanutfm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/12.
 */
public class TimeTranstor {
    public static String getTime(long longtime)
    {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(longtime);
        String realtime=format.format(date);
        return realtime;
    }
}
