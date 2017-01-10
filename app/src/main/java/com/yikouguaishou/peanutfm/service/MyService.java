package com.yikouguaishou.peanutfm.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.yikouguaishou.peanutfm.RadioPlayActivity;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现音乐的播放
 */
public class MyService extends Service implements RadioPlayActivity.CallMediaChangeCallBack {
    Binder mBinder = new MyBinder();
    public MediaPlayer mediaPlayer = null;
    private int playIndex = 0;   //播放的index
    private int currentPage = 0; //播放的页数
    private boolean pauseOrNot = false; //是不是由暂停而来
    private boolean playOrNot = false;
    private boolean recycleTag = true;
    private List<ColumnListBean> radios = new ArrayList<>();  //存放所有歌曲

    /**
     * 那个绑定者
     */
    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void changeNow(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    @Override
    public void pauseNow() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void startNow() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    //通知进度条更新。
    MyCallbackSeekBar myCallbackSeekBar = null;

    public interface MyCallbackSeekBar {
        public void refresh(int currentPosition, int maxPosition);
    }

    public void setProgressListener(MyCallbackSeekBar myCallbackSeekBar) {
        this.myCallbackSeekBar = myCallbackSeekBar;
    }

    //下一首时，通知activity psition 改变。
    private MyOnComppletion myOnComppletion = null;

    public void setOnPositionChangeListener(MyOnComppletion myOnComppletion) {
        this.myOnComppletion = myOnComppletion;
    }

    public interface MyOnComppletion {
        public void UpdeteIndex(int currentPage, int playIndex);
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
        int wantIndex = extras.getInt("index", -1);
        String control = extras.getString("control");
        int wantPage = extras.getInt("currentPage", -1);
        List<ColumnListBean> columnListBeen = (List<ColumnListBean>) extras.getSerializable("columnListBeen");
        radios.clear();
        radios.addAll(columnListBeen);
        Log.e("==MyService==", "currentPage = " + this.currentPage);
        Log.e("==MyService==", "index = " + playIndex);
        Log.e("==MyService==", "radios.size() = " + radios.size());
        Log.e("==MyService==", "control = " + control);
        if (control.equals("play")) {   //这个表示的是传输的命令是播放
            if (wantIndex != playIndex || wantPage != currentPage) {   //如果这两个值不一样的话说明播放的不是同一首歌了...
                pauseOrNot = false;
                playOrNot = false;
                initPalyer();
            }
            if (wantIndex != -1 && wantPage != -1) {   //如果等于-1的话那么认为是非法的
                playIndex = wantIndex;
                currentPage = wantPage;
                play();
            }
            Log.e("---onStartCommand---", this.currentPage + ":" + playIndex);
        } else if (control.equals("pause")) {  //暂停
            pause();

        } else if (control.equals("stop")) {   //停止
            stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 停止
     */
    private void stop() {
        if (null != mediaPlayer && mediaPlayer.isPlaying()) {
            initPalyer();
            pauseOrNot = false;
            playOrNot = false;
            recycleTag = false;
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
     * 播放
     */
    private void play() {
        if (!pauseOrNot && !playOrNot) {
            //说明是第一次进来
            try {
                String playPath = radios.get(currentPage).getCon().get(playIndex - 1).getPlayUrl();
//                mediaPlayer = MediaPlayer.create(MyService.this, Uri.parse(playPath));
//                Log.e("===play===", "currentPage:playIndex" + currentPage + ":" + playIndex);
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.start();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(this, Uri.parse(playPath));
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MyOncompletionListener());
                    }
                });
                mediaPlayer.prepareAsync();
                playOrNot = true;
                recycleTag = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (recycleTag) {
                            try {
                                Thread.sleep(1000);
                                if (mediaPlayer != null && myCallbackSeekBar != null) {
                                    myCallbackSeekBar.refresh(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                                }
                            } catch (InterruptedException e) {

                            } catch (Exception e) {

                            }
                        }
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (pauseOrNot && playOrNot) {
            mediaPlayer.start();
        }
    }


    /**
     * 还原MediaPlayer
     */
    private void initPalyer() {
        try {
            if (null != mediaPlayer) {
                mediaPlayer.release();    //释放资源
                mediaPlayer = null;
            }
        } catch (Exception err) {
            mediaPlayer = null;
        }
    }

    private class MyOncompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("--------", "-----------------");
            int maxConSize = radios.get(currentPage).getCon().size();
            int maxPageSize = radios.size();
            playIndex++;
            if (playIndex > maxConSize) {
                playIndex = 1;
                currentPage++;
                if (currentPage > maxPageSize - 1) {
                    currentPage = 0;
                }
            }
            if (myOnComppletion != null) {
                myOnComppletion.UpdeteIndex(currentPage, playIndex);
            }
            initPalyer();
            pauseOrNot = false;
            playOrNot = false;
            play();
        }
    }
}
