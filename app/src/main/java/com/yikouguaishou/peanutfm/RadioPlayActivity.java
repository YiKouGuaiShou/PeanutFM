package com.yikouguaishou.peanutfm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.RecommendHttpApiService;
import com.yikouguaishou.peanutfm.apiservice.SortApiService;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.bean.RadioPlayCommentBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioPlayCommentAdapter;
import com.yikouguaishou.peanutfm.service.MyService;
import com.yikouguaishou.peanutfm.utils.ShareUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RadioPlayActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView mBack;
    ImageView mShare;
    TextView mTitle;
    ImageView mBGPicture;
    LinearLayout mPlayList;
    TextView mCurrentTime;
    ImageButton mPlayLastOne;
    ImageButton mPlayOrPause;
    ImageView mHeart;
    TextView mTotleTime;
    SeekBar mSeekBar;
    List<ColumnListBean> columnListBeens = new ArrayList<>();
    private List<RadioPlayCommentBean.ConEntity> commentList = new ArrayList<>();
    private List<String> commentStrings = new ArrayList<>();
    boolean isPause = false;
    private int index;
    private int currentPage;
    private String mName;
    private ImageButton mPlayNextOne;
    private String baseUrl = "https://fsapp.linker.cc/";

    MyService myService = null;
    MyServiceConn myServiceConn;
    private String mDuration;
    private UltimateRecyclerView mRecyclerView;
    private RadioPlayCommentAdapter mComentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_play);
        receiveIntent(getIntent());
        findView();
        setLisener();
        initData();
        initCommentData();
    }

    private void initData() {
        String logoUrl = columnListBeens.get(0).getLogoUrl();
        Glide.with(this).load(logoUrl).into(mBGPicture);
        mBGPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_pause);
        mName = columnListBeens.get(currentPage).getCon().get(index - 1).getName();
        mDuration = columnListBeens.get(currentPage).getCon().get(index - 1).getDuration();
        mTitle.setText(mName);
        mTotleTime.setText(mDuration);

        Intent intentService = new Intent(RadioPlayActivity.this, MyService.class);
        sendIntent(intentService, "play");
        startService(intentService);

        myServiceConn = new MyServiceConn();
        Intent bindIntent = new Intent(RadioPlayActivity.this, MyService.class);
        bindService(bindIntent, myServiceConn, BIND_AUTO_CREATE);

    }

    private void receiveIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        index = extras.getInt("index", 1);
        currentPage = extras.getInt("currentPage", 0);
        List<ColumnListBean> columnListBeen = (List<ColumnListBean>) extras.getSerializable("columnListBeen");
        columnListBeens.clear();
        columnListBeens.addAll(columnListBeen);
    }

    private void setLisener() {
        mPlayOrPause.setOnClickListener(this);
        mPlayList.setOnClickListener(this);
        mPlayLastOne.setOnClickListener(this);
        mPlayNextOne.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(new MySeekBarChanageListener());
        mShare.setOnClickListener(this);
    }

    private void findView() {
        mBack = (ImageView) findViewById(R.id.mIv_play_back);
        mShare = (ImageView) findViewById(R.id.mIv_play_share);
        mTitle = (TextView) findViewById(R.id.mTV_play_title);
        mBGPicture = (ImageView) findViewById(R.id.play_bgPicture);
        mPlayList = (LinearLayout) findViewById(R.id.mIv_play_list);
        mCurrentTime = (TextView) findViewById(R.id.play_currentTime);
        mPlayLastOne = (ImageButton) findViewById(R.id.play_lastone);
        mPlayNextOne = (ImageButton) findViewById(R.id.play_nextone);
        mPlayOrPause = (ImageButton) findViewById(R.id.play_playOrpause);
        mHeart = (ImageView) findViewById(R.id.mIv_play_heart);
        mTotleTime = (TextView) findViewById(R.id.play_totleTime);
        mSeekBar = (SeekBar) findViewById(R.id.play_seekBar);
        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.radioplay_comment_recycler);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mComentAdapter = new RadioPlayCommentAdapter(this);
        mComentAdapter.setCommentList(commentList, commentStrings);
        mRecyclerView.setAdapter(mComentAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent mIntent = new Intent(this, MyService.class);
        switch (view.getId()) {
            //控制暂停还是播放的点击事件。
            case R.id.play_playOrpause:
                setPauseOrPlayBG();
                break;
            //播放列表的点击事件。
            case R.id.mIv_play_list:
                Intent intent = new Intent(this, RadioPlayListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("currentPage", currentPage);
                bundle.putInt("index", index);
                bundle.putSerializable("columnListBeen", (Serializable) columnListBeens);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            //上一首。
            case R.id.play_lastone:
                checkIndex("down");
                sendIntent(mIntent, "play");
                isPause = false;
                startService(mIntent);
                initData();
                initCommentData();
                break;
            //下一首。
            case R.id.play_nextone:
                checkIndex("up");
                sendIntent(mIntent, "play");
                isPause = false;
                startService(mIntent);
                initData();
                initCommentData();
                break;
            case R.id.mIv_play_back:
                Intent CoumnIntent = new Intent(this, ColumnListActivity.class);
                CoumnIntent.putExtra("index", index);
                CoumnIntent.putExtra("page", currentPage);
                setResult(3, CoumnIntent);
                finish();
                break;
            case R.id.mIv_play_share:
                ShareUtils.showShare(this);
                break;
        }
    }

    //改变 the PauseOrPlay 背景
    private void setPauseOrPlayBG() {
        Intent serviceIntent = new Intent(this, MyService.class);
        if (isPause) {
            //点击从暂停到播放状态。
            mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_pause);
            isPause = false;
            sendIntent(serviceIntent, "play");
            startService(serviceIntent);
        } else {
            //点击从播放到暂停状态。
            mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_play);
            isPause = true;
            sendIntent(serviceIntent, "pause");
            startService(serviceIntent);
        }
    }

    private void checkIndex(String upOrdown) {
        int maxConSize = columnListBeens.get(currentPage).getCon().size();
        int maxPageSize = columnListBeens.size();
        if (upOrdown.equals("up")) {
            index++;
            if (index > maxConSize) {
                index = 1;
                currentPage++;
                if (currentPage > maxPageSize - 1) {
                    currentPage = 0;
                }
            }
        }
        if (upOrdown.equals("down")) {
            index--;
            if (index < 1) {
                currentPage--;
                if (currentPage < 0) {
                    currentPage = 0;
                    index = maxConSize;
                } else {
                    index = 20;
                }
            }
        }
        Log.e("==upOrdown==", "currentPage:index" + currentPage + ":" + index);
    }

    public void sendIntent(Intent intent, String contron) {
        Bundle bundle = new Bundle();
        bundle.putString("control", contron);
        bundle.putInt("currentPage", currentPage);
        bundle.putInt("index", index);
        bundle.putSerializable("columnListBeen", (Serializable) columnListBeens);
        intent.putExtras(bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            receiveIntent(data);
            initData();
            initCommentData();
        }
    }


    public interface CallMediaChangeCallBack {
        public void changeNow(int psotion);

        public void pauseNow();

        public void startNow();
    }

    CallMediaChangeCallBack callMediaChangeCallBack = null;

    /**
     * 进度条的监听
     */
    private class MySeekBarChanageListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mSeekBar.setProgress(progress);
                //下面还有一个动作:让播放的位置发生改变
                callMediaChangeCallBack.changeNow(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            callMediaChangeCallBack.pauseNow();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            callMediaChangeCallBack.startNow();
        }
    }

    /**
     * 连接那个服务
     */
    private class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            myService = ((MyService.MyBinder) binder).getService();
            //通知进度条更新。
            myService.setProgressListener(new ProgresListener());
            //通知position改变
            myService.setOnPositionChangeListener(new OnPositionChange());
            //设置activity 和 service关联。
            if (myService instanceof CallMediaChangeCallBack) {
                callMediaChangeCallBack = myService;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }

    public void AsyncStartService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Intent intentService = new Intent(RadioPlayActivity.this, MyService.class);
                    sendIntent(intentService, "play");
                    startService(intentService);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 实现那个进度条监听
     */
    private class ProgresListener implements MyService.MyCallbackSeekBar {
        @Override
        public void refresh(int currentPosition, int maxPosition) {
            mSeekBar.setMax(maxPosition);
            mSeekBar.setProgress(currentPosition);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            date.setTime(currentPosition);
            String str = sdf.format(date);
            mCurrentTime.setText(str);
        }
    }

    //实现位置改变的监听
    private class OnPositionChange implements MyService.MyOnComppletion {

        @Override
        public void UpdeteIndex(int currentPage1, int playIndex) {
            currentPage = currentPage1;
            index = playIndex;
            initData();
            initCommentData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent CoumnIntent = new Intent(this, ColumnListActivity.class);
            CoumnIntent.putExtra("index", index);
            CoumnIntent.putExtra("page", currentPage);
            setResult(3, CoumnIntent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initCommentData() {
        commentStrings.clear();
        ColumnListBean columnListBean = columnListBeens.get(currentPage);
        commentStrings.add(columnListBean.getLogoUrl());
        commentStrings.add(columnListBean.getColumnName());
        commentStrings.add(columnListBean.getAnchorpersons());
        commentStrings.add("0");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService apiService = retrofit.create(RecommendHttpApiService.class);

        Observable<RadioPlayCommentBean> radioPlayCommentBean = apiService.getRadioPlayCommentBean(columnListBeens.get(currentPage).getCon().get(index - 1).getId(), 3);
        radioPlayCommentBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RadioPlayCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===RadioPlayCommentBean===" + e.getMessage());
                        mComentAdapter.setCommentList(new ArrayList<RadioPlayCommentBean.ConEntity>(), commentStrings);
                    }

                    @Override
                    public void onNext(RadioPlayCommentBean radioPlayCommentBean) {
                        Log.e("=radioPlayCommentBean=", "correlateId = " + radioPlayCommentBean.getCon().get(0).getCorrelateId());
                        if (radioPlayCommentBean != null) {
                            List<RadioPlayCommentBean.ConEntity> con = radioPlayCommentBean.getCon();
                            int size = con.size();
                            Log.e("= con.size()=", " con.size() =  " + size + "ww");
                            commentStrings.remove(3);
                            commentStrings.add(size + "");
                            mComentAdapter.setCommentList(con, commentStrings);
                            // mComentAdapter.notifyItemRemoved();
                        } else {
                            Log.e("= con.size()=", " con.size() =  " + "空");
                            mComentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    //绑定模式启动Service一定要在Activity销毁的时候解除绑定。
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(myServiceConn);
    }
}
