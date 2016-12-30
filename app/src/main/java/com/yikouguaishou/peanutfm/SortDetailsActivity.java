package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.SortDetailsApiService;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.bean.SortDetailsBean;
import com.yikouguaishou.peanutfm.fragment.adapter.SortDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SortDetailsActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SortDetailsAdapter.onSortItemClickListener {
    private String baseUrl = "http://fsapp.linker.cc";

    private UltimateRecyclerView ultimateRecyclerView_sortDetails;
    private Button btn_sortDetails_back;
    private Button btn_sortDetails_more;
    private TextView tv_sortDetails_title;
    private TextView tv_sortDetails_name;

    private List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas = new ArrayList<>();
    private SortDetailsAdapter sortDetailsAdapter;

    private int sort_id;
    private String sort_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_details);
        Bundle bundle = getIntent().getExtras();
        sort_id = bundle.getInt("sort_id");
        sort_name = bundle.getString("sort_name");

        initViews();
        setListener();

        sortDetailsAdapter = new SortDetailsAdapter(this, sortDetailsDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        ultimateRecyclerView_sortDetails.setLayoutManager(gridLayoutManager);
        ultimateRecyclerView_sortDetails.setAdapter(sortDetailsAdapter);
        ultimateRecyclerView_sortDetails.enableDefaultSwipeRefresh(true);//开启下拉刷新
        ultimateRecyclerView_sortDetails.setDefaultOnRefreshListener(this);
        ultimateRecyclerView_sortDetails.addItemDecoration(new SpaceItemDecoration(3));
        getSortDetails();
    }

    private void initViews() {
        ultimateRecyclerView_sortDetails = (UltimateRecyclerView) findViewById(R.id.mUltimateRecyclerView_sortDetails);
        btn_sortDetails_back = (Button) findViewById(R.id.mButton_sortDetails_back);
        btn_sortDetails_more = (Button) findViewById(R.id.mButton_sortDetails_more);
        tv_sortDetails_title = (TextView) findViewById(R.id.mTextView_sortDetails_title);
        tv_sortDetails_title.setText(sort_name);
        tv_sortDetails_name = (TextView) findViewById(R.id.mTextView_sortDetails_name);
        tv_sortDetails_name.setText(sort_name);
    }

    private void setListener() {
        btn_sortDetails_back.setOnClickListener(this);
        btn_sortDetails_more.setOnClickListener(this);
        sortDetailsAdapter.setOnItemClickListener(this);
    }

    /**
     * 获取分类详情
     */
    private void getSortDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        SortDetailsApiService sortDetailsApiService = retrofit.create(SortDetailsApiService.class);

        Observable<SortDetailsBean> sortDetailsBean = sortDetailsApiService.getSortDetailsData(sort_id);

        sortDetailsBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SortDetailsBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getSortDetails===" + e.getMessage());
                    }

                    @Override
                    public void onNext(SortDetailsBean sortDetailsBean) {
                        SortDetailsBean.ConBean conBean = sortDetailsBean.getCon().get(0);
                        List<SortDetailsBean.ConBean.DetailListBean> sortDetailsData = conBean.getDetailList();
//                        for (int i = 0; i < sortDetailsData.size(); i++) {
//                            sort_id = sortDetailsData.get(i).getId();
//                        }
                        if (sortDetailsData != null) {
                            sortDetailsDatas.addAll(sortDetailsData);
                        }
                        sortDetailsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mButton_sortDetails_back:
                finish();
                break;
            case R.id.mButton_sortDetails_more:
                //// TODO: 2016/12/30 点击更多跳转
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sortDetailsAdapter.notifyDataSetChanged();
                ultimateRecyclerView_sortDetails.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(View v, int position) {
        SortDetailsBean.ConBean.DetailListBean detailListBean = sortDetailsDatas.get(position);
        if (detailListBean != null) {
            Intent intent = new Intent(this, ColumnListActivity.class);
            long albumId = detailListBean.getAlbumId();
            int providerCode = detailListBean.getProviderCode();
            Bundle bundle = new Bundle();
            bundle.putLong("albumId", albumId);
            bundle.putInt("providerCode", providerCode);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    /**
     * 给GridView设置间距
     */
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = 0;
//                outRect.bottom = space;
            }
        }
    }
}
