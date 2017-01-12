package com.yikouguaishou.peanutfm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/12.
 */
public class MySharePreferrences {

    public static void setTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        long time = System.currentTimeMillis();
        editor.putLong("lasttime", time);
        editor.commit();

    }

    public static long getLasttime(Context context) {
        long lasttime = 0;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            Log.e("TAG", "getLasttime: " + "在这里调用了的");
            lasttime = sp.getLong("lasttime", 2);
            Log.e("TAG", "getLasttime: " + lasttime);
        }
        return lasttime;
    }

    public static void setLoadState(Context context, boolean tureorfalse) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (tureorfalse == true) {
            editor.putBoolean("isload", true);
        } else {
            editor.putBoolean("isload", false);
        }
        editor.commit();
    }


    public static boolean getLoadState(Context context) {
        boolean result = false;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getBoolean("isload", false);
        }
        return result;
    }


    public static void setUserID(Context context, String id) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userid", id);
        editor.commit();
    }


    public static String getUserID(Context context) {
        String result = null;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getString("userid", "0");
        }
        return result;

    }

    public static void setSignTime(Context context, long time) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("signTime", time);
        editor.commit();
    }

    public static long getSignTime(Context context) {
        long time = 0;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            time = sp.getLong("signTime", 0);
        }
        return time;
    }


    public static void setIconurl(Context context, String iconurl) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("iconurl", iconurl);
        editor.commit();
    }


    public static String getIconurl(Context context) {
        String result = null;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getString("iconurl", "0");
        }
        return result;

    }

    public static void setUsername(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", name);
        editor.commit();
    }


    public static String getUsername(Context context) {
        String result = null;
        SharedPreferences sp = context.getSharedPreferences("logintime", Context.MODE_PRIVATE);
        if (sp != null) {
            result = sp.getString("username", "0");
        }
        return result;
    }

}
