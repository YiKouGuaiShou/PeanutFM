package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.SortDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/30.
 */
public class SortDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas = new ArrayList<>();
    private View headerSortDetails;
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_NORMAL = 1;

    public SortDetailsAdapter(Context context, List<SortDetailsBean.ConBean.DetailListBean> sortDetailsDatas) {
        this.context = context;
        this.sortDetailsDatas = sortDetailsDatas;
    }

    private OnSortDetailsItemClickListener mItemClickListener = null;

    public interface OnSortDetailsItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnSortDetailsItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public View getHeaderSortDetails() {
        return headerSortDetails;
    }

    public void setHeaderSortDetails(View headerSortDetails) {
        this.headerSortDetails = headerSortDetails;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_TYPE_HEADER : ITEM_TYPE_NORMAL;
    }

    //判断是否是头
    public boolean isHeader(int position){
        return position == 0;
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sortDetails_name;
        TextView tv_sortDetails_more;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_sortDetails_name = (TextView) itemView.findViewById(R.id.mTextView_sortDetails_name);
            tv_sortDetails_more = (TextView) itemView.findViewById(R.id.mTextView_sortDetails_more);
        }
    }

    public class SortDetailsViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_sortDetails_logo;
        TextView tv_sortDetails_name;
        TextView tv_sortDetails_descriptions;

        public SortDetailsViewHolder(View itemView) {
            super(itemView);
            iv_sortDetails_logo = (RoundedImageView) itemView.findViewById(R.id.iv_sortDetails_logo);
            tv_sortDetails_name = (TextView) itemView.findViewById(R.id.tv_sortDetails_name);
            tv_sortDetails_descriptions = (TextView) itemView.findViewById(R.id.tv_sortDetails_descriptions);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, getPosition());
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerSortDetails != null && viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(headerSortDetails);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_details_item, parent, false);
        return new SortDetailsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return headerSortDetails == null ? sortDetailsDatas.size() : sortDetailsDatas.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_NORMAL) {
            SortDetailsBean.ConBean.DetailListBean detailListBean = sortDetailsDatas.get(position - 1);
            if (holder instanceof SortDetailsViewHolder) {
                String name = detailListBean.getName();
                ((SortDetailsViewHolder) holder).tv_sortDetails_name.setText(name);
                String descriptions = detailListBean.getDescriptions();
                ((SortDetailsViewHolder) holder).tv_sortDetails_descriptions.setText(descriptions);
                String logo = detailListBean.getLogo();
                Glide.with(context)
                        .load(logo)
                        .crossFade()
                        .into(((SortDetailsViewHolder) holder).iv_sortDetails_logo);
                return;
            }else if (getItemViewType(position) == ITEM_TYPE_HEADER){
                return;
            }
            return;
        }
    }
}
