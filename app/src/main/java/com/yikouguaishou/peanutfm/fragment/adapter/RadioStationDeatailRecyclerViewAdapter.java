package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.RadioStationBannerBean;
import com.yikouguaishou.peanutfm.bean.RadioStationBean;
import com.yikouguaishou.peanutfm.view.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class RadioStationDeatailRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private List<RadioStationBean.ConBeanX> radioStationData;
    private List<RadioStationBannerBean> radioStationBannerData;

    List<String> images = new ArrayList<>();

    private static final int TYPE_BANNER_LAYOUT = 0;

    public RadioStationDeatailRecyclerViewAdapter(Context ctx, List<RadioStationBean.ConBeanX> radioStationData) {
        this.ctx = ctx;
        this.radioStationData = radioStationData;
    }

    public void setRadioStationBannerData(List<RadioStationBannerBean> radioStationBannerData) {
        this.radioStationBannerData = radioStationBannerData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER_LAYOUT) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.rv_item_radio_station_detail_banner, parent, false);
            return new MyRadioStationDetailBannerViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER_LAYOUT) {
            MyRadioStationDetailBannerViewHolder holder1 = (MyRadioStationDetailBannerViewHolder) holder;

            for (int i = 0; i < radioStationBannerData.get(0).getBannerList().size(); i++) {
                String url = radioStationBannerData.get(0).getBannerList().get(i).getUrl();
                if (images.size() < radioStationBannerData.get(0).getBannerList().size()) {
                    images.add(url);
                }
            }

            //设置图片集合
            holder1.banner_radio_station_detail.setImages(images);
            //设置banner样式
            holder1.banner_radio_station_detail.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            holder1.banner_radio_station_detail.setImageLoader(new MyImageLoader());
            //设置banner动画效果
            holder1.banner_radio_station_detail.setBannerAnimation(Transformer.RotateDown);
            //设置自动轮播，默认为true
            holder1.banner_radio_station_detail.isAutoPlay(true);
            //设置轮播时间
            holder1.banner_radio_station_detail.setDelayTime(4000);
            //设置指示器位置（当banner模式中有指示器时）
            holder1.banner_radio_station_detail.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            holder1.banner_radio_station_detail.start();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER_LAYOUT;
        } else {
            return 0;
        }
    }

    class MyRadioStationDetailBannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner_radio_station_detail;

        public MyRadioStationDetailBannerViewHolder(View itemView) {
            super(itemView);
            banner_radio_station_detail = (Banner) itemView.findViewById(R.id.banner_radio_station_detail);
        }
    }
}
