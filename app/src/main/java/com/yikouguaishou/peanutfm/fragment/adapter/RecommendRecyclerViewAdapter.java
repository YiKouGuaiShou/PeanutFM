package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.load.data.ExifOrientationStream;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.dragsortadapter.DragSortAdapter;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.rendering.HeaderRenderer;
import com.yikouguaishou.peanutfm.R;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
public class RecommendRecyclerViewAdapter extends UltimateViewAdapter {
    private Context context;
    private List<String> lists = new ArrayList<>();

    public RecommendRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setdata(List<String> lists) {
        this.lists = lists;
        Log.e("listsize", "=" + lists.size());
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
        return lists.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeadViewHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

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
        public Banner mBanner;
        public LinearLayout mTurnOne, mTurnTwo, mTurnThree, mTurnFour;
        public ImageView mIvOne, mIvTwo, mIvThree, mIvFour;
        public TextView mTvOne, mTvTwo, mTvThree, mTvFour;

        public HeadViewHolder(View mHeard) {
            super(mHeard);
            mBanner = (Banner) mHeard.findViewById(R.id.mBanner);

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
}
