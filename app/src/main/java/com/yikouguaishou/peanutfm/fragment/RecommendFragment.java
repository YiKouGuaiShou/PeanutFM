package com.yikouguaishou.peanutfm.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RecommendHttpApiService;
import com.yikouguaishou.peanutfm.bean.RecommendBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RecommendRecyclerViewAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendFragment extends Fragment {

    private UltimateRecyclerView mRecycleview;
    private RecommendRecyclerViewAdapter mAdapter;

    private RecommendBean recommendBeans;

    String baseUrl = "http://fsapp.linker.cc/";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendHttpApiService recommendHttpApiService = retrofit.create(RecommendHttpApiService.class);

        Observable<RecommendBean> recommendBean = recommendHttpApiService.getRecommendBean();

        recommendBean.subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(new Subscriber<RecommendBean>() {
                         @Override
                         public void onCompleted() {

                         }

                         @Override
                         public void onError(Throwable e) {

                         }

                         @Override
                         public void onNext(RecommendBean recommendBean) {
                             if(recommendBean!=null){
                                 recommendBeans = recommendBean;
                                 Log.e("onNext",recommendBeans.getDes());
                                 mAdapter.setdata(recommendBeans);
                             }else {
                                 Log.e("onNext","kong");
                             }
                         }
                     });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        mRecycleview = (UltimateRecyclerView) view.findViewById(R.id.mRecycleView);
        mRecycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecommendRecyclerViewAdapter(getActivity());
        mAdapter.setdata(recommendBeans);
        mRecycleview.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
