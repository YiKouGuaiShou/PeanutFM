package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.SortDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/30.
 */
public class SortDetailsAdapter extends UltimateViewAdapter<SortDetailsAdapter.SortDetailsViewHolder> {
    private Context context;
    private List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas = new ArrayList<>();

    public SortDetailsAdapter(Context context, List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas) {
        this.context = context;
        this.sortDetailsDatas = sortDetailsDatas;
    }

    private onSortItemClickListener itemClickListener = null;

    public interface onSortItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(onSortItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class SortDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_sortDetails_logo;
        TextView tv_sortDetails_name;
        TextView tv_sortDetails_descriptions;

        public SortDetailsViewHolder(View itemView) {
            super(itemView);
            iv_sortDetails_logo = (ImageView) itemView.findViewById(R.id.iv_sortDetails_logo);
            tv_sortDetails_name = (TextView) itemView.findViewById(R.id.tv_sortDetails_name);
            tv_sortDetails_descriptions = (TextView) itemView.findViewById(R.id.tv_sortDetails_descriptions);

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
    public SortDetailsViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.sort_details_item, null);
        SortDetailsViewHolder sortDetailsViewHolder = new SortDetailsViewHolder(view);
        return sortDetailsViewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return sortDetailsDatas == null ? 0 : sortDetailsDatas.size();
    }

    @Override
    public void onBindViewHolder(SortDetailsViewHolder holder, int position) {
        SortDetailsBean.ConBean.DetailListBean detailListBean = sortDetailsDatas.get(position);
        String name = detailListBean.getName();
        holder.tv_sortDetails_name.setText(name);
        String descriptions = detailListBean.getDescriptions();
        holder.tv_sortDetails_descriptions.setText(descriptions);
        String logo = detailListBean.getLogo();
        Glide.with(context)
                .load(logo)
                .into(holder.iv_sortDetails_logo);
    }


    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public SortDetailsViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public SortDetailsViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }
}
