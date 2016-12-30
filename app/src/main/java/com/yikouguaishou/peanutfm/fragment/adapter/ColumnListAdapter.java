package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/30.
 */
public class ColumnListAdapter extends UltimateViewAdapter<ColumnListAdapter.ColumnViewHolder> {
    private Context context;
    private List<ColumnListBean.ConBean> columnListDatas = new ArrayList<>();

    public ColumnListAdapter(Context context, List<ColumnListBean.ConBean> columnListDatas) {
        this.context = context;
        this.columnListDatas = columnListDatas;
    }

    @Override
    public ColumnViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ColumnViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ColumnViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ColumnViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ColumnViewHolder extends RecyclerView.ViewHolder {
        public ColumnViewHolder(View itemView) {
            super(itemView);
        }
    }
}
