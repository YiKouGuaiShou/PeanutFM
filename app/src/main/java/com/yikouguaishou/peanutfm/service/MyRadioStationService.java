package com.yikouguaishou.peanutfm.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.yikouguaishou.peanutfm.PlayRadioActivity;

import java.io.IOException;

public class MyRadioStationService extends Service {
    Binder mBinder = new MyBinder();
    public MediaPlayer mediaPlayer = null;
    private String playUrl;   //播放的地址
    private boolean pauseOrNot = false; //是不是由暂停而来
    private boolean playOrNot = false;

    public MyRadioStationService() {
    }

//    @Override
//    public void pauseNow() {
//        mediaPlayer.pause();
//    }
//
//    @Override
//    public void startNow() {
//        mediaPlayer.start();
//    }

    /**
     * 那个绑定者
     */
    public class MyBinder extends Binder {
        public MyRadioStationService getService() {
            return MyRadioStationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * 处理业务逻辑
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        String control = extras.getString("control");
        playUrl = extras.getString("playUrl");
        if (control.equals("play")) {   //这个表示的是传输的命令是播放
            initPlayer();
            play();
        } else if (control.equals("pause")) {  //暂停
            pause();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放
     */
    private void play() {
        if (!pauseOrNot && !playOrNot) { //说明是第一次进来
            if (mediaPlayer != null) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(playUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    playOrNot = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (pauseOrNot && playOrNot) {
                mediaPlayer.start();
            }
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        if (null != mediaPlayer && mediaPlayer.isPlaying()) {  //这种情况才能暂停
            mediaPlayer.pause();
            pauseOrNot = true;
        }
    }

    /**
     * 还原MediaPlayer
     */
    private void initPlayer() {
        try {
            if (null != mediaPlayer) {
                mediaPlayer.release();    //释放资源
                mediaPlayer = null;
            }
        } catch (Exception err) {
            mediaPlayer = null;
        }
    }
}
