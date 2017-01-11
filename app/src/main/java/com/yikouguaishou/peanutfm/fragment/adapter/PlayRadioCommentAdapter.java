package com.yikouguaishou.peanutfm.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.yikouguaishou.peanutfm.R;
import com.yikouguaishou.peanutfm.bean.RadioPlayCommentBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/11.
 */
public class PlayRadioCommentAdapter extends UltimateViewAdapter {
    private List<RadioPlayCommentBean.ConEntity> commentList;
    private Context contex;

    public PlayRadioCommentAdapter(Context contex) {
        this.contex = contex;
    }

    public void setCommentList(List<RadioPlayCommentBean.ConEntity> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
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
        View view = LayoutInflater.from(contex).inflate(R.layout.radioplay_item_two, parent, false);
        return new PlayRadioCommentHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return commentList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlayRadioCommentHolder holder2 = (PlayRadioCommentHolder) holder;
        RadioPlayCommentBean.ConEntity conEntity = commentList.get(position - 1);
        Glide.with(contex).load(conEntity.getDiscussantIcon()).placeholder(R.mipmap.place_holder).into(holder2.iv_pic2);
        holder2.name2.setText(conEntity.getDiscussantName());
        holder2.content2.setText(conEntity.getContent());
        holder2.time2.setText(conEntity.getCreateTime());
        holder2.good2.setText(conEntity.getPraiseCount() + "");
        holder2.comment2.setText(conEntity.getDetailsCount() + "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private static class PlayRadioCommentHolder extends RecyclerView.ViewHolder {
        public CircleImageView iv_pic2;
        public TextView name2, content2, time2, good2, comment2;

        public PlayRadioCommentHolder(View itemView) {
            super(itemView);
            iv_pic2 = (CircleImageView) itemView.findViewById(R.id.mImageView_search_photo);
            name2 = (TextView) itemView.findViewById(R.id.radioplay_itemtwo_name);
            content2 = (TextView) itemView.findViewById(R.id.radioplay_itemtwo_content);
            time2 = (TextView) itemView.findViewById(R.id.riqi);
            good2 = (TextView) itemView.findViewById(R.id.dianzan_nshu);
            comment2 = (TextView) itemView.findViewById(R.id.pingln_shu);
        }
    }
}
