package com.yikouguaishou.peanutfm.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.SortDetailsActivity;
import com.yikouguaishou.peanutfm.apiservice.SortApiService;
import com.yikouguaishou.peanutfm.bean.SortBean;
import com.yikouguaishou.peanutfm.fragment.adapter.SortFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SortFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SortFragmentAdapter.onSortItemClickListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private UltimateRecyclerView ultimateRecyclerView_sort;
    private SortFragmentAdapter sortAdapter;
    private List<SortBean.ConBean> sortDatas = new ArrayList<>();
    private Handler handler = new Handler();
    private int sortId;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sort, container, false);
        ultimateRecyclerView_sort = (UltimateRecyclerView) view.findViewById(R.id.mUltimateRecyclerView_sort);
        sortAdapter = new SortFragmentAdapter(getActivity(), sortDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        ultimateRecyclerView_sort.setLayoutManager(gridLayoutManager);
        ultimateRecyclerView_sort.setAdapter(sortAdapter);
        ultimateRecyclerView_sort.enableDefaultSwipeRefresh(true);//开启下拉刷新
        ultimateRecyclerView_sort.setDefaultOnRefreshListener(this);

        sortAdapter.setOnItemClickListener(this);
        getSort();
        return view;
    }

    /**
     * 获取分类列表数据
     */
    private void getSort() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        SortApiService sortApiService = retrofit.create(SortApiService.class);

        Observable<SortBean> sortBean = sortApiService.getSortData();

        sortBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SortBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===SortList===" + e.getMessage());
                    }

                    @Override
                    public void onNext(SortBean sortBean) {
                        List<SortBean.ConBean> sortData = sortBean.getCon();
                        for (int i = 0; i < sortData.size(); i++) {
                            sortId = sortData.get(i).getId();
                        }
                        if (sortData != null) {
                            sortDatas.addAll(sortData);
                        }
                        sortAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sortAdapter.notifyDataSetChanged();
                ultimateRecyclerView_sort.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(View v, int position) {
        SortBean.ConBean conBean = sortDatas.get(position);
        if (conBean != null) {
            Intent intent = new Intent(getActivity(), SortDetailsActivity.class);
            int sort_id = conBean.getId();
            String sort_name = conBean.getName();
            Bundle bundle = new Bundle();
            bundle.putInt("sort_id", sort_id);
            bundle.putString("sort_name", sort_name);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
