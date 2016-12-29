package com.yikouguaishou.peanutfm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioStationRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RadioStationFragment extends Fragment {
    private Context ctx;
    private RecyclerView rv_radio_station;
    private RadioStationRecyclerViewAdapter mRadioStationAdapter;
    private List<RadioStationBean.ConBeanX> radioStationData = new ArrayList<>();

    private static final String PATH = "http://fsapp.linker.cc/";

    public RadioStationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_station, container, false);

        rv_radio_station = (RecyclerView) view.findViewById(R.id.rv_radio_station);
        rv_radio_station.setLayoutManager(new LinearLayoutManager(ctx));

        mRadioStationAdapter = new RadioStationRecyclerViewAdapter(ctx);
        rv_radio_station.setAdapter(mRadioStationAdapter);

        getRadioStationData();

        return view;
    }

    /**
     * 下载数据
     */
    private void getRadioStationData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        final RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);
        Observable<RadioStationBean> observable = radioStationAPIService.RadioStationList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RadioStationBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RadioStationBean radioStationBean) {
                        radioStationData.add(radioStationBean.getCon().get(0));
                        mRadioStationAdapter.setRadioStationData(radioStationData);
                    }
                });
    }

}
