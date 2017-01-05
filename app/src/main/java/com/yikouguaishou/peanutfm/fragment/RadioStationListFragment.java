package com.yikouguaishou.peanutfm.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.PlayRadioActivity;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.apiservice.RadioStationItemClickListener;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioStationListRecyclerViewAdapter;

import java.io.Serializable;
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
 * A simple {@link Fragment} subclass.
 */
public class RadioStationListFragment extends Fragment implements RadioStationItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private Context ctx;
    private UltimateRecyclerView rv_list_radio_station;
    private TextView tv_radio_station;
    private RadioStationListRecyclerViewAdapter mRadioStationListAdapter;

    private List<RadioStationBean.ConBeanX> radioStationData = new ArrayList<>();

    private static final String PATH = "http://fsapp.linker.cc/";

    public RadioStationListFragment() {
        // Required empty public constructor
    }

    public void setTv_radio_station(TextView tv_radio_station) {
        this.tv_radio_station = tv_radio_station;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_station_list, container, false);

        rv_list_radio_station = (UltimateRecyclerView) view.findViewById(R.id.rv_list_radio_station);
        rv_list_radio_station.setLayoutManager(new LinearLayoutManager(ctx));

        mRadioStationListAdapter = new RadioStationListRecyclerViewAdapter(ctx);

        rv_list_radio_station.setAdapter(mRadioStationListAdapter);

        getData();

        setListener();

        return view;
    }

    private void setListener() {
        rv_list_radio_station.setDefaultOnRefreshListener(this);
        mRadioStationListAdapter.setOnItemClickListener(this);
    }

    /**
     * 回调,打开对应电台详情页面
     *
     * @param position
     */
    @Override
    public void onFetch(int position, String name) {
        //如果类型为1
//        if (radioStationData.get(0).getLiveList().get(position).getModelType().equals("1")) {
//            tv_radio_station.setText(name);
//            getFragment(new RadioStationDetailFragment(), position);
//        } else if (radioStationData.get(0).getLiveList().get(position).getModelType().equals("3")) {
        //如果类型为3
        Intent intent = new Intent(ctx, PlayRadioActivity.class);
        intent.putExtra("radioStationData", (Serializable) radioStationData);
        intent.putExtra("position", position);
        intent.putExtra("name", name);
        startActivity(intent);
//        }
    }

    /**
     * 获取fragment(暂无类型1,无法测试,未用到)
     */
    private void getFragment(Fragment fragment, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("radioStationData", (Serializable) radioStationData);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fl_radio_station, fragment);
        ft.commit();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        getData();
    }

    /**
     * 下载数据
     */
    private void getData() {
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
                        Log.e("===onError===>", e.getMessage());
                    }

                    @Override
                    public void onNext(RadioStationBean radioStationBean) {
                        radioStationData.add(radioStationBean.getCon().get(0));
                        mRadioStationListAdapter.setRadioStationData(radioStationData);
                    }
                });
    }
}
