package com.yikouguaishou.peanutfm.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;

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

public class RadioStationFragment extends Fragment implements View.OnClickListener {
    private Context ctx;
    private FrameLayout fl_radio_station;
    private TextView tv_radio_station;

    List<Fragment> fragments = new ArrayList<>();

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

        fl_radio_station = (FrameLayout) view.findViewById(R.id.fl_radio_station);
        tv_radio_station = (TextView) view.findViewById(R.id.tv_radio_station);

        getFragment();

        tv_radio_station.setOnClickListener(this);

        return view;
    }

    /**
     * 获取listFragment
     */
    private void getFragment() {
        RadioStationListFragment radioStationListFragment = new RadioStationListFragment();
        radioStationListFragment.setTv_radio_station(tv_radio_station);
        fragments.add(radioStationListFragment);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_radio_station, radioStationListFragment);
        ft.commit();
    }

    /**
     * 返回展示listFragment
     *
     * @param i
     */
    private void showFragment(int i) {
        Fragment fragment = fragments.get(i);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.fl_radio_station, fragment);

        //提交事务
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        tv_radio_station.setText("请选择电台");
        showFragment(0);
    }
}
