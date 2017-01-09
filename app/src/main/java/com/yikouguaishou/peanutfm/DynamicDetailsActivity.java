package com.yikouguaishou.peanutfm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;
import com.yikouguaishou.peanutfm.bean.DynamicCommentListBean;
import com.yikouguaishou.peanutfm.fragment.adapter.DynamicCommentAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DynamicDetailsActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private AnchorDynamicListBean.ConBean dynamicDatas;
    private CircleImageView civ_dynamicDetails_photo;
    private RoundedImageView riv_dynamicDetails_contnetPic;
    private TextView tv_dynamicDetails_name;
    private TextView tv_dynamicDetails_content;
    private TextView tv_dynamicDetails_createTime;
    private TextView tv_dynamicDetails_praiseCount;
    private TextView tv_dynamicDetails_commentNum;
    private TextView tv_dynamic_commentList_null;
    private ImageView iv_dynamicDetails_praise;
    private Button btn_comment;
    private Bundle bundle;

    private UltimateRecyclerView urv_dynamic_commentList;
    private List<DynamicCommentListBean.ConBean> commentListDatas = new ArrayList<>();
    private DynamicCommentAdapter commentAdapter;
    private int commentNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        bundle = getIntent().getExtras();
        dynamicDatas = (AnchorDynamicListBean.ConBean) bundle.getSerializable("dynamicDatas");

        initViews();
        urv_dynamic_commentList = (UltimateRecyclerView) findViewById(R.id.mUltimateRecyclerView_dynamicComment_notNull);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        urv_dynamic_commentList.setLayoutManager(manager);
        commentAdapter = new DynamicCommentAdapter(this, commentListDatas);
        urv_dynamic_commentList.setAdapter(commentAdapter);
        setListener();
        if (bundle != bundle) {
            getDynamicCommentList();
            if (commentNum == 0){
                tv_dynamic_commentList_null.setVisibility(View.VISIBLE);
                urv_dynamic_commentList.setVisibility(View.GONE);
            } else {
                tv_dynamic_commentList_null.setVisibility(View.GONE);
                urv_dynamic_commentList.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initViews() {
        civ_dynamicDetails_photo = (CircleImageView) findViewById(R.id.mCircleImageView_dynamicDetails_photo);
        riv_dynamicDetails_contnetPic = (RoundedImageView) findViewById(R.id.mRoundedImageView_dynamicDetails_contentPic);
        tv_dynamicDetails_name = (TextView) findViewById(R.id.mTextView_dynamicDetails_name);
        tv_dynamicDetails_content = (TextView) findViewById(R.id.mTextView_dynamicDetails_content);
        tv_dynamicDetails_createTime = (TextView) findViewById(R.id.mTextView_dynamicDetails_createTime);
        tv_dynamicDetails_praiseCount = (TextView) findViewById(R.id.mTextView_dynamicDetails_praiseCount);
        tv_dynamicDetails_commentNum = (TextView) findViewById(R.id.mTextView_dynamicDetails_commentNum);
        iv_dynamicDetails_praise = (ImageView) findViewById(R.id.mImageView_dynamicDetails_praise);
        btn_comment = (Button) findViewById(R.id.mButton_dynamicDetails_comment);
        urv_dynamic_commentList = (UltimateRecyclerView) findViewById(R.id.mUltimateRecyclerView_dynamicComment_notNull);
        tv_dynamic_commentList_null = (TextView) findViewById(R.id.mTextView_dynamicComment_null);

        if (bundle != null) {
            String anchorName = dynamicDatas.getAnchorName();
            tv_dynamicDetails_name.setText(anchorName);
            String content = dynamicDatas.getContent();
            tv_dynamicDetails_content.setText(content);
            String createTime = dynamicDatas.getCreateTime();
            tv_dynamicDetails_createTime.setText(createTime);
            int praiseCount = dynamicDatas.getPraiseCount();
            tv_dynamicDetails_praiseCount.setText("" + praiseCount);
            commentNum = dynamicDatas.getCommentNum();
            tv_dynamicDetails_commentNum.setText("" + commentNum);

            String imgUrl = dynamicDatas.getImgList().get(0).getUrl();
            if (imgUrl != null) {
                Glide.with(this)
                        .load(imgUrl)
                        .into(riv_dynamicDetails_contnetPic);
            }
            String anchorImg = dynamicDatas.getAnchorImg();
            if (anchorImg != null) {
                Glide.with(this)
                        .load(anchorImg)
                        .into(civ_dynamicDetails_photo);
            }
        }
    }

    private void setListener() {
        riv_dynamicDetails_contnetPic.setOnClickListener(this);
        tv_dynamicDetails_praiseCount.setOnClickListener(this);
        iv_dynamicDetails_praise.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        urv_dynamic_commentList.setDefaultOnRefreshListener(this);
    }

    /**
     * 获取动态详情评论列表
     */
    private void getDynamicCommentList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);

        Observable<DynamicCommentListBean> dynamicCommentListBean = radioStationAPIService.getCommentList(0, "" + 1, "", 11, 0, "3.5.4");

        dynamicCommentListBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DynamicCommentListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===getDynamicCommentList===" + e.getMessage());
                        getDynamicCommentList();
                    }

                    @Override
                    public void onNext(DynamicCommentListBean dynamicCommentListBean) {
                        List<DynamicCommentListBean.ConBean> conBeen = dynamicCommentListBean.getCon();
                        Log.e("======onNext===", "===getDynamicCommentList====" + conBeen.get(0).getDiscussantName());
                        Log.e("======onNext===", "===getDynamicCommentList====" + conBeen.get(0).getContent());
                        if (conBeen != null) {
                            commentListDatas.addAll(conBeen);
                        }
                        commentAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int isPraise = dynamicDatas.getIsPraise();
        switch (view.getId()){
            case R.id.mButton_dynamicDetails_comment:
                Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mRoundedImageView_dynamicDetails_contentPic:
                Toast.makeText(this, "图片", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DynamicDetailsActivity.this, ShowPhotoActivity.class);
                Bundle bundle = new Bundle();
                List<AnchorDynamicListBean.ConBean.ImgListBean> imgList = dynamicDatas.getImgList();
                bundle.putSerializable("imgList", (Serializable) imgList);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mTextView_dynamicDetails_praiseCount:
                if (isPraise == 0){ //当前登录的用户未为当前动态点赞
                    iv_dynamicDetails_praise.setImageResource(R.mipmap.icon_good_selected);
                } else { //当前登录的用户已为当前动态点赞
                    iv_dynamicDetails_praise.setImageResource(R.mipmap.icon_good);
                }
                break;
            case R.id.mImageView_dynamicDetails_praise:
                if (isPraise == 0){ //当前登录的用户未为当前动态点赞
                    iv_dynamicDetails_praise.setImageResource(R.mipmap.icon_good_selected);
                } else { //当前登录的用户已为当前动态点赞
                    iv_dynamicDetails_praise.setImageResource(R.mipmap.icon_good);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                urv_dynamic_commentList.setRefreshing(false);
                commentListDatas.clear();
                getDynamicCommentList();
            }
        }, 2000);
    }
}
