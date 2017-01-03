package com.yikouguaishou.peanutfm;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class ColumnListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String pid;
    private int providerCode;
    private String columnName;
    private String mobileId = "";

    private FrameLayout fl_columnList;
    private RecyclerView rv_columnList;
    private TextView tv_columnList_title;
    private ImageView iv_back;
    private ImageView iv_share;
    LinearLayoutManager layoutManager;
    private List<ColumnListBean.ConBean> columnListDatas = new ArrayList<>();
    private ColumnListAdapter columnListAdapter;
    private String logoUrl;

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
        setHeader(rv_columnList);
        setListener();
        if (bundle != null) {
            getColumnList();
        }
    }

    private void initViews() {
        fl_columnList = (FrameLayout) findViewById(R.id.mFrameLayout_columnList);
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
//        rv_columnList.setDefaultOnRefreshListener(this);
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
                        List<ColumnListBean.ConBean> columnListData = columnListBean.getCon();
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
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                columnListDatas.clear();
                getColumnList();
            }
        }, 2000);
    }

    void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_columnlist_header, view, false);
        RoundedImageView iv_columnList_logo;
        ImageView iv_columnList_header;
        iv_columnList_logo = (RoundedImageView) header.findViewById(R.id.riv_columnList_logo);
        iv_columnList_header = (ImageView) header.findViewById(R.id.iv_columnList_header);
        Glide.with(this)
                .load(logoUrl)
                .crossFade()
                .into(iv_columnList_logo);
        Glide.with(this)
                .load(logoUrl)
                .crossFade()
                .into(iv_columnList_header);
        columnListAdapter.setHeaderView(header);
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
