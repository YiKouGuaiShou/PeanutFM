package com.yikouguaishou.peanutfm.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;
import com.yikouguaishou.peanutfm.fragment.adapter.AnchorDynamicAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者动态列表
 */
public class AnchorDynamicFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String anchorId;

    private UltimateRecyclerView urv_anchor_dynamic;

    private List<AnchorDynamicListBean.ConBean> dynamicDatas = new ArrayList<>();
    private AnchorDynamicAdapter dynamicAdapter;

    public AnchorDynamicFragment(String anchorId) {
        this.anchorId = anchorId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_anchor_dynamic, container, false);
        urv_anchor_dynamic = (UltimateRecyclerView) view.findViewById(R.id.mUltimateRecyclerView_anchor_dynamic);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        urv_anchor_dynamic.setLayoutManager(manager);
        dynamicAdapter = new AnchorDynamicAdapter(getActivity(), dynamicDatas);
        urv_anchor_dynamic.setAdapter(dynamicAdapter);
        setListener();
        if (anchorId != null) {
            getAnchorDynamicList();
        }
        return view;
    }

    private void setListener() {
        urv_anchor_dynamic.setDefaultOnRefreshListener(this);
    }

    /**
     * 获取主播动态
     */
    private void getAnchorDynamicList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);

        final Observable<AnchorDynamicListBean> anchorDynamicList = radioStationAPIService.getAnchorDynamicList(anchorId);

        anchorDynamicList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnchorDynamicListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getAnchorProduct===" + e.getMessage());
                        getAnchorDynamicList();
                    }

                    @Override
                    public void onNext(AnchorDynamicListBean anchorDynamicListBean) {
                        List<AnchorDynamicListBean.ConBean> conBeen = anchorDynamicListBean.getCon();
                        if (conBeen != null) {
                            dynamicDatas.addAll(conBeen);
                        }
                        dynamicAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                urv_anchor_dynamic.setRefreshing(false);
                dynamicDatas.clear();
                getAnchorDynamicList();
            }
        }, 2000);
    }
}
