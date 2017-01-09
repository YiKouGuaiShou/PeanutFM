package com.yikouguaishou.peanutfm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;
import com.yikouguaishou.peanutfm.view.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class ShowPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private List<AnchorDynamicListBean.ConBean.ImgListBean> imgList;
    List<String> images = new ArrayList<>();
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        banner = (Banner) findViewById(R.id.mBanner_showPhoto);
        Bundle bundle = getIntent().getExtras();
        imgList = (List<AnchorDynamicListBean.ConBean.ImgListBean>) bundle.getSerializable("imgList");
        //循环获取图片。
        for (int i = 0; i < imgList.size(); i++) {
            AnchorDynamicListBean.ConBean.ImgListBean imgListBean = imgList.get(i);
            //避免重复加载图片。
            if (images.size() < imgList.size()) {
                images.add(imgListBean.getUrl());
            }
        }
        //设置图片集合
        banner.setImages(images);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new MyImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.RotateUp);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
