package com.yikouguaishou.peanutfm;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
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
        SwipeRefreshLayout.OnRefreshListener,
        RadioGroup.OnCheckedChangeListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String pid;
    private int providerCode;
    private String columnName;
    private String mobileId = "";

    private Toolbar fl_columnList;
    private RecyclerView rv_columnList;
    private TextView tv_columnList_title;
    private ImageView iv_back;
    private ImageView iv_share;
    LinearLayoutManager layoutManager;
    private List<ColumnListBean.ConBean> columnListDatas = new ArrayList<>();
    private ColumnListAdapter columnListAdapter;
    private String logoUrl;
    private List<ColumnListBean.ConBean> columnListData;
    private String count;
    private String providerName;
    private String listens;
    private String descriptions;
    private String name;

    private LinearLayout ll_columnDetails;
    private RelativeLayout rl_collect;
    private TextView tv_columnList_collect;
    private ImageView iv_collect_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_list);
        Bundle bundle = getIntent().getExtras();
        columnName = bundle.getString("name");
        logoUrl = bundle.getString("logoUrl");
        long albumId = bundle.getLong("albumId");
        pid = String.valueOf(albumId);
        providerCode = bundle.getInt("providerCode");
        Log.e("======bundle===", "===getName===" + columnName);
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
        }
        setHeader();
        setHeaderRecycleView(rv_columnList);
    }

    private void initViews() {
        ll_columnDetails = (LinearLayout) findViewById(R.id.ll_columnDetails);
        rl_collect = (RelativeLayout) findViewById(R.id.mRelativeLayout_collect);
        tv_columnList_collect = (TextView) findViewById(R.id.mTextView_collect);
        iv_collect_logo = (ImageView) findViewById(R.id.mImageView_collect_logo);
        fl_columnList = (Toolbar) findViewById(R.id.mFrameLayout_columnList);
        fl_columnList.bringToFront();
        rv_columnList = (RecyclerView) findViewById(R.id.mRecyclerView_columnList);
        tv_columnList_title = (TextView) findViewById(R.id.mTextView_columnList_title);
        tv_columnList_title.setText(columnName);
        iv_back = (ImageView) findViewById(R.id.mButton_columnList_back);
        iv_share = (ImageView) findViewById(R.id.mButton_columnList_share);
    }

    private void setListener() {
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
//        swipeRefreshLayout.setOnRefreshListener(this);
        rl_collect.setOnClickListener(this);
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
                        int counts = columnListBean.getCount();
                        count = String.valueOf(counts);
                        Log.e("======onNext===", "===ColumnList==count=" + counts);
                        name = columnListBean.getColumnName();
                        providerName = columnListBean.getProviderName();
                        int listenNum = columnListBean.getListenNum();
                        listens = String.valueOf(listenNum);
                        descriptions = columnListBean.getDescriptions();
                        columnListData = columnListBean.getCon();
                        Log.e("======onNext===", "===getColumnList==name=" + columnListData.get(0).getName());
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
                break;
        }
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
                columnListDatas.clear();
                getColumnList();

            }
        }, 2000);
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

    void setHeader() {
        RoundedImageView iv_columnList_logo;
        ImageView iv_columnList_header;
        RadioGroup rgp_columnList;
        iv_columnList_logo = (RoundedImageView) findViewById(R.id.riv_columnList_logo);
        iv_columnList_header = (ImageView) findViewById(R.id.iv_columnList_header);
        rgp_columnList = (RadioGroup) findViewById(R.id.mRadioGroup_columnList);
        Glide.with(this)
                .load(logoUrl)
                .crossFade()
                .into(iv_columnList_logo);
        Glide.with(this)
                .load(logoUrl)
                .crossFade()
                .into(iv_columnList_header);

        rgp_columnList.setOnCheckedChangeListener(this);
    }

    void setHeaderRecycleView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.columnlist_recycleview_header, view, false);
        TextView tv_columnList_count;
        ImageView iv_columnList_sort;
        RelativeLayout rl_sort;
        tv_columnList_count = (TextView) header.findViewById(R.id.mTextView_columnList_count);
        tv_columnList_count.setText("共" + count + "首");
        iv_columnList_sort = (ImageView) header.findViewById(R.id.mImageView_columnList_sort);
        iv_columnList_sort.setImageResource(R.mipmap.sort_icon);
        rl_sort = (RelativeLayout) header.findViewById(R.id.rl_sort);
        rl_sort.setOnClickListener(this);
        columnListAdapter.setHeaderRecycleView(header);
    }


    void setColumnDetails() {
        TextView tv_columnDetails_name,
                tv_columnDetails_providerName,
                tv_columnDetails_listens,
                tv_columnDetails_descriptions;
        tv_columnDetails_name = (TextView) findViewById(R.id.mTextView_columnDetails_name);
        tv_columnDetails_providerName = (TextView) findViewById(R.id.mTextView_columnDetails_play);
        tv_columnDetails_listens = (TextView) findViewById(R.id.mTextView_columnDetails_listens);
        tv_columnDetails_descriptions = (TextView) findViewById(R.id.mTextView_columnDetails_descriptions);

        tv_columnDetails_name.setText(name);
        tv_columnDetails_providerName.setText(providerName);
        tv_columnDetails_listens.setText(listens);
        tv_columnDetails_descriptions.setText(descriptions);
    }

    /**
     * 设置图片模糊
     * @param bitmap
     * @return
     */
//    public Bitmap blurBitmap(Bitmap bitmap){
//
//        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//
//        RenderScript rs = RenderScript.create(getApplicationContext());
//
//        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//
//        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
//        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
//
//        //设置模糊度 0 < radius <= 25
//        blurScript.setRadius(25.0f);
//
//        //Perform the Renderscript
//        blurScript.setInput(allIn);
//        blurScript.forEach(allOut);
//
//        //Copy the final bitmap created by the out Allocation to the outBitmap
//        allOut.copyTo(outBitmap);
//
//        //recycle the original bitmap
//        bitmap.recycle();
//
//        //After finishing everything, we destroy the Renderscript.
//        rs.destroy();
//
//        return outBitmap;
//
//    }
}
