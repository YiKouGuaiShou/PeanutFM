package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.PlayRadioStationItemClickListener;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.fragment.adapter.PlayListRadioStationAdapter;

import java.io.Serializable;
import java.util.List;

public class PlayRadioStationActivity extends AppCompatActivity implements PlayRadioStationItemClickListener {
    private UltimateRecyclerView play_list_radio_station_rv;
    private PlayListRadioStationAdapter playListRadioStationAdapter;

    private List<RadioStationBean.ConBeanX> radioStationData;
    private List<RadioStationBean.ConBeanX.LiveListBean.ProgamlistBean> progamlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_radio_station);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        radioStationData = (List<RadioStationBean.ConBeanX>) intent.getSerializableExtra("radioStationData");
        progamlist = radioStationData.get(0).getLiveList().get(position).getProgamlist();

        play_list_radio_station_rv = (UltimateRecyclerView) findViewById(R.id.play_list_radio_station_rv);
        play_list_radio_station_rv.setLayoutManager(new LinearLayoutManager(this));
        playListRadioStationAdapter = new PlayListRadioStationAdapter(this, progamlist);
        play_list_radio_station_rv.setAdapter(playListRadioStationAdapter);

        playListRadioStationAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onFetch(int position) {
        Intent intent = new Intent();
        intent.putExtra("progamlist", (Serializable) progamlist);
        intent.putExtra("position", position);
        setResult(200, intent);
        finish();
    }
}
