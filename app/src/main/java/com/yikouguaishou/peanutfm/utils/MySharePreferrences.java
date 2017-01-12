package com.yikouguaishou.peanutfm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/12.
 */
public class MySharePreferrences {
    public static void setTime(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences("logintime",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        long time=System.currentTimeMillis();
        editor.putLong("lasttime",time);
        editor.commit();

    }

    public static long getLasttime(Context context){
        long lasttime=0;
        SharedPreferences sp=context.getSharedPreferences("logintime",Context.MODE_PRIVATE);
        if (sp!=null)
        {
            Log.e("TAG", "getLasttime: "+"在这里调用了的");
            lasttime=sp.getLong("lasttime",2);
            Log.e("TAG", "getLasttime: "+lasttime);
        }
        return lasttime;
    }

    public static void setLoadState(Context context,boolean tureorfalse){
        SharedPreferences sp=context.getSharedPreferences("logintime",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        long time=System.currentTimeMillis();
        if (tureorfalse=true)
        {
            editor.putBoolean("isload",true);
        }
        else {
            editor.putBoolean("isload",true);
        }
        editor.commit();
    }


    public static boolean getLoadState(Context context){
        boolean result=false;
        SharedPreferences sp=context.getSharedPreferences("logintime",Context.MODE_PRIVATE);
        if (sp!=null)
        {
            result=sp.getBoolean("isload",false);
        }
        return result;
    }


}
