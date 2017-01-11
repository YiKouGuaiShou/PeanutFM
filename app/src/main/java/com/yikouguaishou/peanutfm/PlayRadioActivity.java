package com.yikouguaishou.peanutfm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.RecommendHttpApiService;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.bean.RadioPlayCommentBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.cn.sharesdk.onekeyshare.OnekeyShare;
import com.yikouguaishou.peanutfm.fragment.adapter.PlayRadioCommentAdapter;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioPlayCommentAdapter;
import com.yikouguaishou.peanutfm.service.MyRadioStationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlayRadioActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_play_radio_title, tv_play_radio_name, tv_play_radio_anchorperson, tv_play_radio_flower, play_radio_comment_count_tv;
    private ImageView iv_play_radio_logo, iv_play_radio_anchorperson, iv_play_radio_back, iv_play_radio_share, iv_play_radio_list, playing_radio;
    private LinearLayout ll_play_radio_flower;
    private UltimateRecyclerView play_radio_rv;

    private PlayRadioCommentAdapter playRadioCommentAdapter;
    private List<RadioStationBean.ConBeanX> radioStationData;
    private int position;
    private String name;
    private String anchorId;
    private String anchorpersonPic;
    private String anchorpersonName;

    private String playUrl;
    MyRadioStationService myRadioStationService = null;
    MyServiceConn myServiceConn;

    private List<RadioPlayCommentBean.ConEntity> commentList = new ArrayList<>();

    private String baseUrl = "https://fsapp.linker.cc/";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_radio);

        initView();
        initData();
        setListener();
        openService();
    }

    /**
     * 打开服务
     */
    private void openService() {
        Intent intentService = new Intent(PlayRadioActivity.this, MyRadioStationService.class);
        Bundle bundle = new Bundle();
        bundle.putString("control", "play");
        bundle.putString("playUrl", playUrl);
        intentService.putExtras(bundle);
        startService(intentService);

        myServiceConn = new MyServiceConn();
        Intent bindIntent = new Intent(PlayRadioActivity.this, MyRadioStationService.class);
        bindService(bindIntent, myServiceConn, BIND_AUTO_CREATE);
    }

    /**
     * 连接那个服务
     */
    private class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            myRadioStationService = ((MyRadioStationService.MyBinder) binder).getService();

//            //设置activity 和 service关联。
//            if (myRadioStationService instanceof CallMediaChangeCallBack) {
//                callMediaChangeCallBack = myRadioStationService;
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myRadioStationService = null;
        }
    }

