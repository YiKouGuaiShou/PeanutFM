package com.yikouguaishou.peanutfm;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.RecommendHttpApiService;
import com.yikouguaishou.peanutfm.bean.TypeThreeItemBean;
import com.yikouguaishou.peanutfm.bean.TypeZeroItemBean;
import com.yikouguaishou.peanutfm.fragment.adapter.TypeZeroMoreAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TypeZeroMoreActivity extends AppCompatActivity {
    private UltimateRecyclerView mRecyclerView;
    private String baseUrl = "http://fsapp.linker.cc/";
    private List<TypeZeroItemBean.ConEntity> conList = new ArrayList<>();
    private int categoryId;
    private TextView mTvTitle;
    private TypeZeroMoreAdapter zeroMoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_zero_more);
        categoryId = getIntent().getIntExtra("categoryId", -1);
        String title = getIntent().getStringExtra("title");
         getData(categoryId);
        ImageButton mbtnBack = (ImageButton) findViewById(R.id.mbtn_back);
        mTvTitle = (TextView) findViewById(R.id.mtv_title);
        mTvTitle.setText(title);
        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (UltimateRecyclerView) findViewById(R.id.recycler_moreZero);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zeroMoreAdapter = new TypeZeroMoreAdapter(this);
        //设置刷新。
        mRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(categoryId);
            }
        });

        zeroMoreAdapter.setConList(conList);
        mRecyclerView.setAdapter(zeroMoreAdapter);
    }

    public void getData(int categoryId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService recommendHttpApiService = retrofit.create(RecommendHttpApiService.class);

        Observable<TypeZeroItemBean> typeZeroItemBean = recommendHttpApiService.getTypeZeroItemBean(categoryId);

        typeZeroItemBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TypeZeroItemBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("==onError", "onError= " + e.getMessage());
                        // Toast.makeText(TypeThreeMoreActivity.this, "检测到你目前网络较差！", Toast.LENGTH_SHORT).show();
                        getData(TypeZeroMoreActivity.this.categoryId);
                    }

                    @Override
                    public void onNext(TypeZeroItemBean typeZeroItemBean) {
                        if (typeZeroItemBean != null) {
                            if (mRecyclerView != null) {
                                mRecyclerView.setRefreshing(false);
                            }
                            Log.e("==typeZeroItemBean", "onNext " + typeZeroItemBean.getDes());
                            List<TypeZeroItemBean.ConEntity> con = typeZeroItemBean.getCon();
                            zeroMoreAdapter.setConList(con);
                        }
                    }
                });
    }
}
