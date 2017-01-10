package com.yikouguaishou.peanutfm;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.RecommendHttpApiService;
import com.yikouguaishou.peanutfm.bean.TurnTwoItemBean;
import com.yikouguaishou.peanutfm.fragment.adapter.TurnTwoAdapter;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TurnTwoActivity extends AppCompatActivity {

    private TextView mTvTitle;
    private UltimateRecyclerView mRecyclerView;
    private String baseUrl = "http://fsapp.linker.cc/";
    private TurnTwoAdapter turnTwoAdapter;
    private List<TurnTwoItemBean.VideoPlateListEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_two);
        String title = getIntent().getStringExtra("title");
        getData();
        ImageButton mbtnBack = (ImageButton) findViewById(R.id.mbtn_back);
        mTvTitle = (TextView) findViewById(R.id.mtv_title);
        mTvTitle.setText(title);
        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.recycler_turnTwo);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        turnTwoAdapter = new TurnTwoAdapter(this);
        //设置刷新。
        mRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        turnTwoAdapter.setData(data);
        mRecyclerView.setAdapter(turnTwoAdapter);
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService recommendHttpApiService = retrofit.create(RecommendHttpApiService.class);

        Observable<TurnTwoItemBean> turnTwoItemBean = recommendHttpApiService.getTurnTwoItemBean(0);

        turnTwoItemBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TurnTwoItemBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("==onError", "onError= " + e.getMessage());
                    }

                    @Override
                    public void onNext(TurnTwoItemBean turnTwoItemBean) {
                        if (turnTwoItemBean != null) {
                            if (mRecyclerView != null) {
                                mRecyclerView.setRefreshing(false);
                            }
                            Log.e("==TurnTwoItemBean", "onNext " + turnTwoItemBean.getDes());
                            List<TurnTwoItemBean.VideoPlateListEntity> con = turnTwoItemBean.getVideoPlateList();
                            turnTwoAdapter.setData(con);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
