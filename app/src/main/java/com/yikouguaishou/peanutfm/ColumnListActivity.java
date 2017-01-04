package com.yikouguaishou.peanutfm;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.apiservice.SortApiService;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;
import com.yikouguaishou.peanutfm.fragment.adapter.ColumnListAdapter;
import com.yikouguaishou.peanutfm.utils.APP;
import com.yikouguaishou.peanutfm.utils.FastBlur;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ColumnListActivity extends AppCompatActivity
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String pid;
    private int providerCode;
    private String columnName;
    private String mobileId = "";

    private RelativeLayout rl_columnList_bar;
    private RecyclerView rv_columnList;
    private TextView tv_columnList_title;
    private ImageView iv_back;
    private ImageView iv_share;
    private LinearLayoutManager layoutManager;
    private List<ColumnListBean.ConBean> columnListDatas = new LinkedList<>();
    private ColumnListAdapter columnListAdapter;
    private String logoUrl;
    private List<ColumnListBean.ConBean> columnListData;
    private String providerName;
    private String listens;
    private String descriptions;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ll_columnDetails;
    private RoundedImageView iv_columnList_logo;
    private ImageView iv_columnList_header;
    private RadioGroup rgp_columnList;
    private RelativeLayout rl_collect;
    private TextView tv_columnList_collect;
    private ImageView iv_collect_logo;
    private ImageView iv_columnList_asc;
    private ImageView iv_columnList_desc;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);
        Bundle bundle = getIntent().getExtras();
        logoUrl = bundle.getString("logoUrl");
        long albumId = bundle.getLong("albumId");
        pid = String.valueOf(albumId);
        providerCode = bundle.getInt("providerCode");
        Log.e("======bundle===", "===getproviderCode===" + providerCode);

        initViews();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_columnList.setLayoutManager(layoutManager);
        columnListAdapter = new ColumnListAdapter(this, columnListDatas);
        rv_columnList.setAdapter(columnListAdapter);
        setListener();
        if (bundle != null) {
            getColumnList();
            setHeader();
            setHeaderRecycleView(rv_columnList);
        }
    }

    private void initViews() {
        rl_columnList_bar = (RelativeLayout) findViewById(R.id.mRelativeLayout_columnList);
        ll_columnDetails = (LinearLayout) findViewById(R.id.ll_columnDetails);
        rl_collect = (RelativeLayout) findViewById(R.id.mRelativeLayout_collect);
        tv_columnList_collect = (TextView) findViewById(R.id.mTextView_collect);
        iv_collect_logo = (ImageView) findViewById(R.id.mImageView_collect_logo);
        rv_columnList = (RecyclerView) findViewById(R.id.mRecyclerView_columnList);
        tv_columnList_title = (TextView) findViewById(R.id.mTextView_columnList_title);
        tv_columnList_title.setText(columnName);
        iv_back = (ImageView) findViewById(R.id.mButton_columnList_back);
        iv_share = (ImageView) findViewById(R.id.mButton_columnList_share);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
        rv_columnList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) { //到底部

                    }
                    if (!recyclerView.canScrollVertically(-1)) { //到顶部
                        //获取 RecycleView第一个子view
                        View childView = rv_columnList.getChildAt(0);
                        //获取第一个子view的顶部坐标
                        int top = childView.getTop();
                        //获取 RecycleView的顶部坐标
                        //正常来说RecycleView的顶部坐标应该是0,但是严格来考虑,当RecycleView设置了paddingTop时,所有子view的绘制将以paddingTop的位置为起始位置,所以实际的顶部应该是paddingTop的高度的数值.
                        int topEdge = rv_columnList.getPaddingTop();
                        if (top >= topEdge) {
                            //子view完全显示

                            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(false);
                                            columnListDatas.clear();
                                            getColumnList();
                                        }
                                    }, 2000);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {   //下滑

                } else {        //上滑

                }
            }
        });
    }

    /**
     * 获取分类详情
     */
    private void getColumnList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        SortApiService columnListApiService = retrofit.create(SortApiService.class);

        Observable<ColumnListBean> columnListBeans = columnListApiService.getColumnListData(providerCode, 1, pid, mobileId, 0);

        columnListBeans.subscribeOn(Schedulers.io())
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
                        count = columnListBean.getCount();
                        Log.e("======onNext===", "===ColumnList==count=" + count);
                        columnName = columnListBean.getColumnName();
                        Log.e("======onNext===", "===ColumnList==columnName=" + columnName);
                        providerName = columnListBean.getProviderName();
                        int listenNum = columnListBean.getListenNum();
                        listens = String.valueOf(listenNum);
                        descriptions = columnListBean.getDescriptions();
                        columnListData = columnListBean.getCon();
                        if (columnListData != null) {
                            columnListDatas.addAll(columnListData);
                        }
                        columnListAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mButton_columnList_back:  //返回
                finish();
                break;
            case R.id.mButton_columnList_share:  //分享
                Toast.makeText(ColumnListActivity.this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mRelativeLayout_collect:  //收藏
                if (tv_columnList_collect.getText().toString().equals("收藏")) {
                    tv_columnList_collect.setText("已收藏");
                    iv_collect_logo.setImageResource(R.mipmap.collected);
                } else {
                    tv_columnList_collect.setText("收藏");
                    iv_collect_logo.setImageResource(R.mipmap.collect);
                }
                break;
            case R.id.rl_sort:    //排序
                Toast.makeText(ColumnListActivity.this, "排序", Toast.LENGTH_SHORT).show();
//                columnListDatas.addFirst(columnListData);
//                columnListAdapter.notifyDataSetChanged();
                if (iv_columnList_asc.getVisibility() == View.GONE){
                    iv_columnList_asc.setVisibility(View.VISIBLE);
                    iv_columnList_desc.setVisibility(View.GONE);
                } else {
                    iv_columnList_asc.setVisibility(View.GONE);
                    iv_columnList_desc.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.mRadioButton_program:     //节目
                rv_columnList.setVisibility(View.VISIBLE);
                ll_columnDetails.setVisibility(View.GONE);
                break;
            case R.id.mRadioButton_details:     //详情
                rv_columnList.setVisibility(View.GONE);
                ll_columnDetails.setVisibility(View.VISIBLE);
                setColumnDetails();
                break;
        }
    }

    /**
     * 设置页面头部布局
     */
    void setHeader() {
        iv_columnList_logo = (RoundedImageView) findViewById(R.id.riv_columnList_logo);
        iv_columnList_header = (ImageView) findViewById(R.id.iv_columnList_header);
        rgp_columnList = (RadioGroup) findViewById(R.id.mRadioGroup_columnList);

        Glide.with(this)
                .load(logoUrl)
                .crossFade()
                .into(iv_columnList_logo);

        applyBlur();

        rgp_columnList.setOnCheckedChangeListener(this);
    }

    /**
     * 设置RecycleView 头部布局
     *
     * @param view
     */
    void setHeaderRecycleView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.columnlist_recycleview_header, view, false);
        TextView tv_columnList_count;
        RelativeLayout rl_sort;
        tv_columnList_count = (TextView) header.findViewById(R.id.mTextView_columnList_count);
        tv_columnList_count.setText("共" + count + "首");
        iv_columnList_asc = (ImageView) header.findViewById(R.id.mImageView_columnList_asc);
        iv_columnList_desc = (ImageView) header.findViewById(R.id.mImageView_columnList_desc);

        rl_sort = (RelativeLayout) header.findViewById(R.id.rl_sort);
        rl_sort.setOnClickListener(this);
        columnListAdapter.setHeaderRecycleView(header);
    }

    /**
     * 设置详情页面布局
     */
    void setColumnDetails() {
        TextView tv_columnDetails_name,
                tv_columnDetails_providerName,
                tv_columnDetails_listens,
                tv_columnDetails_descriptions;
        tv_columnDetails_name = (TextView) findViewById(R.id.mTextView_columnDetails_name);
        tv_columnDetails_providerName = (TextView) findViewById(R.id.mTextView_columnDetails_play);
        tv_columnDetails_listens = (TextView) findViewById(R.id.mTextView_columnDetails_listens);
        tv_columnDetails_descriptions = (TextView) findViewById(R.id.mTextView_columnDetails_descriptions);

        tv_columnDetails_name.setText(columnName);
        tv_columnDetails_providerName.setText(providerName);
        tv_columnDetails_listens.setText(listens);
        tv_columnDetails_descriptions.setText(descriptions);
    }

    /**
     * 图片的模糊处理
     */
    private void applyBlur() {
        //url为网络图片的url，2 是缩放的倍数（越大模糊效果越高）
        new Thread(new Runnable() {
            @Override
            public void run() {
                //下面的这个方法必须在子线程中执行
                final Bitmap blurBitmap = FastBlur.GetUrlBitmap(logoUrl, 2);

                //刷新ui必须在主线程中执行
                APP.runOnUIThread(new Runnable() {//这个是我自己封装的在主线程中刷新ui的方法。
                    @Override
                    public void run() {
                        iv_columnList_header.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        iv_columnList_header.setImageBitmap(blurBitmap);

                    }
                });
            }
        }).start();
    }

}
