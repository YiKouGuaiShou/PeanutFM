package com.yikouguaishou.peanutfm.fragment.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.SortBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/29.
 */
public class SortFragmentAdapter extends UltimateViewAdapter<SortFragmentAdapter.SortViewHolder> {

    private List<SortBean.ConBean> sortDatas = new ArrayList<>();
    private Context context;

    public SortFragmentAdapter(Context context, List<SortBean.ConBean> sortDatas) {
        this.context = context;
        this.sortDatas = sortDatas;
    }

    private onSortItemClickListener itemClickListener = null;

    public interface onSortItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(onSortItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class SortViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_sort_logo;
        TextView tv_sort_name;

        public SortViewHolder(View itemView) {
            super(itemView);
            iv_sort_logo = (ImageView) itemView.findViewById(R.id.iv_sort_logo);
            tv_sort_name = (TextView) itemView.findViewById(R.id.tv_sort_name);

            itemView.setOnClickListener(new View.OnClickListener() {   //为每一个item绑定监听
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
        }
    }

    @Override
    public SortViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.sort_item, null);
        SortViewHolder sortViewHolder = new SortViewHolder(view);
        return sortViewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return sortDatas == null ? 0 : sortDatas.size();
    }

    @Override
    public void onBindViewHolder(SortViewHolder holder, int position) {
        SortBean.ConBean conBean = sortDatas.get(position);
        String name = conBean.getName();
        holder.tv_sort_name.setText(name);
        String logo = conBean.getLogo();
        Glide.with(context)
                .load(logo)
                .into(holder.iv_sort_logo);
    }


    @Override
    public SortViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public SortViewHolder newHeaderHolder(View view) {
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
