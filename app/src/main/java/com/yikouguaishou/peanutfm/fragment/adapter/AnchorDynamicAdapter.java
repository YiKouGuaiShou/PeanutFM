package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.ShowPhotoActivity;
import com.yikouguaishou.peanutfm.bean.AnchorDynamicListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snowflake on 2017/1/6.
 */
public class AnchorDynamicAdapter extends UltimateViewAdapter<AnchorDynamicAdapter.DynamicViewHolder> {
    private Context context;
    private List<AnchorDynamicListBean.ConBean> dynamicDatas = new ArrayList<>();

    public AnchorDynamicAdapter(Context context, List<AnchorDynamicListBean.ConBean> dynamicDatas) {
        this.context = context;
        this.dynamicDatas = dynamicDatas;
    }

    private OnDynamicItemClickListener onDynamicItemClickListener;



    public void setOnDynamicItemClickListener(OnDynamicItemClickListener onProductItemClickListener) {
        this.onDynamicItemClickListener = onProductItemClickListener;
    }

    public interface OnDynamicItemClickListener {
        void onItemClick(View v, int position);
    }

    public class DynamicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView civ_anchorDynamic_photo;
        TextView tv_anchorDynamic_name, tv_anchorDynamic_content, tv_anchorDynamic_creatTime, tv_anchorDynamic_praiseCount, tv_anchorDynamic_commentNum;
        RoundedImageView riv_anchorDynamic_contentPic;

        public DynamicViewHolder(View itemView) {
            super(itemView);
            civ_anchorDynamic_photo = (CircleImageView) itemView.findViewById(R.id.mCircleImageView_anchor_dynamic_photo);
            riv_anchorDynamic_contentPic = (RoundedImageView) itemView.findViewById(R.id.mRoundedImageView_anchorDynamic_contentPic);
            tv_anchorDynamic_name = (TextView) itemView.findViewById(R.id.mTextView_anchorDynamic_name);
            tv_anchorDynamic_content = (TextView) itemView.findViewById(R.id.mTextView_anchorDynamic_content);
            tv_anchorDynamic_creatTime = (TextView) itemView.findViewById(R.id.mTextView_anchorDynamic_createTime);
            tv_anchorDynamic_praiseCount = (TextView) itemView.findViewById(R.id.mTextView_anchorDynamic_praiseCount);
            tv_anchorDynamic_commentNum = (TextView) itemView.findViewById(R.id.mTextView_anchorDynamic_commentNum);

            itemView.setOnClickListener(this);
            riv_anchorDynamic_contentPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowPhotoActivity.class);
                    Bundle bundle = new Bundle();
                    List<AnchorDynamicListBean.ConBean.ImgListBean> imgList = dynamicDatas.get(0).getImgList();
                    bundle.putSerializable("imgList", (Serializable) imgList);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (onDynamicItemClickListener != null) {
                onDynamicItemClickListener.onItemClick(view, getPosition());
            }
        }
    }


    @Override
    public AnchorDynamicAdapter.DynamicViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_item, null);
        return new DynamicViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return dynamicDatas == null ? 0 : dynamicDatas.size();
    }

    @Override
    public void onBindViewHolder(AnchorDynamicAdapter.DynamicViewHolder holder, int position) {
        AnchorDynamicListBean.ConBean conBean = dynamicDatas.get(position);
        String anchorName = conBean.getAnchorName();
        holder.tv_anchorDynamic_name.setText(anchorName);
        String content = conBean.getContent();
        holder.tv_anchorDynamic_content.setText(content);
        String createTime = conBean.getCreateTime();
        holder.tv_anchorDynamic_creatTime.setText(createTime);
        int praiseCount = conBean.getPraiseCount();
        holder.tv_anchorDynamic_praiseCount.setText("" + praiseCount);
        int commentNum = conBean.getCommentNum();
        holder.tv_anchorDynamic_commentNum.setText("" + commentNum);

        String anchorImg = conBean.getAnchorImg();
        if (anchorImg != null) {
            Glide.with(context)
                    .load(anchorImg)
                    .into(holder.civ_anchorDynamic_photo);
        }
        String imgUrl = conBean.getImgList().get(0).getUrl();
        if (imgUrl != null) {
            Glide.with(context)
                    .load(imgUrl)
                    .into(holder.riv_anchorDynamic_contentPic);
        }
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
