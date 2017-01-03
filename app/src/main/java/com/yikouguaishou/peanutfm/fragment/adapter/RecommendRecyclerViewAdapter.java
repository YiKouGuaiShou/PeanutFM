package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.AdviceActivity;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.TurnOneActivity;
import com.yikouguaishou.peanutfm.TypeOneMoreActivity;
import com.yikouguaishou.peanutfm.TypeThreeMoreActivity;
import com.yikouguaishou.peanutfm.TypeZeroMoreActivity;
import com.yikouguaishou.peanutfm.bean.RecommendBean;
import com.yikouguaishou.peanutfm.view.MyImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
public class RecommendRecyclerViewAdapter extends UltimateViewAdapter {
    private Context context;
    private RecommendBean recommendBean = new RecommendBean();
    private List<RecommendBean.ConEntity> conList = new ArrayList<>();
    private List<RecommendBean.BannerListEntity> bannerList = new ArrayList<>();
    private List<RecommendBean.TurnListEntity> turnList = new ArrayList<>();
    List<String> images = new ArrayList<>();
    public static final int TYPE_ZERO_LAYOUT = 0;
    public static final int TYPE_ONE_LAYOUT = 1;
    public static final int TYPE_TWO_LAYOUT = 2;
    public static final int TYPE_THREE_LAYOUT = 3;
    public static final int TYPE_BANNER_LAYOUT = 4;

