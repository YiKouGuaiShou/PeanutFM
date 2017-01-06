package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.AnchorProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowflake on 2017/1/6.
 */
public class AnchorProductAdapter extends UltimateViewAdapter<AnchorProductAdapter.ProductViewHolder> {
    private List<AnchorProductBean.ConBean> productDatas = new ArrayList<>();
    private Context context;

    public AnchorProductAdapter(Context context, List<AnchorProductBean.ConBean> productDatas) {
        this.context = context;
        this.productDatas = productDatas;
    }

    private OnProductItemClickListener onProductItemClickListener;

    public void setOnProductItemClickListener(OnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    public interface OnProductItemClickListener {
        void onItemClick(View v, int position);
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RoundedImageView riv_product_logo;
        TextView tv_product_name, tv_product_songName;

        public ProductViewHolder(View itemView) {
            super(itemView);
            riv_product_logo = (RoundedImageView) itemView.findViewById(R.id.mRoundedImageView_product_logo);
            tv_product_name = (TextView) itemView.findViewById(R.id.mTextView_product_name);
            tv_product_songName = (TextView) itemView.findViewById(R.id.mTextView_product_songName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onProductItemClickListener != null) {
                onProductItemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    @Override
    public AnchorProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return productDatas == null ? 0 : productDatas.size();
    }


    @Override
    public void onBindViewHolder(AnchorProductAdapter.ProductViewHolder holder, int position) {
        AnchorProductBean.ConBean conBean = productDatas.get(position);
        String name = conBean.getColumnName();
        holder.tv_product_name.setText(name);
        String songName = conBean.getSongName();
        holder.tv_product_songName.setText("更新至：" + songName);
        String columnCover = conBean.getColumnCover();
        if (columnCover != null) {
            Glide.with(context)
                    .load(columnCover)
                    .crossFade()
                    .into(holder.riv_product_logo);
        }
    }

    @Override
    public AnchorProductAdapter.ProductViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public AnchorProductAdapter.ProductViewHolder newHeaderHolder(View view) {
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
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {  }

}
