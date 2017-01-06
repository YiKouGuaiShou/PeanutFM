package com.yikouguaishou.peanutfm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yikouguaishou.peanutfm.apiservice.RadioStationAPIService;
import com.yikouguaishou.peanutfm.bean.AnchorPersonInfoBean;
import com.yikouguaishou.peanutfm.fragment.AnchorDynamicFragment;
import com.yikouguaishou.peanutfm.fragment.AnchorProductFragment;
import com.yikouguaishou.peanutfm.fragment.adapter.AnchorHomeFragmentAdapter;

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

public class AnchorHomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private String baseUrl = "http://fsapp.linker.cc";
    private String userId = "";
    private String anchorId;

    private CircleImageView civ_anchor_photo;
    private ImageView btn_back, btn_share;
    private TextView tv_anchor_nickname, tv_anchor_followNum, tv_anchor_follow;
    private RadioGroup rgp_anchorHome;
    private RadioButton rb_anchor_dynamicList;

    private List<Fragment> curFragment;
    private ViewPager viewPager_anchorHome;
    private AnchorHomeFragmentAdapter fragmentAdapter;
    private String anchorpersonId;
    private AnchorDynamicFragment dynamicFragment;
    private AnchorProductFragment productFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anchor_home);
        Bundle bundle = getIntent().getExtras();
        anchorpersonId = bundle.getString("anchorId");
        String anchorpersonName = bundle.getString("anchorpersonName");
        String anchorpersonPic = bundle.getString("anchorpersonPic");


        initViews();
        initFragment();
        setListener();
        if (bundle != null) {
            tv_anchor_nickname.setText(anchorpersonName);
            Glide.with(this)
                    .load(anchorpersonPic)
                    .crossFade()
                    .into(civ_anchor_photo);
            this.anchorId = bundle.getString("anchorId");
            getAnchorPersonInfo();
        }

    }

    private void initViews() {
        civ_anchor_photo = (CircleImageView) findViewById(R.id.mCircleImageView_anchor_photo);
        btn_back = (ImageView) findViewById(R.id.mButton_anchor_back);
        btn_share = (ImageView) findViewById(R.id.mButton_anchor_share);
        tv_anchor_nickname = (TextView) findViewById(R.id.mTextView_anchor_nickname);
        tv_anchor_followNum = (TextView) findViewById(R.id.mTextView_anchor_focusNum);
        tv_anchor_follow = (TextView) findViewById(R.id.mTextView_anchor_focus);
        rgp_anchorHome = (RadioGroup) findViewById(R.id.mRadioGroup_anchorHome);
        rb_anchor_dynamicList = (RadioButton) findViewById(R.id.mRadioButton_anchorHome_dynamic);
        rb_anchor_dynamicList.setChecked(true);
        viewPager_anchorHome = (ViewPager) findViewById(R.id.mViewPager_anchorHome);

    }

    private void setListener() {
        btn_back.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        tv_anchor_follow.setOnClickListener(this);
        rgp_anchorHome.setOnCheckedChangeListener(this);
        viewPager_anchorHome.addOnPageChangeListener(this);
    }

    /**
     * 添加碎片
     */
    private void initFragment() {
        curFragment = new ArrayList<>();
        dynamicFragment = new AnchorDynamicFragment(anchorpersonId);
        curFragment.add(dynamicFragment);
        productFragment = new AnchorProductFragment(anchorpersonId);
        curFragment.add(productFragment);

        fragmentAdapter = new AnchorHomeFragmentAdapter(getSupportFragmentManager(), curFragment);
        viewPager_anchorHome.setAdapter(fragmentAdapter);
    }

    /**
     * 获取主播信息
     */
    private void getAnchorPersonInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RadioStationAPIService radioStationAPIService = retrofit.create(RadioStationAPIService.class);

        Observable<AnchorPersonInfoBean> anchorInfoBean = radioStationAPIService.getAnchorPersonInfo(anchorId, userId);

        anchorInfoBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AnchorPersonInfoBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("======onError===", "===AnchorHome===" + e.getMessage());
                        getAnchorPersonInfo();
                    }

                    @Override
                    public void onNext(AnchorPersonInfoBean anchorPersonInfoBean) {
                        AnchorPersonInfoBean.EntityBean entity = anchorPersonInfoBean.getEntity();
                        Log.e("======onNext===", "===AnchorHome==AnchorpersonId==" + entity.getAnchorpersonId());
                        Log.e("======onNext===", "===AnchorHome==AnchorpersonName==" + entity.getAnchorpersonName());

                        int fansNum = entity.getFansNum();
                        tv_anchor_followNum.setText("关注 " + fansNum);
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for (int i = 0; i < rgp_anchorHome.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rgp_anchorHome.getChildAt(i);
            if (radioButton.isChecked()) {
                viewPager_anchorHome.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        RadioButton rb = (RadioButton) rgp_anchorHome.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mButton_anchor_back:
                finish();
                break;
            case R.id.mButton_anchor_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mTextView_anchor_focus:
                if (tv_anchor_follow.getText().toString().equals("+ 关注")) {
                    tv_anchor_follow.setText("已关注");
                }
                break;
        }
    }
}