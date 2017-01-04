package com.yikouguaishou.peanutfm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.RadioStationBannerBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioStationDeatailRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 暂时无类型1频道,没用到此fragment(未运行测试,不可用)
 */
public class RadioStationDetailFragment extends Fragment {
    private Context ctx;
    private RecyclerView rv_detail_radio_station;

    private List<RadioStationBean.ConBeanX> radioStationData;
    private List<RadioStationBannerBean> radioStationBannerData = new ArrayList<>();
    private RadioStationDeatailRecyclerViewAdapter mRadioStationDetailAdapter;

    private int position;

    private static final String PATH = "http://fsapp.linker.cc/";

    public RadioStationDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_radio_station_detail, container, false);

        rv_detail_radio_station = (RecyclerView) view.findViewById(R.id.rv_detail_radio_station);
        rv_detail_radio_station.setLayoutManager(new LinearLayoutManager(ctx));

        Bundle bundle = getArguments();
        radioStationData = (List<RadioStationBean.ConBeanX>) bundle.getSerializable("radioStationData");
        position = bundle.getInt("position");
        String broadCastId = radioStationData.get(0).getLiveList().get(position).getId();

        mRadioStationDetailAdapter = new RadioStationDeatailRecyclerViewAdapter(ctx, radioStationData);
        rv_detail_radio_station.setAdapter(mRadioStationDetailAdapter);

        getData(broadCastId);

        return view;
    }

    /**
     * 获取广告条数据
     */
    private void getData(String broadCastId) {
        Log.e("=====broadCastId===1==>",broadCastId);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);
        Observable<RadioStationBannerBean> observable = radioStationAPIService.RadioStationBannerList(broadCastId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RadioStationBannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("=====onError===1==>",e.getMessage());
                    }

                    @Override
                    public void onNext(RadioStationBannerBean radioStationBannerBean) {
                        Log.e("=====onNext===2==>",radioStationBannerBean.getBannerList().size()+"");
                        radioStationBannerData.add(radioStationBannerBean);
                        mRadioStationDetailAdapter.setRadioStationBannerData(radioStationBannerData);
                    }
                });
    }

}
