package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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
    boolean isPause = true;
    private int index;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_play);
        receiveIntent();
        findView();
        setLisener();
        initData();
    }

    private void initData() {
        String logoUrl = columnListBeens.get(0).getLogoUrl();
        mBGPicture.setScaleType(ImageView.ScaleType.FIT_XY);
        mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_play);
        Glide.with(this).load(logoUrl).into(mBGPicture);
    }

    private void receiveIntent() {
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("index", 1);
        currentPage = extras.getInt("currentPage", 0);
        List<ColumnListBean> columnListBeen = (List<ColumnListBean>) extras.getSerializable("columnListBeen");
        columnListBeens.addAll(columnListBeen);

//        ColumnListBean columnListBean = columnListBeens.get(1);
//        String columnName = columnListBean.getColumnName();
//        int size = columnListBean.getCon().size();
//        ColumnListBean.ConBean conBean = columnListBean.getCon().get(0);
//        String playUrl = conBean.getPlayUrl();
//        String duration = conBean.getDuration();
//        String name = conBean.getName();
//        Log.e("======", "name=" + name);
//        Log.e("======", "size=" + size);
//        Log.e("======", "playUrl=" + playUrl);
//        Log.e("======", "duration=" + duration);
//        String logoUrl = conBean.getLogoUrl();
    }

    private void setLisener() {
        mPlayOrPause.setOnClickListener(this);
        mPlayList.setOnClickListener(this);
    }

    private void findView() {
        mBack = (ImageView) findViewById(R.id.mIv_play_back);
        mShare = (ImageView) findViewById(R.id.mIv_play_share);
        mTitle = (TextView) findViewById(R.id.mTV_play_title);
        mBGPicture = (ImageView) findViewById(R.id.play_bgPicture);
        mPlayList = (LinearLayout) findViewById(R.id.mIv_play_list);
        mCurrentTime = (TextView) findViewById(R.id.play_currentTime);
        mPlayLastOne = (ImageButton) findViewById(R.id.play_lastone);
        mPlayOrPause = (ImageButton) findViewById(R.id.play_playOrpause);
        mHeart = (ImageView) findViewById(R.id.mIv_play_heart);
        mTotleTime = (TextView) findViewById(R.id.play_totleTime);
        mSeekBar = (SeekBar) findViewById(R.id.play_seekBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //控制暂停还是播放的点击事件。
            case R.id.play_playOrpause:
                if (isPause) {
                    //点击从暂停到播放状态。
                    mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_pause);
                    isPause = false;
                } else {
                    //点击从播放到暂停状态。
                    mPlayOrPause.setBackgroundResource(R.drawable.play_ondemand_image_play);
                    isPause = true;
                }
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {
            Bundle extras = data.getExtras();
            List<ColumnListBean> columnListBeen =
                    (List<ColumnListBean>) extras.getSerializable("columnListBeen");
            columnListBeens.clear();
            columnListBeens.addAll(columnListBeen);
            index = extras.getInt("index", 1);
            currentPage = extras.getInt("currentPage", 0);
            Log.e("==onActivityResult==", "currentPage = " + currentPage);
            Log.e("==onActivityResult==", "index = " + index);
            Log.e("==onActivityResult==", "columnListBeens.size() = " + columnListBeens.size());
        }
    }
}
