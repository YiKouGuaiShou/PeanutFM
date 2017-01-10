package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.SortApiService;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.fragment.adapter.RadioPlayListAdapter;

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

public class RadioPlayListActivity extends AppCompatActivity implements RadioPlayListAdapter.RadioPlayListLisener {

    private TextView mTitle;
    private UltimateRecyclerView mRecycler;
    private List<ColumnListBean> columnListBeens = new ArrayList<>();
    private List<ColumnListBean> sendColumnListBeens = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private RadioPlayListAdapter adapter;
    private int index;
    private int currentPage;
    //请求参数
    private String baseUrl = "http://fsapp.linker.cc/";
    private String pid;
    private String providerCode;
    private String mobileId = "";
    private boolean isRefreshing = false;
    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_play_list);
        receiveIntent();
        findView();
        initData();
        setLisener();
    }

    private void setLisener() {
        adapter.setRadioPlayListLisener(this);

        mRecycler.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                getColumnList();
                isRefreshing = true;
            }
        });

        mRecycler.reenableLoadmore();
        mRecycler.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                if (currentPage < totalPage - 1) {
                    Log.e("========", "totalPage = " + totalPage);
                    currentPage++;
                    Log.e("========", "loadMore currentPage= " + currentPage);
                    getColumnList();
                    isRefreshing = false;
                }
            }
        });
    }

    private void findView() {
        mTitle = (TextView) findViewById(R.id.playList_title);
        mRecycler = (UltimateRecyclerView) findViewById(R.id.playList_recycler);
        adapter = new RadioPlayListAdapter(this);
        adapter.setNameList(nameList);
        adapter.setCurrentPage(currentPage);
        adapter.setIndex(index);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);

        pid = columnListBeens.get(0).getColumnId();
        totalPage = columnListBeens.get(0).getTotalPage();
        providerCode = columnListBeens.get(0).getProviderCode() + "";
    }

    private void initData() {
        int size = columnListBeens.size();
        nameList.clear();
        for (int i = 0; i < size; i++) {
            ColumnListBean columnListBean = columnListBeens.get(i);
            List<ColumnListBean.ConBean> con = columnListBean.getCon();
            for (int j = 0; j < con.size(); j++) {
                ColumnListBean.ConBean conBean = con.get(j);
                String name = conBean.getName();
                nameList.add(name);
            }
        }
        adapter.setNameList(nameList);
    }

    private void getColumnList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        SortApiService columnListApiService = retrofit.create(SortApiService.class);

        Observable<ColumnListBean> columnListBeansOb = columnListApiService.getColumnListData(providerCode, 1, pid, mobileId, currentPage);
        columnListBeansOb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ColumnListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getColumnList===" + e.getMessage());
                    }

                    @Override
                    public void onNext(ColumnListBean columnListBean) {
                        if (isRefreshing) {
                            columnListBeens.clear();
                            columnListBeens.add(columnListBean);
                            mRecycler.setRefreshing(false);
                            initData();
                            isRefreshing = false;
                        } else {
                            columnListBeens.add(columnListBean);
                            sendColumnListBeens.clear();
                            sendColumnListBeens.addAll(columnListBeens);
                            initData();
                        }
                    }
                });
    }

    private void receiveIntent() {
        Bundle extras = getIntent().getExtras();
        index = extras.getInt("index", 1);
        currentPage = extras.getInt("currentPage", 0);
        List<ColumnListBean> columnListBeen = (List<ColumnListBean>) extras.getSerializable("columnListBeen");
        columnListBeens.addAll(columnListBeen);
        sendColumnListBeens.addAll(columnListBeens);
    }

    @Override
    public void onListItemClick(int currentPage, int index) {
        this.currentPage = currentPage;
        this.index = index;
//        Log.e("====onListItemClick====", "currentPage = " + currentPage);
//        Log.e("====onListItemClick====", "index = " + index);
        Intent intent = new Intent(this, RadioPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("currentPage", currentPage);
        bundle.putInt("index", index);
        bundle.putSerializable("columnListBeen", (Serializable) sendColumnListBeens);
//        Log.e("====onListItemClick====", "sendColumnListBeens.size() = " + sendColumnListBeens.size());
        intent.putExtras(bundle);
        setResult(100, intent);
        finish();
    }
}