    public RecommendRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setdata(RecommendBean recommendBean) {
        this.recommendBean = recommendBean;
        notifyDataSetChanged();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public int getAdapterItemCount() {
        if (null != recommendBean) {
            return recommendBean.getCon().size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER_LAYOUT;
        } else {
            RecommendBean.ConEntity conEntity = recommendBean.getCon().get(position - 1);
            int layout = conEntity.getLayout();
            if (layout == TYPE_THREE_LAYOUT) {
                return TYPE_THREE_LAYOUT;
            } else if (layout == TYPE_TWO_LAYOUT) {
                return TYPE_TWO_LAYOUT;
            } else if (layout == TYPE_ONE_LAYOUT) {
                return TYPE_ONE_LAYOUT;
            } else {
                return TYPE_ZERO_LAYOUT;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER_LAYOUT) {
            //头部布局
            View view = LayoutInflater.from(context).inflate(R.layout.recommend_head_banner, parent, false);
            return new HeadViewHolder(view);
        } else if (viewType == TYPE_THREE_LAYOUT) {
            //布局3
            View view = LayoutInflater.from(context).inflate(R.layout.typethree_layout, parent, false);
            return new TypeThreeHolder(view, context);
        } else if (viewType == TYPE_TWO_LAYOUT) {
            //布局2
            View view = LayoutInflater.from(context).inflate(R.layout.typetwo_layout, parent, false);
            return new TypeTwoHolder(view);
        } else if (viewType == TYPE_ONE_LAYOUT) {
            //布局1
            View view = LayoutInflater.from(context).inflate(R.layout.typeone_layout, parent, false);
            return new TypeOneHolder(view, context);
        } else {
            //布局0
            View view = LayoutInflater.from(context).inflate(R.layout.typezero_layout, parent, false);
            return new TypeZeroHolder(view, context);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER_LAYOUT) {
            //头部布局
            HeadViewHolder holder4 = (HeadViewHolder) holder;
            List<RecommendBean.BannerListEntity> bannerList = recommendBean.getBannerList();
            //设置图片点击事件。
            holder4.banner.setOnBannerClickListener(new BannerClickListener(context, bannerList));
            //设置"活动点击事件"。
            holder4.mTurnOne.setOnClickListener(new TurnOneClickListener(context, recommendBean.getTurnList().get(0)));
            //设置"视频点击事件"。
            holder4.mTurnTwo.setOnClickListener(new TurnTwoClikeListener(context, recommendBean.getTurnList().get(1)));
            //设置"商城点击事件"。
            holder4.mTurnThree.setOnClickListener(new TurnThreeClikeListener(context, recommendBean.getTurnList().get(2)));
            //设置"签到点击事件"。
            holder4.mTurnFour.setOnClickListener(new TurnFourClikeListener(context, recommendBean.getTurnList().get(3)));
            //循环获取图片。
            for (int i = 0; i < bannerList.size(); i++) {
                RecommendBean.BannerListEntity bannerListEntity = bannerList.get(i);
                //避免重复加载图片。
                if (images.size() < bannerList.size()) {
                    images.add(bannerListEntity.getUrl());
                }
            }
            //设置图片集合
            holder4.banner.setImages(images);
            //设置banner样式
            holder4.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            holder4.banner.setImageLoader(new MyImageLoader());
            //设置banner动画效果
            holder4.banner.setBannerAnimation(Transformer.RotateDown);
            //设置自动轮播，默认为true
            holder4.banner.isAutoPlay(true);
            //设置轮播时间
            holder4.banner.setDelayTime(4000);
            //设置指示器位置（当banner模式中有指示器时）
            holder4.banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            holder4.banner.start();

            //设置turn 图片 就是(活动，视频。。。)
            List<RecommendBean.TurnListEntity> turnList = recommendBean.getTurnList();
            for (int i = 0; i < turnList.size(); i++) {
                if (i == 0) {
                    //活动
                    RecommendBean.TurnListEntity turnListEntity = turnList.get(i);
                    Glide.with(context).load(turnListEntity.getIcon()).placeholder(R.mipmap.place_holder).into(holder4.mIvOne);
                    holder4.mTvOne.setText(turnListEntity.getTitle());
                } else if (i == 1) {
                    //视频
                    RecommendBean.TurnListEntity turnListEntity = turnList.get(i);
                    Glide.with(context).load(turnListEntity.getIcon()).placeholder(R.mipmap.place_holder).into(holder4.mIvTwo);
                    holder4.mTvTwo.setText(turnListEntity.getTitle());
                } else if (i == 2) {
                    //商城
                    RecommendBean.TurnListEntity turnListEntity = turnList.get(i);
                    Glide.with(context).load(turnListEntity.getIcon()).placeholder(R.mipmap.place_holder).into(holder4.mIvThree);
                    holder4.mTvThree.setText(turnListEntity.getTitle());
                } else {
                    //签到
                    RecommendBean.TurnListEntity turnListEntity = turnList.get(i);
                    Glide.with(context).load(turnListEntity.getIcon()).placeholder(R.mipmap.place_holder).into(holder4.mIvFour);
                    holder4.mTvFour.setText(turnListEntity.getTitle());
                }
            }

        } else if (viewType == TYPE_THREE_LAYOUT) {
            //布局3
            TypeThreeHolder holder3 = (TypeThreeHolder) holder;
            RecommendBean.ConEntity conEntity = recommendBean.getCon().get(position - 1);
            String name = conEntity.getName();
            holder3.tvMore3.setText("更多 >");
            //设置监听。
            holder3.tvMore3.setOnClickListener(new TypeThreeMoreListener(context, conEntity));
            holder3.tvTitle3.setText(name);
            holder3.tvTitle3.setTextColor(context.getResources().getColor(R.color.colorYellow));
            holder3.typeThreeAdapter.setDetailListEntities(conEntity.getDetailList());
        } else if (viewType == TYPE_TWO_LAYOUT) {
            //布局2
            RecommendBean.ConEntity conEntity = recommendBean.getCon().get(position - 1);
            List<RecommendBean.ConEntity.DetailListEntity> detailList = conEntity.getDetailList();
            TypeTwoHolder holder2 = (TypeTwoHolder) holder;
            holder2.tv_typeTwo.setText(detailList.get(0).getName());
            //设置监听。
            holder2.iv_typeTwo.setOnClickListener(new TypeThreeMoreListener(context, conEntity));
            //下载图片。
            Glide.with(context).load(detailList.get(0).getLogo()).into(holder2.iv_typeTwo);
        } else if (viewType == TYPE_ONE_LAYOUT) {
            //布局1
            TypeOneHolder holder1 = (TypeOneHolder) holder;
            RecommendBean.ConEntity conEntity = recommendBean.getCon().get(position - 1);
            Log.e("TypeOneHolder", "conEntuty.getname()" + conEntity.getName());
            String name = conEntity.getName();
            //设置监听。
            holder1.tvMore1.setOnClickListener(new TypeThreeMoreListener(context, conEntity));
            holder1.tvMore1.setText("更多 >");
            holder1.tvTitle1.setText(name);
            holder1.tvTitle1.setTextColor(context.getResources().getColor(R.color.colorYellow));
            holder1.typeOneAdapter.setDetailListEntities(conEntity.getDetailList());
        } else {
            //布局0
            TypeZeroHolder holder0 = (TypeZeroHolder) holder;
            RecommendBean.ConEntity conEntity = recommendBean.getCon().get(position - 1);
            String name = conEntity.getName();
            //设置监听。
            holder0.tvMore0.setOnClickListener(new TypeThreeMoreListener(context, conEntity));
            holder0.tvMore0.setText("更多 >");
            holder0.tvTitle0.setText(name);
            holder0.tvTitle0.setTextColor(context.getResources().getColor(R.color.colorYellow));
            //刷新数据。
            holder0.typeZeroAdapter.setDetailListEntities(conEntity.getDetailList());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    /**
     * 头部布局viewholder
     */
    private static class HeadViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public LinearLayout mTurnOne, mTurnTwo, mTurnThree, mTurnFour;
        public ImageView mIvOne, mIvTwo, mIvThree, mIvFour;
        public TextView mTvOne, mTvTwo, mTvThree, mTvFour;

        public HeadViewHolder(View mHeard) {
            super(mHeard);
            banner = (Banner) mHeard.findViewById(R.id.mBanner);

            mTurnOne = (LinearLayout) mHeard.findViewById(R.id.turn_one);
            mIvOne = (ImageView) mHeard.findViewById(R.id.iv_one);
            mTvOne = (TextView) mHeard.findViewById(R.id.tv_one);

            mTurnTwo = (LinearLayout) mHeard.findViewById(R.id.turn_two);
            mIvTwo = (ImageView) mHeard.findViewById(R.id.iv_two);
            mTvTwo = (TextView) mHeard.findViewById(R.id.tv_two);

            mTurnThree = (LinearLayout) mHeard.findViewById(R.id.turn_three);
            mIvThree = (ImageView) mHeard.findViewById(R.id.iv_three);
            mTvThree = (TextView) mHeard.findViewById(R.id.tv_three);

            mTurnFour = (LinearLayout) mHeard.findViewById(R.id.turn_four);
            mIvFour = (ImageView) mHeard.findViewById(R.id.iv_four);
            mTvFour = (TextView) mHeard.findViewById(R.id.tv_four);
        }
    }

    /**
     * typeThree布局。
     */
    private static class TypeThreeHolder extends RecyclerView.ViewHolder {

        public RecyclerView recyvler3;
        public TextView tvTitle3;
        public TextView tvMore3;
        public List<RecommendBean.ConEntity.DetailListEntity> detailListEntities = new ArrayList<>();
        public Context context;
        public TypeThreeAdapter typeThreeAdapter;

        public TypeThreeHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvTitle3 = (TextView) itemView.findViewById(R.id.tv_title);
            tvMore3 = (TextView) itemView.findViewById(R.id.tv_more);
            recyvler3 = (RecyclerView) itemView.findViewById(R.id.recycler_typeThree);
            //设置LayoutManager.
            GridLayoutManager manager = new GridLayoutManager(context, 2);
            recyvler3.setLayoutManager(manager);
            typeThreeAdapter = new TypeThreeAdapter(context);
            typeThreeAdapter.setDetailListEntities(detailListEntities);
            recyvler3.setAdapter(typeThreeAdapter);
        }
    }

    /**
     * typeTwo布局。
     */
    private static class TypeTwoHolder extends RecyclerView.ViewHolder {
        public ImageView iv_typeTwo;
        public TextView tv_typeTwo;

        public TypeTwoHolder(View itemView) {
            super(itemView);
            iv_typeTwo = (ImageView) itemView.findViewById(R.id.iv_typetwo);
            tv_typeTwo = (TextView) itemView.findViewById(R.id.tv_typetwo);
        }
    }

    /**
     * typeOne布局。大玩家
     */
    private static class TypeOneHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyvler1;
        public TextView tvTitle1;
        public TextView tvMore1;
        public List<RecommendBean.ConEntity.DetailListEntity> detailListEntities = new ArrayList<>();
        public Context context;
        public TypeOneAdapter typeOneAdapter;

        public TypeOneHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvTitle1 = (TextView) itemView.findViewById(R.id.tv_title);
            tvMore1 = (TextView) itemView.findViewById(R.id.tv_more);
            recyvler1 = (RecyclerView) itemView.findViewById(R.id.recycler_typeOne);
            //设置LayoutManager.
            recyvler1.setLayoutManager(new LinearLayoutManager(context));
            typeOneAdapter = new TypeOneAdapter(context);
            typeOneAdapter.setDetailListEntities(detailListEntities);
            recyvler1.setAdapter(typeOneAdapter);
        }
    }

