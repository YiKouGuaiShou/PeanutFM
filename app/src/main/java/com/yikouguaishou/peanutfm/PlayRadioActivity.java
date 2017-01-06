package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayRadioActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_play_radio_title, tv_play_radio_name, tv_play_radio_anchorperson, tv_play_radio_flower;
    private ImageView iv_play_radio_logo, iv_play_radio_anchorperson, iv_play_radio_back, iv_play_radio_share, iv_play_radio_list, playing_radio;
    private LinearLayout ll_play_radio_flower;

    private List<RadioStationBean.ConBeanX> radioStationData;
    private int position;
    private String name;
    private String anchorId;
    private String anchorpersonName;
    private String anchorpersonPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_radio);

        initView();
        initData();
        setListener();
    }

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
        name = intent.getStringExtra("name");

        if (radioStationData != null && position != -1) {
            List<RadioStationBean.ConBeanX.LiveListBean.ProgamlistBean> progamlist =  radioStationData.get(0).getLiveList().get(position).getProgamlist();

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
                        anchorpersonName = progamlist.get(i).getAnchorpersonList().get(j).getAnchorpersonName();
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
                }
            }

        }

        //设置频道名
        tv_play_radio_title.setText(name);
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
        iv_play_radio_logo = (ImageView) findViewById(R.id.iv_play_radio_logo);
        iv_play_radio_anchorperson = (ImageView) findViewById(R.id.iv_play_radio_anchorperson);
        iv_play_radio_back = (ImageView) findViewById(R.id.iv_play_radio_back);
        iv_play_radio_share = (ImageView) findViewById(R.id.iv_play_radio_share);
        iv_play_radio_list = (ImageView) findViewById(R.id.iv_play_radio_list);
        playing_radio = (ImageView) findViewById(R.id.playing_radio);
        ll_play_radio_flower = (LinearLayout) findViewById(R.id.ll_play_radio_flower);
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
                Toast.makeText(this, "分享!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_play_radio_list:
                Toast.makeText(this, "列表!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.playing_radio:
                Toast.makeText(this, "播放!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_play_radio_anchorperson:
                Intent intent = new Intent(PlayRadioActivity.this,AnchorHomeActivity.class);
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
}
