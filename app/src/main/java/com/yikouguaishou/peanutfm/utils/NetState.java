package com.yikouguaishou.peanutfm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/1/9.
 */
public class NetState {


    public static String getState(Context context)
    {
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info=manager.getActiveNetworkInfo();

        String netState=null;

        if (info.getTypeName().equals("WIFI"))
        {

            return "WIFI";
        }

        // getSubtypeName得到移动数据网络断类别

        else if (info.getTypeName().equals("MOBILE")) {
            String telState = info.getSubtypeName();

            return telState.contains("LTE")?"4G":"3G";

        }
        return "no connected!";


    }
}
