package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yikouguaishou.peanutfm.apiservice.SortDetailsApiService;
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

public class SortDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        SortDetailsAdapter.OnSortDetailsItemClickListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private RecyclerView recyclerView_sortDetails;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_sortDetails_back;
    private TextView tv_sortDetails_title;
    private TextView tv_sortDetails_name;
    private TextView tv_sortDetails_more;

    private List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas = new ArrayList<>();
    private SortDetailsAdapter sortDetailsAdapter;

    private int sort_id;
    private String sort_name;
    private int categoryId;
    private String name;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_details);
        Bundle bundle = getIntent().getExtras();
        sort_id = bundle.getInt("sort_id");
        sort_name = bundle.getString("sort_name");

        initViews();
        sortDetailsAdapter = new SortDetailsAdapter(this, sortDetailsDatas);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView_sortDetails.setLayoutManager(gridLayoutManager);
        recyclerView_sortDetails.setAdapter(sortDetailsAdapter);
        recyclerView_sortDetails.addItemDecoration(new SpaceItemDecoration(2));
        setListener();
        getSortDetails();
        setHeader(recyclerView_sortDetails);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return sortDetailsAdapter.isHeader(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    private void initViews() {
        recyclerView_sortDetails = (RecyclerView) findViewById(R.id.mRecyclerView_sortDetails);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout_sortDetails);
        btn_sortDetails_back = (Button) findViewById(R.id.mButton_sortDetails_back);
        tv_sortDetails_title = (TextView) findViewById(R.id.mTextView_sortDetails_title);
        tv_sortDetails_title.setText(sort_name);

    }

    private void setListener() {
        btn_sortDetails_back.setOnClickListener(this);
        sortDetailsAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    void setHeader(RecyclerView view){
        header = LayoutInflater.from(this).inflate(R.layout.sortdetails_header, view, false);
        tv_sortDetails_more = (TextView) header.findViewById(R.id.mTextView_sortDetails_more);
        tv_sortDetails_name = (TextView) header.findViewById(R.id.mTextView_sortDetails_name);
        tv_sortDetails_name.setText(sort_name);
        tv_sortDetails_more.setOnClickListener(this);
        sortDetailsAdapter.setHeaderSortDetails(header);
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
                        getSortDetails();
                    }

                    @Override
                    public void onNext(SortDetailsBean sortDetailsBean) {
                        SortDetailsBean.ConBean conBean = sortDetailsBean.getCon().get(0);
                        List<SortDetailsBean.ConBean.DetailListBean> sortDetailsData = conBean.getDetailList();
                        categoryId = conBean.getCategoryId();
                        name = conBean.getName();
                        if (sortDetailsData != null) {
                            sortDetailsDatas.addAll(sortDetailsData);
                        }
                        sortDetailsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mButton_sortDetails_back:
                finish();
                break;
            case R.id.mTextView_sortDetails_more:
                Intent intent = new Intent(this, TypeThreeMoreActivity.class);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("title", name);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                sortDetailsDatas.clear();
                getSortDetails();
            }
        }, 2000);
    }

    @Override
    public void onItemClick(View v, int position) {
        SortDetailsBean.ConBean.DetailListBean detailListBean = sortDetailsDatas.get(position - 1);
        if (detailListBean != null) {
            Intent intent = new Intent(this, ColumnListActivity.class);
            String name = detailListBean.getName();
            String logo = detailListBean.getLogo();
            long pid = detailListBean.getAlbumId();
            String albumId = String.valueOf(pid);
            int code = detailListBean.getProviderCode();
            String providerCode = String.valueOf(code);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            bundle.putString("logoUrl", logo);
            bundle.putString("albumId", albumId);
            bundle.putString("providerCode", providerCode);
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