//    public interface CallMediaChangeCallBack {
//        public void pauseNow();
//
//        public void startNow();
//    }
//
//    CallMediaChangeCallBack callMediaChangeCallBack = null;

    /**
     * 控件监听
     */
    private void setListener() {
        iv_play_radio_back.setOnClickListener(this);
        iv_play_radio_share.setOnClickListener(this);
        iv_play_radio_list.setOnClickListener(this);
        playing_radio.setOnClickListener(this);
        iv_play_radio_anchorperson.setOnClickListener(this);
        ll_play_radio_flower.setOnClickListener(this);
    }

    /**
     * 获得数据,并设置
     */
    private void initData() {
        String names = null;

        Intent intent = getIntent();
        radioStationData = (List<RadioStationBean.ConBeanX>) intent.getSerializableExtra("radioStationData");
        position = intent.getIntExtra("position", -1);
        //获取音频地址
        playUrl = radioStationData.get(0).getLiveList().get(position).getPlayUrl();
        name = intent.getStringExtra("name");

        if (radioStationData != null && position != -1) {
            List<RadioStationBean.ConBeanX.LiveListBean.ProgamlistBean> progamlist = radioStationData.get(0).getLiveList().get(position).getProgamlist();

            int time = getTime();
            for (int i = 0; i < progamlist.size(); i++) {
                String startTime = progamlist.get(i).getStartTime();
                String endTime = progamlist.get(i).getEndTime();
                int start = getInteger(startTime);
                int end = getInteger(endTime);
                if (time >= start && time < end) {
                    //设置节目背景图片
                    String url = progamlist.get(i).getLogoList().get(0).getUrl();
                    if (url != null) {
                        Glide.with(this).load(url).into(iv_play_radio_logo);
                    } else {
                        iv_play_radio_logo.setImageResource(R.mipmap.ic_launcher);
                    }

                    //设置节目名
                    String name = progamlist.get(i).getName();
                    tv_play_radio_name.setText(name);

                    //设置主播头像
                    anchorpersonPic = progamlist.get(i).getAnchorpersonList().get(0).getAnchorpersonPic();
                    if (anchorpersonPic != null) {
                        Glide.with(this).load(anchorpersonPic).into(iv_play_radio_anchorperson);
                    } else {
                        iv_play_radio_logo.setImageResource(R.mipmap.ic_launcher);
                    }

                    //设置主播名
                    for (int j = 0; j < progamlist.get(i).getAnchorpersonList().size(); j++) {
                        String anchorpersonName = progamlist.get(i).getAnchorpersonList().get(j).getAnchorpersonName();
                        if (j == 0) {
                            names = anchorpersonName;
                        } else {
                            names = names + "," + anchorpersonName;
                        }
                    }

                    tv_play_radio_anchorperson.setText(names);

                    //设置鲜花数
                    int appraiseCount = progamlist.get(i).getAppraiseCount();
                    tv_play_radio_flower.setText("" + appraiseCount);

                    //获取主播ID
                    anchorId = progamlist.get(i).getAnchorpersonList().get(0).getAnchorpersonId();
                    //获取主播名
                    anchorpersonName = progamlist.get(i).getAnchorpersonList().get(0).getAnchorpersonName();
                    //获取音频ID
                    id = progamlist.get(i).getId();
                }
            }

        }

        //设置频道名
        tv_play_radio_title.setText(name);

        //显示评论
        showComment();
    }

    /**
     * 显示评论
     */
    private void showComment() {
        play_radio_rv.setLayoutManager(new LinearLayoutManager(this));
        playRadioCommentAdapter = new PlayRadioCommentAdapter(this);
        playRadioCommentAdapter.setCommentList(commentList);
        play_radio_rv.setAdapter(playRadioCommentAdapter);

        initCommentData();
    }

    /**
     * 下载评论
     */
    private void initCommentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService apiService = retrofit.create(RecommendHttpApiService.class);

        Observable<RadioPlayCommentBean> radioPlayCommentBean = apiService.getRadioPlayCommentBean(id + "", 3);
        radioPlayCommentBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RadioPlayCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===RadioPlayCommentBean===" + e.getMessage());
                    }

                    @Override
                    public void onNext(RadioPlayCommentBean radioPlayCommentBean) {
                        Log.e("=radioPlayCommentBean=", "correlateId = " + radioPlayCommentBean.getCon().get(0).getCorrelateId());
                        if (radioPlayCommentBean != null) {
                            List<RadioPlayCommentBean.ConEntity> con = radioPlayCommentBean.getCon();

                            //设置评论数
                            int size = con.size();
                            play_radio_comment_count_tv.setText("互动评论(" + size + "条)");

                            playRadioCommentAdapter.setCommentList(con);
                            playRadioCommentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 时间字符串转化为int类型
     *
     * @param date
     * @return
     */
    private int getInteger(String date) {
        String replaceDate = date.replace(":", "");
        int time = Integer.parseInt(replaceDate);
        return time;
    }

    /**
     * 获取系统时间
     */
    private int getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(new Date());
        return getInteger(date);
    }

    /**
     * 找到控件
     */
    private void initView() {
        tv_play_radio_title = (TextView) findViewById(R.id.tv_play_radio_title);
        tv_play_radio_name = (TextView) findViewById(R.id.tv_play_radio_name);
        tv_play_radio_anchorperson = (TextView) findViewById(R.id.tv_play_radio_anchorperson);
        tv_play_radio_flower = (TextView) findViewById(R.id.tv_play_radio_flower);
        play_radio_comment_count_tv = (TextView) findViewById(R.id.play_radio_comment_count_tv);
        iv_play_radio_logo = (ImageView) findViewById(R.id.iv_play_radio_logo);
        iv_play_radio_anchorperson = (ImageView) findViewById(R.id.iv_play_radio_anchorperson);
        iv_play_radio_back = (ImageView) findViewById(R.id.iv_play_radio_back);
        iv_play_radio_share = (ImageView) findViewById(R.id.iv_play_radio_share);
        iv_play_radio_list = (ImageView) findViewById(R.id.iv_play_radio_list);
        playing_radio = (ImageView) findViewById(R.id.playing_radio);
        ll_play_radio_flower = (LinearLayout) findViewById(R.id.ll_play_radio_flower);
        play_radio_rv = (UltimateRecyclerView) findViewById(R.id.play_radio_rv);
    }

    /**
     * 控件监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_radio_back:
                finish();
                break;
            case R.id.iv_play_radio_share:
                showShare();
                break;
            case R.id.iv_play_radio_list:
                Toast.makeText(this, "列表!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.playing_radio:
                Toast.makeText(this, "播放!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_play_radio_anchorperson:
                Intent intent = new Intent(PlayRadioActivity.this, AnchorHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("anchorpersonName", anchorpersonName);
                bundle.putString("anchorpersonPic", anchorpersonPic);
                bundle.putString("anchorId", anchorId);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_play_radio_flower:
                Toast.makeText(this, "播放!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    //绑定模式启动Service一定要在Activity销毁的时候解除绑定。
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(myServiceConn);
    }
}
