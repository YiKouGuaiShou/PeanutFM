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
import com.yikouguaishou.peanutfm.bean.TurnOneItemBean;
import com.yikouguaishou.peanutfm.bean.TypeOneItemBean;
import com.yikouguaishou.peanutfm.fragment.adapter.TurnOneAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TurnOneActivity extends AppCompatActivity {

    private TextView mTvTitle;
    private UltimateRecyclerView mRecyclerView;
    private String baseUrl = "http://fsapp.linker.cc/";
    private TurnOneAdapter turnOneAdapter;
    private List<TurnOneItemBean.ConEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_one);
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

        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.recycler_turnOne);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        turnOneAdapter = new TurnOneAdapter(this);
        //设置刷新。
        mRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        turnOneAdapter.setData(data);
        mRecyclerView.setAdapter(turnOneAdapter);
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService recommendHttpApiService = retrofit.create(RecommendHttpApiService.class);

        Observable<TurnOneItemBean> turnOneItemBean = recommendHttpApiService.getTurnOneItemBean();

        turnOneItemBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TurnOneItemBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("==onError", "onError= " + e.getMessage());
                        getData();
                    }

                    @Override
                    public void onNext(TurnOneItemBean turnOneItemBean) {
                        if (turnOneItemBean != null) {
                            if (mRecyclerView != null) {
                                mRecyclerView.setRefreshing(false);
                            }
                            Log.e("==TurnOneItemBean", "onNext " + turnOneItemBean.getDes());
                            List<TurnOneItemBean.ConEntity> con = turnOneItemBean.getCon();
                            turnOneAdapter.setData(con);
                        }
                    }
                });
    }
}
