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
import com.yikouguaishou.peanutfm.ColumnListActivity;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.AnchorProductBean;
import com.yikouguaishou.peanutfm.fragment.adapter.AnchorProductAdapter;

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
 * 主播作品列表
 */
public class AnchorProductFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, AnchorProductAdapter.OnProductItemClickListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String anchorId;

    private UltimateRecyclerView urv_anchor_product;

    private List<AnchorProductBean.ConBean> productDatas = new ArrayList<>();
    private AnchorProductAdapter productAdapter;

    public AnchorProductFragment(String anchorId) {
        this.anchorId = anchorId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_anchor_product, container, false);
        urv_anchor_product = (UltimateRecyclerView) view.findViewById(R.id.mUltimateRecyclerView_anchor_product);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        urv_anchor_product.setLayoutManager(manager);
        productAdapter = new AnchorProductAdapter(getActivity(), productDatas);
        urv_anchor_product.setAdapter(productAdapter);
        setListener();
        if (anchorId != null) {
            getAnchorProduct();
        }
        return view;
    }

    private void setListener() {
        urv_anchor_product.setDefaultOnRefreshListener(this);
        productAdapter.setOnProductItemClickListener(this);
    }

    /**
     * 获取主播作品
     */
    private void getAnchorProduct() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);

        Observable<AnchorProductBean> anchorInfoBean = radioStationAPIService.getAnchorProduct(anchorId);

        anchorInfoBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnchorProductBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getAnchorProduct===" + e.getMessage());
                        getAnchorProduct();
                    }

                    @Override
                    public void onNext(AnchorProductBean anchorProductBean) {
                        List<AnchorProductBean.ConBean> conBeen = anchorProductBean.getCon();
                        if (conBeen != null) {
                            productDatas.addAll(conBeen);
                        }
                        productAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                urv_anchor_product.setRefreshing(false);
                productDatas.clear();
                getAnchorProduct();
            }
        }, 2000);
    }

    @Override
    public void onItemClick(View v, int position) {
        AnchorProductBean.ConBean conBean = productDatas.get(position);
        String columnId = conBean.getColumnId();
        String columnCover = conBean.getColumnCover();
        String providerCode = conBean.getProviderCode();

        Bundle bundle = new Bundle();
        bundle.putString("albumId", columnId);
        bundle.putString("logoUrl", columnCover);
        bundle.putString("providerCode", providerCode);

        Intent intent = new Intent(getActivity(), ColumnListActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);

    }
}
