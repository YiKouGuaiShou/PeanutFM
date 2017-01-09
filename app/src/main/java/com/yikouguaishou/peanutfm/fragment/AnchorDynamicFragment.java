package com.yikouguaishou.peanutfm.fragment;


import android.content.Intent;
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
import com.yikouguaishou.peanutfm.DynamicDetailsActivity;
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
        implements SwipeRefreshLayout.OnRefreshListener, AnchorDynamicAdapter.OnDynamicItemClickListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String anchorId;

    private UltimateRecyclerView urv_anchor_dynamic;

    private List<AnchorDynamicListBean.ConBean> dynamicDatas = new ArrayList<>();
    private AnchorDynamicAdapter dynamicAdapter;

    private int fId = 0;
    private String userId = "";
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
            Log.e("====AnchorDynamic===", "------anchorId----" + anchorId);
            getAnchorDynamicList();
        }
        return view;
    }

    private void setListener() {
        urv_anchor_dynamic.setDefaultOnRefreshListener(this);
        dynamicAdapter.setOnDynamicItemClickListener(this);
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

        Observable<AnchorDynamicListBean> anchorDynamicList = radioStationAPIService.getAnchorDynamicList(fId, anchorId, userId);

        anchorDynamicList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnchorDynamicListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getAnchorDynamicList===" + e.getMessage());
//                        getAnchorDynamicList();
                    }

                    @Override
                    public void onNext(AnchorDynamicListBean anchorDynamicListBean) {
                        List<AnchorDynamicListBean.ConBean> conBeen = anchorDynamicListBean.getCon();
                        Log.e("======onNext===", "===getAnchorDynamicList===" + conBeen.get(0).getAnchorName());
                        Log.e("======onNext===", "===getAnchorDynamicList===" + conBeen.get(0).getContent());
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

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getActivity(), DynamicDetailsActivity.class);
        Bundle bundle = new Bundle();
        AnchorDynamicListBean.ConBean conBean = dynamicDatas.get(position);
        bundle.putSerializable("dynamicDatas", conBean);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
