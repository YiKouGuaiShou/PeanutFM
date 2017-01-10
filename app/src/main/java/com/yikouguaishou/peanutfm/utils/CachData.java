package com.yikouguaishou.peanutfm.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2017/1/9.
 */
public class CachData {
    Context context;

    public static String getTotalSize(Context context){
        long totalSize=getFolderSize(context.getCacheDir());


        String finalSize=getFormatSize(totalSize);
        if (finalSize.length()>7)
        {
            finalSize=finalSize.substring(0,3)+(Integer.valueOf(""+finalSize.charAt(3))+1)
                    +finalSize.substring(finalSize.length()-2,finalSize.length());
        }

        return finalSize;
    }





    public static long getFolderSize(File file)
    {
        long size=0;

        File[] files=file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
            {
                size=size+getFolderSize(files[i]);
            }
            else {
                size=size+files[i].length();
            }
        }


        return size;
    }

    public static String getFormatSize(double size)
    {
        double kb=size/1024;
        if (kb<1) {

            return size+"b";
        }

        double mb=kb/1024;
        if (kb<1)
        {
            return kb+"kb";
        }

        double gb=mb/1024;
        if (gb<1)
        {

            return mb+"mb";
        }






        return null;
    }




    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
