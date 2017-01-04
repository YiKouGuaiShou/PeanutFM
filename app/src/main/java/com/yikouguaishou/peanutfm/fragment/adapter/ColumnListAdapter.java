package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.ColumnListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2016/12/30.
 */
public class ColumnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ColumnListBean.ConBean> columnListDatas = new ArrayList<>();
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private View headerRecycleView;

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public ColumnListAdapter(Context context, List<ColumnListBean.ConBean> columnListDatas) {
        this.context = context;
        this.columnListDatas = columnListDatas;
    }

    public View getHeaderRecycleView() {
        return headerRecycleView;
    }

    public void setHeaderRecycleView(View headerRecycleView) {
        this.headerRecycleView = headerRecycleView;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (headerRecycleView == null) {
            return ITEM_TYPE_CONTENT;
        }
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return ITEM_TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return headerRecycleView == null ? columnListDatas.size() : columnListDatas.size() + 1;
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_columnList_count;
        ImageView iv_columnList_sort;
        RelativeLayout rl_sort;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_columnList_count = (TextView) itemView.findViewById(R.id.mTextView_columnList_count);
            iv_columnList_sort = (ImageView) itemView.findViewById(R.id.mImageView_columnList_sort);
            rl_sort = (RelativeLayout) itemView.findViewById(R.id.rl_sort);
        }

    }

    //内容 ViewHolder
    public class ColumnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RoundedImageView iv_column_logo;
        ImageView iv_column_download;
        TextView tv_column_name, tv_column_replyNum, tv_column_listenNum, tv_column_duration;

        public ColumnViewHolder(View itemView) {
            super(itemView);

            iv_column_logo = (RoundedImageView) itemView.findViewById(R.id.mImageView_column_logo);
            iv_column_download = (ImageView) itemView.findViewById(R.id.mImageView_column_download);
            tv_column_name = (TextView) itemView.findViewById(R.id.mTextView_column_name);
            tv_column_replyNum = (TextView) itemView.findViewById(R.id.mTextView_column_comment);
            tv_column_listenNum = (TextView) itemView.findViewById(R.id.mTextView_column_listen);
            tv_column_duration = (TextView) itemView.findViewById(R.id.mTextView__column_duration);

            iv_column_download.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "下载", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerRecycleView != null && viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(headerRecycleView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.columnlist_item, parent, false);
        return new ColumnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_CONTENT) {
            ColumnListBean.ConBean conBean = columnListDatas.get(position - 1);
            if (holder instanceof ColumnViewHolder) {
                String name = conBean.getName();
                ((ColumnViewHolder) holder).tv_column_name.setText(name);
                int replyNum = conBean.getReplyNum();
                String comments = String.valueOf(replyNum);
                ((ColumnViewHolder) holder).tv_column_replyNum.setText(comments);
                int listenNum = conBean.getListenNum();
                String listens = String.valueOf(listenNum);
                ((ColumnViewHolder) holder).tv_column_listenNum.setText(listens);
                String duration = conBean.getDuration();
                ((ColumnViewHolder) holder).tv_column_duration.setText(duration);

                String logoUrl = conBean.getLogoUrl();
                Glide.with(context)
                        .load(logoUrl)
                        .into(((ColumnViewHolder) holder).iv_column_logo);
                return;
            }else if (getItemViewType(position) == ITEM_TYPE_HEADER) {
                return;
            }
            return;
        }

    }
}
