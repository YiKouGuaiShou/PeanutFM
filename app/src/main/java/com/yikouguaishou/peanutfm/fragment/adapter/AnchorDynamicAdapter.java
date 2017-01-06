package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2017/1/6.
 */
public class AnchorDynamicAdapter extends UltimateViewAdapter<AnchorDynamicAdapter.DynamicViewHolder> {
    private Context context;
    private List<AnchorDynamicListBean> dynamicDatas = new ArrayList<>();

    public AnchorDynamicAdapter(Context context, List<AnchorDynamicListBean> dynamicDatas) {
        this.context = context;
        this.dynamicDatas = dynamicDatas;
    }

    public class DynamicViewHolder extends RecyclerView.ViewHolder {

        public DynamicViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public AnchorDynamicAdapter.DynamicViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(AnchorDynamicAdapter.DynamicViewHolder holder, int position) {

    }



    @Override
    public DynamicViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public DynamicViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

}