    /**
     * typeZero布局。
     */
    private static class TypeZeroHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyvler0;
        public TextView tvTitle0;
        public TextView tvMore0;
        public List<RecommendBean.ConEntity.DetailListEntity> detailListEntities = new ArrayList<>();
        public Context context;
        public TypeZeroAdapter typeZeroAdapter;

        public TypeZeroHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvTitle0 = (TextView) itemView.findViewById(R.id.tv_title);
            tvMore0 = (TextView) itemView.findViewById(R.id.tv_more);
            recyvler0 = (RecyclerView) itemView.findViewById(R.id.recycler_typeZero);
            //设置LayoutManager.
            GridLayoutManager manager = new GridLayoutManager(context, 3);
            recyvler0.setLayoutManager(manager);
            typeZeroAdapter = new TypeZeroAdapter(context);
            typeZeroAdapter.setDetailListEntities(detailListEntities);
            recyvler0.setAdapter(typeZeroAdapter);
        }
    }

    /**
     * 广告条点击事件。
     */
    private class BannerClickListener implements OnBannerClickListener {
        Context context;
        List<RecommendBean.BannerListEntity> bannerList;

        public BannerClickListener(Context context, List<RecommendBean.BannerListEntity> bannerList) {
            this.context = context;
            this.bannerList = bannerList;
        }

        @Override
        public void OnBannerClick(int position1) {
            int position = position1 - 1;
            String linkType = bannerList.get(position).getLinkType();
            if (linkType.equals("2")) {
                //表示连接的是webview。
                Intent intent = new Intent(context, AdviceActivity.class);
                context.startActivity(intent);
            } else {
                //表示是自定义布局。
                Toast.makeText(context, "linkType = " + linkType, Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * TurnOne （活动）的点击事件。
     */
    private class TurnOneClickListener implements View.OnClickListener {
        Context context;
        RecommendBean.TurnListEntity turnListEntity;

        public TurnOneClickListener(Context context, RecommendBean.TurnListEntity turnListEntity) {
            this.context = context;
            this.turnListEntity = turnListEntity;
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(context, "我是" + turnListEntity.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, TurnOneActivity.class);
            String title = turnListEntity.getTitle();
            intent.putExtra("title", title);
            context.startActivity(intent);
        }
    }

    /**
     * TurnTwo （视频）的点击事件。
     */
    private class TurnTwoClikeListener extends TurnOneClickListener {

        public TurnTwoClikeListener(Context context, RecommendBean.TurnListEntity turnListEntity) {
            super(context, turnListEntity);

        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
        }
    }

    /**
     * TurnThree （商城）的点击事件。
     */
    private class TurnThreeClikeListener extends TurnOneClickListener {

        public TurnThreeClikeListener(Context context, RecommendBean.TurnListEntity turnListEntity) {
            super(context, turnListEntity);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
        }
    }

    /**
     * TurnFour （签到）的点击事件。
     */
    private class TurnFourClikeListener extends TurnOneClickListener {

        public TurnFourClikeListener(Context context, RecommendBean.TurnListEntity turnListEntity) {
            super(context, turnListEntity);
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
        }
    }

    /**
     * "推荐页的'更多'"的点击事件。
     */
    private class TypeThreeMoreListener implements View.OnClickListener {
        Context context;
        int categoryId;
        int layout;
        String name;

        public TypeThreeMoreListener(Context context, RecommendBean.ConEntity conEntity) {
            this.context = context;
            this.categoryId = conEntity.getCategoryId();
            this.layout = conEntity.getLayout();
            this.name = conEntity.getName();
        }

        @Override
        public void onClick(View view) {
            switch (layout) {
                //跳到第3种类型的activity。
                case TYPE_THREE_LAYOUT:
                    // Toast.makeText(context, "我是布局" + layout + "\n我的CategoryId = " + categoryId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, TypeThreeMoreActivity.class);
                    intent.putExtra("categoryId", categoryId);
                    intent.putExtra("title", name);
                    context.startActivity(intent);
                    break;
                //跳到第2种类型的activity。
                case TYPE_TWO_LAYOUT:
                    Toast.makeText(context, "我是布局" + layout + "\n我的CategoryId = " + categoryId, Toast.LENGTH_SHORT).show();
                    break;
                //跳到第1种类型的activity。
                case TYPE_ONE_LAYOUT:
                    Intent intent1 = new Intent(context, TypeOneMoreActivity.class);
                    intent1.putExtra("categoryId", categoryId);
                    intent1.putExtra("title", name);
                    context.startActivity(intent1);
                    break;
                //跳到第0种类型的activity。
                case TYPE_ZERO_LAYOUT:
                    Intent intent0 = new Intent(context, TypeZeroMoreActivity.class);
                    intent0.putExtra("categoryId", categoryId);
                    intent0.putExtra("title", name);
                    context.startActivity(intent0);
                    break;
            }
        }
    }
}
